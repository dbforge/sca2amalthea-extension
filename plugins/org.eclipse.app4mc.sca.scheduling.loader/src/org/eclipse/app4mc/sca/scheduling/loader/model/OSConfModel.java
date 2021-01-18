package org.eclipse.app4mc.sca.scheduling.loader.model;
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
 **********************************************************************************/


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;



public class OSConfModel {
  private Map<String, OSTask> tasks=new HashMap<String,OSTask>();

  /**
   * @param tasks the tasks to set
   */
  public void setTasks(final Map<String, OSTask> tasks) {
    this.tasks = tasks;
  }


  /**
   * @return the tasks
   */
  public Map<String, OSTask> getTasks() {
    return this.tasks;
  }

  /**
   * @param name name of the task
   * @param task the {@link OSTask} instance
   */
  public void addTasks(final String name, final OSTask task) {
    this.tasks.put(name, task);
  }


  /**
   * @return Task/ISR infomration as a map
   */
  public Map<String, ArrayList<String>> getTaskISRInfoAsMap() {
    ArrayList<String> taskList =
        (ArrayList<String>) (getTasks().values()).stream().filter(osTask -> !osTask.getType().equals(TaskType.ISR))
            .map(osTask -> osTask.getName()).collect(Collectors.toList());

    ArrayList<String> isrList =
        (ArrayList<String>) (getTasks().values()).stream().filter(osTask -> osTask.getType().equals(TaskType.ISR))
            .map(osTask -> osTask.getName()).collect(Collectors.toList());
    Map<String, ArrayList<String>> infoMap = new HashMap<>();
    infoMap.put("TASK", taskList);
    infoMap.put("ISR", isrList);
    return infoMap;

  }
}
