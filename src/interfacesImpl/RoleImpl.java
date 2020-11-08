package interfacesImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.AdminDao;
import dao.RoleDao;
import interfaces.BaseInterface;
import model.Role;
import utility.ConnectionManager;
import utility.RegistrationValidation;

public class RoleImpl implements BaseInterface {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintStream out = System.out;
	Role role = new Role();
	AdminDao adminDao = new AdminDao();
	RoleDao roleDao = new RoleDao();
	RegistrationValidation validation = new RegistrationValidation();

	@Override
	public void add() {
		boolean role_id = true;
		String roleId = "";
		while (role_id) {
			out.println("Enter the Role Id");

			try {

				roleId = br.readLine();
				if (validation.id(roleId)) {
					role_id = false;
				} else {
					System.out.println(
							" 1. Left part has fixed 4 capital English letters.\n 2. Right part has digits where length of digits between 2 and 5.");
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		String roleType = "";
		boolean role_type = true;
		while (role_type) {
			out.println("Enter the Role type");

			try {
				roleType = br.readLine();
				if (validation.name(roleType)) {
					role_type = false;
				} else {
					System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Role role = new Role(roleId, roleType);
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "insert into role values(?,?)";
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, role.getRoleId());
			preparedStatement.setString(2, role.getRoleType());

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int rows = 0;
		try {
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Invalid Operations May be This ID already exist");
		}

		if (rows > 0) {
			out.println("Inserted Successfully!!!");
		} else {
			out.println("Not Inserted Successfully!!!");
		}
	}

	@Override
	public void displayById() {
		out.println("Enter the Role Id");
		String roleId = "";
		try {
			roleId = br.readLine();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		role.setRoleId(roleId);
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT * FROM ROLE WHERE ROLE_ID = " + "'" + role.getRoleId() + "'";
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("   <<<==| ROLE DETAILS |==>>>");
			System.out.println("------------------------------");
			System.out.printf("%10s %13s", "ROLE ID", "ROLE TYPE");
			System.out.println();
			System.out.println("------------------------------");

			while (resultSet.next()) {
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);
				System.out.format("%11s %12s", id, name);
				System.out.println();
			}
			System.out.println("------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update() {
		out.println("Enter the Role Id which you want to update??");
		String roleId = "";
		try {
			roleId = br.readLine();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		Connection connection = null;
		boolean result = true;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (result) {
			System.out.println(
					"\t\t\t\t\t\tEnter the choice which you want to Update?\n\t\t\t\t\t\t\t 1. Enter the Role Type\n\t\t\t\t\t\t\t 2. Exit");
			int choice = 0;
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (NumberFormatException | IOException e2) {
				e2.printStackTrace();
			}

			switch (choice) {
			case 1:

				String roleType = "";
				boolean role_type = true;
				while (role_type) {
					out.println("Enter the Updated Role type");

					try {
						roleType = br.readLine();
						if (validation.name(roleType)) {
							role_type = false;
						} else {
							System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				String sql = "UPDATE ROLE SET ROLE_TYPE = ? where ROLE_ID = ?";
				PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.setString(1, roleType);
					statement.setString(2, roleId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				int rows = 0;
				try {
					rows = statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (rows != 0) {
					System.out.println("Updated Successfully!!!");
				} else {
					System.out.println(" Invalid Role Id Try Again...");
				}
				break;
			case 2:
				result = false;
				roleDao.roleMenu();
				break;
			default:
				result = false;
				roleDao.roleMenu();
				break;
			}
		}
	}

	@Override
	public void displayAll() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "select * from role";
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("   <<<==| ROLE DETAILS |==>>>");

			System.out.println("------------------------------");
			System.out.printf("%10s %13s", "ROLE ID", "ROLE TYPE");
			System.out.println();
			System.out.println("------------------------------");
			while (resultSet.next()) {
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);
				System.out.format("%11s %12s", id, name);
				System.out.println();
			}
			System.out.println("------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete() {
		out.println("Enter the Role Id");
		String roleId = "";
		try {
			roleId = br.readLine();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "DELETE FROM ROLE WHERE ROLE_ID = " + "'" + roleId + "'";
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int row = 0;
		try {
			row = statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Invalid Operations May be Child Record Found");
		}

		if (row > 0) {
			System.out.println("Deleted Successfully!!!");
		} else {
			System.out.println("Invalid ID Try Again..!!!");
		}

	}

}
