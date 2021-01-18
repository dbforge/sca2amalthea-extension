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
 ********************************************************************************/

package org.eclipse.app4mc.sca.scheduling.loader.model;

public class OSTask {

  private String name;
  private TaskType type;
  private String cycle;

  public OSTask(String name,TaskType taskType,String cycle){
	  this.name=name;
	  this.type=taskType;
	  this.cycle=cycle;
  }
  /**
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name the name to set
   */
  public void setName(final String name) {
    this.name = name;
  }


  /**
   * @return the type
   */
  public TaskType getType() {
    return this.type;
  }

  /**
   * @param type the type to set
   */
  public void setType(final TaskType type) {
    this.type = type;
  }


  /**
   * @return the cycle
   */
  public String getCycle() {
    return this.cycle;
  }

  /**
   * @param cycle the cycle to set
   */
  public void setCycle(final String cycle) {
    this.cycle = cycle;
  }



}
