/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.exporter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.Array;
import org.eclipse.app4mc.amalthea.model.BaseTypeDefinition;
import org.eclipse.app4mc.amalthea.model.DataType;
import org.eclipse.app4mc.amalthea.model.DataTypeDefinition;
import org.eclipse.app4mc.amalthea.model.Pointer;
import org.eclipse.app4mc.amalthea.model.SWModel;
import org.eclipse.app4mc.amalthea.model.StringObject;
import org.eclipse.app4mc.amalthea.model.Struct;
import org.eclipse.app4mc.amalthea.model.StructEntry;
import org.eclipse.app4mc.amalthea.model.TypeDefinition;
import org.eclipse.app4mc.amalthea.model.TypeRef;
import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.logging.util.LogUtil;
import org.eclipse.app4mc.sca2amalthea.exporter.util.LLVMLogUtil;
import org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Label;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Project;
import org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef;
import org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResource;
import org.eclipse.emf.common.util.EList;


/**
 * This is a helper class to transform the LLVM type specific information to the AMALTHEA model.
 */
public class SwModelTypeDefTransformer {

  private final Map<String, TypeDefinition> typeDefMap = new HashMap<String, TypeDefinition>();
  private static final String CONST_PATTERN = ".*const.*";
  private static final String VOLATILE_PATTERN = ".*volatile.*";
  private static final String ENUM_PATTERN = "enum\\s*(\\w*)";
  @SuppressWarnings("unused")
  private final Pattern enumPattern;
  // arrays
  private static final String ARRAY_PATTERN = "(struct)*\\s*(\\w+)\\s+(\\*)*(const)*\\s*\\[(\\d*)\\](\\[(\\d*)\\])*"; // This
  // supports 2
  // dimensional
  private final Pattern arrayPattern;
  private static final String POINTER_PATTERN = "(struct)*\\s*(\\w+)\\s*\\**(const)*";
  private final Pattern pointerPattern;
  private static final String FUNCTION_POINTER_PATTERN = ".*\\(\\*(const)*\\).*"; // Function Pointer that contains (*)
  private final Pattern functionPointerPattern;

  private static final String CONSTANT_LITERAL = ", category=";

  /**
   * Default constructor which intitializes the Patterns
   */
  public SwModelTypeDefTransformer() {
    super();
    this.enumPattern = Pattern.compile(ENUM_PATTERN);
    this.arrayPattern = Pattern.compile(ARRAY_PATTERN);
    this.pointerPattern = Pattern.compile(POINTER_PATTERN);
    this.functionPointerPattern = Pattern.compile(FUNCTION_POINTER_PATTERN);
  }


  /**
   * @return the typeDefMap
   */
  public Map<String, TypeDefinition> getTypeDefMap() {
    return this.typeDefMap;
  }


