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
package org.eclipse.app4mc.sca2amalthea.exporter;

import org.eclipse.app4mc.amalthea.model.Amalthea;
import org.eclipse.app4mc.amalthea.model.AmaltheaFactory;
import org.eclipse.app4mc.sca.scheduling.loader.model.OSConfModel;
import org.eclipse.app4mc.sca2amalthea.exporter.locks.LockDefinition;
import org.eclipse.app4mc.sca2amalthea.serialization.SCAResource;



/**
 * This class exports the data from the intermediate model to the amalthea model.
 *
 */
public class SCAToAmaltheaExporter {

  private TransformerDataStore data;

  private OsModelTransformer osModelTransformer;
  private ComponentModelTransformer componentExporter;
  private SwModelTransformer swModelTransformer;

  /**
   * Default Constructor
   */
  public SCAToAmaltheaExporter() {
    super();
  }

  /**
   * Transformation method to transform from the intermediate model to the amalthea model
   *
   * @param resource The {@link SCAResource}
   * @param lockDefintion {@link LockDefinition}
   * @return Returns the amalthea model
   */
  public Amalthea amaltheaTransformation(final SCAResource resource, final LockDefinition lockDefintion,OSConfModel osConfModel) {
    this.data = new TransformerDataStore(osConfModel);
    this.osModelTransformer = new OsModelTransformer(this.data);
    this.componentExporter = new ComponentModelTransformer(this.data);
    this.swModelTransformer = new SwModelTransformer(this.data, this.componentExporter);

    Amalthea amaltheaModel = AmaltheaFactory.eINSTANCE.createAmalthea();

    this.componentExporter.transform(amaltheaModel, resource);
    this.osModelTransformer.transform(amaltheaModel, lockDefintion);
    this.swModelTransformer.transform(amaltheaModel, resource);

    return amaltheaModel;
  }

}
