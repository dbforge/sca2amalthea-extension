
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
package org.eclipse.app4mc.sca.util.workspaces;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca.logging.util.SCALogConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;



public class WorkspaceOperations {

  /**
   * Refresh a project or a directory inside a workspace
   * 
   * @param projectPath path of the current project
   * @param subdirectoryToRefresh subdirectory inside the project which need to be refreshed. Its path should be
   *          relative to the given project.
   */
  public static void refreshWorkspace(final String projectPath, final String subdirectoryToRefresh) {
    IProject[] project = ResourcesPlugin.getWorkspace().getRoot().getProjects();
    if ((projectPath != null) && (project != null)) {
      String projPath = projectPath.replace('\\', '/');
      for (IProject iProject : project) {
        if (iProject.getLocation().toString().contains(projectPath.substring(projPath.lastIndexOf("/") + 1))) {
          try {
            if (((subdirectoryToRefresh != null) && !subdirectoryToRefresh.isEmpty()) && Files
                .exists(Paths.get(projPath + subdirectoryToRefresh.replace('\\', '/')), LinkOption.NOFOLLOW_LINKS)) {

              iProject.getFolder(subdirectoryToRefresh).refreshLocal(IResource.DEPTH_INFINITE,
                  new NullProgressMonitor());
            }
            else {
              iProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
            }

          }
          catch (CoreException e) {
            Logmanager.getInstance().logException(WorkspaceOperations.class, e, SCALogConstants.PLUGIN_ID);
          }
        }
      }
    }

  }
}
