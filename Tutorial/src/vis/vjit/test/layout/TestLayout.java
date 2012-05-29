package vis.vjit.test.layout;

import davinci.Display;
import davinci.ILayout;
import davinci.data.elem.IVisualNode;
import davinci.data.elem.VisualNode;
import davinci.data.graph.Graph;

public class TestLayout implements ILayout {

	public TestLayout() {
	}
	
	public String getName() {
		return "TestLayout";
	}

	public void layout(Display disp) {

		Graph<IVisualNode> graph = (Graph<IVisualNode>)disp.getData("mygraph");
		if(null == graph) {
			return;
		}
		
		VisualNode node = null;
		int ncnt = graph.getNodeCount();
		for(int i = 0; i < ncnt; ++i) {
			node = (VisualNode)graph.getNode(i);
			node.setX(Math.random() * disp.getWidth());
			node.setY(Math.random() * disp.getHeight());
			node.setWidth(20);
			node.setHeight(10);
		}
	}

	public void reset() {
	}

}
