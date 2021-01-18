/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair.impl;

import org.eclipse.app4mc.sca2amalthea.ir.scair.Function;
import org.eclipse.app4mc.sca2amalthea.ir.scair.FunctionCall;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionCallImpl#getCalls <em>Calls</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionCallImpl extends StmtCallImpl implements FunctionCall {
	/**
	 * The cached value of the '{@link #getCalls() <em>Calls</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCalls()
	 * @generated
	 * @ordered
	 */
	protected Function calls;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionCallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return scairPackage.Literals.FUNCTION_CALL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Function getCalls() {
		if (calls != null && calls.eIsProxy()) {
			InternalEObject oldCalls = (InternalEObject)calls;
			calls = (Function)eResolveProxy(oldCalls);
			if (calls != oldCalls) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, scairPackage.FUNCTION_CALL__CALLS, oldCalls, calls));
			}
		}
		return calls;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function basicGetCalls() {
		return calls;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCalls(Function newCalls) {
		Function oldCalls = calls;
		calls = newCalls;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.FUNCTION_CALL__CALLS, oldCalls, calls));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case scairPackage.FUNCTION_CALL__CALLS:
				if (resolve) return getCalls();
				return basicGetCalls();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case scairPackage.FUNCTION_CALL__CALLS:
				setCalls((Function)newValue);
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
			case scairPackage.FUNCTION_CALL__CALLS:
				setCalls((Function)null);
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
			case scairPackage.FUNCTION_CALL__CALLS:
				return calls != null;
		}
		return super.eIsSet(featureID);
	}

} //FunctionCallImpl
