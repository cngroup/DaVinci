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
package davinci.data.graph;

import davinci.data.IData;
import davinci.data.elem.IEdge;

/**
 * 
 * The node link graph data structure.
 * 
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 * @param <N> the data type of the graph nodes.
 */
public interface IGraph<N> extends IData {

	public boolean containsNode(N node);
	
	public boolean containsEdge(IEdge<N> edge);
	
	public boolean isEmpty();
	
	// node related operations
	public void addNode(N node);
	
	public int getNodeCount();
	
	public N getNode(int i);
	
	public N getNode(String id);
	
	public N[] getNodes(N[] a);
	
	public void removeNode(N node);
	
	
	// edge related operations
	public void addEdge(IEdge<N> edge);
	
	public IEdge<N> addEdge(N n1, N n2);
	
	public void removeEdge(IEdge<N> edge);
	
	public int getEdgeCount();
	
	public IEdge<N> getEdge(int idx);
	
	public IEdge<N> getEdge(String id);
	
	public IEdge<N> getEdge(N n1, N n2);
	
	public IEdge[] getEdges();
	
	public IEdge[] getEdgesFromFirstNode(N node);
	
	public IEdge[] getEdgesFromSecondNode(N node);
	
	
	// listener
	public void addGraphListener(IGraphListener<N> l);
	
	public void removeGraphListener(IGraphListener<N> l);
	
}
