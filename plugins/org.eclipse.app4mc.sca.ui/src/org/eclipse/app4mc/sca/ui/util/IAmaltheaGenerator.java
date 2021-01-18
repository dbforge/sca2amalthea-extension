/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.util;

import java.util.Properties;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

/**
 * This is the generator interface class for Amalthea generation
 */
@FunctionalInterface
public interface IAmaltheaGenerator {

  /**
   * Funcitonality for amalthea generation based on the provided input @Properties
   *
   * @param monitor IProgressMonitor
   * @param properties Properties
   * @return IStatus
   */
  public IStatus execute(final IProgressMonitor monitor, final Properties properties);

}
