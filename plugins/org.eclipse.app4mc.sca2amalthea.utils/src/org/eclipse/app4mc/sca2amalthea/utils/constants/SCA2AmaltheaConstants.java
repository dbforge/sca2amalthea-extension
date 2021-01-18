/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.utils.constants;


/**
 * Constants for all the SCA2Amalthea operations
 */
public final class SCA2AmaltheaConstants {
    /**
     * Ini Task cycle constant
     */
    public static final String INI_TASK_CYCLE = "ONCE";

    /**
     * Parsing Error constant
     */
    public static final String PARSING_ERROR = "There was some error while parsing c files or when generating the AMALTHEA model.";

    /**
     * Amalthea generation taking too long message.
     */
    public static final String AMALTHEA_GENERATION_TOO_LONG = "The amalthea model generation job is still running.It might take some more time. Do you wish to continue? Press Ok to continue or cancel to abort";

    /**
     * Amalthea generation time depends on the size of the Project error.
     */
    public static final String AMALTHEA_GENERATION__PROJECT_SIZE_MESSAGE = "The time required to generate the Amalthea model depends on the size of the Project.";

    /**
     * Added private constructor to hide the implicit public one
     */
    private SCA2AmaltheaConstants() {
        // empty constructor
    }
}
