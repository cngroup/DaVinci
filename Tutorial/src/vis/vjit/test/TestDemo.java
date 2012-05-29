package vis.vjit.test;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import davinci.data.elem.IVisualNode;
import davinci.data.graph.Graph;
import davinci.util.graph.GraphLib;

public class TestDemo extends JFrame {

	private static final long serialVersionUID = 7976440950665878269L;

	public TestDemo() {
		
		// initiate your visualization
		TestVjit vjit = new TestVjit();
		vjit.setBackground(Color.white);
		
		// prepare the data
		Graph<IVisualNode> graph = GraphLib.getGrid(5, 5);
		graph.setID("mygraph");
		vjit.addData(graph);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(vjit, BorderLayout.CENTER);
		this.setSize(1024, 768);
		this.setTitle("This is test using DaVinci");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		TestDemo demo = new TestDemo();
		demo.setVisible(true);
	}
	
}
