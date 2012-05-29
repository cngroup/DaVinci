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

public interface IVisualNode extends IElement {
	
	public double getX();
	
	public double getY();
	
	public double getWidth();
	
	public double getHeight();
	
	public double getMinX();
	
	public double getMinY();
	
	public double getMaxX();
	
	public double getMaxY();
	
	public float[][] getBoundary();
	
	public void setX(double x);
	
	public void setY(double y);
	
	public void setWidth(double width);
	
	public void setHeight(double height);
	
	public void setBoundary(float[][] bounds);
	
	//////////////// animations coordinates
	public double getStartX();
	
	public double getStartY();
	
	public double getStartWidth();
	
	public double getStartHeight();
	
	public double getTargetX();
	
	public double getTargetY();
	
	public double getTargetWidth();
	
	public double getTargetHeight();
	
	public float[][] getStartBondary();
	
	public float[][] getTargetBondary();
	
	public void setStartX(double x);
	
	public void setStartY(double y);
	
	public void setTargetX(double x);
	
	public void setTargetY(double y);
	
	public void setStartWidth(double w);
	
	public void setStartHeight(double h);
	
	public void setTargetWidth(double w);
	
	public void setTargetHeight(double h);
	
	public void setStartBondary(float[][] spolygon);
	
	public void setTargetBoundary(float[][] tpolygon);
	
	///////////////////// animation
	public void updateX(double frac);
	
	public void updateY(double frac);
	
	public void updateWidth(double frac);
	
	public void updateHeight(double frac);
	
	public void updateBondary(double frac);
	
	public void updateSize(double frac);
	
	public void updateLocation(double frac);
	
	public void updateShape(double frac);
	
	public void locate();
	
	public void reset();
	
}
