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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import davinci.data.AbstractData;
import davinci.data.PropHelper;
import davinci.data.elem.Edge;
import davinci.data.elem.IEdge;
import davinci.data.elem.IElement;

public class Graph<N> extends AbstractData implements IGraph<N> {

	private static final long serialVersionUID = 8666879141870435739L;

	private static final IEdge[] EMPTY_EDGES = new IEdge[]{};
	
	protected Map<String, N> m_nidx = null;
	protected Map<String, IEdge<N>> m_eidx = null;
	protected Map<N, List<IEdge<N>>> m_edges1 = null;
	protected Map<N, List<IEdge<N>>> m_edges2 = null;
	
	protected List<N> m_nodes = null;
	protected List<IEdge<N>> m_edges = null;
	
	protected double m_weight = 0;
	
	protected List<IGraphListener<N>> m_listeners = null;
	
	public Graph() {
		m_nidx = new HashMap<String, N>();
		m_eidx = new HashMap<String, IEdge<N>>();
		m_edges1 = new HashMap<N, List<IEdge<N>>>();
		m_edges2 = new HashMap<N, List<IEdge<N>>>();
		m_nodes = new ArrayList<N>();
		m_edges = new ArrayList<IEdge<N>>();
		m_listeners = new ArrayList<IGraphListener<N>>();
	}

	public void addEdge(IEdge<N> edge) {
		N n1 = edge.getFirstNode();
		N n2 = edge.getSecondNode();
		if(!containsNode(n1)) {
			addNode(n1);
		}
		if(!containsNode(n2)) {
			addNode(n2);
		}
		
		List<IEdge<N>> edges = m_edges1.get(n1);
		if(null == edges) {
			edges = new ArrayList<IEdge<N>>();
			m_edges1.put(n1, edges);
		}
		edges.add(edge);
		
		edges = m_edges2.get(n2);
		if(null == edges) {
			edges = new ArrayList<IEdge<N>>();
			m_edges2.put(n2, edges);
		}
		edges.add(edge);
		
		m_edges.add(edge);
		m_eidx.put(edge.getID(), edge);
		
		fireEdgeAdded(edge);
	}

	public IEdge<N> addEdge(N n1, N n2) {
		IEdge<N> edge = getEdge(n1, n2);
		if(null != edge) {
			return edge;
		}
		edge = new Edge<N>(n1, n2);
		addEdge(edge);
		return edge;
	}
	
	public void removeEdge(IEdge<N> edge) {
		if(!m_edges.remove(edge)) {
			return;
		}
		
		N n1 = edge.getFirstNode();
		N n2 = edge.getSecondNode();
		
		List<IEdge<N>> edges = m_edges1.get(n1);
		edges.remove(edge);
		
		edges = m_edges2.get(n2);
		edges.remove(edge);
		
		m_eidx.remove(edge.getID());
		
		fireEdgeRemoved(edge);
	}

	@Override
	public IEdge<N> getEdge(int idx) {
		if(idx < 0 || idx >= m_edges.size()) {
			return null;
		}
		return m_edges.get(idx);
	}

	@Override
	public IEdge<N> getEdge(String id) {
		return m_eidx.get(id);
	}

	@Override
	public IEdge<N> getEdge(N n1, N n2) {
		String id = PropHelper.getID(n1) + ":" + PropHelper.getID(n2);
		return m_eidx.get(id);
	}

	@Override
	public int getEdgeCount() {
		return m_edges.size();
	}

	public IEdge[] getEdgesFromFirstNode(N node) {
		List<IEdge<N>> edges = m_edges1.get(node);
		if(edges == null) {
			return EMPTY_EDGES;
		}
		return edges.toArray(EMPTY_EDGES);
	}

	public IEdge[] getEdgesFromSecondNode(N node) {
		List<IEdge<N>> edges = m_edges2.get(node);
		if(edges == null) {
			return EMPTY_EDGES;
		}
		return edges.toArray(EMPTY_EDGES);
	}

	public IEdge[] getEdges() {
		return m_edges.toArray(new IEdge[]{});
	}
	
	public boolean containsEdge(IEdge<N> edge) {
		return m_eidx.containsKey(edge.getID());
	}

	public void addNode(N node) {
		if(null == node) {
			throw new NullPointerException("can't add an null node");
		}
		m_nodes.add(node);
		m_nidx.put(PropHelper.getID(node), node);
		m_weight += PropHelper.getDouble("weight", node);
		this.fireNodeAdded(node);
	}
	
	public void removeNode(N node) {
		if(!m_nodes.remove(node)) {
			return;
		}
		m_nidx.remove(PropHelper.getID(node));
		List<IEdge<N>> edges = m_edges1.remove(node);
		if(edges != null)
		for(IEdge<N> e : edges) {
			N n = e.getSecondNode();
			List<IEdge<N>> ee = m_edges2.get(n);
			ee.remove(e);
			m_edges.remove(e);
			m_eidx.remove(e.getID());
		}
		edges = m_edges2.remove(node);
		if(edges != null)
		for(IEdge<N> e : edges) {
			N n = e.getFirstNode();
			List<IEdge<N>> ee = m_edges1.get(n);
			ee.remove(e);
			m_edges.remove(e);
			m_eidx.remove(e.getID());
		}
		m_weight -= PropHelper.getDouble("weight", node);
		fireNodeRemoved(node);
	}

	@Override
	public N getNode(int i) {
		if(i < 0 || i >= m_nodes.size()) {
			return null;
		}
		return m_nodes.get(i);
	}

	@Override
	public N getNode(String id) {
		return m_nidx.get(id);
	}

	@Override
	public int getNodeCount() {
		return m_nodes.size();
	}

	@Override
	public N[] getNodes(N[] a) {
		return m_nodes.toArray(a);
	}
	
	@Override
	public boolean containsNode(N node) {
		return m_nidx.containsKey(PropHelper.getID(node));
	}

	public void addGraphListener(IGraphListener<N> l) {
		m_listeners.add(l);
	}
	
	@Override
	public void removeGraphListener(IGraphListener<N> l) {
		m_listeners.remove(l);
	}
	
	public void fireNodeAdded(N node) {
		for(IGraphListener<N> l : m_listeners) {
			l.nodeAdded(node);
		}
	}
	
	public void fireNodeRemoved(N node) {
		for(IGraphListener<N> l : m_listeners) {
			l.nodeRemoved(node);
		}
	}
	
	public void fireEdgeAdded(IEdge<N> edge) {
		for(IGraphListener<N> l : m_listeners) {
			l.edgeAdded(edge);
		}
	}
	
	public void fireEdgeRemoved(IEdge<N> edge) {
		for(IGraphListener<N> l : m_listeners) {
			l.edgeRemoved(edge);
		}
	}
	
	public boolean isEmpty() {
		return m_nodes.isEmpty() && m_edges.isEmpty();
	}
	
	public void clear() {
		super.clear();
		m_nidx.clear();
		m_eidx.clear();
		m_nodes.clear();
		m_edges.clear();
		m_edges1.clear();
		m_edges2.clear();
	}
}
