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
import interfaces.BaseInterface;
import utility.ConnectionManager;
import utility.RegistrationValidation;

public class LibrarianImpl implements BaseInterface {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintStream out = System.out;
	AdminDao adminDao = new AdminDao();
	RegistrationValidation validation = new RegistrationValidation();

	public void librarianMenu() {
		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t------------------");
			System.out.println("\t\t\t\t\t\t\t LIBRARIAN-MENU");
			System.out.println("\t\t\t\t\t\t\t------------------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\t\t\t\t\t\t\t1.ADD LIBRARIAN\n\t\t\t\t\t\t\t2.UPDATE LIBRARIAN\n\t\t\t\t\t\t\t3.DISPLAY LIBRARIAN By Id\n\t\t\t\t\t\t\t4.DISPLAY LIBRARIAN\n\t\t\t\t\t\t\t5.DELETE LIBRARIAN\n\t\t\t\t\t\t\t6.Exit");
			System.out.println("\t\t\t\t\t____________________________________________________\n");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\t\t\t\t\tEnter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(reader.readLine());
			} catch (Exception e) {
				System.out.println("Please enter Valid key");
			}

			switch (choice) {
			case 1:
				add();
				break;
			case 2:
				update();
				break;
			case 3:
				displayById();
				break;
			case 4:
				displayAll();
				break;
			case 5:
				delete();
				break;
			case 6:
				result = false;
				adminDao.adminMenu();
				break;
			default:
				out.println("Invalid Pressed keys");
				break;
			}
		}
	}

	// Get Librarian ID from Role
	public String getLibrarianId() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT ROLE_ID FROM ROLE WHERE ROLE_TYPE = 'Librarian' OR ROLE_TYPE = 'librarian' OR ROLE_TYPE = 'LIBRARIAN' ";
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
			System.out.println("Please Add First Role Id for Librarian or Check Whether Role as Librarian Exist");
		}
		return id;
	}

	@Override
	public void add() {
		String librarianId = "";
		boolean librarianIdFlag = true;
		while (librarianIdFlag) {
			out.println("Enter the Librarian Id");
			try {
				librarianId = br.readLine();
				if (validation.id(librarianId)) {
					librarianIdFlag = false;
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
		boolean flagPassword = true;
		String password = "";
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
		boolean flagPhone = true;
		while (flagPhone) {
			out.println("Enter the Phone No.");
			try {
				phoneNo = br.readLine();
				if (validation.phoneNumber(phoneNo)) {
					flagPhone = false;
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
		String fk = getLibrarianId();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, librarianId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, phoneNo);
			preparedStatement.setString(5, address);
			preparedStatement.setString(6, fk);
		} catch (SQLException e1) {
			System.out.println("Invalid Operations May be ID already exist try Again");
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
	public void update() {
		out.println("Enter the Librarian Id which you want to update??");
		String librarianId = "";
		try {
			librarianId = br.readLine();
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
				System.out.println("Invalid Key..");
			}

			switch (choice) {
			case 1:
				String librarianName = "";
				boolean flagName = true;
				while (flagName) {
					out.println("Enter the updated Librarian name");

					try {
						librarianName = br.readLine();
						if (validation.name(librarianName)) {
							flagName = false;
						} else {
							System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement("UPDATE USERS SET USER_NAME = ? where USER_ID = ?");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.setString(1, librarianName);
					statement.setString(2, librarianId);
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

				boolean flagPassword = true;
				String password = "";
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

				String sql = "UPDATE USERS SET USER_PASSWORD = ? where USER_ID = ?";
				PreparedStatement statement1 = null;
				try {
					statement1 = connection.prepareStatement(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement1.setString(1, password);
					statement1.setString(2, librarianId);
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
				boolean flagPhone = true;
				while (flagPhone) {
					out.println("Enter the Phone No.");
					try {
						phoneNo = br.readLine();
						if (validation.phoneNumber(phoneNo)) {
							flagPhone = false;
						} else {
							System.out.println("Please Enter 10 Digit number & Number Should starts with 7,8 or 9");

						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				PreparedStatement statement2 = null;
				try {
					statement2 = connection.prepareStatement("UPDATE USERS SET USER_PHONE_NO = ? where USER_ID = ?");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement2.setString(1, phoneNo);
					statement2.setString(2, librarianId);
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
					statement3 = connection.prepareStatement("UPDATE USERS SET USER_ADDRESS = ? where USER_ID = ?");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement3.setString(1, address);
					statement3.setString(2, librarianId);
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
				librarianMenu();
				break;
			default:
				result = false;
				librarianMenu();
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

		String sql = "SELECT * FROM USERS WHERE ROLE = " + "'" + getLibrarianId() + "'";
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
			System.out.println("\t\t\t\t\t <<<==| LIBRARIAN DETAILS |==>>>");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------");
			System.out.printf("%7s %9s %11s %15s %18s %18s ", "LIBRARIAN ID", "NAME", "PASSWORD", "PHONE NO.",
					"ADDRESS", "ROLE");
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

		String sql = "SELECT * FROM USERS WHERE USER_ID = " + "'" + userId + "'";
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
			System.out.println("\t\t\t\t\t <<<==| LIBRARIAN DETAILS |==>>>");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------");
			System.out.printf("%7s %9s %11s %15s %18s %18s ", "LIBRARIAN ID", "NAME", "PASSWORD", "PHONE NO.",
					"ADDRESS", "ROLE");
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
	public void delete() {
		out.println("Enter the Librarian Id");
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

		String sql = "DELETE FROM USERS WHERE USER_ID = " + "'" + userId + "'";
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
			System.out.println("May be used this ID in Child tables");
		}

		if (rows > 0) {
			System.out.println("Deleted Successfully!!!");
		} else {
			System.out.println("Invalid ID Try Again..!!!");
		}

	}

}
