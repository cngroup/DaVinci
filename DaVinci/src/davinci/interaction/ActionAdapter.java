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
package davinci.interaction;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import davinci.Display;
import davinci.data.elem.IElement;

/**
 * 
 * Default implementation of IAction interface. This is a empty interaction does<br>
 * nothing. Users can overwrite its methods when necessary.
 * 
 * @see davinci.interaction.IAction
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public class ActionAdapter implements IAction {

	protected boolean m_bEnable = true;

	protected boolean m_bGeneralAction = true;

	protected Display m_owner;

	protected int m_mask = MouseEvent.BUTTON3;

	protected boolean m_trigger = false;

	protected Point m_mousePt = null;

	protected boolean m_isdragged = false;

	public ActionAdapter() {
	}

	public void elemsSelected(IElement[] elems) {
	}

	public void elemDragged(IElement element, MouseEvent e) {
		if (m_bGeneralAction) {
			mouseDragged(e);
		}
	}

	public void elemMoved(IElement element, MouseEvent e) {
		if (m_bGeneralAction) {
			mouseMoved(e);
		}
	}

	public void elemWheelMoved(IElement element, MouseWheelEvent e) {
		if (m_bGeneralAction) {
			mouseWheelMoved(e);
		}
	}

	public void elemClicked(IElement element, MouseEvent e) {
		if (m_bGeneralAction) {
			mouseClicked(e);
		}
	}

	public void elemPressed(IElement element, MouseEvent e) {
		m_mousePt = e.getPoint();
		if (m_bGeneralAction) {
			mousePressed(e);
		}
	}

	public void elemReleased(IElement element, MouseEvent e) {
		if (m_bGeneralAction) {
			mouseReleased(e);
		}
	}

	public void elemEntered(IElement element, MouseEvent e) {
	}

	public void elemExited(IElement element, MouseEvent e) {
	}

	public void elemKeyPressed(IElement element, KeyEvent e) {
		if (m_bGeneralAction) {
			keyPressed(e);
		}
	}

	public void elemKeyReleased(IElement element, KeyEvent e) {
		if (m_bGeneralAction) {
			keyReleased(e);
		}
	}

	public void elemKeyTyped(IElement element, KeyEvent e) {
		if (m_bGeneralAction) {
			keyTyped(e);
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		m_mousePt = e.getPoint();
	}

	public void mouseReleased(MouseEvent e) {
		if (m_isdragged) {
			m_isdragged = false;
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		m_isdragged = true;
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void setEnable(boolean enable) {
		m_bEnable = enable;
	}

	public boolean isEnable() {
		return m_bEnable;
	}

	public boolean isGeneralAction() {
		return m_bGeneralAction;
	}

	public void setGeneralAction(boolean bGeneral) {
		m_bGeneralAction = bGeneral;
	}

	public void setOwner(Display disp) {
		m_owner = disp;
	}

	public Display getOwner() {
		return m_owner;
	}

	public void setActionMask(int mask) {
		m_mask = mask;
	}

	public int getActionMask() {
		return m_mask;
	}

	public void actionAdded() {
	}

	public void actionRemoved() {
	}

	public void perform(Object param) {
	}

	public void reset() {
	}
}
