/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getStmtseq <em>Stmtseq</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getContainer <em>Container</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getSrcline <em>Srcline</em>}</li>
 *   <li>{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getSrccol <em>Srccol</em>}</li>
 * </ul>
 *
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getFunction()
 * @model
 * @generated
 */
public interface Function extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Stmtseq</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stmtseq</em>' containment reference list.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getFunction_Stmtseq()
	 * @model containment="true"
	 * @generated
	 */
	EList<StmtCall> getStmtseq();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum
	 * @see #setType(EFunctionTypeEnum)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getFunction_Type()
	 * @model
	 * @generated
	 */
	EFunctionTypeEnum getType();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum
	 * @see #getType()
	 * @generated
	 */
	void setType(EFunctionTypeEnum value);

	/**
	 * Returns the value of the '<em><b>Container</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Container#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container</em>' container reference.
	 * @see #setContainer(Container)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getFunction_Container()
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Container#getFunctions
	 * @model opposite="functions" transient="false"
	 * @generated
	 */
	Container getContainer();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getContainer <em>Container</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container</em>' container reference.
	 * @see #getContainer()
	 * @generated
	 */
	void setContainer(Container value);

	/**
	 * Returns the value of the '<em><b>Srcline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Srcline</em>' attribute.
	 * @see #setSrcline(String)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getFunction_Srcline()
	 * @model
	 * @generated
	 */
	String getSrcline();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getSrcline <em>Srcline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Srcline</em>' attribute.
	 * @see #getSrcline()
	 * @generated
	 */
	void setSrcline(String value);

	/**
	 * Returns the value of the '<em><b>Srccol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Srccol</em>' attribute.
	 * @see #setSrccol(String)
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage#getFunction_Srccol()
	 * @model
	 * @generated
	 */
	String getSrccol();

	/**
	 * Sets the value of the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getSrccol <em>Srccol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Srccol</em>' attribute.
	 * @see #getSrccol()
	 * @generated
	 */
	void setSrccol(String value);

} // Function
