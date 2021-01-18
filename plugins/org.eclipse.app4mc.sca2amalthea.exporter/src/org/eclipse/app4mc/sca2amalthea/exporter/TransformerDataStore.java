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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.app4mc.amalthea.model.Component;
import org.eclipse.app4mc.amalthea.model.ComponentInstance;
import org.eclipse.app4mc.amalthea.model.Composite;
import org.eclipse.app4mc.amalthea.model.ISR;
import org.eclipse.app4mc.amalthea.model.Label;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.amalthea.model.Semaphore;
import org.eclipse.app4mc.amalthea.model.Task;
import org.eclipse.app4mc.amalthea.model.TypeDefinition;
import org.eclipse.app4mc.sca.scheduling.loader.model.OSConfModel;

/**
 * This class contains the global maps of all runnables and labels for fast access during transformation.
 */
public class TransformerDataStore {

  private final Map<String, Semaphore> semaMap = new HashMap<String, Semaphore>();
  private final Map<String, ISR> isrMap = new HashMap<String, ISR>();
  private final Map<String, TypeDefinition> typeDefMap = new HashMap<String, TypeDefinition>();
  private final Map<String, Component> componentMap = new HashMap<String, Component>();
  private final Map<String, Composite> compositeMap = new HashMap<String, Composite>();
  private final Map<String, ComponentInstance> componentInstance = new HashMap<String, ComponentInstance>();
  private final Map<String, Label> labelMap = new HashMap<String, Label>();
  private final Map<String, Runnable> runnableMap = new HashMap<String, Runnable>();
  private final Map<String, Task> taskMap = new HashMap<String, Task>();
  private final OSConfModel osConfModel;

  /**
   * @param osConfModel
   */
  public TransformerDataStore(OSConfModel osConfModel) {
    this.osConfModel = osConfModel;
  }

  /**
   * @return the semaMap
   */
  public Map<String, Semaphore> getSemaMap() {
    return this.semaMap;
  }

  /**
   * @return the isrMap
   */
  public Map<String, ISR> getIsrMap() {
    return this.isrMap;
  }

  /**
   * @return the runnableMap
   */
  public Map<String, Runnable> getRunnableMap() {
    return this.runnableMap;
  }

  /**
   * @return the taskMap
   */
  public Map<String, Task> getTaskMap() {
    return this.taskMap;
  }

  /**
   * @return the labelMap
   */
  public Map<String, Label> getLabelMap() {
    return this.labelMap;
  }

  /**
   * @return the typeDefMap
   */
  public Map<String, TypeDefinition> getTypeDefMap() {
    return this.typeDefMap;
  }

  /**
   * @return the componentMap
   */
  public Map<String, Component> getComponentMap() {
    return this.componentMap;
  }

  /**
   * @return the componentMap
   */
  public Map<String, Composite> getCompositeMap() {
    return this.compositeMap;
  }

  /**
   * @return componentInstance map
   */
  public Map<String, ComponentInstance> getComponentInstanceMap() {
    return this.componentInstance;
  }

  /**
   * @return the osConfModel
   */
  public OSConfModel getOSConfModel() {
    return this.osConfModel;
  }

}