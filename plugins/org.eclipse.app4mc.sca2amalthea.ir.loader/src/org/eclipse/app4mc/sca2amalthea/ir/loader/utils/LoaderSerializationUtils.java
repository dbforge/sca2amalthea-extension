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
package org.eclipse.app4mc.sca2amalthea.ir.loader.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLResource.XMLInfo;
import org.eclipse.emf.ecore.xmi.impl.XMLInfoImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;


/**
 * The loader and serializer util class
 */
public class LoaderSerializationUtils {


  /**
   * singleton
   */
  public static final LoaderSerializationUtils INSTANCE = new LoaderSerializationUtils();

  /**
   * @return {@link Map}
   */
  public Map<String, Object> xmlSettings() {
    Map<String, Object> saveOptions = new HashMap<String, Object>();

    XMLMapImpl map = new XMLMapImpl();

    XMLInfoImpl functionNameElement = new XMLInfoImpl();
    XMLInfoImpl containerElement = new XMLInfoImpl();
    XMLInfoImpl labelElement = new XMLInfoImpl();
    XMLInfoImpl functionElement = new XMLInfoImpl();
    XMLInfoImpl typeDefElement = new XMLInfoImpl();
    XMLInfoImpl typeMemberElement = new XMLInfoImpl();

    functionNameElement.setXMLRepresentation(XMLInfo.ELEMENT);
    containerElement.setXMLRepresentation(XMLInfo.ELEMENT);
    containerElement.setName("container");

    labelElement.setXMLRepresentation(XMLInfo.ELEMENT);
    labelElement.setName("label");
    functionElement.setName("function");
    functionElement.setXMLRepresentation(XMLInfo.ELEMENT);
    typeDefElement.setName("typedef");
    typeDefElement.setXMLRepresentation(XMLInfo.ELEMENT);
    typeMemberElement.setName("member");
    typeMemberElement.setXMLRepresentation(XMLInfo.ELEMENT);

    map.add(scairPackage.eINSTANCE.getIIdentifiable_Name(), functionNameElement);
    map.add(scairPackage.eINSTANCE.getProject_Containers(), containerElement);
    map.add(scairPackage.eINSTANCE.getProject_Labels(), labelElement);
    map.add(scairPackage.eINSTANCE.getFunction(), functionElement);
    map.add(scairPackage.eINSTANCE.getProject_Typedefs(), typeDefElement);
    map.add(scairPackage.eINSTANCE.getTypeDef_Members(), typeMemberElement);

    saveOptions.put(XMLResource.OPTION_XML_MAP, map); // TODO: Check if needed ->
                                                      // saveOptions.put(XMLResource.OPTION_PARSER_FEATURES, map);
    return saveOptions;
  }
}
