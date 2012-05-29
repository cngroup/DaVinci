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

import java.io.Serializable;

import davinci.Display;
import davinci.util.IPacer;




public interface IActivity extends Comparable, Serializable {

	public void setEnable(boolean enable);

	public boolean isEnable();

	public void setDuration(long duration);

	public long getDuration();

	public void setRate(long rate);

	public long getRate();

	public void setStartTime(long rate);

	public long getStartTime();

	public void setPacer(IPacer pacer);

	public IPacer getPacer();

	public boolean isPerforming();

	public void setActivityManager(ActivityManager manager);

	public void after(IActivity activity);

	public void before(IActivity activity);

	public void setDisplay(Display disp);

	public Display getDisplay();

	public void setParam(Object param);

	public Object getParam();

	public boolean start();

	public void finish();

	public void pause();

	public boolean isPaused();

	public void terminate();

	public long process(long current);

	public void addListener(IActivityListener d);

	public void removeListener(IActivityListener d);

	public void clearAllListeners();

	public IActivityGroup getOwner();

	public void setOwner(IActivityGroup group);

	public IActivity next();

	public boolean hasNext();

}
