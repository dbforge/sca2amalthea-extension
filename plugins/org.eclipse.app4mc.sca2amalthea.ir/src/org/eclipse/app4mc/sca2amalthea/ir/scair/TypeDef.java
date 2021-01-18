/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Def</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getCat <em>Cat</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getMembers <em>Members</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getTypeDef()
 * @model
 * @generated
 */
public interface TypeDef extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getTypeDef_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getTypeDef_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Cat</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cat</em>' attribute.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory
	 * @see #setCat(ETypeCategory)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getTypeDef_Cat()
	 * @model required="true"
	 * @generated
	 */
	ETypeCategory getCat();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getCat <em>Cat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cat</em>' attribute.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory
	 * @see #getCat()
	 * @generated
	 */
	void setCat(ETypeCategory value);

	/**
	 * Returns the value of the '<em><b>Members</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Members</em>' containment reference list.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getTypeDef_Members()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeDefMember> getMembers();

} // TypeDef
