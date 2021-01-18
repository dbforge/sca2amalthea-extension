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
package org.eclipse.app4mc.sca2amalthea.ir.serializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca2amalthea.ir.loader.Activator;
import org.eclipse.app4mc.sca2amalthea.ir.loader.utils.LoaderSerializationUtils;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResourceFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;


/**
 */
public class SCAIRSerializer {

  private final static String EXT = ".xml";

  /**
   * @param filepath The path on the drive
   * @param filename The name of the file without extension
   * @param content The contents of the SCAIR that needs to be saved
   */
  public void save(final String filepath, final String filename, final List<EObject> content) {
    Map<String, Object> saveOptions = LoaderSerializationUtils.INSTANCE.xmlSettings();
    URI fileURI = URI.createFileURI(filepath + "/" + filename + EXT);
    ResourceSet resourceSet = new ResourceSetImpl();


    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(EXT, new SCAResourceFactory());
    Resource res = resourceSet.createResource(fileURI);
    res.getContents().addAll(content);
    try {
      res.save(saveOptions);
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
      Logmanager.getInstance().log(e.getCause().getMessage());
    }
  }
}
