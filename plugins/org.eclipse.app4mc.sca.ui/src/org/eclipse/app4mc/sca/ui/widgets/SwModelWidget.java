/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.widgets;

import java.util.Observer;
import java.util.Properties;

import org.eclipse.app4mc.sca.ui.util.WizardConstants;
import org.eclipse.app4mc.sca.ui.widget.builder.WidgetBuilder;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;


/**
 * This class provides the UI elements for SW Model in Amalthea generation wizard
 */
public class SwModelWidget extends Widget {

  private final Properties properties;
  private Widget optionalSubWidget;

  /**
   * @param parent - Composite
   * @param subWidget - componenet specific widget
   * @param options - Properties
   */
  public SwModelWidget(final Composite parent, final WidgetBuilder subWidget, final Properties options) {
    this.properties = options;
    createComponent(parent, subWidget);
  }


  /**
   *
   */
  private void createComponent(final Composite parent, final WidgetBuilder subWidget) {

    Composite compositeForGroup = new Composite(parent, SWT.NULL);
    compositeForGroup.setLayout(new GridLayout());
    GridDataFactory.defaultsFor(compositeForGroup).grab(true, false).span(1, 1).applyTo(compositeForGroup);

    Group group = new Group(compositeForGroup, SWT.NONE);
    GridLayout grpLayout = new GridLayout(1, false);
    group.setLayout(grpLayout);
    group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    group.setText(WizardConstants.SW_DATA);

    // Create optional subWidget
    if (subWidget != null) {
      this.optionalSubWidget = subWidget.buildWidget(group);
    }

  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Properties getOptions() {
    if (this.optionalSubWidget != null) {
      Properties options = this.optionalSubWidget.getOptions();
      this.properties.putAll(options);
    }
    return this.properties;
  }


  /**
   * @return - validate and return Status
   */
  @Override
  public IStatus validate() {
    if (this.optionalSubWidget != null) {
      IStatus validate = this.optionalSubWidget.validate();
      if (!validate.isOK()) {
        return validate;
      }
    }

    return Status.OK_STATUS;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void addObserver(final Observer o) {
    super.addObserver(o);
    if (this.optionalSubWidget != null) {
      this.optionalSubWidget.addObserver(o);
    }
  }


}
