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
package davinci.data.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import davinci.data.AbstractData;
import davinci.data.IDimension;
import davinci.data.PropHelper;

public class Tree<T> extends AbstractData implements ITree<T> {

	private static final long serialVersionUID = 7092419783613518496L;
	protected Map<T, T> m_parents = null;
	protected Map<T, List<T>> m_children = null;
	protected Map<String, T> m_nodes = null;
	protected Map<T, Integer> m_depth = null;
	protected T m_root = null;
	protected Comparator<T> m_comp = null;
	protected int depth = 0;
	
	public Tree() {
		this(null);
	}
	
	public Tree(T root) {
		m_parents = new HashMap<T, T>();
		m_children = new HashMap<T, List<T>>();
		m_nodes = new HashMap<String, T>();
		m_depth = new HashMap<T, Integer>();
		setTreeRoot(root);
	}
	
	public T getNode(String id) {
		return m_nodes.get(id);
	}
	
	public void addChild(T parent, T node) {
		if(node == null) {
			return;
		}
		
		T oldp = m_parents.get(node);
		if(oldp != null) {
			List<T> list = m_children.get(oldp);
			list.remove(node);
		}
		
		m_parents.put(node, parent);
		List<T> children = m_children.get(parent);
		if(children == null) {
			children = new ArrayList<T>();
			m_children.put(parent, children);
		}
		children.add(node);
		if(m_comp != null) {
			Collections.sort(children, m_comp);
		}
		String id = PropHelper.getID(node);
		m_nodes.put(id, node);
	}

	public T getChild(T parent, int index) {
		List<T> children = m_children.get(parent);
		if(children == null || children.isEmpty()) {
			return null;
		}
		return children.get(index);
	}
	
	public T getFirstChild(T parent) {
		return getChild(parent, 0);
	}
	
	public T getLastChild(T parent) {
		List<T> children = m_children.get(parent);
		if(children == null || children.isEmpty()) {
			return null;
		}
		return children.get(children.size() - 1);
	}

	public int getChildCount(T parent) {
		List<T> children = m_children.get(parent);
		return children == null ? 0 : children.size();
	}

	public T[] getChildren(T parent, T[] a) {
		List<T> children = m_children.get(parent);
		if(children == null || children.isEmpty()) {
			return null;
		}
		return children.toArray(a);
	}

	public T getParent(T child) {
		return m_parents.get(child);
	}

	public T getTreeRoot() {
		return m_root;
	}

	public boolean isLeaf(T node) {
		return getChildCount(node) == 0;
	}
	
	public boolean isRoot(T node) {
		return null != m_root && m_root.equals(node);
	}

	public void removeChild(T child) {

		int cnt = getChildCount(child);
		for(int i = 0; i < cnt; ++i) {
			removeChild(getChild(child, i));
		}
		String id = PropHelper.getID(child);
		m_nodes.remove(id);
		T parent = m_parents.get(child);
		if(parent == null) {
			return;
		}
		List<T> children = m_children.get(parent);
		children.remove(child);
	}
	
	public void deleteNode(T node) {
		if(node == m_root) {
			return;
		}
		
		List<T> children = m_children.get(node);
		if(m_children == null) {
			removeChild(node);
		} else {
			T parent = m_parents.remove(node);
			List<T> subsling = m_children.get(parent);
			subsling.remove(node);
			for(T nn : children) {
				this.addChild(parent, nn);
			}
		}
	}
	
	public boolean containsNode(T node) {
		if(null == node) {
			return false;
		}
		String id = PropHelper.getID(node);
		return m_nodes.containsKey(id);
	}
	
	public void setTreeRoot(T root) {
		if(m_root != null) {
			String id = PropHelper.getID(m_root);
			m_nodes.remove(id);
		}
		m_root = root;
		if(m_root != null) {
			String id = PropHelper.getID(m_root);
			m_nodes.put(id, m_root);
		}
	}
	
	public int getDepth(T node) {
		Integer dep = m_depth.get(node);
		return dep == null ? -1 : dep;
	}
	
	public T getAncesstor(T node, int depth) {
		if(depth == getDepth(node)) {
			return node;
		}
		do {
			node = getParent(node);
			if(depth == getDepth(node)) {
				return node;
			}
		} while(node != null);
		return null;
	}
	
	public void setComparator(Comparator<T> comp) {
		m_comp = comp;
	}
	
	public Comparator<T> getComparator() {
		return m_comp;
	}
	
	public boolean isEmpty() {
		return m_parents.isEmpty();
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void clear() {
		m_parents.clear();
		m_children.clear();
		m_nodes.clear();
	}
	
	/**
	 * update weight and treat nodes in the given depth as leaves
	 * @param depth
	 */
	public void update(IDimension<Double> weidim, int depth) {
		calweight(m_root, weidim, depth);
	}
	
	public void update(IDimension<Double> weidim) {
		update(m_root, weidim, 0);
	}
	
	private double update(T parent, IDimension<Double> weidim, int depth) {
		double wei = 0;
		this.depth = Math.max(this.depth, depth);
		m_depth.put(parent, depth);
		int cnt = getChildCount(parent);
		if(cnt == 0) {
			return weidim.getValue(parent);
		}
		for(int i = 0; i < cnt; ++i) {
			wei += update(getChild(parent, i), weidim, depth + 1);
		}
		
		weidim.setValue(parent, wei);
		
		return wei;
	}
	
	private double calweight(T parent, IDimension<Double> weidim, int depth) {
		double wei = 0;
		int cnt = getChildCount(parent);
		if((depth == getDepth(parent)) || cnt == 0) {
			weidim.getValue(parent);
		}
		for(int i = 0; i < cnt; ++i) {
			wei += calweight(getChild(parent, i), weidim, depth);
		}
		weidim.setValue(parent, wei);
		return wei;
	}
	
	public void print() {
		print(m_root, "");
	}
	
	private void print(T parent, String path) {
		System.out.println(path + " " + parent);
		int cnt = getChildCount(parent);
		if(0 == cnt) {
			return;
		}
		for(int i = 0; i < cnt; ++i) {
			print(getChild(parent, i), path + "-");
		}
	}
	
	public int size() {
		return m_nodes.size();
	}
}
