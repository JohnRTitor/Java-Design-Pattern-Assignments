package paint_app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class MyDrawPanel extends JPanel implements MouseListener, MouseMotionListener {
	String tool = "";
	int x1, y1, x2, y2;

	public MyDrawPanel() {
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);

	}

	public void setTool(String t) {
		tool = t;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (tool.equals("Line")) {
			g.drawLine(x1, y1, x2, y2);
		} else if (tool.equals("Circle")) {
			int r = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
			g.drawOval(x1 - r, y1 - r, 2 * r, 2 * r);
		} else if (tool.equals("Rectangle")) {

			int x = Math.min(x1, x2);
			int y = Math.min(y1, y2);
			int width = Math.abs(x2 - x1);
			int height = Math.abs(y2 - y1);

			g.drawRect(x, y, width, height);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		repaint();
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}