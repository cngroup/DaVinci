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


/**
 * 
 * The default implementation of IDimension
 * 
 * @see davinci.data.Dimension
 * @see davinci.data.IData
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 * @param <V>
 *            the data type within this dimension
 */
public class Dimension<V> implements IDimension<V> {

	protected String m_id = "";
	protected String m_name = "";
	protected int m_metatype = 0;
	
	public Dimension(String id, String name, int metatype) {
		m_id = id;
		m_name = name;
		m_metatype = metatype;
	}
	
	public String getID() {
		return m_id;
	}

	public int getMetaType() {
		return m_metatype;
	}

	public String getName() {
		return m_name;
	}

	public V getValue(Object elem) {
		return (V)PropHelper.getProperty(m_id, elem);
	}

	public void setValue(Object elem, V value) {
		PropHelper.setProperty(elem, getID(), value);
	}
	
	public static boolean isCategorical(int metatype) {
		return (metatype & IDimension.META_TYPE_CATEGORICAL) == IDimension.META_TYPE_CATEGORICAL;
	}
	
	public static boolean isText(int metatype) {
		return (metatype & IDimension.META_TYPE_TEXT) == IDimension.META_TYPE_TEXT;
	}
	
	public static boolean isNumerical(int metatype) {
		return (metatype & IDimension.META_TYPE_NUMERICAL) == IDimension.META_TYPE_NUMERICAL;
	}
	
	public static boolean isSpacial(int metatype) {
		return (metatype & IDimension.META_TYPE_SPACIAL) == IDimension.META_TYPE_SPACIAL;
	}
	
	public static boolean isTemporal(int metatype) {
		return (metatype & IDimension.META_TYPE_TEMPORAL) == IDimension.META_TYPE_TEMPORAL;
	}
	
	public static boolean isID(int metatype) {
		return (metatype & IDimension.META_TYPE_ID) == IDimension.META_TYPE_ID;
	}
	
	public String toString() {
		return m_name;
	}
	
}
