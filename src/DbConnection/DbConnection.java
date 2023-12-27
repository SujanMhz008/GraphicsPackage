package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbConnection {

	String driver = "com.mysql.jdbc.Driver";
	String URL = "jdbc:mysql://localhost/assignment";
	String username = "root";
	String password = "";
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	boolean b = false;
	int res, z;

	public DbConnection() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(URL, username, password);
		}

		catch (ClassNotFoundException ce) {

			ce.printStackTrace();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public Connection getMyConnection() {
		return con;
	}

	public int selectUser(String username, String password) {
		try {
			pst = con
					.prepareStatement("select * from User where Username = ? and Password = ?");
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if(rs.next()){
				return rs.getInt("id");
			}
		}

		catch (SQLException se) {
			se.printStackTrace();
		}
		return 0;
	}

	public int insertUser(String txtname, String txtpassword) {
		try{
			pst = con.prepareStatement("insert into User(Username, Password) values (?,?)");
			pst.setString(1, txtname);
			pst.setString(2, txtpassword);
			res = pst.executeUpdate();
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		return res;

	}
	public int addImage(String fileName, int userId) {

		String sql = "insert into images(name, userid ) values (?, ?)";

		try {
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, fileName);
			stmt.setInt(2, userId);

			z=stmt.executeUpdate(); 
				
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return z;

	}
	public ArrayList<String> getFileName(int userid)
	 {
		 ArrayList<String> fnames=new ArrayList<String>();
		 try {
			pst=con.prepareStatement("select name from images where userid=?");
			pst.setInt(1,userid);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				fnames.add(rs.getString(1));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		 return fnames;
	 }
}
