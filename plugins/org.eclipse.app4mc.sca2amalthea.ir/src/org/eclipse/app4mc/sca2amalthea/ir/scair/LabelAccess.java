/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Label Access</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getLabelAccess()
 * @model
 * @generated
 */
public interface LabelAccess extends StmtCall {
	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum
	 * @see #setAccess(AccessTypeEnum)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getLabelAccess_Access()
	 * @model
	 * @generated
	 */
	AccessTypeEnum getAccess();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum
	 * @see #getAccess()
	 * @generated
	 */
	void setAccess(AccessTypeEnum value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' reference.
	 * @see #setLabel(Label)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getLabelAccess_Label()
	 * @model required="true"
	 * @generated
	 */
	Label getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess#getLabel <em>Label</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(Label value);

} // LabelAccess
