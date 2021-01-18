/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getContainers <em>Containers</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getCallgraph <em>Callgraph</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getLabels <em>Labels</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getTypedefs <em>Typedefs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends EObject {
	/**
	 * Returns the value of the '<em><b>Containers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.app4mc.sca2amalthea.ir.scair.Container}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containers</em>' containment reference list.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getProject_Containers()
	 * @model containment="true" keys="name"
	 * @generated
	 */
	EList<Container> getContainers();

	/**
	 * Returns the value of the '<em><b>Callgraph</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.app4mc.sca2amalthea.ir.scair.CallGraph}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Callgraph</em>' reference list.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getProject_Callgraph()
	 * @model
	 * @generated
	 */
	EList<CallGraph> getCallgraph();

	/**
	 * Returns the value of the '<em><b>Labels</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.app4mc.sca2amalthea.ir.scair.Label}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Labels</em>' containment reference list.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getProject_Labels()
	 * @model containment="true"
	 * @generated
	 */
	EList<Label> getLabels();

	/**
	 * Returns the value of the '<em><b>Typedefs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typedefs</em>' containment reference list.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getProject_Typedefs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeDef> getTypedefs();

} // Project
