package vis.vjit.test.action;

import java.awt.event.MouseEvent;

import davinci.data.elem.IElement;
import davinci.interaction.ActionAdapter;

public class TestAction extends ActionAdapter {

	public TestAction() {
		
	}
	
	public void elemPressed(IElement elem, MouseEvent e) {
		System.out.println("Element Pressed: " + elem.getID());
	}
	
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse Pressed");
	}
	
}
