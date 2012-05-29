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

import davinci.data.IData;
import davinci.data.IDimension;

/**
 * The table data structure.
 * 
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 *
 * @param <N> the data type of the table rows.
 */
public interface ITable<N> extends IData {

	public void addRow(N row);
	
	public void removeRow(N row);
	
	public N getRow(int i);
	
	public N getRow(String key);
	
	public int getRowCount();
	
	public Object getValue(int i, int j);
	
	public void addColumn(IDimension dim);
	
	public IDimension getColumn(String id);
	
	public IDimension getColumn(int i);
	
	public IDimension[] getDimensions();
	
}
