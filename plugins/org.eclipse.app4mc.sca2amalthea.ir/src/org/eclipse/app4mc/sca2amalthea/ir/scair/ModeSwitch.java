/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mode Switch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ModeSwitch#getModeentry <em>Modeentry</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getModeSwitch()
 * @model
 * @generated
 */
public interface ModeSwitch extends StmtCall {
	/**
	 * Returns the value of the '<em><b>Modeentry</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modeentry</em>' containment reference list.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getModeSwitch_Modeentry()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModeEntry> getModeentry();

} // ModeSwitch
