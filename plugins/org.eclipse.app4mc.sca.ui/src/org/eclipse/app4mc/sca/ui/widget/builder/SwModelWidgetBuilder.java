/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.widget.builder;

import java.util.Properties;

import org.eclipse.app4mc.sca.ui.widgets.SwModelWidget;
import org.eclipse.app4mc.sca.ui.widgets.Widget;
import org.eclipse.swt.widgets.Composite;

/**
 * This is the WidgetBuilder class for @SWModelWidget. This creates the UI elements and provides options to set default
 * values for UI components.
 */
public class SwModelWidgetBuilder implements WidgetBuilder {

  private final Properties properties;
  private final WidgetBuilder subWidget;


  /**
   * @param subWidget WidgetBuilder
   * @param properties Properties
   */
  public SwModelWidgetBuilder(final WidgetBuilder subWidget, final Properties properties) {
    this.subWidget = subWidget;
    this.properties = properties;
  }


  @Override
  public Widget buildWidget(final Composite parent) {
    return new SwModelWidget(parent, this.subWidget, this.properties);
  }

}
