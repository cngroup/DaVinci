package vis.vjit.test;

import vis.vjit.test.action.TestAction;
import vis.vjit.test.animation.TestAnimator;
import vis.vjit.test.layout.TestLayout;
import vis.vjit.test.render.TestEdgeRender;
import vis.vjit.test.render.TestEdgeTheme;
import vis.vjit.test.render.TestNodeRender;
import vis.vjit.test.render.TestNodeTheme;
import vis.vjit.test.render.TestVjitRender;
import davinci.Display;
import davinci.activity.Activity;

public class TestVjit extends Display {

	private static final long serialVersionUID = 4845917505341550575L;
	
	private Activity m_animator = null;

	public TestVjit() {
		
		m_animator = new TestAnimator();
		
		// register layout algorithm
		this.addLayout(new TestLayout());
		
		// add interactions
		this.addAction(new TestAction());
		
		// add elemnt finder
		this.setElemFinder(new TestElemFinder());
		
		// register renders
		this.setDisplayRender(new TestVjitRender());
		this.addElemRender("node", new TestNodeRender());
		this.addElemTheme("node", new TestNodeTheme());
		this.addElemRender("edge", new TestEdgeRender());
		this.addElemTheme("edge", new TestEdgeTheme());
	}
	
	public void doLayout() {
		
		super.doLayout();
		
		m_animator.setDisplay(this);
		m_animator.setStartTime(System.currentTimeMillis());
		this.getActivityManager().addActivity(m_animator);
	}
}
