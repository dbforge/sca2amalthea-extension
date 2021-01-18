/**
 */
package org.eclipse.app4mc.sca2amalthea.ir.scair;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.scairFactory
 * @model kind="package"
 * @generated
 */
public interface scairPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "scair";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.app4mc.scair.core.model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "scair";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	scairPackage eINSTANCE = org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable <em>IIdentifiable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getIIdentifiable()
	 * @generated
	 */
	int IIDENTIFIABLE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IIDENTIFIABLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Unique Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IIDENTIFIABLE__UNIQUE_NAME = 1;

	/**
	 * The number of structural features of the '<em>IIdentifiable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IIDENTIFIABLE_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Compute Unique Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IIDENTIFIABLE___COMPUTE_UNIQUE_NAME = 0;

	/**
	 * The operation id for the '<em>Encode</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IIDENTIFIABLE___ENCODE__STRING = 1;

	/**
	 * The number of operations of the '<em>IIdentifiable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IIDENTIFIABLE_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.IdentifiableElementImpl <em>Identifiable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.IdentifiableElementImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getIdentifiableElement()
	 * @generated
	 */
	int IDENTIFIABLE_ELEMENT = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT__NAME = IIDENTIFIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Unique Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT__UNIQUE_NAME = IIDENTIFIABLE__UNIQUE_NAME;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT__FILE = IIDENTIFIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT__PACKAGE = IIDENTIFIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Identifiable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT_FEATURE_COUNT = IIDENTIFIABLE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Compute Unique Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT___COMPUTE_UNIQUE_NAME = IIDENTIFIABLE___COMPUTE_UNIQUE_NAME;

	/**
	 * The operation id for the '<em>Encode</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT___ENCODE__STRING = IIDENTIFIABLE___ENCODE__STRING;

	/**
	 * The number of operations of the '<em>Identifiable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT_OPERATION_COUNT = IIDENTIFIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = IDENTIFIABLE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Unique Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__UNIQUE_NAME = IDENTIFIABLE_ELEMENT__UNIQUE_NAME;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__FILE = IDENTIFIABLE_ELEMENT__FILE;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__PACKAGE = IDENTIFIABLE_ELEMENT__PACKAGE;

	/**
	 * The feature id for the '<em><b>Stmtseq</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__STMTSEQ = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__TYPE = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__CONTAINER = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Srcline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__SRCLINE = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Srccol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__SRCCOL = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Compute Unique Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION___COMPUTE_UNIQUE_NAME = IDENTIFIABLE_ELEMENT___COMPUTE_UNIQUE_NAME;

	/**
	 * The operation id for the '<em>Encode</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION___ENCODE__STRING = IDENTIFIABLE_ELEMENT___ENCODE__STRING;

	/**
	 * The number of operations of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERATION_COUNT = IDENTIFIABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelImpl <em>Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getLabel()
	 * @generated
	 */
	int LABEL = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__NAME = IDENTIFIABLE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Unique Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__UNIQUE_NAME = IDENTIFIABLE_ELEMENT__UNIQUE_NAME;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__FILE = IDENTIFIABLE_ELEMENT__FILE;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__PACKAGE = IDENTIFIABLE_ELEMENT__PACKAGE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__TYPE = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__CAT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Compute Unique Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL___COMPUTE_UNIQUE_NAME = IDENTIFIABLE_ELEMENT___COMPUTE_UNIQUE_NAME;

	/**
	 * The operation id for the '<em>Encode</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL___ENCODE__STRING = IDENTIFIABLE_ELEMENT___ENCODE__STRING;

	/**
	 * The number of operations of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_OPERATION_COUNT = IDENTIFIABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.CallGraphImpl <em>Call Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.CallGraphImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getCallGraph()
	 * @generated
	 */
	int CALL_GRAPH = 2;

	/**
	 * The number of structural features of the '<em>Call Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_GRAPH_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Call Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALL_GRAPH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ContainerImpl <em>Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ContainerImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__LOCATION = 1;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__FUNCTIONS = 2;

	/**
	 * The number of structural features of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ProjectImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getProject()
	 * @generated
	 */
	int PROJECT = 4;

	/**
	 * The feature id for the '<em><b>Containers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__CONTAINERS = 0;

	/**
	 * The feature id for the '<em><b>Callgraph</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__CALLGRAPH = 1;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__LABELS = 2;

	/**
	 * The feature id for the '<em><b>Typedefs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__TYPEDEFS = 3;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.StmtCallImpl <em>Stmt Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.StmtCallImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getStmtCall()
	 * @generated
	 */
	int STMT_CALL = 8;

	/**
	 * The feature id for the '<em><b>Srcline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STMT_CALL__SRCLINE = 0;

	/**
	 * The feature id for the '<em><b>Srccol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STMT_CALL__SRCCOL = 1;

	/**
	 * The number of structural features of the '<em>Stmt Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STMT_CALL_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Stmt Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STMT_CALL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelAccessImpl <em>Label Access</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelAccessImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getLabelAccess()
	 * @generated
	 */
	int LABEL_ACCESS = 5;

	/**
	 * The feature id for the '<em><b>Srcline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ACCESS__SRCLINE = STMT_CALL__SRCLINE;

	/**
	 * The feature id for the '<em><b>Srccol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ACCESS__SRCCOL = STMT_CALL__SRCCOL;

	/**
	 * The feature id for the '<em><b>Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ACCESS__ACCESS = STMT_CALL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ACCESS__LABEL = STMT_CALL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Label Access</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ACCESS_FEATURE_COUNT = STMT_CALL_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Label Access</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ACCESS_OPERATION_COUNT = STMT_CALL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.TypeDefImpl <em>Type Def</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.TypeDefImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getTypeDef()
	 * @generated
	 */
	int TYPE_DEF = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Cat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF__CAT = 2;

	/**
	 * The feature id for the '<em><b>Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF__MEMBERS = 3;

	/**
	 * The number of structural features of the '<em>Type Def</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Type Def</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.TypeDefMemberImpl <em>Type Def Member</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.TypeDefMemberImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getTypeDefMember()
	 * @generated
	 */
	int TYPE_DEF_MEMBER = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF_MEMBER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Cat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF_MEMBER__CAT = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF_MEMBER__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Type Def Member</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF_MEMBER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Type Def Member</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEF_MEMBER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionCallImpl <em>Function Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionCallImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getFunctionCall()
	 * @generated
	 */
	int FUNCTION_CALL = 9;

	/**
	 * The feature id for the '<em><b>Srcline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__SRCLINE = STMT_CALL__SRCLINE;

	/**
	 * The feature id for the '<em><b>Srccol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__SRCCOL = STMT_CALL__SRCCOL;

	/**
	 * The feature id for the '<em><b>Calls</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__CALLS = STMT_CALL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Function Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_FEATURE_COUNT = STMT_CALL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Function Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_OPERATION_COUNT = STMT_CALL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeEntryImpl <em>Mode Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeEntryImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getModeEntry()
	 * @generated
	 */
	int MODE_ENTRY = 12;

	/**
	 * The feature id for the '<em><b>Stmtseq</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_ENTRY__STMTSEQ = 0;

	/**
	 * The number of structural features of the '<em>Mode Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_ENTRY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Mode Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeSwitchImpl <em>Mode Switch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeSwitchImpl
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getModeSwitch()
	 * @generated
	 */
	int MODE_SWITCH = 13;

	/**
	 * The feature id for the '<em><b>Srcline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_SWITCH__SRCLINE = STMT_CALL__SRCLINE;

	/**
	 * The feature id for the '<em><b>Srccol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_SWITCH__SRCCOL = STMT_CALL__SRCCOL;

	/**
	 * The feature id for the '<em><b>Modeentry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_SWITCH__MODEENTRY = STMT_CALL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mode Switch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_SWITCH_FEATURE_COUNT = STMT_CALL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Mode Switch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_SWITCH_OPERATION_COUNT = STMT_CALL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum <em>EFunction Type Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getEFunctionTypeEnum()
	 * @generated
	 */
	int EFUNCTION_TYPE_ENUM = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum <em>Access Type Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getAccessTypeEnum()
	 * @generated
	 */
	int ACCESS_TYPE_ENUM = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory <em>EType Category</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getETypeCategory()
	 * @generated
	 */
	int ETYPE_CATEGORY = 16;


	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getStmtseq <em>Stmtseq</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Stmtseq</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getStmtseq()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Stmtseq();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getType()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Type();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Container</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getContainer()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Container();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getSrcline <em>Srcline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Srcline</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getSrcline()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Srcline();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getSrccol <em>Srccol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Srccol</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Function#getSrccol()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Srccol();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Label <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Label
	 * @generated
	 */
	EClass getLabel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Label#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Label#getType()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Label#getCat <em>Cat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cat</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Label#getCat()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_Cat();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.CallGraph <em>Call Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Call Graph</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.CallGraph
	 * @generated
	 */
	EClass getCallGraph();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Container
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Container#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Container#getName()
	 * @see #getContainer()
	 * @generated
	 */
	EAttribute getContainer_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Container#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Container#getLocation()
	 * @see #getContainer()
	 * @generated
	 */
	EAttribute getContainer_Location();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Container#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Container#getFunctions()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_Functions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getContainers <em>Containers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Containers</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getContainers()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Containers();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getCallgraph <em>Callgraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Callgraph</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getCallgraph()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Callgraph();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getLabels <em>Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Labels</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getLabels()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Labels();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getTypedefs <em>Typedefs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Typedefs</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.Project#getTypedefs()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Typedefs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess <em>Label Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label Access</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess
	 * @generated
	 */
	EClass getLabelAccess();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess#getAccess <em>Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess#getAccess()
	 * @see #getLabelAccess()
	 * @generated
	 */
	EAttribute getLabelAccess_Access();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Label</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.LabelAccess#getLabel()
	 * @see #getLabelAccess()
	 * @generated
	 */
	EReference getLabelAccess_Label();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef <em>Type Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Def</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef
	 * @generated
	 */
	EClass getTypeDef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getName()
	 * @see #getTypeDef()
	 * @generated
	 */
	EAttribute getTypeDef_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getType()
	 * @see #getTypeDef()
	 * @generated
	 */
	EAttribute getTypeDef_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getCat <em>Cat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cat</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getCat()
	 * @see #getTypeDef()
	 * @generated
	 */
	EAttribute getTypeDef_Cat();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getMembers <em>Members</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Members</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDef#getMembers()
	 * @see #getTypeDef()
	 * @generated
	 */
	EReference getTypeDef_Members();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember <em>Type Def Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Def Member</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember
	 * @generated
	 */
	EClass getTypeDefMember();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember#getName()
	 * @see #getTypeDefMember()
	 * @generated
	 */
	EAttribute getTypeDefMember_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember#getCat <em>Cat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cat</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember#getCat()
	 * @see #getTypeDefMember()
	 * @generated
	 */
	EAttribute getTypeDefMember_Cat();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.TypeDefMember#getType()
	 * @see #getTypeDefMember()
	 * @generated
	 */
	EAttribute getTypeDefMember_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall <em>Stmt Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stmt Call</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall
	 * @generated
	 */
	EClass getStmtCall();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall#getSrcline <em>Srcline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Srcline</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall#getSrcline()
	 * @see #getStmtCall()
	 * @generated
	 */
	EAttribute getStmtCall_Srcline();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall#getSrccol <em>Srccol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Srccol</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.StmtCall#getSrccol()
	 * @see #getStmtCall()
	 * @generated
	 */
	EAttribute getStmtCall_Srccol();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.FunctionCall <em>Function Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Call</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.FunctionCall
	 * @generated
	 */
	EClass getFunctionCall();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.FunctionCall#getCalls <em>Calls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Calls</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.FunctionCall#getCalls()
	 * @see #getFunctionCall()
	 * @generated
	 */
	EReference getFunctionCall_Calls();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable <em>IIdentifiable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IIdentifiable</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable
	 * @generated
	 */
	EClass getIIdentifiable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#getName()
	 * @see #getIIdentifiable()
	 * @generated
	 */
	EAttribute getIIdentifiable_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#getUniqueName <em>Unique Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unique Name</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#getUniqueName()
	 * @see #getIIdentifiable()
	 * @generated
	 */
	EAttribute getIIdentifiable_UniqueName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#computeUniqueName() <em>Compute Unique Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Compute Unique Name</em>' operation.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#computeUniqueName()
	 * @generated
	 */
	EOperation getIIdentifiable__ComputeUniqueName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#encode(java.lang.String) <em>Encode</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Encode</em>' operation.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable#encode(java.lang.String)
	 * @generated
	 */
	EOperation getIIdentifiable__Encode__String();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IdentifiableElement <em>Identifiable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifiable Element</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IdentifiableElement
	 * @generated
	 */
	EClass getIdentifiableElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IdentifiableElement#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IdentifiableElement#getFile()
	 * @see #getIdentifiableElement()
	 * @generated
	 */
	EAttribute getIdentifiableElement_File();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IdentifiableElement#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IdentifiableElement#getPackage()
	 * @see #getIdentifiableElement()
	 * @generated
	 */
	EAttribute getIdentifiableElement_Package();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry <em>Mode Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mode Entry</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry
	 * @generated
	 */
	EClass getModeEntry();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry#getStmtseq <em>Stmtseq</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Stmtseq</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ModeEntry#getStmtseq()
	 * @see #getModeEntry()
	 * @generated
	 */
	EReference getModeEntry_Stmtseq();

	/**
	 * Returns the meta object for class '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ModeSwitch <em>Mode Switch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mode Switch</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ModeSwitch
	 * @generated
	 */
	EClass getModeSwitch();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ModeSwitch#getModeentry <em>Modeentry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Modeentry</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ModeSwitch#getModeentry()
	 * @see #getModeSwitch()
	 * @generated
	 */
	EReference getModeSwitch_Modeentry();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum <em>EFunction Type Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EFunction Type Enum</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum
	 * @generated
	 */
	EEnum getEFunctionTypeEnum();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum <em>Access Type Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Access Type Enum</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum
	 * @generated
	 */
	EEnum getAccessTypeEnum();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory <em>EType Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>EType Category</em>'.
	 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory
	 * @generated
	 */
	EEnum getETypeCategory();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	scairFactory getscairFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>Stmtseq</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__STMTSEQ = eINSTANCE.getFunction_Stmtseq();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__TYPE = eINSTANCE.getFunction_Type();

		/**
		 * The meta object literal for the '<em><b>Container</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__CONTAINER = eINSTANCE.getFunction_Container();

		/**
		 * The meta object literal for the '<em><b>Srcline</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__SRCLINE = eINSTANCE.getFunction_Srcline();

		/**
		 * The meta object literal for the '<em><b>Srccol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__SRCCOL = eINSTANCE.getFunction_Srccol();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelImpl <em>Label</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getLabel()
		 * @generated
		 */
		EClass LABEL = eINSTANCE.getLabel();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL__TYPE = eINSTANCE.getLabel_Type();

		/**
		 * The meta object literal for the '<em><b>Cat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL__CAT = eINSTANCE.getLabel_Cat();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.CallGraphImpl <em>Call Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.CallGraphImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getCallGraph()
		 * @generated
		 */
		EClass CALL_GRAPH = eINSTANCE.getCallGraph();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ContainerImpl <em>Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ContainerImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getContainer()
		 * @generated
		 */
		EClass CONTAINER = eINSTANCE.getContainer();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTAINER__NAME = eINSTANCE.getContainer_Name();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTAINER__LOCATION = eINSTANCE.getContainer_Location();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER__FUNCTIONS = eINSTANCE.getContainer_Functions();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ProjectImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getProject()
		 * @generated
		 */
		EClass PROJECT = eINSTANCE.getProject();

		/**
		 * The meta object literal for the '<em><b>Containers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__CONTAINERS = eINSTANCE.getProject_Containers();

		/**
		 * The meta object literal for the '<em><b>Callgraph</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__CALLGRAPH = eINSTANCE.getProject_Callgraph();

		/**
		 * The meta object literal for the '<em><b>Labels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__LABELS = eINSTANCE.getProject_Labels();

		/**
		 * The meta object literal for the '<em><b>Typedefs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__TYPEDEFS = eINSTANCE.getProject_Typedefs();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelAccessImpl <em>Label Access</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.LabelAccessImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getLabelAccess()
		 * @generated
		 */
		EClass LABEL_ACCESS = eINSTANCE.getLabelAccess();

		/**
		 * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL_ACCESS__ACCESS = eINSTANCE.getLabelAccess_Access();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL_ACCESS__LABEL = eINSTANCE.getLabelAccess_Label();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.TypeDefImpl <em>Type Def</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.TypeDefImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getTypeDef()
		 * @generated
		 */
		EClass TYPE_DEF = eINSTANCE.getTypeDef();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_DEF__NAME = eINSTANCE.getTypeDef_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_DEF__TYPE = eINSTANCE.getTypeDef_Type();

		/**
		 * The meta object literal for the '<em><b>Cat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_DEF__CAT = eINSTANCE.getTypeDef_Cat();

		/**
		 * The meta object literal for the '<em><b>Members</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_DEF__MEMBERS = eINSTANCE.getTypeDef_Members();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.TypeDefMemberImpl <em>Type Def Member</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.TypeDefMemberImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getTypeDefMember()
		 * @generated
		 */
		EClass TYPE_DEF_MEMBER = eINSTANCE.getTypeDefMember();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_DEF_MEMBER__NAME = eINSTANCE.getTypeDefMember_Name();

		/**
		 * The meta object literal for the '<em><b>Cat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_DEF_MEMBER__CAT = eINSTANCE.getTypeDefMember_Cat();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_DEF_MEMBER__TYPE = eINSTANCE.getTypeDefMember_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.StmtCallImpl <em>Stmt Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.StmtCallImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getStmtCall()
		 * @generated
		 */
		EClass STMT_CALL = eINSTANCE.getStmtCall();

		/**
		 * The meta object literal for the '<em><b>Srcline</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STMT_CALL__SRCLINE = eINSTANCE.getStmtCall_Srcline();

		/**
		 * The meta object literal for the '<em><b>Srccol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STMT_CALL__SRCCOL = eINSTANCE.getStmtCall_Srccol();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionCallImpl <em>Function Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.FunctionCallImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getFunctionCall()
		 * @generated
		 */
		EClass FUNCTION_CALL = eINSTANCE.getFunctionCall();

		/**
		 * The meta object literal for the '<em><b>Calls</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_CALL__CALLS = eINSTANCE.getFunctionCall_Calls();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable <em>IIdentifiable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.IIdentifiable
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getIIdentifiable()
		 * @generated
		 */
		EClass IIDENTIFIABLE = eINSTANCE.getIIdentifiable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IIDENTIFIABLE__NAME = eINSTANCE.getIIdentifiable_Name();

		/**
		 * The meta object literal for the '<em><b>Unique Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IIDENTIFIABLE__UNIQUE_NAME = eINSTANCE.getIIdentifiable_UniqueName();

		/**
		 * The meta object literal for the '<em><b>Compute Unique Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IIDENTIFIABLE___COMPUTE_UNIQUE_NAME = eINSTANCE.getIIdentifiable__ComputeUniqueName();

		/**
		 * The meta object literal for the '<em><b>Encode</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IIDENTIFIABLE___ENCODE__STRING = eINSTANCE.getIIdentifiable__Encode__String();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.IdentifiableElementImpl <em>Identifiable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.IdentifiableElementImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getIdentifiableElement()
		 * @generated
		 */
		EClass IDENTIFIABLE_ELEMENT = eINSTANCE.getIdentifiableElement();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIABLE_ELEMENT__FILE = eINSTANCE.getIdentifiableElement_File();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIABLE_ELEMENT__PACKAGE = eINSTANCE.getIdentifiableElement_Package();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeEntryImpl <em>Mode Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeEntryImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getModeEntry()
		 * @generated
		 */
		EClass MODE_ENTRY = eINSTANCE.getModeEntry();

		/**
		 * The meta object literal for the '<em><b>Stmtseq</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODE_ENTRY__STMTSEQ = eINSTANCE.getModeEntry_Stmtseq();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeSwitchImpl <em>Mode Switch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.ModeSwitchImpl
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getModeSwitch()
		 * @generated
		 */
		EClass MODE_SWITCH = eINSTANCE.getModeSwitch();

		/**
		 * The meta object literal for the '<em><b>Modeentry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODE_SWITCH__MODEENTRY = eINSTANCE.getModeSwitch_Modeentry();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum <em>EFunction Type Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getEFunctionTypeEnum()
		 * @generated
		 */
		EEnum EFUNCTION_TYPE_ENUM = eINSTANCE.getEFunctionTypeEnum();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum <em>Access Type Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.AccessTypeEnum
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getAccessTypeEnum()
		 * @generated
		 */
		EEnum ACCESS_TYPE_ENUM = eINSTANCE.getAccessTypeEnum();

		/**
		 * The meta object literal for the '{@link org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory <em>EType Category</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.ETypeCategory
		 * @see org.eclipse.app4mc.sca2amalthea.ir.scair.impl.scairPackageImpl#getETypeCategory()
		 * @generated
		 */
		EEnum ETYPE_CATEGORY = eINSTANCE.getETypeCategory();

	}

} //scairPackage
