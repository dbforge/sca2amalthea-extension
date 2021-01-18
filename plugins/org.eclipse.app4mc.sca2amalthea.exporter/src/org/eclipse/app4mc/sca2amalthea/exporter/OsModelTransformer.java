/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.exporter;

import java.util.List;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.IAnnotatable;
import org.eclipse.app4mc.amalthea.model.OSModel;
import org.eclipse.app4mc.amalthea.model.Semaphore;
import org.eclipse.app4mc.sca.amalthea.model.utils.helper.CustomPropertiesUtil;
import org.eclipse.app4mc.sca2amalthea.exporter.locks.LockDefinition;
import org.eclipse.app4mc.sca2amalthea.exporter.locks.LockFunction;
import org.eclipse.app4mc.sca2amalthea.exporter.util.CustomPropertiesAdder;

/**
 * This class transforms an AMALHTEA OsModel. Right now only the Semaphore definition is part of this OsModel
 */
public class OsModelTransformer {

  private final TransformerDataStore dataStore;

  /**
   * Constructor
   *
   * @param dataStore
   */
  public OsModelTransformer(final TransformerDataStore dataStore) {
    super();
    this.dataStore = dataStore;
  }

  /**
   * This function creates OSModel and also initializes the global {@link #data.getSemaMap()}
   *
   * @param amaltheaModel
   * @param lockDefintion
   */
  public void transform(final Amalthea amaltheaModel, final LockDefinition lockDefintion) {
	  if(lockDefintion!=null && lockDefintion.getLockFunctions().size()!=0){
		  OSModel osModel = AmaltheaFactory.eINSTANCE.createOSModel();
		  amaltheaModel.setOsModel(osModel);
		  createSemaphoresFillMap(lockDefintion, osModel);
       }
   }

  /**
   * @param lockDefintion
   * @param osModel
   */
  private void createSemaphoresFillMap(final LockDefinition lockDefintion, final OSModel osModel) {
    List<LockFunction> lockFunctions = lockDefintion.getLockFunctions();
    for (LockFunction lockFunction : lockFunctions) {
      Semaphore semaphore = AmaltheaFactory.eINSTANCE.createSemaphore();
      semaphore.setName(lockFunction.getName());
      semaphore.setInitialValue(0);
      semaphore.setMaxValue(1);
      String getLockFunctionName = lockFunction.getGetLockFunction();
      String releaseLockFunctionName = lockFunction.getReleaseLockFunction();
      addSemaphoreFunctions(semaphore, getLockFunctionName, releaseLockFunctionName);
      CustomPropertiesUtil.addToCustomProperties(semaphore, CustomPropertiesAdder.LOCK_TYPE,
          lockFunction.getLockType().name());
      osModel.getSemaphores().add(semaphore);
      this.dataStore.getSemaMap().put(getLockFunctionName, semaphore);
      this.dataStore.getSemaMap().put(releaseLockFunctionName, semaphore);
    }
  }

  /**
   * @param amBaseObject
   * @param getLockFunctionName
   * @param releaseLockFunctionName
   */
  private void addSemaphoreFunctions(final IAnnotatable amBaseObject, final String getLockFunctionName,
      final String releaseLockFunctionName) {
    CustomPropertiesUtil.addToCustomProperties(amBaseObject, CustomPropertiesAdder.GET_LOCK_FUNC_NAME,
        getLockFunctionName);
    CustomPropertiesUtil.addToCustomProperties(amBaseObject, CustomPropertiesAdder.RELEASE_LOCK_FUNC_NAME,
        releaseLockFunctionName);
  }


}
