package vis.vjit.test.render;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import davinci.data.elem.IElement;
import davinci.data.elem.IVisualNode;
import davinci.rendering.ElemRender;
import davinci.rendering.IElemTheme;

public class TestNodeRender extends ElemRender {
	
	private Ellipse2D m_shape = null;

	public TestNodeRender() {
		m_shape = new Ellipse2D.Double();
	}
	
	public Shape getRawShape(IElement elem) {
		
		IVisualNode node = (IVisualNode)elem;
		
		m_shape.setFrameFromCenter(
				node.getX(), 
				node.getY(), 
				node.getX() + node.getWidth() / 2, 
				node.getY() + node.getWidth() / 2);
		
		return m_shape;
	}

	public void render(Graphics2D g, IElement elem, IElemTheme theme,
			boolean highlight) {
		Shape s = getRawShape(elem);
		g.setColor(theme.getFillColor(elem));
		g.fill(s);
		g.setColor(theme.getBorderColor(elem));
		g.draw(s);
	}

}
