/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stmt Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall#getSrcline <em>Srcline</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall#getSrccol <em>Srccol</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getStmtCall()
 * @model abstract="true"
 * @generated
 */
public interface StmtCall extends EObject {
	/**
	 * Returns the value of the '<em><b>Srcline</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Srcline</em>' attribute.
	 * @see #setSrcline(String)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getStmtCall_Srcline()
	 * @model default=""
	 * @generated
	 */
	String getSrcline();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall#getSrcline <em>Srcline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Srcline</em>' attribute.
	 * @see #getSrcline()
	 * @generated
	 */
	void setSrcline(String value);

	/**
	 * Returns the value of the '<em><b>Srccol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Srccol</em>' attribute.
	 * @see #setSrccol(String)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getStmtCall_Srccol()
	 * @model
	 * @generated
	 */
	String getSrccol();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall#getSrccol <em>Srccol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Srccol</em>' attribute.
	 * @see #getSrccol()
	 * @generated
	 */
	void setSrccol(String value);

} // StmtCall
