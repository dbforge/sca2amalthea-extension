/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca.ui.widgets;

import java.util.Observable;

import org.eclipse.app4mc.sca.ui.util.IOptionsProvider;
import org.eclipse.core.runtime.IStatus;


/**
 * This is an interface to create UI components in Amalthea generation wizard
 */
public abstract class Widget extends Observable implements IOptionsProvider {

  /**
   * @return {@link IStatus}
   */
  public abstract IStatus validate();

}
