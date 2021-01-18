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

import org.eclipse.app4mc.amalthea.model.ActivityGraphItem;
import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.Component;
import org.eclipse.app4mc.amalthea.model.Label;
import org.eclipse.app4mc.amalthea.model.LabelAccess;
import org.eclipse.app4mc.amalthea.model.Runnable;
import org.eclipse.app4mc.sca.amalthea.loader.AMALTHEAResourceLoader;
import org.eclipse.app4mc.sca.amalthea.model.utils.Activator;
import org.eclipse.app4mc.sca.logging.util.SCALogConstants;
import org.eclipse.app4mc.sca.logging.util.LogUtil;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;


/**
 *
 */
public class StructMemberRemover implements IModelManipulator {

  private final String FUNCTION_STATIC_VARIABLES_MARKER = "APP4MC_FS";
  private final String FILE_STATIC_VARIABLES_MARKER = "APP4MC_CS";

  private Amalthea amaltheaModel;
  private String inFile;


  /**
   *
   */
  public StructMemberRemover() {
    super();
  }

  /**
   * @param inFile
   * @param outFile
   */
  public StructMemberRemover(final String inFile) {
    super();
    this.inFile = inFile;
    AMALTHEAResourceLoader arl = new AMALTHEAResourceLoader();
    URI createFileURI = URI.createFileURI(this.inFile);
    this.amaltheaModel = arl.getAmaltheaModelFromResource(createFileURI);
  }

  /**
   * @param amaltheaModel
   */
  public StructMemberRemover(final Amalthea amaltheaModel) {
    this.amaltheaModel = amaltheaModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void manipulateModel(final Amalthea amaltheaModel) {
    manipulateGivenModel(amaltheaModel);
  }

  /**
   *
   */
  public void manipulateModel() {
    manipulateGivenModel(this.amaltheaModel);
  }

  private void manipulateGivenModel(final Amalthea amaltheaModel) {
    removeStructMembersInLabelAccess(amaltheaModel);
    deleteLabelsInComponentModel(amaltheaModel);
    deleteLabelWithStructMembers(amaltheaModel);

  }

  /**
   * Delete the labels with struct members information from the label list
   *
   * @param amaltheaModel
   */
  private void deleteLabelWithStructMembers(final Amalthea amaltheaModel) {
    EList<Label> labels = amaltheaModel.getSwModel().getLabels();
    EList<Label> labelsToBeRemoved = new BasicEList<Label>();
    if ((labels != null) && !labels.isEmpty()) {
      for (Label label : labels) {
        String labelName = label.getName();
        if (containsStructMember(labelName)) {
          labelsToBeRemoved.add(label);
        }
      }
      labels.removeAll(labelsToBeRemoved);
    }
  }

  /**
   * Returns true if the labelname doesn't contain struct member information or if it is the name a static variable
   * (global or inside a function)
   *
   * @param labelName label name
   * @return true if if the labelname doesn't contain struct member information or if it is the name a static variable
   *         (global or inside a function)
   */
  public boolean containsStructMember(final String labelName) {
    return (labelName.contains(".") && !labelName.contains(this.FUNCTION_STATIC_VARIABLES_MARKER) &&
        !labelName.contains(this.FILE_STATIC_VARIABLES_MARKER));
  }

  /**
   * Remove the member access information from the label accesses inside runnables
   *
   * @param amaltheaModel
   */
  private void removeStructMembersInLabelAccess(final Amalthea amaltheaModel) {
    EList<Runnable> runnables = amaltheaModel.getSwModel().getRunnables();
    try {
      for (Runnable runnable : runnables) {
        EList<ActivityGraphItem> activityGraphItemList = runnable.getRunnableItems();
        if ((activityGraphItemList != null) && !activityGraphItemList.isEmpty()) {
          for (ActivityGraphItem activityGraphItem : activityGraphItemList) {
            if (activityGraphItem instanceof LabelAccess) {
              LabelAccess labelAccess = (LabelAccess) activityGraphItem;
              String labelName = labelAccess.getData().getName();
              if (containsStructMember(labelName)) {
                String mainLabelName = removeMemberInformation(labelName);
                labelAccess.setData(getLabel(amaltheaModel, mainLabelName));
              }
            }
          }
        }
      }
    }
    catch (Exception e) {
      LogUtil.logException(SCALogConstants.TOOL_ID, e.getClass(), e, Activator.PLUGIN_ID);
    }
  }


  /**
   * @return the amaltheaModel
   */
  public Amalthea getAmaltheaModel() {
    return this.amaltheaModel;
  }

  /**
   * Get a defined Label from the AMALTHEA model
   *
   * @param amaltheaModel AMALTHEA model
   * @param mainLabelName name of the label to be returned
   * @return
   */
  private Label getLabel(final Amalthea amaltheaModel, final String mainLabelName) {
    EList<Label> labels = amaltheaModel.getSwModel().getLabels();

    for (Label label : labels) {
      if (label.getName().equals(mainLabelName)) {
        return label;
      }
    }
    return null;
  }

  /**
   * Removes the member's name and returns the label which represents the whole structure
   *
   * @param labelName label name containing the members information
   * @return
   */
  private String removeMemberInformation(final String labelName) {
    int startPositionOfFirstMember = labelName.indexOf(".");
    StringBuilder sb = new StringBuilder(labelName.substring(0, startPositionOfFirstMember));
    int postionOfHypen = labelName.indexOf('-');
    if (postionOfHypen > 0) {
      sb.append(labelName.substring(postionOfHypen));
    }
    return sb.toString();
  }

  private void deleteLabelsInComponentModel(final Amalthea amalthea) {
    for (Component c : amalthea.getComponentsModel().getComponents()) {
      EList<Label> labels = c.getLabels();
      EList<Label> labelsToBeRemoved = new BasicEList<Label>();
      if ((labels != null) && !labels.isEmpty()) {
        for (Label label : labels) {
          String labelName = label.getName();
          if (containsStructMember(labelName)) {
            labelsToBeRemoved.add(label);
          }
        }
        labels.removeAll(labelsToBeRemoved);
      }
    }
  }
}
