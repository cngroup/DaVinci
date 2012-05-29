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

import java.awt.Graphics2D;

import davinci.Display;
import davinci.data.PropHelper;
import davinci.data.elem.IEdge;
/**
 * Default implementation of IDisplayRender
 * 
 * @see davinci.rendering.IDisplayRender
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public abstract class DisplayRender implements IDisplayRender {

	protected Display m_owner = null;
	
	protected boolean m_benable = true;
	
	public DisplayRender() {
	}
	
	public Display getOwner() {
		return m_owner;
	}
	
	@Override
	public void setOwner(Display disp) {
		m_owner = disp;
	}
	
	@Override
	public void setEnable(boolean benable) {
		m_benable = benable;
	}

	@Override
	public boolean isEnabled() {
		return m_benable;
	}
	
	public IElemRender getNodeRender(Object node) {
		if(m_owner == null) {
			return null;
		}
		IElemRender r = m_owner.getElemRender(PropHelper.getID(node));
		if(r == null) {
			r = m_owner.getElemRender("node");
		}
		return r;
	}
	
	public IElemRender getEdgeRender(IEdge edge) {
		if(m_owner == null) {
			return null;
		}
		IElemRender r = m_owner.getElemRender(edge.getID());
		if(r == null) {
			r = m_owner.getElemRender("edge");
		}
		return r;
	}
	
	public IElemTheme getNodeTheme(Object node) {
		if(m_owner == null) {
			return null;
		}
		IElemTheme theme = m_owner.getElemTheme(PropHelper.getID(node));
		if(theme == null) {
			theme = m_owner.getElemTheme("node");
		}
		return theme;
	}
	
	public IElemTheme getEdgeTheme(IEdge edge) {
		if(m_owner == null) {
			return null;
		}
		IElemTheme theme = m_owner.getElemTheme(edge.getID());
		if(null == theme) {
			theme = m_owner.getElemTheme("edge");
		}
		return theme;
	}

	@Override
	public abstract void render(Graphics2D g);
}
