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
package org.eclipse.app4mc.sca.ui.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

/**
 * Utilities for all the SCA ui operations
 */
public final class SCAToolsUIUtil {

  /**
   * Private constructor
   */
  private SCAToolsUIUtil() {
    // Private constructor
  }


  /**
   * method to get the absolute path to project level
   *
   * @param projectPath - project path
   * @param absoluteOrRelativePath : relative path of the file
   * @return absolute path of the file
   */
  public static String getProjectAbsoluteFilePath(final String projectPath, final String absoluteOrRelativePath) {
    try {
      if ((projectPath != null) && (absoluteOrRelativePath != null)) {
        // get canonical path
        String path = new File(projectPath).getCanonicalPath();
        if (new File(absoluteOrRelativePath).isAbsolute()) {
          return absoluteOrRelativePath;
        }
        File projectAbsPath = new File(path + File.separator + absoluteOrRelativePath);
        if (projectAbsPath.exists()) {
          return projectAbsPath.getAbsolutePath();
        }
      }
      return absoluteOrRelativePath;
    }
    catch (IOException e) {
      return absoluteOrRelativePath;
    }
  }


  /**
   * @param projectPath - Project path
   * @param path - Absoulte/Relative path
   * @param isFile - true if it is file else false(for directory)
   * @param fileExtension - File extension
   * @return true if valid else false
   */
  public static boolean validatePath(final String projectPath, final String path, final boolean isFile,
      final String... fileExtension) {
    if ((projectPath != null) && !projectPath.isEmpty()) {
      File file = new File(getProjectAbsoluteFilePath(projectPath, path.trim()));
      String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf('.') + 1);
      if (isFile && (fileExtension != null)) {
        return (file.canRead() && file.isFile() &&
            Arrays.asList(fileExtension).stream().anyMatch(e -> e.equalsIgnoreCase(extension) || e.equals("*.*")));
      }
      return (file.canRead() && file.isDirectory());
    }
    return false;
  }

  /**
   * @param path - path
   * @return true if it is a drive else false
   */
  public static boolean isDrive(final String path) {
    String regex = "[a-zA-Z]:|[a-zA-Z]:\\\\";
    return path.matches(regex);
  }

  /**
   * @param resource project whose preferences have to loaded.
   * @param pluginId plugin id
   * @return IEclipsePreferences
   */
  public static IEclipsePreferences loadProjectScopePrefs(final IProject resource, final String pluginId) {
    ProjectScope projectScope = new ProjectScope(resource);
    return projectScope.getNode(pluginId);
  }

  /**
   * @return IProject
   */
  public static IProject getCurrentProjectResource() {
    Object currentProjectResource = getSelectionObject();
    if (currentProjectResource != null) {
      return ((IResource) currentProjectResource).getProject();
    }
    return null;
  }

  /**
   * @return selected element
   */
  public static Object getSelectionObject() {
    ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
    if (selection instanceof IStructuredSelection) {
      return ((IStructuredSelection) selection).getFirstElement();
    }
    return null;
  }

  /**
   * @return the project folder path of the selected ifile.
   */
  public static String getCurrentSelectionProjectPath() {
    IProject currentSelectionProject = getCurrentProjectResource();
    if (currentSelectionProject != null) {
      return currentSelectionProject.getLocation().toOSString();
    }
    return null;
  }

}
