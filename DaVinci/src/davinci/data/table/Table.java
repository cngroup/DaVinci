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
package davinci.data.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import davinci.data.AbstractData;
import davinci.data.IDimension;
import davinci.data.PropHelper;

public class Table<N> extends AbstractData implements ITable<N> {

	private static final long serialVersionUID = 4467168296932613518L;
	
	private Map<String, N> m_ridx = null;
	private List<N> m_rows = null;
	private List<IDimension> m_columns = null;

	public Table() {
		this(null);
	}
	
	public Table(IDimension... dims) {
		m_rows = new ArrayList<N>();
		m_ridx = new HashMap<String, N>();
		m_columns = new ArrayList<IDimension>();
		if(dims == null) return;
		for(int i= 0; i < dims.length; ++i) {
			addColumn(dims[i]);
		}
	}
	
	public void addColumn(IDimension dim) {
		super.addDimension("table", dim);
		m_columns.add(dim);
	}
	
	public IDimension getColumn(String id) {
		return super.getDimension("table", id);
	}
	
	public IDimension getColumn(int i) {
		return m_columns.get(i);
	}

	public void addRow(N row) {
		String id = PropHelper.getID(row);
		m_rows.add(row);
		m_ridx.put(id, row);
	}
	
	public N getRow(String key) {
		return m_ridx.get(key);
	}

	public IDimension[] getDimensions() {
		return super.getDimensions("table");
	}

	public N getRow(int i) {
		return m_rows.get(i);
	}

	public int getRowCount() {
		return m_rows.size();
	}
	
	public void clearColumns() {
		super.clearDimensions("table");
	}
	
	public void clear() {
		m_rows.clear();
		m_ridx.clear();
		m_columns.clear();
	}

	@Override
	public Object getValue(int i, int j) {
		N r = m_rows.get(i);
		IDimension c = m_columns.get(j);
		return c.getValue(r);
	}

	@Override
	public void removeRow(N row) {
		m_rows.remove(row);
		String id = PropHelper.getID(row);
		m_ridx.put(id, row);
	}

}
