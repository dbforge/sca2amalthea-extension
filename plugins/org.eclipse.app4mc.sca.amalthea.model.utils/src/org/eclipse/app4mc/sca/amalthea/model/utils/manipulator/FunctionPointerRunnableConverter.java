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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.app4mc.amalthea.model.ActivityGraphItem;
import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.LabelAccess;
import org.eclipse.app4mc.amalthea.model.LabelAccessEnum;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.amalthea.model.RunnableCall;
import org.eclipse.app4mc.amalthea.model.StringObject;
import org.eclipse.app4mc.sca.amalthea.model.utils.Activator;
import org.eclipse.app4mc.sca.amalthea.model.utils.helper.CustomPropertiesUtil;
import org.eclipse.app4mc.sca.amalthea.model.utils.helper.ModelAccess;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.logging.util.SCALogConstants;
import org.eclipse.app4mc.sca.logging.util.LogUtil;
import org.eclipse.emf.common.util.EList;


/**
 * This class does a simple function pointer resolution on the existing AMALTHEA model. Each function pointer access
 * that ends with "-FPTR" is transformed into a runnable call.
 */
public class FunctionPointerRunnableConverter implements IModelManipulator {

  /**
   * {@inheritDoc}
   */
  @Override
  public void manipulateModel(final Amalthea amaltheaModel) {
    String patternString = "(.*)-FPTR";
    Pattern pattern = Pattern.compile(patternString);

    EList<Runnable> runnables = amaltheaModel.getSwModel().getRunnables();
    try {
      for (Runnable runnable : runnables) {
        for (int i = 0; i < runnable.getRunnableItems().size(); i++) {
        	ActivityGraphItem activityGraphItem = runnable.getRunnableItems().get(i);
          if ((activityGraphItem != null) && (activityGraphItem instanceof LabelAccess)) {
            LabelAccess labelAccess = (LabelAccess) activityGraphItem;
            if (labelAccess.getData() != null) {
              Matcher matcher = pattern.matcher(labelAccess.getData().getName());
              if ((labelAccess.getAccess() == LabelAccessEnum.READ) && matcher.matches()) {
                String runnableName = matcher.group(1);
                Runnable fpRunnable = ModelAccess.getRunnable(runnables, runnableName);
                if (fpRunnable != null) {
                  LogUtil.log(SCALogConstants.TOOL_ID, Severity.DEBUG,
                      "Add runnable call from function pointer access to: " + runnable.getName() + ", calls: " +
                          fpRunnable.getName(),
                      this.getClass(), Activator.PLUGIN_ID);
                  RunnableCall runnableCall = AmaltheaFactory.eINSTANCE.createRunnableCall();
                  runnableCall.setRunnable(fpRunnable);
                  runnable.getRunnableItems().add((i + 1), runnableCall);
                  if (labelAccess.getCustomProperties() != null) {
                    String srcline = "";
                    if (labelAccess.getCustomProperties().containsKey(CustomPropertiesUtil.SRC_LINE)) {
                      srcline =
                          ((StringObject) runnable.getCustomProperties().get(CustomPropertiesUtil.SRC_LINE)).getValue();
                    }
                    String srccol = "";
                    if (labelAccess.getCustomProperties().containsKey(CustomPropertiesUtil.SRC_COL)) {
                      srccol =
                          ((StringObject) runnable.getCustomProperties().get(CustomPropertiesUtil.SRC_COL)).getValue();
                    }
                    CustomPropertiesUtil.addSourceLineInformation(runnableCall, srcline, srccol);
                  }
                }
              }
            }
            else {
              LogUtil.log(SCALogConstants.TOOL_ID, Severity.DEBUG,
                  "No Label Available for a label access in the runnable: " + runnable.getName(), this.getClass(),
                  Activator.PLUGIN_ID);
            }
          }
        }
      }
    }
    catch (Exception e) {
      LogUtil.logException(SCALogConstants.TOOL_ID, e.getClass(), e, Activator.PLUGIN_ID);
    }
  }


}
