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

import org.eclipse.app4mc.sca.logging.manager.LogFactory.Severity;
import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;


/**
 * The Util class to hold all utilities of App4mc logging
 */
public final class App4mcLogUtil {

  private static boolean testMode;

  private static boolean standaloneMode;

  /**
   * Private Constructor
   */
  private App4mcLogUtil() {
    // Private constructor
  }

  /**
   * Checks for the constraints during log and returns the boolean state
   *
   * @param logInfo {@link LogInformation}
   * @return True if the logging can be proceeded else false
   */
  public static boolean isInDebugMode(final LogInformation logInfo) {
    if (logInfo.getSeverity().equals(Severity.DEBUG)) {
      if (App4mcLogUtil.isJavaInDebug() || Logmanager.getInstance().isDebugMode()) {
        return true;
      }
      return false;
    }
    return true;
  }

  /**
   * Checks if the tool is running in debug mode. Returns the status of the debug mode
   *
   * @return true if the tool is running in debug mode
   */
  public static boolean isJavaInDebug() {
    return java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString()
        .indexOf("jdwp") >= 0;
  }

  /**
   * Prepares the logInformation from the given data and returns
   *
   * @param logClass The class which logs the message or exception
   * @param pluginId The plugin id of the caller
   * @param msg The message to be logged. In case of an exception the message from the exception is considered
   * @param exception The exception that has to be logged
   * @param messageId message id
   * @return {@link LogInformation}
   */
  public static LogInformation prepareLogInfo(final Class<?> logClass, final String pluginId, final String msg,
      final Exception exception, final String messageId) {
    LogInformation logInfo = new LogInformation();
    logInfo.setSeverity(Severity.ERROR);
    logInfo.setMessageId(messageId);
    if (exception != null) {
      logInfo.setException(exception);
    }
    logInfo.setLogClass(logClass);
    logInfo.setMessage(msg);
    if ((pluginId == null) || pluginId.isEmpty()) {
      logInfo.setPluginId(SCALogConstants.PLUGIN_ID);
    }
    else {
      logInfo.setPluginId(pluginId);
    }
    return logInfo;
  }

  /**
   * Prepares the log ifnormation with the give data
   *
   * @param message The message to be logged. In case of an exception the message from the exception is considered
   * @param messageId message id
   * @param severity The severity of the log information
   * @return {@link LogInformation}
   */
  public static LogInformation prepareLogInfo(final String message, final String messageId, final Severity severity) {
    LogInformation logInfo = new LogInformation();
    logInfo.setSeverity(severity);
    logInfo.setMessageId(messageId);
    logInfo.setMessage(message);
    logInfo.setPluginId(SCALogConstants.PLUGIN_ID);
    return logInfo;
  }

 

  /**
   * Checks if the runtime is an eclipse product. If its an eclipse product the method return true.
   *
   * @return true if the runtime is an eclipse product
   */
  public static boolean isEclipseProduct() {
    String product = System.getProperty("eclipse.product");
    if (product != null) {
      IExtensionRegistry registry = Platform.getExtensionRegistry();
      IExtensionPoint point = registry.getExtensionPoint("org.eclipse.core.runtime.products");
      if (point != null) {
        return iterateExtension(point, product);
      }
    }
    return false;
  }

  /**
   * @param point {@link IExtensionPoint}
   * @param product {@link String}
   * @return if the product is an eclipse product
   */
  private static boolean iterateExtension(final IExtensionPoint point, final String product) {
    IExtension[] extensions = point.getExtensions();
    for (IExtension ext : extensions) {
      if ((ext != null) && product.equals(ext.getUniqueIdentifier()) && (getBundle(ext) != null)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the bundle from the given extension
   *
   * @param ext {@link IExtension}
   * @return {@link Bundle}
   */
  private static Bundle getBundle(final IExtension ext) {
    Bundle bundle = null;
    IContributor contributor = ext.getContributor();
    if (contributor != null) {
      bundle = Platform.getBundle(contributor.getName());
    }
    return bundle;
  }

  /**
   * Returns the product property which can be identified with the given {@code propertyKey}.
   *
   * @param propertyKey A {@link String} with the property key.
   * @return The property which is associated with the given key or {@code null} if ther is no such property.
   * @throws IllegalArgumentException - if given parameter is null or the length is < 1.
   */
  public static String getProductProperty(final String propertyKey) throws IllegalArgumentException {

    String property = null;

    final IProduct product = Platform.getProduct();
    if (product != null) {
      property = product.getProperty(propertyKey);
    }

    return property;

  }

  /**
   * @return the isTestMode
   */
  public static boolean isTestMode() {
    return testMode;
  }


  /**
   * @param isTestMode the isTestMode to set
   */
  public static void setTestMode(final boolean isTestMode) {
    App4mcLogUtil.testMode = isTestMode;
  }


  /**
   * @return the isStandalone
   */
  public static boolean isStandalone() {
    return standaloneMode;
  }


  /**
   * @param isStandalone the isStandalone to set
   */
  public static void setStandalone(final boolean isStandalone) {
    App4mcLogUtil.standaloneMode = isStandalone;
  }
}
