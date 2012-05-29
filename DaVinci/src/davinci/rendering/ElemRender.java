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
package davinci.rendering;

import java.awt.Shape;

import davinci.Display;
import davinci.data.elem.IElement;
import davinci.data.elem.IVisualNode;

public abstract class ElemRender implements IElemRender {

	protected Display m_owner = null;
	
	public ElemRender() {
	}
	
	@Override
	public void getOwner(Display disp) {
		m_owner = disp;
	}

	@Override
	public void setOwner(Display owner) {
		m_owner = owner;
	}
	
	public boolean locatePoint(double x, double y, IElement e) {
		if (e instanceof IVisualNode) {
			Shape s = getRawShape(e);
			if(s == null) {
				return false;
			}
			return null == s ? false : s.contains(x, y);
		}
		return false;
	}
}
