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
package org.eclipse.app4mc.sca2amalthea.scairmodelenricher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.app4mc.sca2amalthea.ir.scair.Container;
import org.eclipse.app4mc.sca2amalthea.ir.scair.EFunctionTypeEnum;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Function;
import org.eclipse.app4mc.sca2amalthea.ir.scair.Project;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResource;
import org.eclipse.emf.common.util.EList;


/**
 */
public final class SCAIRModelEnrichmentUtils {

  /**
   * The private constructor
   */
  private SCAIRModelEnrichmentUtils() {
    // Empty private constructor
  }

  /**
   * @param resource The {@link SCAResource}
   * @param infoMap The list of task/isr names {@link List}
   */

  public static void markTasksIsrsRunnablesInModel(final SCAResource resource,
      final Map<String, ArrayList<String>> infoMap) {

    ArrayList<String> tmpTasklist = infoMap.get("TASK");
    ArrayList<String> tmpIsrlist = infoMap.get("ISR");
    EList<Container> containers = ((Project) resource.getContents().get(0)).getContainers();
    for (Container container : containers) {
      EList<Function> functions = container.getFunctions();
      for (Function function : functions) {
        if (!checkinTaskList(tmpTasklist, function)) {
          checkinIsrList(tmpIsrlist, function);
        }
      }
    }
  }


  /**
   * @param tmpTasklist
   * @param function
   */
  private static boolean checkinTaskList(final ArrayList<String> tmpTasklist, final Function function) {
    boolean contains = false;
    String fname = function.getName();
    for (String s : tmpTasklist) {
      if (fname.endsWith(s) &&
          !fname.toLowerCase(Locale.ENGLISH).startsWith("isr_")) {
        function.setType(EFunctionTypeEnum.TASK);
        function.setName(s);
        tmpTasklist.remove(s);
        contains = true;
        break;
      }
    }


    return contains;
  }

  /**
   * @param tmpIsrlist
   * @param function
   * @return
   */
  private static boolean checkinIsrList(final ArrayList<String> tmpIsrlist, final Function function) {
    boolean contains = false;
    String fname = function.getName();
    for (String s : tmpIsrlist) {
      if (fname.endsWith(s)) {
        function.setType(EFunctionTypeEnum.ISR);
        function.setName(s);
        tmpIsrlist.remove(s);
        contains = true;
        break;
      }
    }


    return contains;
  }

}
