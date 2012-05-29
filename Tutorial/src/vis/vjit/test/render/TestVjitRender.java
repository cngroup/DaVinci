package vis.vjit.test.render;

import java.awt.Graphics2D;

import davinci.data.elem.IEdge;
import davinci.data.elem.IVisualNode;
import davinci.data.graph.Graph;
import davinci.rendering.DisplayRender;
import davinci.rendering.IElemRender;
import davinci.rendering.IElemTheme;

public class TestVjitRender extends DisplayRender {

	public TestVjitRender() {
	}
	
	
	public void render(Graphics2D g) {
		
		Graph<IVisualNode> graph = (Graph<IVisualNode>)m_owner.getData("mygraph");
		if(null == graph) {
			return;
		}
		
		IElemRender r = null;
		IElemTheme t = null;
		IVisualNode node = null;
		IEdge<IVisualNode> edge = null;
		int ecnt = graph.getEdgeCount();
		for(int i = 0; i < ecnt; ++i) {
			edge = graph.getEdge(i);
			if(!edge.isVisible()) {
				continue;
			}
			
			r = m_owner.getElemRender(edge.getID());
			if(r == null) {
				r = m_owner.getElemRender("edge");
			}
			t = m_owner.getElemTheme(edge.getID());
			if(t == null) {
				t = m_owner.getElemTheme("edge");
			}
			r.render(g, edge, t, edge.isHighlight() || edge.isFocused());
		}
		
		int ncnt = graph.getNodeCount();
		for(int i = 0; i < ncnt; ++i) {
			node = graph.getNode(i);
			if(!node.isVisible()) {
				continue;
			}
			r = m_owner.getElemRender(node.getID());
			if(r == null) {
				r = m_owner.getElemRender("node");
			}
			t = m_owner.getElemTheme(node.getID());
			if(t == null) {
				t = m_owner.getElemTheme("node");
			}
			
			r.render(g, node, t, node.isHighlight() || node.isFocused());
		}

	}

}
