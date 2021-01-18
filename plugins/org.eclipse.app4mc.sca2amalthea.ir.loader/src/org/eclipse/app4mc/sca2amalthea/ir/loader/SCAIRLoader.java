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

import java.io.IOException;
import java.util.Map;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca2amalthea.ir.loader.utils.LoaderSerializationUtils;
import org.eclipse.app4mc.sca2amalthea.ir.scair.scairPackage;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResource;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResourceFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;


/**
 * The loader class to load the XML contents to the intermediate model
 */
public class SCAIRLoader {

  private static String PLUGIN_ID = "org.eclipse.app4mc.sca2amalthea.ir.loader";

  /**
   * This method returns a SCAIR resource
   *
   * @param filepath The URI of the file to be loaded
   * @return the instance of SCAResource
   */
  public SCAResource load(final URI filepath) {
    Map<String, Object> saveOptions = LoaderSerializationUtils.INSTANCE.xmlSettings();

    ResourceSet resourceSet = new ResourceSetImpl();

    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xml", new SCAResourceFactory());
    resourceSet.getPackageRegistry().put(scairPackage.eNS_URI, scairPackage.eINSTANCE);


    SCAResource res = (SCAResource) resourceSet.createResource(filepath);
    try {
      res.load(saveOptions);
      EcoreUtil.resolveAll(res);
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, PLUGIN_ID);
      Logmanager.getInstance().log(e.getCause().getMessage());
    }
    return res;
  }
}
