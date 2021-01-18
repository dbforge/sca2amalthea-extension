/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair.impl;

import java.util.Collection;

import org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry;
import org.eclipse.app4mc.sca2amalthea.ir.scair.ModeSwitch;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mode Switch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeSwitchImpl#getModeentry <em>Modeentry</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModeSwitchImpl extends StmtCallImpl implements ModeSwitch {
	/**
	 * The cached value of the '{@link #getModeentry() <em>Modeentry</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModeentry()
	 * @generated
	 * @ordered
	 */
	protected EList<ModeEntry> modeentry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModeSwitchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return scairPackage.Literals.MODE_SWITCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ModeEntry> getModeentry() {
		if (modeentry == null) {
			modeentry = new EObjectContainmentEList<ModeEntry>(ModeEntry.class, this, scairPackage.MODE_SWITCH__MODEENTRY);
		}
		return modeentry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case scairPackage.MODE_SWITCH__MODEENTRY:
				return ((InternalEList<?>)getModeentry()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case scairPackage.MODE_SWITCH__MODEENTRY:
				return getModeentry();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case scairPackage.MODE_SWITCH__MODEENTRY:
				getModeentry().clear();
				getModeentry().addAll((Collection<? extends ModeEntry>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case scairPackage.MODE_SWITCH__MODEENTRY:
				getModeentry().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case scairPackage.MODE_SWITCH__MODEENTRY:
				return modeentry != null && !modeentry.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ModeSwitchImpl
