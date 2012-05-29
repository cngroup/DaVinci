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
package davinci.rendering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.Map;

import davinci.Display;
import davinci.data.elem.IElement;

public abstract class ElemTheme implements IElemTheme {

	protected Display m_owner = null;
	
	protected Map<String, Color> m_colors = null;
	
	protected Color m_cfill = Color.white;
	protected Color m_cborder = Color.black;
	protected Color m_clabel = Color.black;
	protected Color m_ctext = Color.black;
	protected Font m_flabel = new Font("Arial", Font.BOLD, 10); 
	protected Font m_ftext = new Font("Arial", Font.PLAIN, 10);
	protected Stroke m_thick = new BasicStroke(1f);
	
	public ElemTheme() {
	}
	
	@Override
	public Color getBorderColor(IElement e) {
		return m_cborder;
	}

	@Override
	public Color getFillColor(IElement e) {
		return m_cfill;
	}

	@Override
	public Color getLabelColor(IElement e) {
		return m_clabel;
	}

	@Override
	public Font getLabelFont(IElement e) {
		return m_flabel;
	}

	@Override
	public Display getOwner() {
		return m_owner;
	}

	@Override
	public Color getTextColor(IElement e) {
		return m_ctext;
	}

	@Override
	public Font getTextFont(IElement e) {
		return m_ftext;
	}

	@Override
	public Stroke getThickness(IElement e) {
		return m_thick;
	}

	@Override
	public void setOwner(Display disp) {
		m_owner = disp;
	}

}