  /**
   * @param resource SCAIR resource
   * @param swModel AMALTHEA Model
   */
  public void transform(final SCAResource resource, final SWModel swModel) {
    EList<TypeDef> types = ((Project) resource.getContents().get(0)).getTypedefs();
    EList<TypeDefinition> amtypes = swModel.getTypeDefinitions();


    // first iteration transforms the top level of a type definition
    for (TypeDef type : types) {
      TypeDefinition amaltheaTypeDefinition = transformTopLevelTypeDefinition(type);
      this.typeDefMap.put(type.getName(), amaltheaTypeDefinition);
      amtypes.add(amaltheaTypeDefinition);
    }

    createBaseTypesFromCSpecification(amtypes, this.typeDefMap);

    // second iterations fills the type definition internals that need to have access to all defined type
    for (TypeDef type : types) {
      try {
        transformTypeDefinitionInternals(type, this.typeDefMap);
      }
      catch (Exception e) {
        LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "**TypeDef transformation problem" + type.getName(),
            this.getClass(), Activator.PLUGIN_ID);
        LogUtil.logException(LLVMLogUtil.LOG_MSG_ID, e.getClass(), e, Activator.PLUGIN_ID);
      }
    }
  }

  /**
   * This class transforms the type of a Label from the SCAIR to the AMALTHEA Label
   *
   * @param amLabel AMALTHEA label for which the type is transformed
   * @param label SCAIR label that is transformed into an AMALTHEA Label
   */
  public void transformTypeDefitionToLabel(final org.eclipse.app4mc.amalthea.model.Label amLabel, final Label label) {

    // first remove the const or volatile string from the type, which is always at the first position (e.g. const
    // volatile int *)
    transformConstVolatile(label.getType(), amLabel);
    String remainingString = removeConstVolatile(label.getType());

    // if the remaining string is only one word then check for a reference in the typdef list
    if (remainingString.matches("\\w+") || remainingString.endsWith(" *")) {
      if (remainingString.endsWith(" *")) {
        remainingString = remainingString.substring(0, remainingString.length() - 2);
      }
      TypeDefinition amType = this.typeDefMap.get(remainingString);
      if (amType != null) {
        TypeRef typeRef = AmaltheaFactory.eINSTANCE.createTypeRef();
        typeRef.setTypeDef(amType);
        amLabel.setDataType(typeRef);
        return;
      }
    }

    switch (label.getCat()) {
      case STRUCT:
        debugLogStruct(label, remainingString);
        Struct amStruct = AmaltheaFactory.eINSTANCE.createStruct();
        amLabel.setDataType(amStruct);
        break;
      case PRIMITIVE:
        // these should be the base C types, such as "unsigned char" that do not have a typedef
        LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
            "**Label** Warning primitive typedef not found, type string=" + remainingString + CONSTANT_LITERAL +
                label.getCat(),
            this.getClass(), Activator.PLUGIN_ID);
        break;
      case ARRAY:
        Array amArray = AmaltheaFactory.eINSTANCE.createArray();
        amLabel.setDataType(amArray);
        // the array string need to be parsed
        transformArray(remainingString, amArray, this.typeDefMap);
        break;
      case POINTER:
        Pointer amPointer = AmaltheaFactory.eINSTANCE.createPointer();
        amLabel.setDataType(amPointer);
        // the pointer string need to be parsed
        transformPointer(remainingString, amPointer, this.typeDefMap);
        break;

      default:
        LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
            "**Label** Warning unknown category, type string=" + remainingString + CONSTANT_LITERAL + label.getCat(),
            this.getClass(), Activator.PLUGIN_ID);
        break;
    }
  }


  /**
   * @param label
   * @param remainingString
   */
  private void debugLogStruct(final Label label, final String remainingString) {
    LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
        "**Label** Warning Struct should be defined as a typedef, type string=" + remainingString + CONSTANT_LITERAL +
            label.getCat(),
        this.getClass(), Activator.PLUGIN_ID);
  }

  private void transformConstVolatile(final String typeString, final org.eclipse.app4mc.amalthea.model.Label amLabel) {

    if (typeString.matches(CONST_PATTERN)) {
      amLabel.setConstant(true);
    }
    if (typeString.matches(VOLATILE_PATTERN)) {
      amLabel.setBVolatile(true);
    }
  }

  private String removeConstVolatile(final String typeString) {
    if (typeString == null) {
      return null;
    }
    return typeString.replaceAll("const", "").replaceAll("volatile", "").trim();
  }


  private void transformArray(final String typeString, final Array amArray, final Map<String, TypeDefinition> typeMap) {
    Matcher matcher = this.arrayPattern.matcher(typeString);
    if (matcher.matches()) {
      // Comment to show which is the matching group: String structArray = matcher.group(1);
      String arrayType = matcher.group(2);
      String pointerStr = matcher.group(3);
      // Comment to show which is the matching group: String constStr = matcher.group(4);
      String sizeStr = matcher.group(5);
      String arrayOfArray = matcher.group(6);
      String arrayOfArraySize = matcher.group(7);

      setNumberOfElements(sizeStr, amArray);

      Array amArray2 = null;
      if (arrayOfArray != null) {
        // case of 2 dimensional array
        amArray2 = AmaltheaFactory.eINSTANCE.createArray();
        amArray.setDataType(amArray2);
        setNumberOfElements(arrayOfArraySize, amArray2);
      }

      TypeDefinition amType = typeMap.get(arrayType);
      DataType arrayDataType;

      if (amType != null) {
        TypeRef typeRef = AmaltheaFactory.eINSTANCE.createTypeRef();
        typeRef.setTypeDef(amType);

        if (pointerStr != null) {
          Pointer amPointer = AmaltheaFactory.eINSTANCE.createPointer();
          amPointer.setDataType(typeRef);
          arrayDataType = amPointer;
        }
        else {
          arrayDataType = typeRef;
        }

        if (amArray2 != null) {
          amArray2.setDataType(arrayDataType);
        }
        else {
          amArray.setDataType(arrayDataType);
        }
      }
      else {
        // TODO: array of a C-base type or others?
        LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
            "Warning No type reference of the Array, type string=" + typeString, this.getClass(), Activator.PLUGIN_ID);
      }
    }
    else {
      LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG,
          "Warning these Array pattern did not match, type string=" + typeString, this.getClass(), Activator.PLUGIN_ID);
    }
  }

  private void setNumberOfElements(final String sizeStr, final Array amArray) {
    int size = 0;
    if (sizeStr != null) {
      try {
        size = Integer.parseInt(sizeStr);
      }
      catch (NumberFormatException e) {
        // ignore, do not set size
      }
    }
    if (size > 0) {
      amArray.setNumberElements(size);
    }
  }

  private void transformPointer(final String typeString, final Pointer amPointer,
      final Map<String, TypeDefinition> typeMap) {


    if (typeString.length() == 0) {
      LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "Pointer could not be parsed, type string=" + typeString,
          this.getClass(), Activator.PLUGIN_ID);
      return;
    }

    Matcher matcher;
    Matcher fpmatcher;
    try {
      matcher = this.pointerPattern.matcher(typeString.replaceAll("\\*\\s+", "*")); // the current pointer pattern was
                                                                                    // not supporting if the type had
                                                                                    // multipe * separated by spaces.For
                                                                                    // eg int * * *.To
                                                                                    // accomodate that
                                                                                    // typeString.replaceAll("\\*\\s+",
                                                                                    // "*") is incorporated.
      fpmatcher = this.functionPointerPattern.matcher(typeString);
    }
    catch (Exception e) {
      // TODO: check PVER why it fails sometimes
      LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.INFO,
          "Pointer could not be parsed, exception during matching, type string=" + typeString, this.getClass(),
          Activator.PLUGIN_ID);
      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
      return;
    }
    if (matcher.matches()) {
      // Hint: "unit8 *cont" const pointer cannot be transfered top AMALTHEA

      String pointerType = matcher.group(2);
      TypeDefinition amSubType = typeMap.get(pointerType);
      if (amSubType != null) {
        TypeRef typeRef = AmaltheaFactory.eINSTANCE.createTypeRef();
        typeRef.setTypeDef(amSubType);
        amPointer.setDataType(typeRef);
      }
      else {
        // TODO: log warning, pointer to no reference?
        LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "Pointer to not found reference, type string=" + typeString,
            this.getClass(), Activator.PLUGIN_ID);
      }
    }
    else if (fpmatcher.matches()) {
      // add custom property
      StringObject pointerValue = AmaltheaFactory.eINSTANCE.createStringObject();
      pointerValue.setValue(typeString);
      amPointer.getCustomProperties().put("PointerType", pointerValue);
    }
    else {
      LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "Did not match Pointer pattern, type string=" + typeString,
          this.getClass(), Activator.PLUGIN_ID);
    }
  }

  /**
   * Transforms the type given by SCA to an AMALTHEA type definition. Only create the types with no details and childs.
   * This will be done in the second iteration.
   *
   * @param type of the SCAIR
   * @return the newly created type definition
   */
  public TypeDefinition transformTopLevelTypeDefinition(final TypeDef type) {
    TypeDefinition amaltheaTypeDefinition;
    if (type.getCat() == ETypeCategory.PRIMITIVE) {
      amaltheaTypeDefinition = AmaltheaFactory.eINSTANCE.createBaseTypeDefinition();
      amaltheaTypeDefinition.setName(type.getName());
    }
    else {
      amaltheaTypeDefinition = AmaltheaFactory.eINSTANCE.createDataTypeDefinition();
      amaltheaTypeDefinition.setName(type.getName());
    }
    return amaltheaTypeDefinition;
  }

  /**
   * Creates all basic types of the C language, e.g. void, int
   *
   * @param amtypes types list of the AMALTHEA sw model
   * @param typeMap types map of all defined types within the AMALTHEA model
   */
  public void createBaseTypesFromCSpecification(final EList<TypeDefinition> amtypes,
      final Map<String, TypeDefinition> typeMap) {
    createBaseTypesFromCSpecification("void", amtypes, typeMap);
    createBaseTypesFromCSpecification("enum", amtypes, typeMap);
    createBaseTypesFromCSpecification("char", amtypes, typeMap);
    createBaseTypesFromCSpecification("int", amtypes, typeMap);
    createBaseTypesFromCSpecification("float", amtypes, typeMap);
    createBaseTypesFromCSpecification("double", amtypes, typeMap);
  }

  private void createBaseTypesFromCSpecification(final String typeName, final EList<TypeDefinition> amtypes,
      final Map<String, TypeDefinition> typeMap) {

    if (typeMap.get(typeName) == null) {
      BaseTypeDefinition baseTypeDefinition = AmaltheaFactory.eINSTANCE.createBaseTypeDefinition();
      baseTypeDefinition.setName(typeName);
      typeMap.put(typeName, baseTypeDefinition);
      amtypes.add(baseTypeDefinition);
    }
  }

  /**
   * Transforms the type given by SCA to an AMALTHEA type definition
   *
   * @param type of the SCAIR
   * @param typeMap contains all created AMALTHEA type definitions from the previous step
   */
  public void transformTypeDefinitionInternals(final TypeDef type, final Map<String, TypeDefinition> typeMap) {
    TypeDefinition amaltheaTypeDefinition = typeMap.get(type.getName());
    if (amaltheaTypeDefinition instanceof BaseTypeDefinition) {
      // nothing to be transformed for Basetype expect size, but this is HW dependent
    }
    else {
      String typeString = type.getType();
      // just remove "const", cannot be transformed into AMALTHEA type
      typeString = removeConstVolatile(typeString);

      DataTypeDefinition dataTypeDefinition = (DataTypeDefinition) amaltheaTypeDefinition;
      switch (type.getCat()) {
        case STRUCT:
          transformStruct(type, dataTypeDefinition, typeMap);
          break;
        case ARRAY:
          Array amArray = AmaltheaFactory.eINSTANCE.createArray();
          dataTypeDefinition.setDataType(amArray);
          transformArray(typeString, amArray, typeMap);
          break;
        case POINTER:
          Pointer amPointer = AmaltheaFactory.eINSTANCE.createPointer();
          dataTypeDefinition.setDataType(amPointer);
          transformPointer(typeString, amPointer, typeMap);
          break;

        default:
          // log other primitives as warning, this would result in an NullPointerEx
          LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.WARNING,
              "**TypeDef** Type with different category, type string=" + type.getType() + CONSTANT_LITERAL +
                  type.getCat(),
              this.getClass(), Activator.PLUGIN_ID);
          break;
      }
    }
  }

  private void transformStruct(final TypeDef type, final DataTypeDefinition amType,
      final Map<String, TypeDefinition> typeMap) {
    Struct amStruct = AmaltheaFactory.eINSTANCE.createStruct();
    amType.setDataType(amStruct);

    EList<TypeDefMember> memberList = type.getMembers();
    for (TypeDefMember member : memberList) {
      StructEntry amStructEntry = AmaltheaFactory.eINSTANCE.createStructEntry();
      amStructEntry.setName(member.getName());
      amStruct.getEntries().add(amStructEntry);

      String typeString = member.getType();
      // just remove "const", cannot be transformed into AMALTHEA type
      typeString = removeConstVolatile(typeString);

      TypeDefinition memberType = typeMap.get(typeString);
      if (memberType != null) {
        TypeRef typeRef = AmaltheaFactory.eINSTANCE.createTypeRef();
        typeRef.setTypeDef(memberType);
        amStructEntry.setDataType(typeRef);
      }
      else {
        switch (member.getCat()) {
          case ARRAY:
            Array amArray = AmaltheaFactory.eINSTANCE.createArray();
            amStructEntry.setDataType(amArray);
            break;
          case POINTER:
            Pointer amPointer = AmaltheaFactory.eINSTANCE.createPointer();
            amStructEntry.setDataType(amPointer);
            break;

          default:
            LogUtil.log(LLVMLogUtil.LOG_MSG_ID, Severity.DEBUG, "Struct member without type reference, type string=" +
                member.getType() + ", category=" + member.getCat(), this.getClass(), Activator.PLUGIN_ID);
            break;
        }
      }
    }
  }

}
