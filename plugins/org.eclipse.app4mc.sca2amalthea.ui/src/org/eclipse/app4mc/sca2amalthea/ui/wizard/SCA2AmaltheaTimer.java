/**
 ******************************************************************************** Copyright (c) 2017-2020 Robert Bosch GmbH and others. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/
 * SPDX-License-Identifier: EPL-2.0 Contributors: Robert Bosch GmbH - initial API and implementation
 */
package org.eclipse.app4mc.sca2amalthea.ui.wizard;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.app4mc.sca.util.App4mcToolConstants;
import org.eclipse.app4mc.sca2amalthea.utils.UtilityForProcessHandling;
import org.eclipse.app4mc.sca2amalthea.utils.constants.SCA2AmaltheaConstants;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**
 *
 */
public class SCA2AmaltheaTimer {

  class SCA2AmaltheaTimerTask extends TimerTask {

    private final Timer timer;
    private final Job eclipseJob;

    public SCA2AmaltheaTimerTask(final Timer timerInstance, final Job eclipseJob) {
      this.timer = timerInstance;
      this.eclipseJob = eclipseJob;
    }

    @Override
    public void run() {
      if ((this.eclipseJob.getThread() != null) && this.eclipseJob.getThread().isAlive()) {
        SCAPopup scaPopup = new SCAPopup(App4mcToolConstants.SCA2AMALTHEA_TOOL_ID,
            SCA2AmaltheaConstants.AMALTHEA_GENERATION_TOO_LONG, MessageDialog.CONFIRM);
        Display.getDefault().syncExec(scaPopup);
        int confirmationStatus = scaPopup.getDialogStatus() ? 0 : 1;
        if (confirmationStatus == 1) {
          this.timer.cancel();
          Process p = UtilityForProcessHandling.getCurrentRunningProcess();
          if (p != null) {
            p.destroyForcibly();
            UtilityForProcessHandling.setModelGenerationcancelled(true);
          }
        }
      }
      else {
        this.timer.cancel();
      }
    }
  }

  /**
   * This method starts the timer.The timer is sceduled to run after every 40mins.Every time the timer runs it checks if
   * the eclipse job is still alive. If so it pops up a confirmation dialog asking if the user wishes to continue. If
   * the user wishes to continue the timer keeps running and keeps showing the popup after every 40 mins and if the user
   * wishes to abort then it cancels the timer and kills the sca.exe process.If the eclipse job is not alive it cancels
   * the timer.
   */
  public void startTimer() {
    int timerDelay = 40 * 60 * 1000;
    int timerPeriod = 40 * 60 * 1000;
    Job eclipseJob = Job.getJobManager().currentJob();
    Timer t = new Timer();
    t.scheduleAtFixedRate(new SCA2AmaltheaTimerTask(t, eclipseJob), timerDelay, timerPeriod);
  }

}
