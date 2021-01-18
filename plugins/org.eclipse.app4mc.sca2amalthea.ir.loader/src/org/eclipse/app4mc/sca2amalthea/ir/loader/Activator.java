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
package org.eclipse.app4mc.sca2amalthea.ir.loader;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


/**
 */
public class Activator implements BundleActivator {

  /**
   * Plugin id
   */
  public static final String PLUGIN_ID = "org.eclipse.app4mc.sca2amalthea.ir.loader";

  private static BundleContext context;

  static BundleContext getContext() {
    return context;
  }

  @Override
  public void start(final BundleContext bundleContext) throws Exception {
    Activator.context = bundleContext; // NOSONAR normal Eclipse pattern
  }

  /**
   * (non-Javadoc)
   *
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(final BundleContext bundleContext) throws Exception {
    Activator.context = null; // NOSONAR normal Eclipse pattern
  }


}
