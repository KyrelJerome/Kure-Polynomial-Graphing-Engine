import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import GraphingObjects.Graph;

public class KurikWindow extends JFrame{
    public Graph graph;

    public KurikWindow() {
        super("Kure");       
        setSize(1150,580);   
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        graph = new Graph();
    }

    public void setVisible()
    {
    	setVisible();
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        graph.drawGraph(g2);
    }
    
}
