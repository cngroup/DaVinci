package davinci.util.graph;

import java.util.ArrayList;
import java.util.Random;

import davinci.data.Dimension;
import davinci.data.IDimension;
import davinci.data.elem.IVisualNode;
import davinci.data.elem.VisualNode;
import davinci.data.graph.Graph;
import davinci.data.tree.Tree;

/**
 * Library routines for creating various Graph structures. All Graphs generated
 * by methods of this class include a String-valued "label" field.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class GraphLib {

	private GraphLib() {
		// prevent instantiation
	}

	// ------------------------------------------------------------------------
	// Graph Creation Routines

	/**
	 * Builds a completely unconnected (edge-free) graph with the given number
	 * of nodes
	 * 
	 * @param n
	 *            the number of nodes
	 * @return a graph with n nodes and no edges
	 */
	public static Graph<IVisualNode> getNodes(int n) {
		Graph<IVisualNode> g = new Graph();
		for (int i = 0; i < n; i++) {
			IVisualNode node = new VisualNode();
			node.setID(String.valueOf(i));
			g.addNode(node);
		}
		return g;
	}

	/**
	 * Builds a "star" graph with one central hub connected to the given number
	 * of satellite nodes.
	 * 
	 * @param n
	 *            the number of points of the star
	 * @return a "star" graph with n points, for a total of n+1 nodes
	 */
	public static Graph<IVisualNode> getStar(int n) {
		Graph g = new Graph();

		VisualNode r = new VisualNode();
		r.put("id", "0");
		g.addNode(r);

		for (int i = 1; i <= n; ++i) {
			VisualNode nn = new VisualNode();
			nn.put("id", String.valueOf(i));
			g.addNode(nn);
			g.addEdge(r, nn);
		}
		return g;
	}

	/**
	 * Returns a clique of given size. A clique is a graph in which every node
	 * is a neighbor of every other node.
	 * 
	 * @param n
	 *            the number of nodes in the graph
	 * @return a clique of size n
	 */
	public static Graph<IVisualNode> getClique(int n) {
		Graph<IVisualNode> g = new Graph<IVisualNode>();
		VisualNode nodes[] = new VisualNode[n];
		for (int i = 0; i < n; ++i) {
			nodes[i] = new VisualNode();
			nodes[i].put("id", String.valueOf(i));
			g.addNode(nodes[i]);
		}
		for (int i = 0; i < n; ++i) {
			for (int j = i; j < n; ++j)
				if (i != j)
					g.addEdge(nodes[i], nodes[j]);
		}
		return g;
	}

	/**
	 * Returns a graph structured as an m-by-n grid.
	 * 
	 * @param m
	 *            the number of rows of the grid
	 * @param n
	 *            the number of columns of the grid
	 * @return an m-by-n grid structured graph
	 */
	public static Graph<IVisualNode> getGrid(int m, int n) {
		Graph<IVisualNode> g = new Graph<IVisualNode>();
		VisualNode[] nodes = new VisualNode[m * n];
		for (int i = 0; i < m * n; ++i) {
			nodes[i] = new VisualNode();
			nodes[i].setID(String.valueOf(i));
			g.addNode(nodes[i]);
			if (i >= n)
				g.addEdge(nodes[i - n], nodes[i]);
			if (i % n != 0)
				g.addEdge(nodes[i - 1], nodes[i]);
		}
		return g;
	}

	public static Graph<IVisualNode> getHoneycomb(int levels) {
		Graph<IVisualNode> g = new Graph<IVisualNode>();

		ArrayList<IVisualNode> layer1 = halfcomb(g, levels);
		ArrayList<IVisualNode> layer2 = halfcomb(g, levels);
		for (int i = 0; i < (levels << 1); ++i) {
			VisualNode n1 = (VisualNode) layer1.get(i);
			VisualNode n2 = (VisualNode) layer2.get(i);
			g.addEdge(n1, n2);
		}
		return g;
	}

	private static ArrayList<IVisualNode> halfcomb(Graph g, int levels) {
		ArrayList<IVisualNode> top = new ArrayList<IVisualNode>();
		ArrayList<IVisualNode> layer = new ArrayList<IVisualNode>();

		int label = 0;

		for (int i = 0; i < levels; ++i) {
			VisualNode n = new VisualNode();
			n.put("id", String.valueOf(label++));
			g.addNode(n);
			top.add(n);
		}
		for (int i = 0; i < levels; ++i) {
			VisualNode n = null;
			for (int j = 0; j < top.size(); ++j) {
				VisualNode p = (VisualNode) top.get(j);
				if (n == null) {
					n = new VisualNode();
					n.put("id", String.valueOf(label++));
					g.addNode(n);
					layer.add(n);
				}
				g.addEdge(p, n);
				n = new VisualNode();
				n.put("id", String.valueOf(label++));
				g.addNode(n);
				layer.add(n);
				g.addEdge(p, n);
			}
			if (i == levels - 1) {
				return layer;
			}
			top.clear();
			for (int j = 0; j < layer.size(); ++j) {
				VisualNode p = (VisualNode) layer.get(j);
				n = new VisualNode();
				n.put("id", String.valueOf(label++));
				g.addNode(n);
				top.add(n);
				g.addEdge(p, n);
			}
			layer.clear();
		}
		// should never happen
		return top;
	}

	/**
	 * Returns a balanced tree of the requested breadth and depth.
	 * 
	 * @param breadth
	 *            the breadth of each level of the tree
	 * @param depth
	 *            the depth of the tree
	 * @return a balanced tree
	 */
	public static Tree<IVisualNode> getBalancedTree(int breadth, int depth) {
		Tree<IVisualNode> t = new Tree<IVisualNode>();
		IDimension<Double> dim = new Dimension<Double>("weight", "weight", IDimension.META_TYPE_NUMERICAL);
		t.addDimension("node", dim);
		
		IVisualNode r = new VisualNode();
		r.setID("root");
		r.setLabel("root");
		t.setTreeRoot(r);
		if (depth > 0)
			balancedHelper(t, r, breadth, depth - 1, 1);
		t.update(dim);
		return t;
	}

	private static Random m_random = new Random(10);
	private static String[] categories = new String[]{"cancer", "diabetes", "kidney disorder", "heart disease", "hiv", "high blood pressure"};
	private static void balancedHelper(Tree<IVisualNode> t, IVisualNode parent,
			int breadth, int depth, double v) {
		if(depth == 0) {
			breadth += 4;
		}
		for (int i = 0; i < breadth; ++i) {
			IVisualNode c = new VisualNode();
			c.setID(parent.getID() + "-" + i);
			c.setLabel(String.valueOf(i));
			c.setWeight(1D);
			t.addChild(parent, c);
			if (depth > 0) {
				balancedHelper(t, c, breadth, depth - 1, m_random.nextDouble());
			} else {
				c.setLabel(String.format("%3.1f ~ %3.1f", i * 0.1, (i + 1) * 0.1));
				c.put("category", categories[Integer.parseInt(parent.getLabel())]);
				c.setWeight(1);
//				c.setWeight();
//				System.out.println(c.getID() + " : " + c.getWeight());
			}
		}
	}

	/**
	 * Returns a left deep binary tree
	 * 
	 * @param depth
	 *            the depth of the tree
	 * @return the generated tree
	 */
	public static Tree<IVisualNode> getLeftDeepTree(int depth) {
		Tree<IVisualNode> t = new Tree<IVisualNode>();
		IVisualNode r = new VisualNode();
		r.setID("0,0");
		t.setTreeRoot(r);
		deepHelper(t, r, 2, depth, true);
		return t;
	}

	/**
	 * Returns a right deep binary tree
	 * 
	 * @param depth
	 *            the depth of the tree
	 * @return the generated Tree
	 */
	public static Tree<IVisualNode> getRightDeepTree(int depth) {
		Tree<IVisualNode> t = new Tree<IVisualNode>();
		IVisualNode r = new VisualNode();
		r.setID("0,0");
		r.setLabel("0");
		t.setTreeRoot(r);
		deepHelper(t, r, 2, depth, false);
		return t;
	}

	/**
	 * Create a diamond tree, with a given branching factor at each level, and
	 * depth levels for the two main branches.
	 * 
	 * @param b
	 *            the number of children of each branch node
	 * @param d1
	 *            the length of the first (left) branch
	 * @param d2
	 *            the length of the second (right) branch
	 * @return the generated Tree
	 */
	public static Tree<IVisualNode> getDiamondTree(int b, int d1, int d2) {
		Tree<IVisualNode> t = new Tree<IVisualNode>();
		IVisualNode r = new VisualNode();
		r.setID("0,0");
		t.setTreeRoot(r);
		
		IVisualNode left = new VisualNode();
		left.setID("1,0");
		t.addChild(r, left);
		
		IVisualNode right = new VisualNode();
		right.setID("1,1");
		t.addChild(r, right);
		
		deepHelper(t, left, b, d1 - 2, true);
		deepHelper(t, right, b, d1 - 2, false);

		while (t.getFirstChild(left) != null)
			left = t.getFirstChild(left);
		while (t.getLastChild(right) != null)
			right = t.getLastChild(right);

		deepHelper(t, left, b, d2 - 1, false);
		deepHelper(t, right, b, d2 - 1, true);

		return t;
	}

	private static void deepHelper(Tree<IVisualNode> t, IVisualNode n,
			int breadth, int depth, boolean left) {
		IVisualNode c = new VisualNode();
		c.setID("0," + depth);
		t.addChild(n, c);
		if (left && depth > 0)
			deepHelper(t, c, breadth, depth - 1, left);

		for (int i = 1; i < breadth; ++i) {
			c = new VisualNode();
			t.addChild(n, c);
			c.setID(i + "," + depth);
			c.setLabel("" + i);
		}
		if (!left && depth > 0)
			deepHelper(t, c, breadth, depth - 1, left);
	}

} // end of class GraphLib
