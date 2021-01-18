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
package org.eclipse.app4mc.sca2amalthea.exporter.locks;


/**
 * This class contains the data that defines a lock.
 */
public class LockFunction {

  /**
   * Enum lock type
   */
  public static enum LockType {
                               /**
                                * normal spin lock
                                */
                               SPIN_LOCK,
                               /**
                                * exclusive locks are interrupt locks
                                */
                               EXCLUSIVE_LOCK
  }

  private final String name;
  private final String getLockFunction;
  private final String releaseLockFunction;
  private final LockType lockType;

  /**
   * @param name name of the sempaphore later in AMALTHEA
   * @param getLockFunction string of the c-function that aquires the lock
   * @param releaseLockFunction string of the c-funtion that releases the lock
   * @param lockType type of the lock. Could be exclusive for interrupt locks
   */
  public LockFunction(final String name, final String getLockFunction, final String releaseLockFunction,
      final LockType lockType) {
    super();
    this.name = name;
    this.getLockFunction = getLockFunction;
    this.releaseLockFunction = releaseLockFunction;
    this.lockType = lockType;
  }


  /**
   * @return the name
   */
  public String getName() {
    return this.name;
  }


  /**
   * @return the getLockFunction
   */
  public String getGetLockFunction() {
    return this.getLockFunction;
  }


  /**
   * @return the releaseLockFunction
   */
  public String getReleaseLockFunction() {
    return this.releaseLockFunction;
  }


  /**
   * @return the lockType
   */
  public LockType getLockType() {
    return this.lockType;
  }

}
