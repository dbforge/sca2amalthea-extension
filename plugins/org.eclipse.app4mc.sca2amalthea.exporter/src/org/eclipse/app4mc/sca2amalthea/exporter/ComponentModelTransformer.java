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



import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.Component;
import org.eclipse.app4mc.amalthea.model.ComponentsModel;
import org.eclipse.app4mc.amalthea.model.Process;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Container;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Function;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Project;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResource;
import org.eclipse.emf.common.util.EList;

/**
 * This class transforms the C-File containers into an AMALTHEA component model in which each c-file is one component
 * that can contain runnables, isrs and tasks.
 */
public class ComponentModelTransformer {

  private final TransformerDataStore dataStore;

  /**
   * Construtor
   *
   * @param data global data store
   */
  public ComponentModelTransformer(final TransformerDataStore data) {
    super();
    this.dataStore = data;
  }

  /**
   * transforms the component model to AMALTHEA
   *
   * @param amaltheaModel the amalthea model to be transformed
   * @param resource Eclipse Resource object representation of xmlcalltree.xml
   * @param fcbc a collection which holds the fc and bc information.
   * @param componentModelFcBcBased boolean value determining whether the component model is fc/bc based or c/h file
   *          based.
   */
  public void transform(final Amalthea amaltheaModel, final SCAResource resource) {
    ComponentsModel compModel = AmaltheaFactory.eINSTANCE.createComponentsModel();
    amaltheaModel.setComponentsModel(compModel);
    fillComponentsMapData(compModel, resource);
  }

  /**
   * @param containers
   * @param compModel
   */
  private void fillComponentsMapData(final ComponentsModel compModel, final SCAResource resource) {
      EList<Container> containers = ((Project) resource.getContents().get(0)).getContainers();
      for (Container container : containers) {
        Component comp = AmaltheaFactory.eINSTANCE.createComponent();
        comp.setName(container.getName());
        this.dataStore.getComponentMap().put(container.getName(), comp);
        compModel.getComponents().add(comp);
      }
  }

  /**
   * @param function function reference representing a function in the xmlcalltree
   * @param runnable Runnable reference in the amalthea model.
   */
  public void addRunnableToComponent(final Function function, final Runnable runnable) {
    String fc = function.getContainer().getName();
    if ((fc != null) && (this.dataStore.getComponentMap().get(fc) != null)) {
      this.dataStore.getComponentMap().get(fc).getRunnables().add(runnable);
    }
  }

  /**
   * @param task function reference representing a function in the xmlcalltree
   * @param process Task or ISR reference in the amalthea model.
   */
  public void addTaskToComponent(final Function task, final Process process) {
    String fc = task.getContainer().getName();
    if ((fc != null) && (this.dataStore.getComponentMap().get(fc) != null)) {
      this.dataStore.getComponentMap().get(fc).getProcesses().add(process);
    }
  }
}
