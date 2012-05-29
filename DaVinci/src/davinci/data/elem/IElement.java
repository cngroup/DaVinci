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

import java.io.Serializable;

import davinci.data.IData;

/**
 * 
 * This interface defines a single data element. Each element may has multiple<br>
 * attributes which can be treated as data dimensions. Two types of elements, <br>
 * INode and IEdge, are defined in DaVici. The elements will be stored and managed<br>
 * in a data structure which implements IData.
 * 
 * @see davinci.data.Element
 * @see davinci.data.Dimension
 * @see davinci.data.IData
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public interface IElement extends Serializable, Comparable<IElement> {

	public String getID();

	public void setID(String id);

	public String getLabel();

	public void setLabel(String label);

	public void setWeight(double wei);

	public double getWeight();

	public boolean isFocused();

	public boolean isSelected();

	public boolean isHighlight();

	public boolean isVisible();

	public boolean isFixed();

	public void setVisible(boolean bvisible);

	public void setFocused(boolean bfocus);

	public void setSelected(boolean bselected);

	public void setHighlight(boolean bhighlight);

	public void setFixed(boolean bfix);

	public IData getOwner();

	public void setOwner(IData owner);

	public void put(String key, Object attr);

	public Object get(String key);

	public Object remove(String key);

}
