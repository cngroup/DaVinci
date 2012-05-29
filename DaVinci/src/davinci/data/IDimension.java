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
 * This interface define a single data dimension. Each data may contain<br>
 * multiple dimensions. Each value in the dimension can be seen as an <br>
 * attribute in the IElement.
 * 
 * @see davinci.data.Dimension
 * @see davinci.data.IData
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 * @param <V>
 *            the data type within this dimension
 */
public interface IDimension<V> {

	public static final int META_TYPE_NONE = 0x0000;

	public static final int META_TYPE_NUMERICAL = 0x00000001;

	public static final int META_TYPE_CATEGORICAL = 0x00000010;

	public static final int META_TYPE_TEXT = 0x00000100;

	public static final int META_TYPE_ID = 0x00001000;

	public static final int META_TYPE_SPACIAL = 0x00010000;

	public static final int META_TYPE_TEMPORAL = 0x00100000;

	public static final int META_TYPE_VECTOR = 0x01000000;

	public String getID();

	public String getName();

	public int getMetaType();

	public V getValue(Object elem);

	public void setValue(Object elem, V value);
}
