/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair.impl;

import java.util.Collection;

import org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Function;
import org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl#getStmtseq <em>Stmtseq</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl#getContainer <em>Container</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl#getSrcline <em>Srcline</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl#getSrccol <em>Srccol</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionImpl extends IdentifiableElementImpl implements Function {
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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final EFunctionTypeEnum TYPE_EDEFAULT = EFunctionTypeEnum.RUNNABLE;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EFunctionTypeEnum type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSrcline() <em>Srcline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcline()
	 * @generated
	 * @ordered
	 */
	protected static final String SRCLINE_EDEFAULT = null;

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
	protected FunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return scairPackage.Literals.FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<StmtCall> getStmtseq() {
		if (stmtseq == null) {
			stmtseq = new EObjectContainmentEList<StmtCall>(StmtCall.class, this, scairPackage.FUNCTION__STMTSEQ);
		}
		return stmtseq;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EFunctionTypeEnum getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(EFunctionTypeEnum newType) {
		EFunctionTypeEnum oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.FUNCTION__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.app4mc.sca2amalthea.ir.scair.Container getContainer() {
		if (eContainerFeatureID() != scairPackage.FUNCTION__CONTAINER) return null;
		return (org.eclipse.app4mc.sca2amalthea.ir.scair.Container)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainer(org.eclipse.app4mc.sca2amalthea.ir.scair.Container newContainer, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newContainer, scairPackage.FUNCTION__CONTAINER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContainer(org.eclipse.app4mc.sca2amalthea.ir.scair.Container newContainer) {
		if (newContainer != eInternalContainer() || (eContainerFeatureID() != scairPackage.FUNCTION__CONTAINER && newContainer != null)) {
			if (EcoreUtil.isAncestor(this, newContainer))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContainer != null)
				msgs = ((InternalEObject)newContainer).eInverseAdd(this, scairPackage.CONTAINER__FUNCTIONS, org.eclipse.app4mc.sca2amalthea.ir.scair.Container.class, msgs);
			msgs = basicSetContainer(newContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.FUNCTION__CONTAINER, newContainer, newContainer));
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
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.FUNCTION__SRCLINE, oldSrcline, srcline));
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
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.FUNCTION__SRCCOL, oldSrccol, srccol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case scairPackage.FUNCTION__CONTAINER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetContainer((org.eclipse.app4mc.sca2amalthea.ir.scair.Container)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case scairPackage.FUNCTION__STMTSEQ:
				return ((InternalEList<?>)getStmtseq()).basicRemove(otherEnd, msgs);
			case scairPackage.FUNCTION__CONTAINER:
				return basicSetContainer(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case scairPackage.FUNCTION__CONTAINER:
				return eInternalContainer().eInverseRemove(this, scairPackage.CONTAINER__FUNCTIONS, org.eclipse.app4mc.sca2amalthea.ir.scair.Container.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case scairPackage.FUNCTION__STMTSEQ:
				return getStmtseq();
			case scairPackage.FUNCTION__TYPE:
				return getType();
			case scairPackage.FUNCTION__CONTAINER:
				return getContainer();
			case scairPackage.FUNCTION__SRCLINE:
				return getSrcline();
			case scairPackage.FUNCTION__SRCCOL:
				return getSrccol();
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
			case scairPackage.FUNCTION__STMTSEQ:
				getStmtseq().clear();
				getStmtseq().addAll((Collection<? extends StmtCall>)newValue);
				return;
			case scairPackage.FUNCTION__TYPE:
				setType((EFunctionTypeEnum)newValue);
				return;
			case scairPackage.FUNCTION__CONTAINER:
				setContainer((org.eclipse.app4mc.sca2amalthea.ir.scair.Container)newValue);
				return;
			case scairPackage.FUNCTION__SRCLINE:
				setSrcline((String)newValue);
				return;
			case scairPackage.FUNCTION__SRCCOL:
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
			case scairPackage.FUNCTION__STMTSEQ:
				getStmtseq().clear();
				return;
			case scairPackage.FUNCTION__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case scairPackage.FUNCTION__CONTAINER:
				setContainer((org.eclipse.app4mc.sca2amalthea.ir.scair.Container)null);
				return;
			case scairPackage.FUNCTION__SRCLINE:
				setSrcline(SRCLINE_EDEFAULT);
				return;
			case scairPackage.FUNCTION__SRCCOL:
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
			case scairPackage.FUNCTION__STMTSEQ:
				return stmtseq != null && !stmtseq.isEmpty();
			case scairPackage.FUNCTION__TYPE:
				return type != TYPE_EDEFAULT;
			case scairPackage.FUNCTION__CONTAINER:
				return getContainer() != null;
			case scairPackage.FUNCTION__SRCLINE:
				return SRCLINE_EDEFAULT == null ? srcline != null : !SRCLINE_EDEFAULT.equals(srcline);
			case scairPackage.FUNCTION__SRCCOL:
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
		result.append(" (type: ");
		result.append(type);
		result.append(", srcline: ");
		result.append(srcline);
		result.append(", srccol: ");
		result.append(srccol);
		result.append(')');
		return result.toString();
	}

} //FunctionImpl
