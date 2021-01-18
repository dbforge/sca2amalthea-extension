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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca.scheduling.loader.activator.Activator;

public class SchedulingInformationLoader {

OSConfModel osConfModel;
  /**
   * returns the list of String containing taskname in the file (PAVAST/AUTOSAR) provided by asolutefilePath
   *
   * @param absolutefilePath
   * @return
   */
  public OSConfModel getTasksInformation(final String absolutefilePath) {
	  this.osConfModel=new OSConfModel();
	  try {
	      BufferedReader inFileBuffer = new BufferedReader(new FileReader(absolutefilePath));
	      String line;
	      while ((line = inFileBuffer.readLine()) != null) {
	        if (line.startsWith("#")) {
	              continue;
	         }
	        String[] csvLineArray = line.split(",");
	        if(csvLineArray.length>1){
	        OSTask osTask=getOSTask(csvLineArray);
	        osConfModel.addTasks(csvLineArray[1], osTask);
	        }
	      }
	      inFileBuffer.close();
	    }
	    catch (IOException e) {
	      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
	    }
return osConfModel;
  }
  
  private TaskType getTaskType(String type){
	  if(type.equals("SOFTWARE")){
		  return TaskType.SOFTWARE;
	  }
	  else if(type.equals("INIT")){
		  return TaskType.INIT;
	  }
	  else if(type.equals("ISR")){
		  return TaskType.ISR;
	  }
	  else{
		  return TaskType.UNKNOWN;
	  }
  }
  
  private OSTask getOSTask(String[] csvLineArray){
	  OSTask osTask=null;
	  switch(csvLineArray.length){
	  case 2:{
		  osTask  =new OSTask(csvLineArray[1],getTaskType(csvLineArray[0]),null);
		  break;
	  }
	  case 3:{
		  osTask =new OSTask(csvLineArray[1],getTaskType(csvLineArray[0]),csvLineArray[2]);
		  break;
	  }
	  default:{
		  break;
	  }
	  }
	  return osTask;
  }


}
