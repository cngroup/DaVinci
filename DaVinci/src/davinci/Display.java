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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JPanel;

import davinci.activity.ActivityManager;
import davinci.data.DataMapping;
import davinci.data.IData;
import davinci.data.Slot;
import davinci.data.elem.IElement;
import davinci.interaction.IAction;
import davinci.rendering.IDisplayRender;
import davinci.rendering.IElemRender;
import davinci.rendering.IElemTheme;

/**
 * Display is the primary class in DaVicin. Display manages the visualization<Br>
 * pipeline based on the reference model of information visualization. Users can<BR>
 * write their own visualization component by extends from the "Display" class.<BR> 
 * <BR>
 * It aggregates all the other key modules in DaVicin including the data module,<BR>
 * the layout module, the rendering module, the interaction module and the<BR>
 * animation module. APIs are designed to allow uers to set their own layouts,<BR>
 * renders, interactions and animators.<BR>
 * 
 * @since V1.0
 * @version 1.0
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public class Display extends JPanel implements IEventDispatcher {

	private static final long serialVersionUID = 8980739501796084434L;

	protected String m_vjitid = "";

	// data
	protected Map<String, IData> m_data = null;
	protected Map<Slot, DataMapping> m_mappings = null;
	protected Map<Object, IElemTheme> m_elemthemes = null;
	protected Map<Object, IElemRender> m_elemrenders = null;

	// rendering
	protected List<IDisplayRender> m_backrenders = null;
	protected List<IDisplayRender> m_forerenders = null;
	protected IDisplayRender m_render = null;
	protected List<IAction> m_actions = null;
	protected Image m_buffer = null;
	protected Graphics2D bufGraphics = null;

	// layout
	protected List<ILayout> m_layouts = null;

	// mouse event
	protected IElemFinder m_finder = null;
	protected boolean mouseDragged = false;
	protected boolean mouseMoved = false;
	protected Point2D.Double m_tempPoint = null;
	protected int mouseX = -1;
	protected int mouseY = -1;
	protected IElement focus;
	protected IElement dragged;

	protected int m_width = 0;
	protected int m_height = 0;

	// animation
	protected ActivityManager m_manager = null;

	protected Set<Slot> m_slots = null;

	public Display() {
		this("");
	}

	public Display(String id) {
		m_vjitid = id;
		m_layouts = new ArrayList<ILayout>();
		m_backrenders = new ArrayList<IDisplayRender>();
		m_forerenders = new ArrayList<IDisplayRender>();
		m_tempPoint = new Point2D.Double();
		m_manager = new ActivityManager();
		m_mappings = new HashMap<Slot, DataMapping>();
		m_elemthemes = new HashMap<Object, IElemTheme>();
		m_elemrenders = new HashMap<Object, IElemRender>();
		m_slots = new TreeSet<Slot>();
		m_data = new HashMap<String, IData>();
		m_actions = new ArrayList<IAction>();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
	}

	public String getID() {
		return m_vjitid;
	}

	public void addSlot(Slot slot) {
		m_slots.add(slot);
	}

	public Slot[] getSlots() {
		return m_slots.toArray(new Slot[] {});
	}

	public void setID(String id) {
		this.m_vjitid = id;
	}

	public void paintComponent(Graphics g) {
		int margin = 10;
		int wmargin = 20;
		bufGraphics.setBackground(getBackground());
		bufGraphics.clearRect(-margin, -margin, getWidth() + wmargin,
				getHeight() + wmargin);
		for (IDisplayRender r : m_backrenders) {
			if (r.isEnabled())
				r.render(bufGraphics);
		}
		if (null != m_render && m_render.isEnabled()) {
			m_render.render(bufGraphics);
		}
		for (IDisplayRender r : m_forerenders) {
			if (r.isEnabled())
				r.render(bufGraphics);
		}
		g.drawImage(m_buffer, 0, 0, null);
	}

	public void doLayout() {
		initCanvas(0, 0, getWidth(), getHeight());
		for (ILayout layout : m_layouts) {
			layout.layout(this);
		}
	}

	public void addLayout(ILayout layout) {
		m_layouts.add(layout);
	}

	public ILayout getLayout(String name) {
		for (ILayout layout : m_layouts) {
			if (name.equals(layout.getName())) {
				return layout;
			}
		}
		return null;
	}

	public ILayout[] getLayouts() {
		return m_layouts.toArray(new ILayout[] {});
	}

	protected synchronized boolean initCanvas(int x, int y, int w, int h) {

		if (w <= 0 || h <= 0) {
			return false;
		}

		if (m_width == w && m_height == h) {
			return false;
		}

		m_width = w;
		m_height = h;

		if (bufGraphics != null) {
			bufGraphics.dispose();
			bufGraphics = null;
		}

		// The background layer image buffer
		m_buffer = this.createImage(w, h);

		bufGraphics = (Graphics2D) m_buffer.getGraphics();
		bufGraphics.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		bufGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		bufGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		bufGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		bufGraphics.setClip(0, 0, w, h);
		System.gc();
		return true;
	}

	public void addElemRender(Object key, IElemRender render) {
		render.setOwner(this);
		this.m_elemrenders.put(key, render);
	}

	public void addElemTheme(Object key, IElemTheme theme) {
		theme.setOwner(this);
		this.m_elemthemes.put(key, theme);
	}

	public IElemRender removeElemRender(Object key) {
		IElemRender r = this.m_elemrenders.remove(key);
		r.setOwner(null);
		return r;
	}

	public IElemTheme removeElemTheme(Object key) {
		IElemTheme t = this.m_elemthemes.remove(key);
		t.setOwner(null);
		return t;
	}

	public IElemRender getElemRender(Object key) {
		return m_elemrenders.get(key);
	}

	public IElemTheme getElemTheme(Object key) {
		return m_elemthemes.get(key);
	}

	public void addForegroundRender(IDisplayRender render) {
		m_forerenders.add(render);
		render.setOwner(this);
	}

	public boolean removeForegroundRender(IDisplayRender render) {
		if (m_forerenders.remove(render)) {
			render.setOwner(null);
			return true;
		}
		return false;
	}

	public void addBackgroundRender(IDisplayRender render) {
		m_backrenders.add(render);
		render.setOwner(this);
	}

	public boolean removeBackgroundRender(IDisplayRender render) {
		if (m_backrenders.remove(render)) {
			render.setOwner(null);
			return true;
		}
		return false;
	}

	public void setDisplayRender(IDisplayRender render) {
		m_render = render;
		m_render.setOwner(this);
	}

	public void setElemFinder(IElemFinder finder) {
		m_finder = finder;
		m_finder.setOwner(this);
	}

	public IElemFinder getElemFinder() {
		return m_finder;
	}

	// ////////////////////////////////////
	// data
	public void addData(IData data) {
		m_data.put(data.getID(), data);
	}

	public IData getData(String id) {
		return m_data.get(id);
	}

	public void clear() {
		m_data.clear();
	}

	public void addDataMapping(DataMapping mapping) {
		this.m_mappings.put(mapping.getSlot(), mapping);
	}

	public void removeDataMapping(Slot slot) {
		this.m_mappings.remove(slot);
	}

	public DataMapping getDataMapping(Slot slot) {
		return this.m_mappings.get(slot);
	}

	// ///////////////////////////////////
	// interaction
	public void mouseMoved(MouseEvent e) {

		if (!mouseMoved) {
			mouseMoved = true;
		}

		mouseX = e.getX();
		mouseY = e.getY();

		m_tempPoint.setLocation(mouseX, mouseY);

		if (isOffComponent(e)) {
			if (focus != null) {
				dispatchEvents(INPUT_EVENT_ELEM_EXITED, focus, e);
			}
			dispatchEvents(INPUT_EVENT_MOUSE_EXITED, focus, e);
			focus = null;
			return;
		}

		if (!check(e.getPoint())) {
			return;
		}

		boolean earlyReturn = false;
		IElement ve = null;

		if (null == m_finder) {
			return;
		}

		ve = m_finder.find(m_tempPoint.getX(), m_tempPoint.getY());

		if (focus != null && focus != ve) {
			dispatchEvents(INPUT_EVENT_ELEM_EXITED, focus, e);
			earlyReturn = true;
			if (focus instanceof IElement) {
				((IElement) focus).setFocused(false);
			}
			focus = null;
		}
		if (ve != null && ve != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_ENTERED, ve, e);
			earlyReturn = true;
			focus = ve;
			if (focus instanceof IElement) {
				((IElement) focus).setFocused(true);
			}
		}

		if (earlyReturn)
			return;

		focus = ve;
		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_MOVED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_MOUSE_MOVED, null, e);
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {

		if (!check(e.getPoint())) {
			return;
		}

		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_WHEEL_MOVED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_MOUSE_WHEEL_MOVED, null, e);
		}
	}

	public void mouseClicked(MouseEvent e) {

		if (!check(e.getPoint())) {
			return;
		}

		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_CLICKED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_MOUSE_CLICKED, null, e);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (!check(e.getPoint())) {
			return;
		}

		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_PRESSED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_MOUSE_PRESSED, null, e);
		}
	}

	public void mouseDragged(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();

		if (!check(e.getPoint())) {
			return;
		}
		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_DRAGGED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_MOUSE_DRAGGED, null, e);
		}
	}

	public void mouseReleased(MouseEvent e) {

		if (!check(e.getPoint())) {
			return;
		}

		// if (!isOffComponent(e)) {
		if (mouseDragged) {
			mouseDragged = false;
			mouseX = -1;
			mouseY = -1;
			if (dragged != null) {
				dispatchEvents(INPUT_EVENT_ELEM_RELEASED, dragged, e);
			} else {
				dispatchEvents(INPUT_EVENT_MOUSE_RELEASED, null, e);
			}
			focus = null;
			dragged = null;
		}
		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_RELEASED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_MOUSE_RELEASED, null, e);
		}
		// } else {
		// dispatchEvents(INPUT_EVENT_ELEM_EXITED, focus, e);
		// dispatchEvents(INPUT_EVENT_MOUSE_EXITED, focus, e);
		// }
	}

	public void mouseExited(MouseEvent e) {
		if (focus != null) {
			dispatchEvents(INPUT_EVENT_ELEM_EXITED, focus, e);
			focus = null;
		}
		dispatchEvents(INPUT_EVENT_MOUSE_EXITED, null, e);
	}

	public void mouseEntered(MouseEvent e) {
		dispatchEvents(INPUT_EVENT_MOUSE_ENTERED, null, e);
	}

	public void keyPressed(KeyEvent e) {

		// if(this instanceof VisualLayer) {
		// System.out.println("this = " + getClass());
		// }

		if (!check(null)) {
			// System.out.println("returned");
			return;
		}

		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_KEY_PRESSED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_KEY_PRESSED, null, e);
		}
	}

	public void keyReleased(KeyEvent e) {

		if (!check(null)) {
			return;
		}

		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_KEY_RELEASED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_KEY_RELEASED, null, e);
		}
	}

	public void keyTyped(KeyEvent e) {

		if (!check(null)) {
			return;
		}

		if (null != focus) {
			dispatchEvents(INPUT_EVENT_ELEM_KEY_TYPED, focus, e);
		} else {
			dispatchEvents(INPUT_EVENT_KEY_TYPED, null, e);
		}
	}

	@Override
	public void addAction(IAction l) {
		l.setOwner(this);
		m_actions.add(l);
	}

	@Override
	public void removeAction(IAction l) {
		if (m_actions.remove(l)) {
			l.setOwner(null);
		}
	}

	@Override
	public void removeActions() {
		m_actions.clear();
	}

	protected boolean isOffComponent(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		boolean flag = x < 0 || x > this.getWidth() || y < 0
				|| y > this.getHeight();
		return flag;
	}

	protected boolean check(Point e) {
		return true;
	}

	public ActivityManager getActivityManager() {
		return m_manager;
	}

	public void dispatchEvents(int type, IElement ve, InputEvent e) {

		if (m_actions.isEmpty())
			return;

		Iterator<IAction> it = m_actions.iterator();
		while (it.hasNext()) {
			IAction l = (IAction) it.next();
			if (!l.isEnable()) {
				continue;
			}

			switch (type) {
			case INPUT_EVENT_ELEM_ENTERED:
				l.elemEntered(ve, (MouseEvent) e);
				break;

			case INPUT_EVENT_ELEM_EXITED:
				l.elemExited(ve, (MouseEvent) e);
				break;

			case INPUT_EVENT_ELEM_PRESSED:
				l.elemPressed(ve, (MouseEvent) e);
				break;

			case INPUT_EVENT_ELEM_RELEASED:
				l.elemReleased(ve, (MouseEvent) e);
				break;

			case INPUT_EVENT_ELEM_CLICKED:
				l.elemClicked(ve, (MouseEvent) e);
				break;

			case INPUT_EVENT_ELEM_DRAGGED:
				l.elemDragged(ve, (MouseEvent) e);
				break;

			case INPUT_EVENT_ELEM_MOVED:
				l.elemMoved(ve, (MouseEvent) e);
				break;

			case INPUT_EVENT_ELEM_WHEEL_MOVED:
				l.elemWheelMoved(ve, (MouseWheelEvent) e);
				break;

			case INPUT_EVENT_ELEM_KEY_PRESSED:
				l.elemKeyPressed(ve, (KeyEvent) e);
				break;

			case INPUT_EVENT_ELEM_KEY_RELEASED:
				l.elemKeyReleased(ve, (KeyEvent) e);
				break;

			case INPUT_EVENT_ELEM_KEY_TYPED:
				l.elemKeyTyped(ve, (KeyEvent) e);
				break;
			case INPUT_EVENT_MOUSE_ENTERED:
				l.mouseEntered((MouseEvent) e);
				break;
			case INPUT_EVENT_MOUSE_EXITED:
				l.mouseExited((MouseEvent) e);
				break;
			case INPUT_EVENT_MOUSE_PRESSED:
				l.mousePressed((MouseEvent) e);
				break;
			case INPUT_EVENT_MOUSE_RELEASED:
				l.mouseReleased((MouseEvent) e);
				break;
			case INPUT_EVENT_MOUSE_CLICKED:
				l.mouseClicked((MouseEvent) e);
				break;
			case INPUT_EVENT_MOUSE_DRAGGED:
				l.mouseDragged((MouseEvent) e);
				break;
			case INPUT_EVENT_MOUSE_MOVED:
				l.mouseMoved((MouseEvent) e);
				break;
			case INPUT_EVENT_MOUSE_WHEEL_MOVED:
				l.mouseWheelMoved((MouseWheelEvent) e);
				break;
			case INPUT_EVENT_KEY_PRESSED:
				l.keyPressed((KeyEvent) e);
				break;
			case INPUT_EVENT_KEY_RELEASED:
				l.keyReleased((KeyEvent) e);
				break;
			case INPUT_EVENT_KEY_TYPED:
				l.keyTyped((KeyEvent) e);
				break;
			}
		}
	}
}
