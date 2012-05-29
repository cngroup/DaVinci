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

import java.awt.geom.Rectangle2D;


public class VisualNode extends Element implements IVisualNode {
	
	private static final long serialVersionUID = -8382609102868259888L;
	protected double x = Double.NaN;
	protected double y = Double.NaN;
	protected double w = Double.NaN;
	protected double h = Double.NaN;
	
	protected double sx = Double.NaN;
	protected double sy = Double.NaN;
	protected double sw = Double.NaN;
	protected double sh = Double.NaN;
	
	protected double ex = Double.NaN;
	protected double ey = Double.NaN;
	protected double ew = Double.NaN;
	protected double eh = Double.NaN;
	
	protected float[][] boundary = null;
	protected float[][] sboundary = null;
	protected float[][] eboundary = null;
	
	protected Rectangle2D m_bounds = null;

	public VisualNode() {
		m_bounds = new Rectangle2D.Double();
	}
	
	public Rectangle2D getBounds() {
		m_bounds.setFrameFromCenter(x, y, x + w / 2, y + h / 2);
		return m_bounds;
	}
	
	public float[][] getBoundary() {
		return boundary;
	}

	public double getHeight() {
		return h;
	}

	public float[][] getStartBondary() {
		return sboundary;
	}

	public double getStartHeight() {
		return sh;
	}

	public double getStartWidth() {
		return sw;
	}

	public double getStartX() {
		return sx;
	}

	public double getStartY() {
		return sy;
	}

	public float[][] getTargetBondary() {
		return eboundary;
	}

	public double getTargetHeight() {
		return eh;
	}

	public double getTargetWidth() {
		return ew;
	}

	public double getTargetX() {
		return ex;
	}

	public double getTargetY() {
		return ey;
	}

	public double getWidth() {
		return w;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setStartBondary(float[][] spolygon) {
		this.sboundary = spolygon;
	}

	public void setStartHeight(double h) {
		this.sh = h;
	}

	public void setStartWidth(double w) {
		this.sw = w;
	}

	public void setStartX(double x) {
		this.sx = x;
	}

	public void setStartY(double y) {
		this.sy = y;
	}

	public void setTargetBoundary(float[][] tpolygon) {
		this.eboundary= tpolygon;
	}

	public void setTargetHeight(double h) {
		this.eh = h;
	}

	public void setTargetWidth(double w) {
		this.ew = w;
	}

	public void setTargetX(double x) {
		this.ex = x;
	}

	public void setTargetY(double y) {
		this.ey = y;
	}

	public void updateBondary(double frac) {
		if(sboundary == null || eboundary == null) {
			return;
		}
		
		if(sboundary.length != eboundary.length) {
			return;
		}
		
		if(boundary == null) {
			boundary = new float[sboundary.length][sboundary[0].length];
		}
		
		for(int i = 0; i < boundary.length; ++i) {
			boundary[i][0] = (float)(sboundary[i][0] + frac * (eboundary[i][0] - sboundary[i][0]));
			boundary[i][1] = (float)(sboundary[i][1] + frac * (eboundary[i][1] - sboundary[i][1]));
		}
	}

	public void updateHeight(double frac) {
		this.h = this.sh + frac * (this.eh - this.sh);
	}

	public void updateLocation(double frac) {
		this.updateX(frac);
		this.updateY(frac);
	}

	public void updateShape(double frac) {
	}

	public void updateSize(double frac) {
		this.updateWidth(frac);
		this.updateHeight(frac);
	}

	public void updateWidth(double frac) {
		this.w = this.sw + frac * (this.ew - this.sw);
	}

	public void updateX(double frac) {
		this.x = this.sx + frac * (this.ex - this.sx);
	}

	public void updateY(double frac) {
		this.y = this.sy + frac * (this.ey - this.sy);
	}
	
	public void setX(double x) {
		if(Double.isNaN(this.ex)) {
			this.x = this.ex = this.sx = x;
		} else {
			this.sx = this.ex;
			this.ex = x;
		}
	}

	public void setY(double y) {
		if(Double.isNaN(this.ey)) {
			this.y = this.ey = this.sy = y;
		} else {
			this.sy = this.ey;
			this.ey = y;
		}
	}
	
	public void setWidth(double width) {
		if(Double.isNaN(this.ew)) {
			this.w = this.ew = this.sw = width;
		} else {
			this.sw = this.ew;
			this.ew = width;
		}
	}
	
	public void setHeight(double height) {
		if(Double.isNaN(this.eh)) {
			this.h = this.eh = this.sh = height;
		} else {
			this.sh = this.eh;
			this.eh = height;
		}
	}
	
	public void setBoundary(float[][] polygon) {
		if(eboundary == null) {
			this.boundary = this.eboundary = this.sboundary = boundary;
		} else {
			this.sboundary = eboundary;
			this.eboundary = polygon;
		}
	}

	public void locate() {
		this.updateX(1);
		this.updateY(1);
		this.updateWidth(1);
		this.updateHeight(1);
		this.updateBondary(1);
	}

	public void reset() {
		x = Double.NaN;
		y = Double.NaN;
		w = Double.NaN;
		h = Double.NaN;
		
		sx = Double.NaN;
		sy = Double.NaN;
		sw = Double.NaN;
		sh = Double.NaN;
		
		ex = Double.NaN;
		ey = Double.NaN;
		ew = Double.NaN;
		eh = Double.NaN;
		
		boundary = null;
		sboundary = null;
		eboundary = null;
	}
	
	public double getMinX() {
		return this.x - w / 2.0;
	}
	
	public double getMinY() {
		return this.y - h / 2.0;
	}
	
	public double getMaxX() {
		return this.x + w / 2.0;
	}
	
	public double getMaxY() {
		return this.y + h / 2.0;
	}
}
