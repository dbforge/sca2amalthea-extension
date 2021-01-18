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

package org.eclipse.app4mc.sca.amalthea.loader;

import java.io.IOException;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.amalthea.model.AmaltheaPackage;
import org.eclipse.app4mc.amalthea.model.SWModel;
import org.eclipse.app4mc.amalthea.sphinx.AmaltheaResourceFactory;
import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;


/**
 *  Extension when loading moel from resource
 */
public class AMALTHEAResourceLoader {

  /**
   * @param filePath URI of the amalthea file
   * @return object of type Resource which contains the parsed information of the provided amalthea file
   */
  public Resource loadAmaltheaResource(final URI filePath) {
    final ResourceSet resourceSet = new ResourceSetImpl();


    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("amxmi", new AmaltheaResourceFactory());
    resourceSet.getPackageRegistry().put(AmaltheaPackage.eNS_URI, AmaltheaPackage.eINSTANCE);
    final Resource res = resourceSet.createResource(filePath);
    try {
      res.load(null);
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, "org.eclipse.app4mc.amalthea.loader");
    }
    return res;

  }

  /**
   * @param filePath URI of the amalthea file
   * @return AMALTHEA intermediate model
   */
  public Amalthea getAmaltheaModelFromResource(final URI filePath) {

    final Resource r = loadAmaltheaResource(filePath);
    EList<EObject> contents = r.getContents();
    if (contents != null) {
      for (EObject content : contents) {
        if (content instanceof Amalthea) {
          return (Amalthea) content;
        }
      }

      /*
       * Extension to support resources without AMALTHEA central model element in resource. - Situation: The Amalthea
       * central model element could not been found. - Approach: Search for SwModel instead. It can be assumed, that the
       * AMALTHEA model builder has created with the SwModel as root element. - Solution: Return baked Amaltha Model
       * with parsed SwModel else null
       */
      for (EObject content : contents) {
        if (content instanceof SWModel) {
          Amalthea amaltheaModel = AmaltheaFactory.eINSTANCE.createAmalthea();
          amaltheaModel.setSwModel((SWModel) content);
          return amaltheaModel;
        }
      }
    }
    return null;
  }
}
