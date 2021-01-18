/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import org.eclipse.app4mc.sca.ui.util.IOptionsProvider;
import org.eclipse.app4mc.sca.ui.util.NoScrollFormLayout;
import org.eclipse.app4mc.sca.ui.widget.builder.WidgetBuilder;
import org.eclipse.app4mc.sca.ui.widgets.Widget;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This is a common WizardPage class for Amalthea generation
 */
public class GenerateAmaltheaWizardPage extends WizardPage implements IOptionsProvider, Observer {

  final List<WidgetBuilder> builders;
  final List<Widget> widgets;
  final Properties props = new Properties();

  /**
   * @param title - Page Title
   * @param widgetBuilders - List of @WidgetBuilder for this page
   */
  public GenerateAmaltheaWizardPage(final String title, final List<WidgetBuilder> widgetBuilders) {
    super("AMALTHEA Generation", title, null);
    this.builders = widgetBuilders;
    this.widgets = new ArrayList<>();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void createControl(final Composite parent) {
    Composite rootComposite = new Composite(parent, SWT.NONE);
    rootComposite.setLayout(new NoScrollFormLayout());

    ScrolledComposite scrolledComposite =
        new ScrolledComposite(rootComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    scrolledComposite
        .setLayoutData(GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, SWT.DEFAULT).create());
    scrolledComposite.setExpandHorizontal(true);
    scrolledComposite.setExpandVertical(true);
    Composite mainComposite = new Composite(scrolledComposite, SWT.FILL);
    mainComposite.setLayout(new GridLayout());

    this.builders.stream().forEach(builder -> {
      Widget widget = builder.buildWidget(mainComposite);
      this.widgets.add(widget);
      widget.addObserver(this);
    });

    scrolledComposite.setContent(mainComposite);
    setControl(rootComposite);
    setPageComplete(false);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Properties getOptions() {
    Display.getDefault().syncExec(() -> this.widgets.stream().forEach(w -> this.props.putAll(w.getOptions())));
    return this.props;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isPageComplete() {
    for (Widget wid : this.widgets) {
      IStatus validate = wid.validate();
      if (validate.getSeverity() == IStatus.ERROR) {
        setErrorMessage(validate.getMessage());
        return false;
      }
      else if ((validate.getSeverity() == IStatus.WARNING) || (validate.getSeverity() == IStatus.INFO)) {
        setErrorMessage(null);
        setMessage(validate.getMessage(), validate.getSeverity());
        return true;
      }
    }
    setErrorMessage(null);
    setMessage(null);
    return true;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void update(final Observable o, final Object arg) {
    setPageComplete(isPageComplete());
  }

}
