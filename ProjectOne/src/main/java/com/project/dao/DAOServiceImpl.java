package com.project.dao;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.classes.Reimbursement;
import com.project.classes.User;


public class DAOServiceImpl implements DAOService {
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("ojdbc7.jar/oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static String dbUrl = "jdbc:oracle:thin:@wvudatabase.ctepdgislsrz.us-east-2.rds.amazonaws.com:1521:orcl";
	private static String dbUsername = "ers_db_admin";
	private static String dbPassword = "p4ssw8rd6";
	
	@Override
	public int createAccount(String username, String password, String fname, String lname, String email, int role) {
		int result = 0;
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			String sql = "INSERT INTO ers_users(ers_username,ers_password,user_first_name,user_last_name,user_email,user_role_id)"
		+ " VALUES(?,?,?,?,?,?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,username);
			ps.setString(2,password);
			ps.setString(3,fname);
			ps.setString(4,lname);
			ps.setString(5,email);
			ps.setInt(6,role);
			
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}		
		return result;
	}

	//do a callable statement
	@Override
	public int createReimbRequest(double amount, String description, int author, int status, int type) {
		int result = 0;
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			String sql = "{ call insert_new_reimb1(?,?,?,?,?)}";

			CallableStatement cs = conn.prepareCall(sql);
			cs.setDouble(1,amount);
			cs.setString(2,description);
			cs.setInt(3,author);
			cs.setInt(4,status);
			cs.setInt(5,type);
			
			result = cs.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}		
		return result;
	}

	//do a callable statement
	@Override
	public int createReimbRequest(double amount, String description, Blob receipt, int author, int status, int type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Reimbursement> selectAllReimb() {
		List<Reimbursement> reimb = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			
//			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_submitted DESC";
			String sql = "SELECT r.reimb_id, r.reimb_type_id, r.reimb_status_id, r.reimb_amount, u.user_first_name, u.user_last_name, r.reimb_submitted, r.reimb_description\n" + 
					" FROM ers_reimbursement r, ers_users u WHERE r.reimb_author = u.ers_users_id ORDER BY r.reimb_submitted DESC";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				//reimb_id, reimb_type_id, reimb_status_id, reimb_amount, user_first_name, user_last_name, reimb_submitted, reimb_description
				reimb.add(new Reimbursement(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimb;		
	}
	
	@Override
	public List<Reimbursement> selectAllReimbByStatus(int status) {
		List<Reimbursement> reimb = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			
//			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_submitted DESC";
			String sql = "SELECT r.reimb_id, r.reimb_type_id, r.reimb_status_id, r.reimb_amount, u.user_first_name, u.user_last_name, r.reimb_submitted, r.reimb_description" + 
					" FROM ers_reimbursement r, ers_users u WHERE r.reimb_author = u.ers_users_id AND r.reimb_status_id = ? ORDER BY r.reimb_submitted DESC";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,status);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				//reimb_id, reimb_type_id, reimb_status_id, reimb_amount, user_first_name, user_last_name, reimb_submitted, reimb_description
				reimb.add(new Reimbursement(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		/*		List<Reimbursement> reimb = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = ? ORDER BY reimb_submitted DESC";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,status);
			ResultSet rs = ps.executeQuery();
			
			//int id, double amount, String dateSubmitted, String dateResolved, String description,
			//Blob receipt, int author, int resolver, int status, int type
			while(rs.next()) {
				reimb.add(new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getBlob(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimb;*/
		return reimb;
	}

	@Override
	public List<Reimbursement> selectAllReimbByUser(int user, int role) {
		List<Reimbursement> reimb = new ArrayList<>();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			if(role == 0) {//employee
				sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ? ORDER BY reimb_submitted DESC";	
			} else if(role == 1) {//finance manager
				sql = "SELECT * FROM ers_reimbursement WHERE reimb_resolver = ? ORDER BY reimb_submitted DESC";
			}
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,user);
			ResultSet rs = ps.executeQuery();
			
			//int id, double amount, String dateSubmitted, String dateResolved, String description,
			//Blob receipt, int author, int resolver, int status, int type
			while(rs.next()) {
				reimb.add(new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getBlob(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimb;
	}
	
	@Override
	public List<Reimbursement> selectAllReimbByEmployeeId(int user) {
		List<Reimbursement> reimb = new ArrayList<>();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ? ORDER BY reimb_submitted DESC";	
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,user);
			ResultSet rs = ps.executeQuery();
			
			//int id, double amount, String dateSubmitted, String dateResolved, String description,
			//Blob receipt, int author, int resolver, int status, int type
			while(rs.next()) {
				reimb.add(new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getBlob(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimb;
	}

	@Override
	public List<Reimbursement> selectAllReimbByAuthorStatus(int author, int status) {
		List<Reimbursement> reimb = new ArrayList<>();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ? AND reimb_status_id = ?  ORDER BY reimb_submitted DESC";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,author);
			ps.setInt(2,status);
			ResultSet rs = ps.executeQuery();
			
			//int id, double amount, String dateSubmitted, String dateResolved, String description,
			//Blob receipt, int author, int resolver, int status, int type
			while(rs.next()) {
				reimb.add(new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getBlob(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimb;
	}

	@Override
	public List<Reimbursement> selectReimbByAuthorType(int author, int type) {
		List<Reimbursement> reimb = new ArrayList<>();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
				sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ? AND reimb_type_id = ? ORDER BY reimb_submitted DESC";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,author);
			ps.setInt(2,type);
			ResultSet rs = ps.executeQuery();
			
			//int id, double amount, String dateSubmitted, String dateResolved, String description,
			//Blob receipt, int author, int resolver, int status, int type
			while(rs.next()) {
				reimb.add(new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getBlob(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimb;		
	}
	
	@Override
	public List<Reimbursement> selectReimByType(int type) {
		List<Reimbursement> reimb = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			
//			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_submitted DESC";
			String sql = "SELECT r.reimb_id, r.reimb_type_id, r.reimb_status_id, r.reimb_amount, u.user_first_name, u.user_last_name, r.reimb_submitted, r.reimb_description" + 
					" FROM ers_reimbursement r, ers_users u WHERE r.reimb_author = u.ers_users_id AND r.reimb_type_id = ? ORDER BY r.reimb_submitted DESC";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,type);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				//reimb_id, reimb_type_id, reimb_status_id, reimb_amount, user_first_name, user_last_name, reimb_submitted, reimb_description
				reimb.add(new Reimbursement(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimb;
	}

	@Override
	public Reimbursement selectReimbById(int id) {
		Reimbursement reimb = new Reimbursement();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";	
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			
			//int id, double amount, String dateSubmitted, String dateResolved, String description,
			//Blob receipt, int author, int resolver, int status, int type
			while(rs.next()) {
				reimb = new Reimbursement(rs.getInt(1),rs.getDouble(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getBlob(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return reimb;
	}

	@Override
	public User getUserByID(int id) {
		User newUser = new User();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			sql = "SELECT * FROM ers_users WHERE ers_user_id = ?";	
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();

			//int id, String username, String password, String fname, String lname, String email, int role
			while(rs.next()) {
				newUser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getString(6),rs.getInt(7));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return newUser;
	}

	@Override
	public User getUserByUsername(String username) {
		User newUser = new User();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			sql = "SELECT * FROM ers_users WHERE ers_username = ?";	
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,username);
			ResultSet rs = ps.executeQuery();

			//int id, String username, String password, String fname, String lname, String email, int role
			while(rs.next()) {
				newUser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getString(6),rs.getInt(7));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return newUser;
	}

	@Override
	public User getUserByEmail(String email) {
		User newUser = new User();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			sql = "SELECT ers_users_id,ers_username,ers_password,user_email,user_role_id FROM ers_users WHERE user_email = ?";	
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,email);
			ResultSet rs = ps.executeQuery();

			//int id, String username, String password, String fname, String lname, String email, int role
			while(rs.next()) {
				newUser = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return newUser;	
	}
	
	@Override
	public List<User> getAllUsersByRoles(int role) {
		List<User> newUser = new ArrayList<>();
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			sql = "SELECT * FROM ers_users WHERE user_role_id = ?";	
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,role);
			ResultSet rs = ps.executeQuery();

			//int id, String username, String password, String fname, String lname, String email, int role
			while(rs.next()) {
				newUser.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
						rs.getString(5),rs.getString(6),rs.getInt(7)));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return newUser;
	}

	@Override
	public int updateReimbResolvedTime(int id) {
		int result = 0;
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){
			String sql = "{ call update_approval_time(?)}";

			CallableStatement cs = conn.prepareCall(sql);
			cs.setInt(1,id);
			
			result = cs.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}		
		return result;
	}

	@Override
	public int updatedReimbResolver(int reimbId, int resolver) {
		int status = 0;
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){		
			sql = "UPDATE ers_reimbursement SET reimb_resolver = ? WHERE reimb_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,resolver);
			ps.setInt(2,reimbId);
			
			status = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}		
		return status;
	}

	@Override
	public int updatedReimbStatus(int id, int status) {
		int result = 0;
		String sql = "";
		try(Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)){		
			sql = "UPDATE ers_reimbursement SET reimb_status_id = ? WHERE reimb_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,status);
			ps.setInt(2,id);
			
			status = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}		
		return result;
	}


}
