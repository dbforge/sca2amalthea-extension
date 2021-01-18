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
package org.eclipse.app4mc.sca.amalthea.model.utils.manipulator;

import org.eclipse.app4mc.amalthea.model.Amalthea;;

/**
 * This interface is the base for all instances that want to manipulate an AMALTHEA model.
 */
public interface IModelManipulator {

  /**
   * Within this methode an AMALTHEA model is changed according to the implementing instances
   *
   * @param amaltheaModel model that is to be changed
   */
  public void manipulateModel(Amalthea amaltheaModel);

}
