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
package org.eclipse.app4mc.sca2amalthea.llvm.srcfiles.support;

import java.util.List;


/**
 * This interface is the basis for all kinds to figure out which .c or.h-files have to be scanned by SCA2AMALTHEA
 */
public interface ISourceFileListProvider {

  /**
   * @return a list of all source files of type .c or .h that need to be processed by the SCA2AMALTHEA program
   */
  public List<String> generateSourceFileList();
}
