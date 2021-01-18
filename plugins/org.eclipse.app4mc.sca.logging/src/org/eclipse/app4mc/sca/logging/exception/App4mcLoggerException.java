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
package org.eclipse.app4mc.sca.logging.exception;


/**
 * Custom exception for the amalthea app4mc logging
 */
public class App4mcLoggerException extends RuntimeException {

  private static final long serialVersionUID = 1997753363232807009L;

  /**
   * Constructor
   */
  public App4mcLoggerException() {
    // Do nothing
  }

  /**
   * @param message String
   */
  public App4mcLoggerException(final String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause {@link Throwable}
   */
  public App4mcLoggerException(final Throwable cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message The message to be displayed
   * @param cause {@link Throwable}
   */
  public App4mcLoggerException(final String message, final Throwable cause) {
    super(message, cause);
  }


  /**
   * Constructor
   *
   * @param message The message to be displayed
   * @param cause {@link Throwable}
   * @param enableSuppression whether or not suppression is enabled or disabled
   * @param writableStackTrace Decides whether the stack trace has to be printed
   */
  public App4mcLoggerException(final String message, final Throwable cause, final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
