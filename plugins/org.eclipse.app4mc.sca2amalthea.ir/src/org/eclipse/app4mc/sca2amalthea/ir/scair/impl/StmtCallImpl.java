/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair.impl;

import org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stmt Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.StmtCallImpl#getSrcline <em>Srcline</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.StmtCallImpl#getSrccol <em>Srccol</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class StmtCallImpl extends MinimalEObjectImpl.Container implements StmtCall {
	/**
	 * The default value of the '{@link #getSrcline() <em>Srcline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcline()
	 * @generated
	 * @ordered
	 */
	protected static final String SRCLINE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getSrcline() <em>Srcline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcline()
	 * @generated
	 * @ordered
	 */
	protected String srcline = SRCLINE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSrccol() <em>Srccol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrccol()
	 * @generated
	 * @ordered
	 */
	protected static final String SRCCOL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSrccol() <em>Srccol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrccol()
	 * @generated
	 * @ordered
	 */
	protected String srccol = SRCCOL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StmtCallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return scairPackage.Literals.STMT_CALL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSrcline() {
		return srcline;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSrcline(String newSrcline) {
		String oldSrcline = srcline;
		srcline = newSrcline;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.STMT_CALL__SRCLINE, oldSrcline, srcline));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSrccol() {
		return srccol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSrccol(String newSrccol) {
		String oldSrccol = srccol;
		srccol = newSrccol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.STMT_CALL__SRCCOL, oldSrccol, srccol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case scairPackage.STMT_CALL__SRCLINE:
				return getSrcline();
			case scairPackage.STMT_CALL__SRCCOL:
				return getSrccol();
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
			case scairPackage.STMT_CALL__SRCLINE:
				setSrcline((String)newValue);
				return;
			case scairPackage.STMT_CALL__SRCCOL:
				setSrccol((String)newValue);
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
			case scairPackage.STMT_CALL__SRCLINE:
				setSrcline(SRCLINE_EDEFAULT);
				return;
			case scairPackage.STMT_CALL__SRCCOL:
				setSrccol(SRCCOL_EDEFAULT);
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
			case scairPackage.STMT_CALL__SRCLINE:
				return SRCLINE_EDEFAULT == null ? srcline != null : !SRCLINE_EDEFAULT.equals(srcline);
			case scairPackage.STMT_CALL__SRCCOL:
				return SRCCOL_EDEFAULT == null ? srccol != null : !SRCCOL_EDEFAULT.equals(srccol);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (srcline: ");
		result.append(srcline);
		result.append(", srccol: ");
		result.append(srccol);
		result.append(')');
		return result.toString();
	}

} //StmtCallImpl
