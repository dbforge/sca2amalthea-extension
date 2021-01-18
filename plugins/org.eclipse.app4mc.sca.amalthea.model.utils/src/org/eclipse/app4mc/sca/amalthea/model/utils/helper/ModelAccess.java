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
package org.eclipse.app4mc.sca.amalthea.model.utils.helper;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.app4mc.amalthea.model.ActivityGraphItem;
import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.Group;
import org.eclipse.app4mc.amalthea.model.ISR;
import org.eclipse.app4mc.amalthea.model.Process;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.amalthea.model.RunnableCall;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.emf.common.util.EList;

/**
 * This class contains static model access helper
 */
public class ModelAccess {

  /**
   * @param amaltheaModel to be accessed
   * @return all runnables that are directly called from a Task or an ISR
   */
  public static Set<Runnable> getRunnablesFromTaskAndIsr(final Amalthea amaltheaModel) {
    Set<Runnable> calledRunnables = new HashSet<>();
    for (Task task : amaltheaModel.getSwModel().getTasks()) {
      Set<Runnable> runnablesFromTask = getRunnablesFromTask(task);
      calledRunnables.addAll(runnablesFromTask);
    }

    for (ISR isr : amaltheaModel.getSwModel().getIsrs()) {
      Set<Runnable> runnablesFromIsr = getRunnablesFromTask(isr);
      calledRunnables.addAll(runnablesFromIsr);
    }
    return calledRunnables;
  }

  /**
   * @param process from which the runnables are taken
   * @return all runnables that are called by the given Task or ISR
   */
  public static Set<Runnable> getRunnablesFromTask(final Process process) {
    Set<Runnable> calledRunnables = new HashSet<>();
    for (ActivityGraphItem activityGraphItem : process.getActivityGraph().getItems()) {
      if (!(activityGraphItem instanceof Group)) {
        continue;
      }
      for (ActivityGraphItem groupItem : ((Group) activityGraphItem).getItems()) {
    	  if(groupItem instanceof RunnableCall) {
    		  RunnableCall rc=(RunnableCall)groupItem;
    		  calledRunnables.add(rc.getRunnable());
    	  }
      }
    }
    return calledRunnables;
  }

  /**
   * @param runnables to be searched for
   * @param name of the runnable
   * @return runnable instance of the given name
   */
  public static Runnable getRunnable(final EList<Runnable> runnables, final String name) {
    for (Runnable runnable : runnables) {
      if (name.equals(runnable.getName())) {
        return runnable;
      }
    }
    return null;
  }


}
