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

import davinci.Display;
import davinci.util.IPacer;
import davinci.util.SlowInSlowOutPacer;

public class Activity implements IActivity {

	private static final long serialVersionUID = 2625797524160810597L;

	protected long m_duration = 1000;

	protected long m_rate = 40;

	protected long m_start = -1;

	protected long m_delta = 0;

	protected boolean isPerforming = false;

	protected IPacer m_pacer = null;

	protected IActivity m_next = null;

	// protected int m_steps = 0;

	protected ActivityManager m_manager = null;

	protected Display m_disp = null;

	protected boolean m_isEnable = true;

	protected List m_delegators = null;

	protected boolean isPaused = false;

	protected Object m_param = null;

	protected IActivityGroup m_owner = null;

	public Activity() {
		this(1000);
	}

	public Activity(long duration) {
		this(duration, 40);
	}

	public Activity(long duration, long rate) {
		this(duration, rate, -1);
	}

	public Activity(long duration, long rate, long start) {
		m_pacer = new SlowInSlowOutPacer();
		m_duration = duration;
		m_start = start;
		m_rate = rate;
		// if(m_duration == 0) {
		// m_steps = 1;
		// } else if(m_duration == -1) {
		// m_steps = Integer.MAX_VALUE;
		// } else {
		// m_steps = (int)(m_duration / m_rate + 1);
		// }
		m_delegators = new ArrayList(0);
	}

	public void setPacer(IPacer pacer) {
		m_pacer = pacer;
	}

	public IPacer getPacer() {
		return m_pacer;
	}

	public void setActivityManager(ActivityManager manager) {
		m_manager = manager;
	}

	public void setDisplay(Display disp) {
		m_disp = disp;
	}

	public void setDuration(long duration) {
		m_duration = duration;
		// if(m_duration == 0) {
		// m_steps = 1;
		// } else if(m_duration == -1) {
		// m_steps = Integer.MAX_VALUE;
		// } else {
		// m_steps = (int)(m_duration / m_rate + 1);
		// }
	}

	public void setRate(long rate) {
		m_rate = rate;
		// if(m_duration == 0) {
		// m_steps = 1;
		// } else if(m_duration == -1) {
		// m_steps = Integer.MAX_VALUE;
		// } else {
		// m_steps = (int)(m_duration / m_rate + 1);
		// }
	}

	public void setStartTime(long start) {
		m_start = start;
	}

	public boolean isPerforming() {
		return isPerforming;
	}

	public void after(IActivity activity) {
		activity.before(this);
	}

	public void before(IActivity activity) {
		this.m_next = activity;
	}

	public boolean start() {
		return this.fireStarted(m_disp);
	}

	public void finish() {
		fireFinished(m_disp);
		if (m_next != null) {
			m_next.setActivityManager(m_manager);
			m_next.setStartTime(System.currentTimeMillis());
			m_manager.addActivity(m_next);
		}
	}

	public void terminate() {
		isPerforming = false;
		if (m_manager != null) {
			m_manager.removeActivity(this);
		}
		finish();
	}

	public void pause() {
		if (isPaused) {
			m_start = System.currentTimeMillis() - m_delta;
			m_manager.addActivity(this);
		} else {
			m_manager.removeActivity(this);
		}
		isPaused = !isPaused;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public long process(long current) {

		if (current < m_start) {
			return 0;
		}

		if (m_start == -1) {
			m_start = System.currentTimeMillis();
		}

		m_delta = (current - m_start);

		if (m_duration < 0) {
			if (!isPerforming) {
				if (start()) {
					isPerforming = true;
				} else {
					this.terminate();
					return -1;
				}
			}
			long t1 = System.currentTimeMillis();
			perform(1);
			m_start = System.currentTimeMillis();
			return m_start - t1;
		} else if ((m_duration == 0 || m_delta >= m_duration)) {
			// if past stop stemp
			if (isPerforming) {
				isPerforming = false;
				if (null != m_manager) {
					m_manager.removeActivity(this);
				}
				perform(1);
				finish();
			} else {
				if (start()) {
					perform(1);
					if (null != m_manager) {
						m_manager.removeActivity(this);
					}
					finish();
				}
			}
			return -1;
		}

		long t1 = System.nanoTime();
		// else should be stepping
		if (!isPerforming) {
			if (start()) {
				isPerforming = true;
			} else {
				this.terminate();
				return -1;
			}
		}

		double rate = (double) m_delta / m_duration;
		if (m_pacer != null) {
			rate = m_pacer.pace(rate);
		}

		perform(rate);
		long t2 = System.nanoTime();
		long delta = (t2 - t1) - m_rate * 1000000;
		if (delta > 0) {
			m_duration = m_duration + Math.round(delta / 1000000D);
		}
		// else {
		// delta = Math.round(-delta / 1000000D);
		// try {
		// Thread.sleep(delta);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// System.out.println("real rate = " + (t2 - t1) / 1000000D + ", rate =
		// " +
		// m_rate + ", duration = " + m_duration);
		return Math.round((t2 - t1) / 1000000D);
	}

	public void perform(double frac) {
		firePrefromed(m_disp, frac);
	}

	public long getDuration() {
		return m_duration;
	}

	public long getRate() {
		return m_rate;
	}

	public long getStartTime() {
		return m_start;
	}

	public Display getDisplay() {
		return m_disp;
	}

	public void setEnable(boolean enable) {
		m_isEnable = enable;
	}

	public boolean isEnable() {
		return m_isEnable;
	}

	public void addListener(IActivityListener d) {
		if(!m_delegators.contains(d)) {
			m_delegators.add(d);
			d.setActivity(this);
		}
	}

	public void removeListener(IActivityListener d) {
		m_delegators.remove(d);
	}

	public void clearAllListeners() {
		m_delegators.clear();
	}

	public boolean fireStarted(Object context) {
		int size = m_delegators.size();
		IActivityListener d = null;
		boolean flag = true;
		for (int i = 0; i < size; ++i) {
			d = (IActivityListener) m_delegators.get(i);
			flag = d.start(context);
			if (!flag) {
				break;
			}
		}
		return flag;
	}

	public void fireFinished(Object context) {
		int size = m_delegators.size();
		IActivityListener d = null;
		for (int i = 0; i < size; ++i) {
			d = (IActivityListener) m_delegators.get(i);
			d.finish(context);
		}
	}

	public void firePrefromed(Object context, double frac) {
		int size = m_delegators.size();
		IActivityListener d = null;
		for (int i = 0; i < size; ++i) {
			d = (IActivityListener) m_delegators.get(i);
			d.perform(context, frac);
		}
	}

	public IActivity next() {
		return m_next;
	}

	public boolean hasNext() {
		return m_next != null;
	}

	public void setParam(Object param) {
		m_param = param;
	}

	public Object getParam() {
		return m_param;
	}

	public int compareTo(Object obj) {
		IActivity act = (IActivity) obj;
		long result = m_start - act.getStartTime();
		if (result > 0) {
			return 1;
		} else if (result < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	public IActivityGroup getOwner() {
		return m_owner;
	}

	public void setOwner(IActivityGroup group) {
		m_owner = group;
	}
}
