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

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.EventListener;

import davinci.Display;
import davinci.data.elem.IElement;

/**
 * The general interface of interactions. All the user interactions should<br>
 * implement this interface.
 * 
 * @see davinci.interaction.ActionAdapter
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public interface IAction extends EventListener {

	public void setOwner(Display disp);

	public Display getOwner();

	public void setEnable(boolean enable);

	public boolean isEnable();

	public void setGeneralAction(boolean bGeneral);

	public boolean isGeneralAction();

	public void setActionMask(int mask);

	public int getActionMask();

	public void actionAdded();

	public void actionRemoved();

	public void elemsSelected(IElement[] elements);

	public void elemDragged(IElement element, MouseEvent e);

	public void elemMoved(IElement element, MouseEvent e);

	public void elemWheelMoved(IElement element, MouseWheelEvent e);

	public void elemClicked(IElement element, MouseEvent e);

	public void elemPressed(IElement element, MouseEvent e);

	public void elemReleased(IElement element, MouseEvent e);

	public void elemEntered(IElement element, MouseEvent e);

	public void elemExited(IElement element, MouseEvent e);

	public void elemKeyPressed(IElement element, KeyEvent e);

	public void elemKeyReleased(IElement element, KeyEvent e);

	public void elemKeyTyped(IElement element, KeyEvent e);

	public void mouseEntered(MouseEvent e);

	public void mouseExited(MouseEvent e);

	public void mousePressed(MouseEvent e);

	public void mouseReleased(MouseEvent e);

	public void mouseClicked(MouseEvent e);

	public void mouseDragged(MouseEvent e);

	public void mouseMoved(MouseEvent e);

	public void mouseWheelMoved(MouseWheelEvent e);

	public void keyPressed(KeyEvent e);

	public void keyReleased(KeyEvent e);

	public void keyTyped(KeyEvent e);
}
