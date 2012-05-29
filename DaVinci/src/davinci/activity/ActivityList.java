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
import java.util.List;

public class ActivityList extends Activity implements IActivityGroup, IActivityListener {

	private static final long serialVersionUID = 8116905038174627634L;

	protected List<IActivity> m_activities = null;

	protected IActivity m_current = null;
	
	protected int m_cur = 0;
	
	protected String m_id = "";

	public ActivityList() {
		this(30);
	}
	
	public ActivityList(long rate) {
		super(-1, rate);
		m_activities = new ArrayList<IActivity>();
		m_id = String.valueOf(hashCode());
	}
	
	public ActivityList(String id) {
		super(-1, 30);
		m_activities = new ArrayList<IActivity>();
		m_id = id;		
	}

	public synchronized void add(IActivity act) {
		act.before(null);
		act.setOwner(this);
		m_activities.add(act);
	}

	public synchronized void remove(IActivity act) {
		m_activities.remove(act);
	}

	public synchronized IActivity remove(int idx) {
		return (IActivity) ((List) m_activities).remove(idx);
	}

	public synchronized void removeAll() {
		m_activities.clear();
	}

	public IActivity[] toArray() {
		return (IActivity[]) m_activities.toArray(new IActivity[] {});
	}

	public int size() {
		return m_activities.size();
	}

	public IActivity getActivity(int idx) {
		return (IActivity) ((List) m_activities).get(idx);
	}

	public synchronized void addActivity(int idx, IActivity act) {
		m_activities.add(idx, act);
	}

	public boolean isEmpty() {
		return m_activities.isEmpty();
	}

	////////////////////////////////////////////////////////////////
	// Overwrite
	////////////////////////////////////////////////////////////////
	public long process(long current) {
		
		m_delta = (current - m_start);
		
		if (!isPerforming) {
			if (start()) {
				isPerforming = true;
			} else {
				this.terminate();
				return -1;
			}
		}
		
//		System.out.println("[" + m_id + "] is running");
		
		if(m_current.getStartTime() < 0) {
			m_current.setStartTime(current);
		}
		
		return m_current.process(current);
	}
	
	public boolean start() {
		
//		System.out.println("[" + m_id + "] started");
		
		if(!super.start()) {
			return false;
		}
		
		if(m_activities.isEmpty()) {
//			System.out.println("[" + m_id + "] is empty");
			return false;
		}
		
		m_current = (IActivity)m_activities.get(0);
		m_cur = 0;
		m_current.removeListener(this);
		m_current = (IActivity)m_activities.get(m_cur);
		m_current.addListener(this);
		m_current.setDisplay(m_disp);
		m_current.setParam(m_param);
		m_current.setStartTime(-1);
		return true;
	}
	
	public void finish() {
//		System.out.println("[" + m_id + "] finished");
		super.finish();
	}
	
	public void pause() {
		if (isPaused) {
			m_current.setStartTime(System.currentTimeMillis() - m_delta);
			m_manager.addActivity(this);
		} else {
			m_manager.removeActivity(this);
		}
		isPaused = !isPaused;
	}
	
	public void addStepListener(IActivityListener l) {
	}
	
	/////////////////////////////////////////////////////////////////
	// implementation of activity listener
	/////////////////////////////////////////////////////////////////
	public boolean start(Object context) {return true;}

	public void finish(Object context) {
//		System.out.println("Animation [" + m_id + "]  switched. size = " + m_activities.size() + ", cur = " + m_cur);
		if(m_cur + 1 < m_activities.size()) {
			m_current.removeListener(this);
			m_current = (IActivity)m_activities.get(++m_cur);
			m_current.addListener(this);
			m_current.setDisplay(m_disp);
			m_current.setParam(m_param);
			m_current.setStartTime(-1);
		} else {
			m_current.removeListener(this);
			this.terminate();
		}
	}

	public void perform(Object context, double frac) {}

	public void setActivity(IActivity act) {}

	public IActivity getActivity() {
		return m_current;
	}
	
	public String toString() {
		return String.valueOf("ActivityList[" + m_id + "] : " + hashCode());
	}
	
	public String getID() {
		return m_id;
	}
}
