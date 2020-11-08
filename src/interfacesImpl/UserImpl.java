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

import dao.LibrarianDao;
import dao.UserDao;
import interfaces.BaseInterface;
import utility.ConnectionManager;
import utility.RegistrationValidation;

public class UserImpl implements BaseInterface {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintStream out = System.out;
	UserDao userDao = new UserDao();
	LibrarianDao librarianDao = new LibrarianDao();
	String fk = getUserRoleId();
	RegistrationValidation validation = new RegistrationValidation();

	// Get MEMBER ID from Role
	public String getUserRoleId() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT ROLE_ID FROM ROLE WHERE ROLE_TYPE = 'user' OR ROLE_TYPE = 'User' OR ROLE_TYPE = 'USER'";
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

		String id = "";
		try {
			while (resultSet.next()) {
				id = resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (id.contentEquals("")) {
			System.out.println("Please Add User Id for User or Check Whether User Exist");
		}
		return id;
	}

	@Override
	public void add() {
		String userId = "";
		boolean userIdFlag = true;
		while (userIdFlag) {
			out.println("Enter the User Id");

			try {
				userId = br.readLine();
				if (validation.id(userId)) {
					userIdFlag = false;
				} else {
					System.out.println(
							" 1. Left part has fixed 4 capital English letters.\n 2. Right part has digits where length of digits between 2 and 5.");
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		String name = "";
		boolean flagName = true;
		while (flagName) {
			out.println("Enter the name");
			try {
				name = br.readLine();
				if (validation.name(name)) {
					flagName = false;
				} else {
					System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		String password = "";
		boolean flagPassword = true;
		while (flagPassword) {
			out.println("Enter the password");

			try {
				password = br.readLine();
				if (validation.validPassword(password)) {
					flagPassword = false;
				} else {
					System.out.println(
							"Password Should Contain Atleast 1 UpperCase, Lowercase & Symbol\n Must be 8 characters upto 20 Characters");

				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		String phoneNo = "";
		boolean phoneNoFlag = true;
		while (phoneNoFlag) {
			out.println("Enter the Phone No.");
			try {
				phoneNo = br.readLine();
				if (validation.phoneNumber(phoneNo)) {
					phoneNoFlag = false;
				} else {
					System.out.println("Please Enter 10 Digit number & Number Should starts with 7,8 or 9");

				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		out.println("Enter the Address");
		String address = "";
		try {
			address = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO USERS(USER_ID, USER_NAME, USER_PASSWORD, USER_PHONE_NO, USER_ADDRESS,ROLE) VALUES(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, phoneNo);
			preparedStatement.setString(5, address);
			preparedStatement.setString(6, fk);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int rows = 0;
		try {
			rows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (rows > 0) {
			out.println("Inserted Successfully!!!");
		} else {
			out.println("Not Inserted Successfully!!!");
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

		String sql = "SELECT * FROM USERS WHERE ROLE = " + "'" + fk + "'";
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
			System.out.println("\t\t\t\t\t <<<==| USER DETAILS |==>>>");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------");
			System.out.printf("%7s %9s %16s %16s %20s %17s ", "USER ID", "NAME", "PASSWORD", "PHONE NO.", "ADDRESS",
					"ROLE");
			System.out.println();
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {
				String id = resultSet.getString("USER_ID");
				String name = resultSet.getString("USER_NAME");
				String password = resultSet.getString("USER_PASSWORD");
				String phoneNo = resultSet.getString("USER_PHONE_NO");
				String address = resultSet.getString("USER_ADDRESS");
				String role = resultSet.getString("ROLE");
//				out.println(id + "          " + name + "          " + password + "          " + phoneNo + "          "
//						+ address + "          " + role);
				System.out.format("%7s %13s %13s %13s %25s %15s ", id, name, password, phoneNo, address, role);
				System.out.println();
			}
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displayById() {
		out.println("Enter the User Id");
		String userId = "";
		try {
			userId = br.readLine();
		} catch (NumberFormatException | IOException e2) {
			e2.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT * FROM USERS WHERE USER_ID = " + "'" + userId + "'" + " AND ROLE = " + "'" + fk + "'";
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
			System.out.println("\t\t\t\t\t <<<==| USER DETAILS |==>>>");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------");
			System.out.printf("%7s %9s %16s %16s %20s %17s ", "USER ID", "NAME", "PASSWORD", "PHONE NO.", "ADDRESS",
					"ROLE");
			System.out.println();
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {
				String id = resultSet.getString("USER_ID");
				String name = resultSet.getString("USER_NAME");
				String password = resultSet.getString("USER_PASSWORD");
				String phoneNo = resultSet.getString("USER_PHONE_NO");
				String address = resultSet.getString("USER_ADDRESS");
				String role = resultSet.getString("ROLE");
//				out.println(id + "          " + name + "          " + password + "          " + phoneNo + "          "
//						+ address + "          " + role);
				System.out.format("%7s %13s %13s %13s %25s %15s ", id, name, password, phoneNo, address, role);
				System.out.println();
			}
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		out.println("Enter the User Id which you want to update??");
		String userId = "";
		try {
			userId = br.readLine();
		} catch (NumberFormatException | IOException e2) {
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
					"\t\t\t\t\t\tEnter the choice which you want to Update?\n\t\t\t\t\t\t\t 1.Name\n\t\t\t\t\t\t\t 2.Password\n\t\t\t\t\t\t\t 3.Phone Number\n\t\t\t\t\t\t\t 4.Address\n\t\t\t\t\t\t\t 5.Exit");
			out.println("\t\t\t\t\t Enter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (NumberFormatException | IOException e2) {
				e2.printStackTrace();
			}

			switch (choice) {
			case 1:
				String userName = "";
				boolean flagName = true;
				while (flagName) {
					out.println("Enter the updated name");
					try {
						userName = br.readLine();
						if (validation.name(userName)) {
							flagName = false;
						} else {
							System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				PreparedStatement statement = null;
				try {
					statement = connection
							.prepareStatement("UPDATE USERS SET USER_NAME = ? where USER_ID = ? AND ROLE = ? ");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.setString(1, userName);
					statement.setString(2, userId);
					statement.setString(3, fk);
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
					System.out.println(" Invalid Id Try Again...");
				}
				break;
			case 2:

				String password = "";
				boolean flagPassword = true;
				while (flagPassword) {
					out.println("Enter the updated password");

					try {
						password = br.readLine();
						if (validation.validPassword(password)) {
							flagPassword = false;
						} else {
							System.out.println(
									"Password Should Contain Atleast 1 UpperCase, Lowercase & Symbol\n Must be 8 characters upto 20 Characters");

						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				String sql = "UPDATE USERS SET USER_PASSWORD = ? where USER_ID = ? AND ROLE = ?";
				PreparedStatement statement1 = null;
				try {
					statement1 = connection.prepareStatement(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement1.setString(1, password);
					statement1.setString(2, userId);
					statement1.setString(3, fk);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				int rows1 = 0;
				try {
					rows1 = statement1.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (rows1 != 0) {
					System.out.println("Updated Successfully!!!");
				} else {
					System.out.println(" Invalid Id Try Again...");
				}
				break;
			case 3:

				String phoneNo = "";
				boolean phoneNoFlag = true;
				while (phoneNoFlag) {
					out.println("Enter the Phone No.");
					try {
						phoneNo = br.readLine();
						if (validation.phoneNumber(phoneNo)) {
							phoneNoFlag = false;
						} else {
							System.out.println("Please Enter 10 Digit number & Number Should starts with 7,8 or 9");

						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				PreparedStatement statement2 = null;
				try {
					statement2 = connection
							.prepareStatement("UPDATE USERS SET USER_PHONE_NO = ? where USER_ID = ? AND ROLE = ? ");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement2.setString(1, phoneNo);
					statement2.setString(2, userId);
					statement2.setString(3, fk);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				int rows2 = 0;
				try {
					rows2 = statement2.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (rows2 != 0) {
					System.out.println("Updated Successfully!!!");
				} else {
					System.out.println(" Invalid Id Try Again...");
				}
				break;
			case 4:

				out.println("Enter the updated Address");
				String address = "";
				try {
					address = br.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				PreparedStatement statement3 = null;
				try {
					statement3 = connection
							.prepareStatement("UPDATE USERS SET USER_ADDRESS = ? where USER_ID = ? AND ROLE = ? ");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement3.setString(1, address);
					statement3.setString(2, userId);
					statement3.setString(3, fk);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				int rows3 = 0;
				try {
					rows3 = statement3.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (rows3 != 0) {
					System.out.println("Updated Successfully!!!");
				} else {
					System.out.println(" Invalid Id Try Again...");
				}
				break;
			case 5:
				result = false;
				userDao.userMenu();
				break;
			default:
				result = false;
				librarianDao.librarianMainMenu();
				break;
			}
		}
	}

	@Override
	public void delete() {
		out.println("Enter the User Id");
		String userId = "";
		try {
			userId = br.readLine();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "DELETE FROM USERS WHERE USER_ID = " + "'" + userId + "'" + "AND ROLE = " + "'" + fk + "'";
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int rows = 0;
		try {
			rows = statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Invalid Operations Try Again..");
		}

		if (rows > 0) {
			System.out.println("Deleted Successfully!!!");
		} else {
			System.out.println("Invalid ID Try Again..!!!");
		}
	}

}
