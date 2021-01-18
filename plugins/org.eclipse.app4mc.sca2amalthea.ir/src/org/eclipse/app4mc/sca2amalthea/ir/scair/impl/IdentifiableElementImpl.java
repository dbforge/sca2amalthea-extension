/**
 * *******************************************************************************
 *  Copyright (c) 2017 Robert Bosch GmbH and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *     Generated using Eclipse EMF
 * 
 * *******************************************************************************
 */ 
package org.eclipse.app4mc.sca2amalthea.ir.scair.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.eclipse.app4mc.sca2amalthea.ir.scair.IdentifiableElement;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Identifiable Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.IdentifiableElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.IdentifiableElementImpl#getUniqueName <em>Unique Name</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.IdentifiableElementImpl#getFile <em>File</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.IdentifiableElementImpl#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IdentifiableElementImpl extends MinimalEObjectImpl.Container implements IdentifiableElement {

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   *
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
  protected String name = NAME_EDEFAULT;

  /**
	 * The default value of the '{@link #getUniqueName() <em>Unique Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
   * end-user-doc -->
	 * @see #getUniqueName()
	 * @generated
	 * @ordered
	 */
  protected static final String UNIQUE_NAME_EDEFAULT = "";

  /**
	 * The default value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_EDEFAULT = null;

		/**
	 * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected String file = FILE_EDEFAULT;

		/**
	 * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_EDEFAULT = null;

		/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected String package_ = PACKAGE_EDEFAULT;

		/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  protected IdentifiableElementImpl() {
		super();
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  protected EClass eStaticClass() {
		return scairPackage.Literals.IDENTIFIABLE_ELEMENT;
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  public String getName() {
		return name;
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.IDENTIFIABLE_ELEMENT__NAME, oldName, name));
	}

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   *
   * @generated NOT
   */
  public String getUniqueName() {
    return computeUniqueName();
  }

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  public void setUniqueName(String newUniqueName) {
		// TODO: implement this method to set the 'Unique Name' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFile() {
		return file;
	}

		/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFile(String newFile) {
		String oldFile = file;
		file = newFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.IDENTIFIABLE_ELEMENT__FILE, oldFile, file));
	}

		/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackage() {
		return package_;
	}

		/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(String newPackage) {
		String oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, scairPackage.IDENTIFIABLE_ELEMENT__PACKAGE, oldPackage, package_));
	}

		/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  public String computeUniqueName() {
		
				String _name = this.getName();
				String _encode = this.encode(_name);
				String _plus = (_encode + "?type=");
				EClass _eClass = this.eClass();
				String _name_1 = _eClass.getName();
				return (_plus + _name_1);
		
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  public String encode(final String str) {
		
					String expr= null;
					boolean _isNullOrEmpty = (str == null || str.length() == 0);
					if (_isNullOrEmpty) {
						expr = "no-name-id";
					}
					else {
						String _string = StandardCharsets.UTF_8.toString();
						try {
							expr = URLEncoder.encode(str, _string);
						}
						catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return expr;
				
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case scairPackage.IDENTIFIABLE_ELEMENT__NAME:
				return getName();
			case scairPackage.IDENTIFIABLE_ELEMENT__UNIQUE_NAME:
				return getUniqueName();
			case scairPackage.IDENTIFIABLE_ELEMENT__FILE:
				return getFile();
			case scairPackage.IDENTIFIABLE_ELEMENT__PACKAGE:
				return getPackage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case scairPackage.IDENTIFIABLE_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case scairPackage.IDENTIFIABLE_ELEMENT__UNIQUE_NAME:
				setUniqueName((String)newValue);
				return;
			case scairPackage.IDENTIFIABLE_ELEMENT__FILE:
				setFile((String)newValue);
				return;
			case scairPackage.IDENTIFIABLE_ELEMENT__PACKAGE:
				setPackage((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public void eUnset(int featureID) {
		switch (featureID) {
			case scairPackage.IDENTIFIABLE_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case scairPackage.IDENTIFIABLE_ELEMENT__UNIQUE_NAME:
				setUniqueName(UNIQUE_NAME_EDEFAULT);
				return;
			case scairPackage.IDENTIFIABLE_ELEMENT__FILE:
				setFile(FILE_EDEFAULT);
				return;
			case scairPackage.IDENTIFIABLE_ELEMENT__PACKAGE:
				setPackage(PACKAGE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public boolean eIsSet(int featureID) {
		switch (featureID) {
			case scairPackage.IDENTIFIABLE_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case scairPackage.IDENTIFIABLE_ELEMENT__UNIQUE_NAME:
				return UNIQUE_NAME_EDEFAULT == null ? getUniqueName() != null : !UNIQUE_NAME_EDEFAULT.equals(getUniqueName());
			case scairPackage.IDENTIFIABLE_ELEMENT__FILE:
				return FILE_EDEFAULT == null ? file != null : !FILE_EDEFAULT.equals(file);
			case scairPackage.IDENTIFIABLE_ELEMENT__PACKAGE:
				return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
		}
		return super.eIsSet(featureID);
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case scairPackage.IDENTIFIABLE_ELEMENT___COMPUTE_UNIQUE_NAME:
				return computeUniqueName();
			case scairPackage.IDENTIFIABLE_ELEMENT___ENCODE__STRING:
				return encode((String)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

  /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", File: ");
		result.append(file);
		result.append(", Package: ");
		result.append(package_);
		result.append(')');
		return result.toString();
	}

} // IdentifiableElementImpl