/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.util;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * This layout is intended to be assigned to the body composite of a FormPage with a ScrolledForm. It ensures that the
 * body composite will never exceed the size of the form window thereby inhibiting the form from creating scrollbars.
 */
public class NoScrollFormLayout extends Layout {

  /**
   * Always return 1,1. Indicating to the parent Form that this composite will shrink to the desired size.
   */
  @Override
  protected Point computeSize(final Composite composite, final int wHint, final int hHint, final boolean flushCache) {
    // Compute sie for Point
    return new Point(1, 1);
  }

  /**
   * All child composites will be made the size of the form.
   */
  @Override
  protected void layout(final Composite composite, final boolean flushCache) {
    // |Get the children from the composite
    Control[] children = composite.getChildren();
    // Get client area for each child element and set bounds
    for (Control element : children) {
      // Get client area
      Rectangle bounds = composite.getClientArea();
      if (bounds != null) {
        // Set bounds
        element.setBounds(bounds);
      }
    }
  }
}