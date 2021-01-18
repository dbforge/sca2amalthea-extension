/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mode Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry#getStmtseq <em>Stmtseq</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getModeEntry()
 * @model
 * @generated
 */
public interface ModeEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Stmtseq</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stmtseq</em>' containment reference list.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getModeEntry_Stmtseq()
	 * @model containment="true"
	 * @generated
	 */
	EList<StmtCall> getStmtseq();

} // ModeEntry
