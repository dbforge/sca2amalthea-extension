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
package org.eclipse.app4mc.sca2amalthea.serialization;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

public class SCAResourceFactory extends XMLResourceFactoryImpl {

  /**
   * Constructor for XMLResourceFactoryImpl.
   */
  public SCAResourceFactory() {
    super();
  }

  /**
   * Creates an XMLResourceImpl and returns it.
   */
  @Override
  public Resource createResource(final URI uri) {
    return new SCAResource(uri);
  }
}
