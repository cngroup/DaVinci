package vis.vjit.test.render;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;

import davinci.data.elem.Edge;
import davinci.data.elem.IElement;
import davinci.data.elem.IVisualNode;
import davinci.rendering.ElemRender;
import davinci.rendering.IElemTheme;

public class TestEdgeRender extends ElemRender {

	private Line2D m_line = null;
	
	public TestEdgeRender() {
		m_line = new Line2D.Double();
	}
	
	public Shape getRawShape(IElement elem) {
		
		Edge<IVisualNode> edge = (Edge<IVisualNode>)elem;
		IVisualNode n1 = edge.getFirstNode();
		IVisualNode n2 = edge.getSecondNode();
		
		m_line.setLine(n1.getX(), n1.getY(), n2.getX(), n2.getY());
		
		return m_line;
	}

	public void render(Graphics2D g, IElement elem, IElemTheme theme,
			boolean highlight) {
		Shape s = getRawShape(elem);
		g.setColor(theme.getBorderColor(elem));
		g.draw(s);
	}

}
