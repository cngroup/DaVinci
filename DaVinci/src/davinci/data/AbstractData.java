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
package davinci.data;

import java.util.HashMap;
import java.util.Map;

/**
 * The abstract implementation of IData. This class implemented all the<br>
 * methods for dimension handling<br><br>
 * 
 * @see davinci.data.IData
 * @see davinci.data.IDimension
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public class AbstractData implements IData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6405692550504196791L;
	
	protected Map<String, Map<String, IDimension>> m_dims = null;

	protected String m_id = null;
	
	public AbstractData() {
		m_dims = new HashMap<String, Map<String, IDimension>>();
	}
	
	public void clear() {
	}

	public String getID() {
		return m_id;
	}

	public void setID(String id) {
		m_id = id;
	}
	
	public void addDimension(String group, IDimension dim) {
		Map<String, IDimension> dims = m_dims.get(group);
		if(null == dims) {
			dims = new HashMap<String, IDimension>();
			m_dims.put(group, dims);
		}
		dims.put(dim.getID(), dim);
	}
	
	public IDimension[] getDimensions(String group) {
		Map<String, IDimension> dims = m_dims.get(group);
		return dims.values().toArray(new IDimension[]{});
	}
	
	public IDimension getDimension(String group, String id) {
		Map<String, IDimension> dims = m_dims.get(group);
		if(dims == null) {
			return null;
		}
		return dims.get(id);
	}
	
	public IDimension removeDimension(String group, String id) {
		Map<String, IDimension> dims = m_dims.get(group);
		if(null != dims) return dims.remove(id);
		return null;
	}
	
	public void clearDimensions(String group) {
		Map<String, IDimension> dims = m_dims.get(group);
		if(null != dims) dims.clear();
	}

	public String[] getDimensionGroups() {
		return m_dims.keySet().toArray(new String[0]);
	}
}
