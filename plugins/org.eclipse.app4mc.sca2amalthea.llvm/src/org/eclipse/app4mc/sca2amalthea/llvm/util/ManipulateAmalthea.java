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
package org.eclipse.app4mc.sca2amalthea.llvm.util;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.CommonElements;
import org.eclipse.app4mc.amalthea.model.Tag;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.app4mc.sca.amalthea.model.utils.manipulator.FunctionPointerRunnableConverter;
import org.eclipse.app4mc.sca.amalthea.model.utils.manipulator.IModelManipulator;
import org.eclipse.app4mc.sca.amalthea.model.utils.manipulator.StructMemberRemover;
import org.eclipse.app4mc.sca.scheduling.loader.model.OSConfModel;
import org.eclipse.app4mc.sca2amalthea.llvm.headless.SCA2AMALTHEAStarterProperties;
import org.eclipse.app4mc.sca2amalthea.utils.constants.SCA2AmaltheaConstants;


/**
 */
public class ManipulateAmalthea {

  /**
   * call the different activated manipulators to modify the given AMALTHEA model
   *
   * @param sca2amProperties object which contains the properties about whether or not a given manipulator should be
   *          called
   * @param amaltheaModel AMALTHEA model to be transformed.
   */
  public void manipulates(final SCA2AMALTHEAStarterProperties sca2amProperties, final Amalthea amaltheaModel) {
    if (!sca2amProperties.isStructMemberEnabled()) {
      IModelManipulator modelManipulator = new StructMemberRemover();
      modelManipulator.manipulateModel(amaltheaModel);
    }

    new FunctionPointerRunnableConverter().manipulateModel(amaltheaModel);
  }

  /**
   * This method takes the amalthea model and the OSConfModel. It reads the tasks which have a task-cycle as "ONCE" from
   * the OSConfModel and marks the corrosponding tasks in the Amalthea model by attaching a tag by name "ini" to them.
   *
   * @param amalthea amalthea model to be modified
   * @param osConfModel osconfmodel instance
   */
  public static void markIniTasksInAmaltheaModel(final Amalthea amalthea, final OSConfModel osConfModel) {
    Tag tag = null;
    CommonElements ce=AmaltheaFactory.eINSTANCE.createCommonElements();
    for (Task task : amalthea.getSwModel().getTasks()) {
      if ((osConfModel.getTasks().get(task.getName()).getCycle() != null) &&
          osConfModel.getTasks().get(task.getName()).getCycle().equals(SCA2AmaltheaConstants.INI_TASK_CYCLE)) {
        if (tag == null) {
          tag = AmaltheaFactory.eINSTANCE.createTag();
          tag.setName("ini");
          amalthea.setCommonElements(ce);
          amalthea.getCommonElements().getTags().add(tag);
        }
        task.getTags().add(tag);
      }
    }

  }

}
