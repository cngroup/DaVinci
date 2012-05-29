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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityGroup extends Activity implements IActivityGroup {

	private static final long serialVersionUID = 8116905038174627634L;
	protected List m_activities = null; 
	
	public ActivityGroup() {	
		m_activities = new ArrayList(0);
	}
	
	public ActivityGroup(long duration, long rate) {
		super(duration, rate);
		m_activities = new ArrayList(0);
	}
	
	public ActivityGroup(long duration, long rate, long start) {
		super(duration, rate, start);
		m_activities = new ArrayList(0);
	}
	
	public synchronized void add(IActivity act) {
		m_activities.add(act);
	}
	
	public synchronized void remove(IActivity act) {
		m_activities.remove(act);
	}
	
	public synchronized IActivity remove(int idx) {
		return (IActivity)m_activities.remove(idx);
	}
	
	public synchronized void removeAll() {
		m_activities.clear();
	}
	
	public IActivity[] toArray() {
		return (IActivity[])m_activities.toArray(new IActivity[]{});
	}
	
	public int size() {
		return m_activities.size();
	}
	
	public IActivity getActivity(int idx) {
		return (IActivity)m_activities.get(idx);
	}
	
	public synchronized void addActivity(int idx, IActivity act) {
		m_activities.add(idx, act);
	}
	
	public void pause() {
		super.pause();
		synchronized (m_activities) {
			int size = m_activities.size();
			Activity act = null;
			for(int i = 0; i < size; ++i) {
				act = ((Activity)m_activities.get(i));
				act.isPaused = isPaused;
			}
		}
	}

	public void perform(double frac) {
		super.perform(frac);
		synchronized (m_activities) {
			int size = m_activities.size();
			Activity act = null;
			for(int i = 0; i < size; ++i) {
				act = ((Activity)m_activities.get(i));
				act.perform(frac);
			}
		}
	}
	
	public void finish() {
		synchronized (m_activities) {
			int size = m_activities.size();
			Activity act = null;
			for(int i = 0; i < size; ++i) {
				act = ((Activity)m_activities.get(i));
				act.isPerforming = false;
				act.finish();
			}
		}
		super.finish();
	}
	
	public boolean start() {
		if(super.start()) {
			synchronized (m_activities) {
				Collections.sort(m_activities);
				boolean flag = true;
				int size = m_activities.size();
				Activity act = null;
				for(int i = 0; i < size; ++i) {
					act = ((Activity)m_activities.get(i));
					act.isPerforming = true;
					act.setActivityManager(m_manager);
					flag = act.start();
					if(!flag) {
						break;
					}
				}
				return flag;
			}
		}
		return false;
	}
	
	public boolean isEmpty() {
		return m_activities.isEmpty();
	}
}
