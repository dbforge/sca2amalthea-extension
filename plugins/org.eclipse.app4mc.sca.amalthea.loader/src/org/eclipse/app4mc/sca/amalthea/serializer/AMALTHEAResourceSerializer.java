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
package org.eclipse.app4mc.sca.amalthea.serializer;

import java.io.IOException;
import java.util.List;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.sphinx.AmaltheaResourceFactory;
import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Class that serializes the amalthea model to the given file
 */
public class AMALTHEAResourceSerializer {

  private static final String EXT = "amxmi";

  /**
   * @param filename filepath without extension
   * @param amaltheaModel {@link AMALTHEA}
   */
  public void saveAmaltheaModel(final String filename, final Amalthea amaltheaModel) {
    if ((null != amaltheaModel) && !amaltheaModel.eContents().isEmpty()) {
        saveModelFile(filename, amaltheaModel);
    }
  }

  private void saveModelFile(final String filename, final EObject content) {
    saveModelFile(filename, new BasicEList<EObject>() {

      /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
        add(content);
      }
    });
  }

  /**
   * @param filename
   * @param amaltheaModel
   */
  private void saveModelFile(final String filename, final List<EObject> content) {
    final String fileNamewithExt = filename.concat(".").concat(EXT);
    final URI tmpUri = URI.createFileURI(fileNamewithExt);
    final ResourceSet resourceSet = new ResourceSetImpl();
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(EXT, new AmaltheaResourceFactory());

    final Resource outResource = resourceSet.createResource(tmpUri);
    outResource.getContents().addAll(content);
    try {
      outResource.save(null);
    }
    catch (IOException e) {
      Logmanager.getInstance().logException(this.getClass(), e, "org.eclipse.app4mc.amalthea.loader");
    }
  }
}
