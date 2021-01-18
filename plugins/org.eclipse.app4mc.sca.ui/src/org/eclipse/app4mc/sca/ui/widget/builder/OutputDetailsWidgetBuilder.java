/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.widget.builder;

import java.util.Properties;

import org.eclipse.app4mc.sca.ui.util.AmaltheaWizardPreferenceConstants;
import org.eclipse.app4mc.sca.ui.widgets.OutputDetailsWidget;
import org.eclipse.app4mc.sca.ui.widgets.Widget;
import org.eclipse.swt.widgets.Composite;


/**
 * This class provides the UI elements/Widgets for Output directory path for the Amalthea generation
 */
public class OutputDetailsWidgetBuilder implements WidgetBuilder {

  private final Properties properties;

  /**
   * @param properties Properties
   */
  public OutputDetailsWidgetBuilder(final Properties properties) {
    this.properties = properties;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Widget buildWidget(final Composite parent) {
    return new OutputDetailsWidget(parent, this.properties);
  }

  /**
   * @param enable - Enable/Disable output directory path
   * @return OutputDetailsWidgetBuilder
   */
  public OutputDetailsWidgetBuilder setOutputDirectoryPathEnabled(final boolean enable) {
    this.properties.setProperty(AmaltheaWizardPreferenceConstants.OUTPUT_DIRECTORY, Boolean.toString(enable));
    return this;
  }

}
