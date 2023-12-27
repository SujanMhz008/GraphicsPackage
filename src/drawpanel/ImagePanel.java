package drawpanel;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImagePanel {
	JFrame fr;
	JPanel panelClick;

	public ImagePanel() {
		fr = new JFrame();
		panelClick = new JPanel() {
			public void paintComponent(Graphics g) {

				g.drawImage(DrawingFrame.buffimg, 0, 0, null);
			}

		};

		fr.add(panelClick);
		fr.setSize(400, 400);
		fr.setVisible(true);
		fr.setLocationRelativeTo(null);

	}

}
