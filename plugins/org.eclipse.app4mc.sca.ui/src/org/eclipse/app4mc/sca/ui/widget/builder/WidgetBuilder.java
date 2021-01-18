/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.widget.builder;

import org.eclipse.app4mc.sca.ui.widgets.Widget;
import org.eclipse.swt.widgets.Composite;


/**
 * This is an interface for building the Widgets to create UI components
 */
@FunctionalInterface
public interface WidgetBuilder {

  /**
   * @param parent - Parent Composite
   * @return - Widget
   */
  public Widget buildWidget(final Composite parent);

}
