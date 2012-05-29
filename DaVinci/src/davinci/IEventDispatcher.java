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
package davinci;

import java.awt.event.InputEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import davinci.data.elem.IElement;
import davinci.interaction.IAction;


/**
 * 
 * This interface dispatches all the input events (mouse events, keyboard events)<br>
 * to an action that are implemented from IAction by users.
 * 
 * @since V1.0
 * @version 1.0
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 *
 */
public interface IEventDispatcher extends MouseMotionListener, MouseWheelListener, MouseListener, KeyListener {
	
    public static final int INPUT_EVENT_ELEM_ENTERED = 0;
    public static final int INPUT_EVENT_ELEM_EXITED = 1;
    public static final int INPUT_EVENT_ELEM_PRESSED = 2;
    public static final int INPUT_EVENT_ELEM_RELEASED = 3;
    public static final int INPUT_EVENT_ELEM_CLICKED = 4;
    public static final int INPUT_EVENT_ELEM_DRAGGED = 5;
    public static final int INPUT_EVENT_ELEM_MOVED = 6;
    public static final int INPUT_EVENT_ELEM_WHEEL_MOVED = 7;
    public static final int INPUT_EVENT_ELEM_KEY_PRESSED = 8;
    public static final int INPUT_EVENT_ELEM_KEY_RELEASED = 9;
    public static final int INPUT_EVENT_ELEM_KEY_TYPED = 10;
    
    public static final int INPUT_EVENT_MOUSE_ENTERED = 11;
    public static final int INPUT_EVENT_MOUSE_EXITED = 12;
    public static final int INPUT_EVENT_MOUSE_PRESSED = 13;
    public static final int INPUT_EVENT_MOUSE_RELEASED = 14;
    public static final int INPUT_EVENT_MOUSE_CLICKED = 15;
    public static final int INPUT_EVENT_MOUSE_DRAGGED = 16;
    public static final int INPUT_EVENT_MOUSE_MOVED = 17;
    public static final int INPUT_EVENT_MOUSE_WHEEL_MOVED = 18;
    public static final int INPUT_EVENT_KEY_PRESSED = 19;
    public static final int INPUT_EVENT_KEY_RELEASED = 20;
    public static final int INPUT_EVENT_KEY_TYPED = 21;
    
    public void dispatchEvents(int type, IElement ve, InputEvent e);
    
	public void addAction(IAction l);
	public void removeAction(IAction l);
	public void removeActions();
}
