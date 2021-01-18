/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair.impl;

import java.util.Collection;

import org.eclipse.app4mc.sca2amalthea.ir.scair.CallGraph;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Label;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Project;
import org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ProjectImpl#getContainers <em>Containers</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ProjectImpl#getCallgraph <em>Callgraph</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ProjectImpl#getLabels <em>Labels</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ProjectImpl#getTypedefs <em>Typedefs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends MinimalEObjectImpl.Container implements Project {
	/**
	 * The cached value of the '{@link #getContainers() <em>Containers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainers()
	 * @generated
	 * @ordered
	 */
	protected EList<org.eclipse.app4mc.sca2amalthea.ir.scair.Container> containers;

	/**
	 * The cached value of the '{@link #getCallgraph() <em>Callgraph</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallgraph()
	 * @generated
	 * @ordered
	 */
	protected EList<CallGraph> callgraph;

	/**
	 * The cached value of the '{@link #getLabels() <em>Labels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabels()
	 * @generated
	 * @ordered
	 */
	protected EList<Label> labels;

	/**
	 * The cached value of the '{@link #getTypedefs() <em>Typedefs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedefs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeDef> typedefs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return scairPackage.Literals.PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<org.eclipse.app4mc.sca2amalthea.ir.scair.Container> getContainers() {
		if (containers == null) {
			containers = new EObjectContainmentEList<org.eclipse.app4mc.sca2amalthea.ir.scair.Container>(org.eclipse.app4mc.sca2amalthea.ir.scair.Container.class, this, scairPackage.PROJECT__CONTAINERS);
		}
		return containers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CallGraph> getCallgraph() {
		if (callgraph == null) {
			callgraph = new EObjectResolvingEList<CallGraph>(CallGraph.class, this, scairPackage.PROJECT__CALLGRAPH);
		}
		return callgraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Label> getLabels() {
		if (labels == null) {
			labels = new EObjectContainmentEList<Label>(Label.class, this, scairPackage.PROJECT__LABELS);
		}
		return labels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeDef> getTypedefs() {
		if (typedefs == null) {
			typedefs = new EObjectContainmentEList<TypeDef>(TypeDef.class, this, scairPackage.PROJECT__TYPEDEFS);
		}
		return typedefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case scairPackage.PROJECT__CONTAINERS:
				return ((InternalEList<?>)getContainers()).basicRemove(otherEnd, msgs);
			case scairPackage.PROJECT__LABELS:
				return ((InternalEList<?>)getLabels()).basicRemove(otherEnd, msgs);
			case scairPackage.PROJECT__TYPEDEFS:
				return ((InternalEList<?>)getTypedefs()).basicRemove(otherEnd, msgs);
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
			case scairPackage.PROJECT__CONTAINERS:
				return getContainers();
			case scairPackage.PROJECT__CALLGRAPH:
				return getCallgraph();
			case scairPackage.PROJECT__LABELS:
				return getLabels();
			case scairPackage.PROJECT__TYPEDEFS:
				return getTypedefs();
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
			case scairPackage.PROJECT__CONTAINERS:
				getContainers().clear();
				getContainers().addAll((Collection<? extends org.eclipse.app4mc.sca2amalthea.ir.scair.Container>)newValue);
				return;
			case scairPackage.PROJECT__CALLGRAPH:
				getCallgraph().clear();
				getCallgraph().addAll((Collection<? extends CallGraph>)newValue);
				return;
			case scairPackage.PROJECT__LABELS:
				getLabels().clear();
				getLabels().addAll((Collection<? extends Label>)newValue);
				return;
			case scairPackage.PROJECT__TYPEDEFS:
				getTypedefs().clear();
				getTypedefs().addAll((Collection<? extends TypeDef>)newValue);
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
			case scairPackage.PROJECT__CONTAINERS:
				getContainers().clear();
				return;
			case scairPackage.PROJECT__CALLGRAPH:
				getCallgraph().clear();
				return;
			case scairPackage.PROJECT__LABELS:
				getLabels().clear();
				return;
			case scairPackage.PROJECT__TYPEDEFS:
				getTypedefs().clear();
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
			case scairPackage.PROJECT__CONTAINERS:
				return containers != null && !containers.isEmpty();
			case scairPackage.PROJECT__CALLGRAPH:
				return callgraph != null && !callgraph.isEmpty();
			case scairPackage.PROJECT__LABELS:
				return labels != null && !labels.isEmpty();
			case scairPackage.PROJECT__TYPEDEFS:
				return typedefs != null && !typedefs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProjectImpl
