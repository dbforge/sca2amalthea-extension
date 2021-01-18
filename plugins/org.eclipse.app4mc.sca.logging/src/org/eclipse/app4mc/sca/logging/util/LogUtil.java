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
package org.eclipse.app4mc.sca.logging.util;


import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Priority;
import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.core.resources.ResourcesPlugin;


/**
 * Helper functionality for using the Logmanager
 */
public final class LogUtil {

  /**
   * Private constructor
   */
  private LogUtil() {
    // Intentionally left blank
  }


  /**
   * wrapper log method
   *
   * @param messageId Message id
   * @param severity {@link Severity}
   * @param exception The exception message to be logged
   * @param clazz Class
   * @param pluginId Plugin id
   */
  public static void logException(final String messageId, final Class<?> clazz, final Throwable exception,
      final String pluginId) {

    LogInformation logInfo = new LogInformation(messageId, Severity.DEBUG,
        exception.getMessage() != null ? exception.getMessage() : "", clazz);
    logInfo.setPluginId(pluginId);
    logInfo.setProject(ResourcesPlugin.getWorkspace().getRoot().getProject());
    logInfo.setLineNumberStart("0");
    logInfo.setPriority(Priority.MINOR);

    Logmanager.getInstance().log(logInfo);
  }

  /**
   * wrapper log method
   *
   * @param messageId Message id
   * @param severity {@link Severity}
   * @param message The message to be logged
   * @param clazz Class
   * @param pluginId Plugin id
   */
  public static void log(final String messageId, final Severity severity, final String message, final Class<?> clazz,
      final String pluginId) {
    LogInformation logInfo = new LogInformation(messageId, severity, message, clazz);
    logInfo.setPluginId(pluginId);
    logInfo.setProject(ResourcesPlugin.getWorkspace().getRoot().getProject());
    logInfo.setLineNumberStart("0");
    logInfo.setPriority(Priority.MINOR);

    Logmanager.getInstance().log(logInfo);
  }
}

