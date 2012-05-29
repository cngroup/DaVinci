package vis.vjit.test;

import davinci.ElemFinder;
import davinci.data.elem.IElement;
import davinci.data.elem.IVisualNode;
import davinci.data.elem.VisualNode;
import davinci.data.graph.Graph;
import davinci.rendering.IElemRender;

public class TestElemFinder extends ElemFinder {

	public TestElemFinder() {
	}
	
	public IElement find(double x, double y) {
		
		Graph<IVisualNode> graph = (Graph<IVisualNode>)m_owner.getData("mygraph");
		if(graph == null) {
			return null;
		}
		
		VisualNode node = null;
		IElemRender r = null;
		int ncnt = graph.getNodeCount();
		for(int i = 0; i < ncnt; ++i) {
			node = (VisualNode)graph.getNode(i);
			r = m_owner.getElemRender(node.getID());
			if(r == null) {
				r = m_owner.getElemRender("node");
			}
			if(r.locatePoint(x, y, node)) {
				return node;
			}
		}
		return null;
	}

}
