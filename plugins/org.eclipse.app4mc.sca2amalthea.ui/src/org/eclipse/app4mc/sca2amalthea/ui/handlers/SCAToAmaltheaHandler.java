/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.ui.handlers;

import org.eclipse.app4mc.sca.ui.util.AmaltheaWizardPreferenceConstants;
import org.eclipse.app4mc.sca.ui.util.SCAToolsUIUtil;
import org.eclipse.app4mc.sca.ui.widget.builder.OutputDetailsWidgetBuilder;
import org.eclipse.app4mc.sca.ui.widget.builder.SchedulingOsDataWidgetBuilder;
import org.eclipse.app4mc.sca.ui.widget.builder.SwModelWidgetBuilder;
import org.eclipse.app4mc.sca.ui.wizard.GenerateAmaltheaWizard;
import org.eclipse.app4mc.sca.ui.wizard.GenerateAmaltheaWizardPage;
import org.eclipse.app4mc.sca.util.App4mcToolConstants;
import org.eclipse.app4mc.sca2amalthea.ui.wizard.SCA2AmaltheaExecutor;
import org.eclipse.app4mc.sca2amalthea.ui.wizard.SCA2AmaltheaWidgetBuilder;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import org.eclipse.jface.wizard.WizardDialog;

import org.eclipse.swt.widgets.Display;

import java.util.Arrays;
import java.util.Properties;


/**
 */
public class SCAToAmaltheaHandler extends AbstractHandler {
    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        Properties properties = getProperties();
        SCA2AmaltheaWidgetBuilder sca2AmaltheaWidgetBuilder = new SCA2AmaltheaWidgetBuilder(properties);
        SwModelWidgetBuilder swModelWidgetBuilder = new SwModelWidgetBuilder(sca2AmaltheaWidgetBuilder,
                properties);
        SchedulingOsDataWidgetBuilder schedulingOsDataWidgetBuilder = new SchedulingOsDataWidgetBuilder(properties);
        OutputDetailsWidgetBuilder outputDetailsWidgetBuilder = new OutputDetailsWidgetBuilder(properties);
        GenerateAmaltheaWizardPage scaAmaltheaWizardPage = new GenerateAmaltheaWizardPage("SCA2Amalthea Configuration page",
                Arrays.asList(swModelWidgetBuilder,
                    schedulingOsDataWidgetBuilder, outputDetailsWidgetBuilder));
        GenerateAmaltheaWizard generateAmaltheaWizard = new GenerateAmaltheaWizard(Arrays.asList(
                    scaAmaltheaWizardPage), properties,
                new SCA2AmaltheaExecutor());
        WizardDialog wizardDialog = new WizardDialog(Display.getCurrent()
                                                            .getActiveShell(),
                generateAmaltheaWizard);
        wizardDialog.setMinimumPageSize(800, 600);

        return wizardDialog.open();
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        IProject selectedProject = SCAToolsUIUtil.getCurrentProjectResource();
        IEclipsePreferences projectpreferences = SCAToolsUIUtil.loadProjectScopePrefs(selectedProject,
                App4mcToolConstants.SCA2AMALTHEA_TOOL_ID);
        properties.setProperty(AmaltheaWizardPreferenceConstants.PROJECT_PATH,
            SCAToolsUIUtil.getCurrentSelectionProjectPath());
        properties.setProperty(AmaltheaWizardPreferenceConstants.ENABLE_STRUCT_MEMBER_KEY,
            projectpreferences.get(
                AmaltheaWizardPreferenceConstants.ENABLE_STRUCT_MEMBER_KEY,
                Boolean.FALSE.toString()));
        properties.setProperty(AmaltheaWizardPreferenceConstants.AST_PATH_KEY,
            projectpreferences.get(AmaltheaWizardPreferenceConstants.AST_PATH_KEY, ""));
        properties.setProperty(AmaltheaWizardPreferenceConstants.HDIR_LIST_KEY,
            projectpreferences.get(AmaltheaWizardPreferenceConstants.HDIR_LIST_KEY, ""));
        properties.setProperty(AmaltheaWizardPreferenceConstants.BLOG_KEY,
            projectpreferences.get(AmaltheaWizardPreferenceConstants.BLOG_KEY, ""));
        properties.setProperty(AmaltheaWizardPreferenceConstants.OS_LOCK_FILE,
            projectpreferences.get(AmaltheaWizardPreferenceConstants.OS_LOCK_FILE, ""));
        properties.setProperty(AmaltheaWizardPreferenceConstants.TASK_SCHEDULING_FILE,
            projectpreferences.get(
                AmaltheaWizardPreferenceConstants.TASK_SCHEDULING_FILE, ""));
        properties.setProperty(AmaltheaWizardPreferenceConstants.OUTPUT_DIRECTORY,
            projectpreferences.get(
                AmaltheaWizardPreferenceConstants.OUTPUT_DIRECTORY, ""));

        return properties;
    }
}
