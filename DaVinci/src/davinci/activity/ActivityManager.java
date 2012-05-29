/*
 * DaVinci V1.0 is a light weighted visualization framework and toolkit. 
 * The design of DaVinci based on the information visualization reference model.
 * Some of the packages are ported from prefuse (http://prefuse.org/). All these packages 
 * keep the original file headers and copyright information. Please read and follow it 
 * if you want to use them.
 * 
 * The original motivation of creating this project is to design a light weighted, 
 * simple and easy to use information visualization framework that facilitates the 
 * Ph.D study of the author. For any other purposes please notify the author through 
 * email.
 * 
 * DaVinci V1.0 is under the MIT opensource license.
 * 
 * Author : Nan Cao
 * Email: nan.cao@gmail.com
 * Homepage: http://www.cse.ust.hk/~nancao/
 * Project Homepage: http://www.cse.ust.hk/~nancao/architecture.html
 * V1 Release Date : 1st June 2010
 */
package davinci.activity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class ActivityManager {

	private List m_activities = new ArrayList(0);
	private Timer m_timer;
	private List m_processing = new ArrayList(0);
	private final int ACTIVITY_FRAME_DELAY = 0;
	
	public ActivityManager() {
	}
		
	public void addActivity(IActivity activity) {
		addActivity(activity, false);
	}
	
	public boolean isPerforming() {
		return getTimer().isRunning();
	}
	
	public boolean isEmpty() {
		return m_activities.isEmpty();
	}

	public void addActivity(IActivity activity, boolean processLast) {
		if (m_activities.contains(activity)) {
			return;
		}
		
		if (processLast) {
			m_activities.add(0, activity);
		} else {
			m_activities.add(activity);
		}
		
		activity.setActivityManager(this);
		
		if (!getTimer().isRunning()) {
			start();
		}
	}
		
	public void removeActivity(IActivity activity) {
		if (!m_activities.contains(activity)) return;

		m_activities.remove(activity);
		
		if (m_activities.size() == 0) {
			stopTimer();
		}					
	}

	public void removeAllActivities() {			
		m_activities.clear();
		stopTimer();
	}

	private void processActivities(long current) {
		int size = m_activities.size();
		if (size > 0) {
			m_processing.clear();
			m_processing.addAll(m_activities);
			IActivity activity = null;
			for (int i = size - 1; i >= 0; i--) {
				activity = (IActivity) m_processing.get(i);
				if(!activity.isEnable()) continue;
				activity.process(current);
			}
		}		
	}
		
	private void start() {
		getTimer().start();
	}
	
	private void stopTimer() {
		getTimer().stop();
	}
	
	private Timer getTimer() {
		if (m_timer == null) {
			m_timer = new Timer (ACTIVITY_FRAME_DELAY, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					processActivities(System.currentTimeMillis());
				}
			});
		}
		return m_timer;
	}
}
