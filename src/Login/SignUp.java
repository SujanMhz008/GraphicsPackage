package Login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DbConnection.DbConnection;

public class SignUp {

	private JFrame fr_signup;
	private JButton b_register, b_back;
	private JLabel lb_signup, lb_fname, lb_lname, lb_username, lb_password, lb_cpassword, lb_dob, lb_email;
	private JTextField tf_username, tf_fname, tf_lname, tf_dob, tf_email;
	private JPasswordField pf_password, pf_cpassword;
	private JPanel p_signup, p_mid, p_bottom, p_contents;

	public SignUp() {
		elements();
	}

	public void elements() {
		fr_signup = new JFrame();

		lb_signup = new JLabel("Sign Up");
		lb_signup.setFont(new Font("Arial", Font.BOLD, 30));

		p_signup = new JPanel();
		p_signup.setPreferredSize(new Dimension(130, 40));
		p_signup.add(lb_signup);

		lb_fname = new JLabel("First Name ");
		lb_fname.setHorizontalAlignment(JLabel.CENTER);

		lb_lname = new JLabel("Last Name ");
		lb_lname.setHorizontalAlignment(JLabel.CENTER);

		lb_username = new JLabel("Username ");
		lb_username.setHorizontalAlignment(JLabel.CENTER);

		lb_password = new JLabel("Password ");
		lb_password.setHorizontalAlignment(JLabel.CENTER);

		lb_cpassword = new JLabel("Confirm Password ");
		lb_cpassword.setHorizontalAlignment(JLabel.CENTER);
		
		lb_dob = new JLabel("Date of Birth ");
		lb_dob.setHorizontalAlignment(JLabel.CENTER);
	
		lb_email = new JLabel("Email Address ");
		lb_email.setHorizontalAlignment(JLabel.CENTER);

		tf_username = new JTextField();

		tf_fname = new JTextField();

		tf_lname = new JTextField();

		pf_password = new JPasswordField();

		pf_cpassword = new JPasswordField();
		
		tf_dob = new JTextField();
		
		tf_email = new JTextField();

		p_mid = new JPanel();
		p_mid.setLayout(new GridLayout(7, 2, 50, 10));
		p_mid.setPreferredSize(new Dimension(400, 300));

		p_mid.add(lb_fname);
		p_mid.add(tf_fname);
		p_mid.add(lb_lname);
		p_mid.add(tf_lname);
		p_mid.add(lb_username);
		p_mid.add(tf_username);
		p_mid.add(lb_password);
		p_mid.add(pf_password);
		p_mid.add(lb_cpassword);
		p_mid.add(pf_cpassword);
		p_mid.add(lb_dob);
		p_mid.add(tf_dob);
		p_mid.add(lb_email);
		p_mid.add(tf_email);

		b_register = new JButton("Register");
		b_register.setBounds(95, 20, 100, 40);

		b_back = new JButton("Back");
		b_back.setBounds(235, 20, 100, 40);

		p_bottom = new JPanel();
		p_bottom.setLayout(null);
		p_bottom.setPreferredSize(new Dimension(430, 100));

		p_bottom.add(b_register);
		p_bottom.add(b_back);

		p_contents = new JPanel();
		p_contents.setLayout(new BorderLayout());

		p_contents.add(p_signup, BorderLayout.NORTH);
		p_contents.add(p_mid, BorderLayout.CENTER);
		p_contents.add(p_bottom, BorderLayout.SOUTH);

		fr_signup.add(p_contents);

		b_back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new Login();
				fr_signup.dispose();
			}
		});

		b_register.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				DbConnection db = new DbConnection();

				char pass[] = pf_password.getPassword();
				char cpass[] = pf_cpassword.getPassword();
				String password = new String(pass);
				String cpassword = new String(cpass);
				int data = db.insertUser(tf_username.getText(), password);

				if (cpassword.equals(password)) {
					if (data == 1) {
						JOptionPane.showMessageDialog(null, "New user created.");
					} else {
						JOptionPane.showMessageDialog(null, "New user not created.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Passwords don't match.");
				}

			}
		});

		fr_signup.setSize(460, 500);
		fr_signup.setVisible(true);
		fr_signup.setLocationRelativeTo(null);
		fr_signup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
