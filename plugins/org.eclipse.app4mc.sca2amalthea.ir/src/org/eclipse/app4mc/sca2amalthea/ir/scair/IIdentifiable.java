/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IIdentifiable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#getUniqueName <em>Unique Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getIIdentifiable()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IIdentifiable extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getIIdentifiable_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Unique Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique Name</em>' attribute.
	 * @see #setUniqueName(String)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getIIdentifiable_UniqueName()
	 * @model default="" id="true" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getUniqueName();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#getUniqueName <em>Unique Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique Name</em>' attribute.
	 * @see #getUniqueName()
	 * @generated
	 */
	void setUniqueName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String computeUniqueName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model strUnique="false"
	 * @generated
	 */
	String encode(String str);

} // IIdentifiable
