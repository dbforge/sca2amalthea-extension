/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca2amalthea.ui.wizard;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca2amalthea.utils.UtilityForProcessHandling;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

/**
 * This class is responsible for showing the progress monitor for sca2Amalthea.
 */
public class SCAProgressMonitor {

  private SubMonitor subMonitor;

  private boolean isGenerationDone = false;

  private final Object mutex = new Object();

  private final Job eclipseJob;

  private static final int TOTAL_PROBABLE_EXECUTION_TIME = 600;

  /**
   * @param eclipseJob Eclipe job instance
   */
  public SCAProgressMonitor(final Job eclipseJob) {
    this.eclipseJob = eclipseJob;
  }


  class InfiniteProgressMonitorObserver extends Thread {

    /**
     * @param subMonitor monitor which will be observed
     */
    public InfiniteProgressMonitorObserver(final SubMonitor subMonitor) {
      super();
      SCAProgressMonitor.this.subMonitor = subMonitor;
    }

    @Override
    public void run() {
      runLogarithmicWaitOnProgressMonitor();
      terminateMonitor();
    }

    /**
     * @param subMonitor
     * @param jobName
     */
    private void runLogarithmicWaitOnProgressMonitor() {
      boolean isProcessCompleted = false;
      int advancementCounter = 0;

      while (!isProcessCompleted) {
        if (SCAProgressMonitor.this.subMonitor.isCanceled()) {
          synchronized (SCAProgressMonitor.this.mutex) {
            SCAProgressMonitor.this.isGenerationDone = true;
          }
          Process p = UtilityForProcessHandling.getCurrentRunningProcess();
          p.destroyForcibly();
          UtilityForProcessHandling.setModelGenerationcancelled(true);

          SCAProgressMonitor.this.eclipseJob.setName("Cancelling the model generation job");
        }
        try {
          SCAProgressMonitor.this.subMonitor.worked(1);
          advancementCounter++;
          if ((advancementCounter % (TOTAL_PROBABLE_EXECUTION_TIME / 2)) == 0) { // half time
            advancementCounter = 1;
            SCAProgressMonitor.this.subMonitor.setWorkRemaining(TOTAL_PROBABLE_EXECUTION_TIME);
          }
          Thread.sleep(1000);
        }
        catch (InterruptedException e) {
          Logmanager.getInstance().log(e.getMessage());
          Thread.currentThread().interrupt();
        }
        synchronized (SCAProgressMonitor.this.mutex) {
          isProcessCompleted = SCAProgressMonitor.this.isGenerationDone;
        }
      }
    }

    /**
     * @param subMonitor
     * @param jobName
     */
    private void terminateMonitor() {
      int numberOfSecondUntilEnd = 3;
      SCAProgressMonitor.this.subMonitor.setWorkRemaining(numberOfSecondUntilEnd);
      while (numberOfSecondUntilEnd > 0) {
        try {
          SCAProgressMonitor.this.subMonitor.worked(1);
          Thread.sleep(1000);
        }
        catch (InterruptedException e) {
          Logmanager.getInstance().log(e.getMessage());
          Thread.currentThread().interrupt();
        }
        --numberOfSecondUntilEnd;
      }
      UtilityForProcessHandling.setModelGenerationcancelled(false);
    }
  }

  /**
   * @param monitor Iprogress monitor
   * @return Thread instance
   */
  public Thread createInfiniteProgressMonitor(final IProgressMonitor monitor) {
    this.subMonitor = SubMonitor.convert(monitor, TOTAL_PROBABLE_EXECUTION_TIME);
    return new InfiniteProgressMonitorObserver(this.subMonitor);
  }

  /**
   * @return SubMonitor instance
   */
  public SubMonitor getSubMonitor() {
    return this.subMonitor;
  }

  /**
   * @return the isGenerationDone
   */
  public boolean isGenerationDone() {
    return this.isGenerationDone;
  }


  /**
   * @param isGenerationDone the isGenerationDone to set
   */
  public void setGenerationDone(final boolean isGenerationDone) {
    synchronized (SCAProgressMonitor.this.mutex) {
      this.isGenerationDone = isGenerationDone;
    }
  }
}
