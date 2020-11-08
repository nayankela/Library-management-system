package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.MainApp;
import model.Issue;
import service.PdfGen;
import utility.ConnectionManager;

public class IssueDao {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintStream out = System.out;
	LibrarianDao librarianDao = new LibrarianDao();
	ReturnDao returnDao = new ReturnDao();
	MainApp mainApp = new MainApp();
	public void issueBook() throws Exception {
		List<Issue> list = getIssueDetails();
		String issueId = getIssueId();
		String userId = getUserId();
		String bookId = getBookId();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBookId().equalsIgnoreCase(bookId) && (list.get(i).getReturnDate() == null)) {
				System.out.println("This BOOk is Already Issued By the Person \n Check Another Ones...");
				librarianDao.librarianMainMenu();
			}
		}

		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO ISSUE(ISSUE_ID,USER_ID, BOOK_ID,ISSUE_DATE) VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, issueId);
			preparedStatement.setString(2, userId);
			preparedStatement.setString(3, bookId);
			preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
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
			out.println("Issued Successfully!!!");
			System.out.println("YOUR ISSUE ID IS: "+issueId);
		} else {
			out.println("Not Inserted Successfully!!!");
		}

	}

	public void issueBookByUser() throws Exception {
		List<Issue> list = getIssueDetails();
		String issueId = getIssueId();
		String userId = getUserId();
		String bookId = getBookId();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBookId().equalsIgnoreCase(bookId) && (list.get(i).getReturnDate() == null)) {
				System.out.println("This BOOk is Already Issued By the Person \n Check Another Ones...");
				mainApp.start();
			}
		}

		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO ISSUE(ISSUE_ID,USER_ID, BOOK_ID,ISSUE_DATE) VALUES(?,?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, issueId);
			preparedStatement.setString(2, userId);
			preparedStatement.setString(3, bookId);
			preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
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
			out.println("Issued Successfully!!!");
			System.out.println("YOUR ISSUE ID IS: "+issueId);
			PdfGen.generatePdfForIssue(issueId, userId, bookId);
		} else {
			out.println("Not Inserted Successfully!!!");
		}

	}

	public String getIssueId() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT ISSUE_ID FROM ISSUE";
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		String finalId = "";
		try {
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				finalId = resultSet.getString("ISSUE_ID");
			}

			String Issueconst = "ISU-";
			if (finalId.equals("")) {
				finalId = Issueconst.concat("1");
			} else {

				String[] str = finalId.split("-");
				int ch = Integer.parseInt(str[1]);
				ch = ch + 1;
				finalId = Issueconst + ch;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return finalId;
	}

	public String getUserId() throws Exception {
		boolean result = true;
		String user_id = "";
		while (result) {
			System.out.println("\t\t\t\t\t1.Enter User ID\n\t\t\t\t\t2. Exit");
			System.out.println("\t\t\t\t\tEnter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (Exception e) {
				System.out.println("Please enter Valid key");
			}

			switch (choice) {
			case 1:
				out.println("Enter User ID");
				String id = "";
				try {
					id = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Connection connection = null;
				try {
					connection = ConnectionManager.getConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}

				String sql = "SELECT USER_ID FROM USERS";
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery(sql);

					while (resultSet.next()) {
						if (resultSet.getString("USER_ID").equals(id)) {
							result = false;
							user_id = resultSet.getString("USER_ID");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				result = false;
				mainApp.start();
				break;
			default:
				result = false;
				mainApp.start();
				break;
			}

			if (result) {
				System.out.println("Invalid ID try Again...");
			}
		}
		return user_id;

	}

	public String getBookId() {
		boolean result = true;
		String book_id = "";
		while (result) {
			System.out.println("\t\t\t\t\t1.Enter Book ID\n\t\t\t\t\t 2. Exit");
			System.out.println("\t\t\t\t\tEnter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (Exception e) {
				System.out.println("Please enter Valid key");
			}

			switch (choice) {
			case 1:
				out.println("Enter Book ID");
				String id = "";
				try {
					id = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Connection connection = null;
				try {
					connection = ConnectionManager.getConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}

				String sql = "SELECT BOOK_ID FROM BOOKS";
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery(sql);

					while (resultSet.next()) {
						if (resultSet.getString("BOOK_ID").equals(id)) {
							result = false;
							book_id = resultSet.getString("BOOK_ID");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				result = false;
				try {
					mainApp.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				result = false;
				try {
					mainApp.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}

			if (result) {
				System.out.println("Invalid ID try Again...");
			}
		}
		return book_id;

	}

	public List<Issue> getIssueDetails() {
		List<Issue> issues = new ArrayList<Issue>();
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT * FROM ISSUE WHERE RETURN_DATE IS NULL";
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
			while (resultSet.next()) {
				String issueId = resultSet.getString("ISSUE_ID");
				String userId = resultSet.getString("USER_ID");
				String bookId = resultSet.getString("BOOK_ID");
				LocalDate issueDate = resultSet.getDate("ISSUE_DATE").toLocalDate();
				Issue issue = new Issue(issueId, userId, bookId, issueDate);
				issues.add(issue);
				// System.out.println(issueId + " " + userId + " " + bookId + " " + issueDate);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return issues;

	}

}
