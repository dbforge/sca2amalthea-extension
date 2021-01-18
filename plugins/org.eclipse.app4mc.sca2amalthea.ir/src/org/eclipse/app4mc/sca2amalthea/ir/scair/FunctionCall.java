/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.FunctionCall#getCalls <em>Calls</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getFunctionCall()
 * @model
 * @generated
 */
public interface FunctionCall extends StmtCall {
	/**
	 * Returns the value of the '<em><b>Calls</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Calls</em>' reference.
	 * @see #setCalls(Function)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getFunctionCall_Calls()
	 * @model required="true"
	 * @generated
	 */
	Function getCalls();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.FunctionCall#getCalls <em>Calls</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Calls</em>' reference.
	 * @see #getCalls()
	 * @generated
	 */
	void setCalls(Function value);

} // FunctionCall
