/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.widget.builder;

import java.util.Properties;

import org.eclipse.app4mc.sca.ui.util.WizardConstants;
import org.eclipse.app4mc.sca.ui.widgets.SchedulingOsDataWidget;
import org.eclipse.app4mc.sca.ui.widgets.Widget;
import org.eclipse.swt.widgets.Composite;


/**
 * This is the WidgetBuilder class for @SchedulingOsDataWidget. This creates the UI elements and provides options to set
 * default values for UI components.
 */
public class SchedulingOsDataWidgetBuilder implements WidgetBuilder {

  private final Properties properties;

  /**
   * @param properties Properties
   */
  public SchedulingOsDataWidgetBuilder(final Properties properties) {
    this.properties = properties;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Widget buildWidget(final Composite parent) {
    return new SchedulingOsDataWidget(parent, this.properties);
  }


  /**
   * @param generate - Enable/Disable OS lock function file Text field
   * @return SchedulingOsDataWidgetBuilder
   */
  public SchedulingOsDataWidgetBuilder setOsLockFunctionFileEnabled(final boolean generate) {
    this.properties.setProperty(WizardConstants.OS_LOCK_FUNC_FILE, Boolean.toString(generate));
    return this;
  }


  /**
   * @param generate - Enable/Disable Task/Scheduling file Text field
   * @return SchedulingOsDataWidgetBuilder
   */
  public SchedulingOsDataWidgetBuilder setTaskSchedulingFileEnabled(final boolean generate) {
    this.properties.setProperty(WizardConstants.TASK_SCHED_FILE, Boolean.toString(generate));
    return this;
  }
}
