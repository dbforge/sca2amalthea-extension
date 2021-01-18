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


import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;



public class SCAResource extends XMLResourceImpl {

  /**
   * @param uri
   */
  public SCAResource(final URI uri) {
    super(uri);
    addDefaults();
  }

  private void addDefaults() {
    getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, "UTF-8");
    getDefaultLoadOptions().put(XMLResource.OPTION_DEFER_ATTACHMENT, Boolean.TRUE);
    getDefaultLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
    getDefaultLoadOptions().put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());
    getDefaultLoadOptions().put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
    setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
  }

  @Override
  protected XMLSave createXMLSave() {
    return new SCAXMLSaveImpl(createXMLHelper());
  }

  @Override
  protected XMLLoad createXMLLoad() {
    return new SCAXMLLoadImpl(createXMLHelper());
  }
}