/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair.impl;

import org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Label;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Label</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelImpl#getCat <em>Cat</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LabelImpl extends IdentifiableElementImpl implements Label {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCat() <em>Cat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCat()
	 * @generated
	 * @ordered
	 */
	protected static final ETypeCategory CAT_EDEFAULT = ETypeCategory.PRIMITIVE;

	/**
	 * The cached value of the '{@link #getCat() <em>Cat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCat()
	 * @generated
	 * @ordered
	 */
	protected ETypeCategory cat = CAT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return scairPackage.Literals.LABEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.LABEL__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ETypeCategory getCat() {
		return cat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCat(ETypeCategory newCat) {
		ETypeCategory oldCat = cat;
		cat = newCat == null ? CAT_EDEFAULT : newCat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.LABEL__CAT, oldCat, cat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case scairPackage.LABEL__TYPE:
				return getType();
			case scairPackage.LABEL__CAT:
				return getCat();
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
			case scairPackage.LABEL__TYPE:
				setType((String)newValue);
				return;
			case scairPackage.LABEL__CAT:
				setCat((ETypeCategory)newValue);
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
			case scairPackage.LABEL__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case scairPackage.LABEL__CAT:
				setCat(CAT_EDEFAULT);
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
			case scairPackage.LABEL__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case scairPackage.LABEL__CAT:
				return cat != CAT_EDEFAULT;
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
		result.append(", cat: ");
		result.append(cat);
		result.append(')');
		return result.toString();
	}

} //LabelImpl
