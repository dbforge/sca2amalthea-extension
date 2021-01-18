/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca2amalthea.ui.wizard;

import java.util.Properties;

import org.eclipse.app4mc.sca.ui.widget.builder.WidgetBuilder;
import org.eclipse.app4mc.sca.ui.widgets.Widget;
import org.eclipse.swt.widgets.Composite;


/**
 * This is a Widget Builder class for @SCA2AmaltheaWidget. This class internally uses SCA2AmaltheaWiget to create the UI
 * elements.
 */
public class SCA2AmaltheaWidgetBuilder implements WidgetBuilder {

  private final Properties properties;

  /**
   * @param properties Properties instance.
   */
  public SCA2AmaltheaWidgetBuilder(final Properties properties) {
    this.properties = properties;
  }

  /**
   * Builds the SCA2Amalthea Widget.
   */
  @Override
  public Widget buildWidget(final Composite parent) {
    return new SCA2AmaltheaWidget(parent, this.properties);
  }

}
