/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair.impl;

import java.util.Collection;

import org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry;
import org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mode Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeEntryImpl#getStmtseq <em>Stmtseq</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModeEntryImpl extends MinimalEObjectImpl.Container implements ModeEntry {
	/**
	 * The cached value of the '{@link #getStmtseq() <em>Stmtseq</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStmtseq()
	 * @generated
	 * @ordered
	 */
	protected EList<StmtCall> stmtseq;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModeEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return scairPackage.Literals.MODE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<StmtCall> getStmtseq() {
		if (stmtseq == null) {
			stmtseq = new EObjectContainmentEList<StmtCall>(StmtCall.class, this, scairPackage.MODE_ENTRY__STMTSEQ);
		}
		return stmtseq;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case scairPackage.MODE_ENTRY__STMTSEQ:
				return ((InternalEList<?>)getStmtseq()).basicRemove(otherEnd, msgs);
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
			case scairPackage.MODE_ENTRY__STMTSEQ:
				return getStmtseq();
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
			case scairPackage.MODE_ENTRY__STMTSEQ:
				getStmtseq().clear();
				getStmtseq().addAll((Collection<? extends StmtCall>)newValue);
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
			case scairPackage.MODE_ENTRY__STMTSEQ:
				getStmtseq().clear();
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
			case scairPackage.MODE_ENTRY__STMTSEQ:
				return stmtseq != null && !stmtseq.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ModeEntryImpl
