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
package davinci.data.elem;

import java.awt.geom.Point2D;

import davinci.data.PropHelper;


public class Edge<N> extends Element implements IEdge<N> {

	private static final long serialVersionUID = 2672613339707931152L;
	protected N node1 = null;
	protected N node2 = null;
	protected Point2D.Float[] spline = null;
	
	public Edge() {
	}
	
	public Edge(N n1, N n2) {
		node1 = n1;
		node2 = n2;
		String id = PropHelper.getID(node1) + ":" + PropHelper.getID(node2);
		this.setID(id);
	}
	
	public N getFirstNode() {
		return node1;
	}

	public N getSecondNode() {
		return node2;
	}

	public void setFirstNode(N node) {
		node1 = node;
	}

	public void setSecondNode(N node) {
		node2 = node;
	}

	public Point2D.Float[] getSpline() {
		return spline;
	}

	public void setSpline(Point2D.Float[] spline) {
		this.spline = spline;
	}
}
