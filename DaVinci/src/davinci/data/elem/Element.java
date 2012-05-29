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
package davinci.data.elem;

import java.util.HashMap;

import davinci.data.IData;

/**
 * The default implementation of IElement
 * 
 * @see davinci.data.IElement
 * @see davinci.data.IDimension
 * @see davinci.data.IData
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public class Element implements IElement {

	private static final long serialVersionUID = 4714362416787485183L;

	protected boolean bfocus = false;
	
	protected boolean bhighlight = false;
	
	protected boolean bselected = false;
	
	protected boolean bvisible = true;
	
	protected boolean bfixed = false;
	
	protected IData m_owner = null;
	
	protected HashMap<String, Object> m_attr = null;
	
	public Element() {
		m_attr = new HashMap<String, Object>();
	}
	
	public String getID() {
		return (String)m_attr.get("id");
	}
	
	public void setID(String id) {
		m_attr.put("id", id);
	}

	public String getLabel() {
		return (String)m_attr.get("label");
	}
	
	public void setLabel(String label) {
		m_attr.put("label", label);
	}

	public boolean isFocused() {
		return bfocus;
	}

	public boolean isSelected() {
		return bselected;
	}
	
	public boolean isVisible() {
		return bvisible;
	}
	
	public boolean isFixed() {
		return bfixed;
	}
	
	public void setFixed(boolean bfixed) {
		this.bfixed = bfixed;
	}
	
	public void setVisible(boolean bvisible) {
		this.bvisible = bvisible;
	}

	public void setFocused(boolean bfocus) {
		this.bfocus = bfocus;
	}

	public void setSelected(boolean bselected) {
		this.bselected = bselected;
	}

	public boolean isHighlight() {
		return this.bhighlight;
	}

	public void setHighlight(boolean bhighlight) {
		this.bhighlight = bhighlight;
	}
	
	public IData getOwner() {
		return m_owner;
	}
	
	public void setOwner(IData data) {
		m_owner = data;
	}

	public double getWeight() {
		Double wei = (Double)get("weight");
		return wei == null ? 0 : wei.doubleValue();
	}

	public void setWeight(double wei) {
		put("weight", new Double(wei));
	}

	public Object get(String key) {
		return m_attr.get(key);
	}

	public void put(String key, Object attr) {
		m_attr.put(key, attr);
	}

	public Object remove(String key) {
		return m_attr.remove(key);
	}

	public int compareTo(IElement elem) {
		double w1 = getWeight();
		double w2 = elem.getWeight();
		if(w1 > w2) {
			return -1;
		} else if(w1 < w2) {
			return 1;
		}
		return 0;
	}
	
	public String toString() {
		return this.getID() + ":" + this.getLabel();
	}
}
