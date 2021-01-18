/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>EType Category</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getETypeCategory()
 * @model
 * @generated
 */
public enum ETypeCategory implements Enumerator {
	/**
	 * The '<em><b>PRIMITIVE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIMITIVE_VALUE
	 * @generated
	 * @ordered
	 */
	PRIMITIVE(0, "PRIMITIVE", "PRIMITIVE"),

	/**
	 * The '<em><b>POINTER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POINTER_VALUE
	 * @generated
	 * @ordered
	 */
	POINTER(1, "POINTER", "POINTER"),

	/**
	 * The '<em><b>ARRAY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ARRAY_VALUE
	 * @generated
	 * @ordered
	 */
	ARRAY(2, "ARRAY", "ARRAY"),

	/**
	 * The '<em><b>STRUCT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRUCT_VALUE
	 * @generated
	 * @ordered
	 */
	STRUCT(3, "STRUCT", "STRUCT");

	/**
	 * The '<em><b>PRIMITIVE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIMITIVE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PRIMITIVE_VALUE = 0;

	/**
	 * The '<em><b>POINTER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POINTER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int POINTER_VALUE = 1;

	/**
	 * The '<em><b>ARRAY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ARRAY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ARRAY_VALUE = 2;

	/**
	 * The '<em><b>STRUCT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRUCT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int STRUCT_VALUE = 3;

	/**
	 * An array of all the '<em><b>EType Category</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ETypeCategory[] VALUES_ARRAY =
		new ETypeCategory[] {
			PRIMITIVE,
			POINTER,
			ARRAY,
			STRUCT,
		};

	/**
	 * A public read-only list of all the '<em><b>EType Category</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ETypeCategory> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>EType Category</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ETypeCategory get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ETypeCategory result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EType Category</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ETypeCategory getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ETypeCategory result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EType Category</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ETypeCategory get(int value) {
		switch (value) {
			case PRIMITIVE_VALUE: return PRIMITIVE;
			case POINTER_VALUE: return POINTER;
			case ARRAY_VALUE: return ARRAY;
			case STRUCT_VALUE: return STRUCT;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ETypeCategory(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ETypeCategory
