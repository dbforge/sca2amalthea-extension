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


import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl;

public class SCAXMLSaveImpl extends XMLSaveImpl {

  /**
   * @param options {@link Map}
   * @param helper {@link XMLHelper}
   * @param encoding {@link String}
   */
  public SCAXMLSaveImpl(final Map<?, ?> options, final XMLHelper helper, final String encoding) {
    super(options, helper, encoding);

  }

  /**
   * @param options {@link Map}
   * @param helper {@link XMLHelper}
   * @param encoding {@link String}
   * @param xmlVersion {@link String}
   */
  public SCAXMLSaveImpl(final Map<?, ?> options, final XMLHelper helper, final String encoding,
      final String xmlVersion) {
    super(options, helper, encoding, xmlVersion);
  }

  /**
   * @param helper {@link XMLHelper}
   */
  public SCAXMLSaveImpl(final XMLHelper helper) {
    super(helper);
  }

  @Override
  protected void saveContainedMany(final EObject o, final EStructuralFeature f) {
    this.doc.startElement(f.getName());
    super.saveContainedMany(o, f);
    this.doc.endElement();
  }
}