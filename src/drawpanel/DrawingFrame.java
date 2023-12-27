package drawpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import DbConnection.DbConnection;
import Login.Login;

public class DrawingFrame {
	private JFrame fr;
	private JMenuBar mb;
	private JMenu file, changeUser;
	private JMenuItem open,save,exit;
	private JButton blur,sharpen,brighten,rotate,negative;
	private BufferedImage image;
	public static BufferedImage buffimg;
	private JPanel draw_panel, panel_west, panel_bottom;
	private JComboBox<String> cb_draw, cb_color;
	private JLabel lb_shape, lb_color;
	private String shape[] = { "Rectangle", "OVal", "Line", "Text" };
	private String color[] = { "Red", "Black", "White", "Yellow", "Green" };
	private Color colorArray[] = { Color.red, Color.black, Color.white, Color.yellow, Color.green };

	private JCheckBox checked;
	private int x1, y1, x2, y2, saved;
	private JLabel lb_lib;

	JList<String> jl;
	File filei;
	String filename;
	DbConnection db;
	Image imge;

	private static final int DEFAULT_WIDTH = 900;
	private static final int DEFAULT_HEIGHT = 700;

	Border border_north, border_east, border_center;

	public DrawingFrame() {
		fr = new JFrame();
		checked = new JCheckBox("Filled:");

		DefaultListModel<String> dl = new DefaultListModel<String>();
		ArrayList<String> filenames = new DbConnection().getFileName(Login.userId);
		for (String name : filenames) {
			dl.addElement(name);
		}
		jl = new JList<String>(dl);

		border_north = BorderFactory.createLineBorder(Color.white, 2);
		border_east = BorderFactory.createLineBorder(Color.white, 4);
		border_center = BorderFactory.createLineBorder(Color.white, 8);

		panel_bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 10));
		panel_bottom.setBackground(Color.BLUE);
		panel_bottom.setPreferredSize(new Dimension(900, 100));
		panel_bottom.setBorder(border_north);

		lb_shape = new JLabel("Shape:");
		lb_color = new JLabel("Color");

		cb_draw = new JComboBox<String>(shape);
		cb_color = new JComboBox<String>(color);

		mb = new JMenuBar();
		file = new JMenu("File");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");

		file.add(open);
		file.add(save);
		file.add(exit);

		mb.add(file);

		changeUser = new JMenu("Change User");
		changeUser.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				new Login();
				fr.setVisible(false);
				fr.dispose();
			}
		});
		blur = new JButton("Blur");
		sharpen = new JButton("Sharpen");
		brighten = new JButton("Brighten");
		rotate = new JButton("Rotate");
		negative = new JButton("Negative");

		mb.add(changeUser);
		
		
		fr.add(mb, BorderLayout.NORTH);
		panel_bottom.add(lb_shape);
		panel_bottom.add(cb_draw);
		panel_bottom.add(lb_color);
		panel_bottom.add(cb_color);
		panel_bottom.add(checked);
		panel_bottom.add(blur);
		panel_bottom.add(sharpen);
		panel_bottom.add(brighten);
		panel_bottom.add(rotate);
		panel_bottom.add(negative);
		fr.add(panel_bottom, BorderLayout.SOUTH);

		panel_west = new JPanel();
		panel_west.setLayout(new BoxLayout(panel_west, BoxLayout.Y_AXIS));
		panel_west.setBackground(Color.white);
		panel_west.setPreferredSize(new Dimension(200, 700));
		panel_west.setBorder(border_east);

		lb_lib = new JLabel("Library");
		lb_lib.setHorizontalAlignment(JLabel.LEFT);
		lb_lib.setFont(new Font("arial black", Font.BOLD, 20));
		panel_west.add(lb_lib);
		panel_west.add(jl);

		fr.add(panel_west, BorderLayout.WEST);

		open.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				openFile();

			}
		});

		draw_panel = new JPanel() {

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (image != null) {
					g.drawImage(image, 10, 10, 600, 500, null);

				}

			}
		};

		draw_panel.setPreferredSize(new Dimension(600, 500));
		draw_panel.setBackground(Color.gray);
		draw_panel.setBorder(border_center);

		draw_panel.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				x2 = e.getX();
				y2 = e.getY();

			}

			public void mousePressed(MouseEvent e) {
				x1 = e.getX();
				y1 = e.getY();

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		draw_panel.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseDragged(MouseEvent e) {
				int s = cb_draw.getSelectedIndex();
				// System.out.println(cb_draw.getSelectedItem());
				Graphics2D gs2 = image.createGraphics();

				int ind = cb_color.getSelectedIndex();
				Color col = colorArray[ind];
				gs2.setColor(col);
				switch (s) {
				case 0:
					gs2.drawRect(x1, y1, x2, y2);
					draw_panel.repaint();
					break;
				case 1:
					gs2.drawOval(x1, y1, Math.abs(x1 - x2), Math.abs(y1 - y2));
					draw_panel.repaint();
					break;
				case 2:
					gs2.drawLine(x1, y1, x2, y2);
					draw_panel.repaint();
					break;
				}

			}
		});

		fr.add(panel_bottom, BorderLayout.SOUTH);
		// panel_center.add(draw_panel);

		fr.add(draw_panel, BorderLayout.CENTER);

		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				db = new DbConnection();
				saved = db.addImage(filename, Login.userId);
				if (saved == 1) {
					JOptionPane.showMessageDialog(null, "image saved in database");
				}

			}
		});

		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		blur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				float weight = 1.0f / 9.0f;
				float[] elements = new float[9];
				for (int i = 0; i < 9; i++)
					elements[i] = weight;
				convolve(elements);
			}
		});

		sharpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				float[] elements = { 0.0f, -1.0f, 0.0f, -1.0f, 5.f, -1.0f, 0.0f, -1.0f, 0.0f };
				convolve(elements);
			}
		});

		brighten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				float a = 1.1f;
				// float b = 20.0f;
				float b = 0;
				RescaleOp op = new RescaleOp(a, b, null);
				filter(op);
			}
		});

		rotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (image == null)
					return;
				AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(90), image.getWidth() / 2,
						image.getHeight() / 2);
				AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
				filter(op);
			}
		});

		negative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				short[] negative = new short[256 * 1];
				for (int i = 0; i < 256; i++)
					negative[i] = (short) (255 - i);
				ShortLookupTable table = new ShortLookupTable(0, negative);
				LookupOp op = new LookupOp(table, null);
				filter(op);
			}
		});

		jl.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("hello sir g");
				String data = jl.getSelectedValue();

				System.out.println(data);
				String datapath = "images\\" + data;
				System.out.println(datapath);

				try {
					File fe = new File(datapath);
					imge = ImageIO.read(fe);
					buffimg = new BufferedImage(imge.getWidth(null), imge.getHeight(null), BufferedImage.TYPE_INT_RGB);
					buffimg.getGraphics().drawImage(imge, 0, 0, null);
					System.out.println("hi");

					new ImagePanel();

				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}

		});

		// fr.add(panelclick,BorderLayout.CENTER);

		fr.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setLocationRelativeTo(null);

		System.out.println("Size is " + draw_panel.getSize());

	}

	public void openFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("./images"));
		String[] extensions = ImageIO.getReaderFileSuffixes();
		chooser.setFileFilter(new FileNameExtensionFilter("Image files", extensions));
		int r = chooser.showOpenDialog(fr);
		if (r != JFileChooser.APPROVE_OPTION)
			return;

		try {

			filei = chooser.getSelectedFile();
			Image img = ImageIO.read(filei);
			filename = filei.getName();

			image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(img, 0, 0, null);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(fr, e);
		}
		fr.repaint();
	}

	private void filter(BufferedImageOp op) {
		if (image == null)
			return;
		image = op.filter(image, null);
		fr.repaint();
	}

	private void convolve(float[] elements) {
		Kernel kernel = new Kernel(3, 3, elements);
		ConvolveOp op = new ConvolveOp(kernel);
		filter(op);
	}
	public static void main(String[] args) {
		new Login();
	}


}
