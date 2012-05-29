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

import java.util.Comparator;

import davinci.data.IData;
import davinci.data.IDimension;

/**
 * 
 * The tree data structure. In this interface no tree edges are stored
 * 
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 * @param <N> the data type of the tree nodes.
 */
public interface ITree<T> extends IData {

	public T getNode(String id);
	
	public void addChild(T parent, T node);

	public T getChild(T parent, int index);
	
	public T getFirstChild(T parent);
	
	public T getLastChild(T parent);
	
	public int getChildCount(T parent);
	
	public T[] getChildren(T parent, T[] a);

	public T getParent(T child);

	public T getTreeRoot();

	public boolean isLeaf(T node);
	
	public boolean isRoot(T node);

	public void removeChild(T child);
	
	public void deleteNode(T node);
	
	public boolean containsNode(T node);
	
	public void setTreeRoot(T root);
	
	public int getDepth(T node);
	
	public T getAncesstor(T node, int depth);
	
	public void setComparator(Comparator<T> comp);
	
	public Comparator<T> getComparator();
	
	public boolean isEmpty();
	
	public int getDepth();
	
	/**
	 * update weight and treat nodes in the given depth as leaves
	 * @param depth
	 */
	public void update(IDimension<Double> weidim, int depth);
	
	public void update(IDimension<Double> weidim);
	
}
