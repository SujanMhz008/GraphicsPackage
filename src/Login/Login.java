package Login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DbConnection.*;
import drawpanel.DrawingFrame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Login {

	private JFrame fr_login;
	private JLabel lb_login, lb_username, lb_password;
	private JTextField tf_username;
	private JPasswordField pf_password;
	private JButton b_login, b_cancel, b_register;
	private JPanel p_login, p_userInput, p_register, p_mid;
	private static JPanel p_buttons;
	private JPanel p_container;
	private ImageIcon bg_Image;
	public static int userId;

	public Login() {
		frameLogin();
	}

	public void frameLogin() {
		fr_login = new JFrame("Login");

		bg_Image = new ImageIcon("index.jpg");

		new JLabel(bg_Image);

		lb_login = new JLabel("Login");
		lb_login.setFont(new Font("Arial", Font.BOLD, 30));

		p_login = new JPanel();
		p_login.setPreferredSize(new Dimension(400, 75));
		p_login.add(lb_login);
		p_login.setOpaque(false);

		lb_username = new JLabel("Username: ");
		lb_username.setHorizontalAlignment(JLabel.CENTER);

		tf_username = new JTextField();
		tf_username.setHorizontalAlignment(JLabel.LEFT);

		lb_password = new JLabel("Password: ");
		lb_password.setHorizontalAlignment(JLabel.CENTER);

		pf_password = new JPasswordField();
		pf_password.setHorizontalAlignment(JLabel.LEFT);

		p_userInput = new JPanel();
		p_userInput.setLayout(new GridLayout(2, 2, 30, 20));
		p_userInput.setPreferredSize(new Dimension(400, 100));
		p_userInput.setOpaque(false);

		p_userInput.add(lb_username);
		p_userInput.add(tf_username);
		p_userInput.add(lb_password);
		p_userInput.add(pf_password);

		b_login = new JButton("Login");
		b_login.setBounds(100, 15, 80, 35);
		b_login.setHorizontalAlignment(JButton.CENTER);

		b_cancel = new JButton("Cancel");
		b_cancel.setBounds(220, 15, 80, 35);
		b_cancel.setHorizontalAlignment(JButton.CENTER);

		p_buttons = new JPanel();
		p_buttons.setLayout(null);
		p_buttons.setPreferredSize(new Dimension(400, 50));
		p_buttons.setOpaque(false);

		p_buttons.add(b_login);
		p_buttons.add(b_cancel);

		p_mid = new JPanel();
		p_mid.setPreferredSize(new Dimension(400, 150));
		p_mid.setOpaque(false);

		p_mid.add(p_userInput);
		p_mid.add(p_buttons);

		b_register = new JButton("Sign up");
		b_register.setBounds(150, 50, 100, 40);

		p_register = new JPanel();
		p_register.setPreferredSize(new Dimension(400, 110));
		p_register.setLayout(null);

		p_register.add(b_register);
		p_register.setOpaque(false);

		p_container = new JPanel();
		p_container.setLayout(new BorderLayout());
		p_container.setOpaque(false);
		
		p_container.add(p_login, BorderLayout.NORTH);
		p_container.add(p_mid, BorderLayout.CENTER);
		p_container.add(p_register, BorderLayout.SOUTH);
		
		fr_login.add(p_container);
		

		b_register.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new SignUp();
				fr_login.setVisible(false);
				fr_login.dispose();
			}
		});

		b_cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ec) {
				System.exit(0);
			}
		});

		b_login.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DbConnection db = new DbConnection();

				char pass[] = pf_password.getPassword();
				String password = new String(pass);
				userId = db.selectUser(tf_username.getText(), password);

				
				if (userId > 1) {
						new DrawingFrame();
						fr_login.setVisible(false);
						fr_login.dispose();
					}

				else {
					JOptionPane.showMessageDialog(null, "Username or password wrong.");
				}
			}
		});

		fr_login.pack();
		fr_login.setSize(430, 400);
		fr_login.setVisible(true);
		fr_login.setLocationRelativeTo(null);
		fr_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
