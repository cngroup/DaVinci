package vis.vjit.test.animation;

import davinci.activity.Activity;
import davinci.data.elem.IVisualNode;
import davinci.data.graph.Graph;

public class TestAnimator extends Activity {

	private static final long serialVersionUID = 2469923962814774228L;
	
	private Graph<IVisualNode> m_graph = null;
	
	public TestAnimator() {
		super(5000, 30);
	}
	
	public boolean start() {
		m_graph = (Graph<IVisualNode>)m_disp.getData("mygraph");
		if(m_graph == null) {
			return false;
		}
		return true;
	}
	
	public void perform(double frac) {
		IVisualNode node = null;
		int ncnt = m_graph.getNodeCount();
		for(int i = 0; i < ncnt; ++i) {
			node = m_graph.getNode(i);
			node.updateLocation(frac);
			node.updateSize(frac);
		}
		super.perform(frac);
		m_disp.repaint();
	}
	
	public void finish() {
		super.finish();
	}

}
