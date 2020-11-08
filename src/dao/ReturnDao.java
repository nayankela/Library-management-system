package dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Issue;
import utility.ConnectionManager;

public class ReturnDao {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	LibrarianDao librarianDao = new LibrarianDao();

	public void returnBook() {
		IssueDao issueDao = new IssueDao();
		List<Issue> issuesList = issueDao.getIssueDetails();

		boolean result = true;
		LocalDate issueDate = null;
		String issueId = "";

		while (result) {
			System.out.println("1. ISSUE Id\n2. Exit");
			int choice = 0;
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (Exception exception) {
				System.out.println("Invalid Number");
			}

			switch (choice) {
			case 1:
				try {
					issueId = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < issuesList.size(); i++) {
					if (issuesList.get(i).getIssueId().equalsIgnoreCase(issueId)) {
						result = false;
						issueDate = issuesList.get(i).getIssueDate();
					}
				}
				if (result) {
					System.out.println("This Issue ID yet Not Issued or you entered wrong Issue ID\n Try Again...");
					librarianDao.librarianMainMenu();
				}

				LocalDate date = LocalDate.now();
				int period = date.compareTo(issueDate);
				int fine = calculateFine(period);

				if (fine > 0) {
					System.out
							.println("Please Submit the Fine: " + fine + " Ruppess\n You Exceeded the Days: " + period);
				} else {
					System.out.println("You Submitted within a given Period of Time so NO FINE");
				}
				Connection connection = null;
				try {
					connection = ConnectionManager.getConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
				String sql = "UPDATE ISSUE SET RETURN_DATE = ?, PERIODS = ?, FINE = ? WHERE ISSUE_ID = ?";
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setDate(1, java.sql.Date.valueOf(date));
					preparedStatement.setInt(2, period);
					preparedStatement.setInt(3, fine);
					preparedStatement.setString(4, issueId);
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
					System.out.println("Updated Successfully!!!");
					getReturnDetails();

				} else {
					System.out.println("Not Updated Successfully!!!");
				}
			case 2:
				librarianDao.librarianMainMenu();
				break;
			default:
				librarianDao.librarianMainMenu();
				break;
			}
		}
	}

	public void returnBookByUser() throws Exception {
		IssueDao issueDao = new IssueDao();
		List<Issue> issuesList = issueDao.getIssueDetails();
		UserDao userDao = new UserDao();

		boolean result = true;
		LocalDate issueDate = null;
		String issueId = "";

		while (result) {
			System.out.println("1. ISSUE Id\n2. Exit");
			int choice = 0;
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (Exception exception) {
				System.out.println("Invalid Number");
			}

			switch (choice) {
			case 1:
				try {
					issueId = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < issuesList.size(); i++) {
					if (issuesList.get(i).getIssueId().equalsIgnoreCase(issueId)) {
						result = false;
						issueDate = issuesList.get(i).getIssueDate();
					}
				}
				if (result) {
					System.out.println("This Issue ID yet Not Issued or you entered wrong Issue ID\n Try Again...");
					librarianDao.librarianMainMenu();
				}

				LocalDate date = LocalDate.now();
				int period = date.compareTo(issueDate);
				int fine = calculateFine(period);

				if (fine > 0) {
					System.out
							.println("Please Submit the Fine: " + fine + " Ruppess\n You Exceeded the Days: " + period);
				} else {
					System.out.println("You Submitted within a given Period of Time so NO FINE");
				}
				Connection connection = null;
				try {
					connection = ConnectionManager.getConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
				String sql = "UPDATE ISSUE SET RETURN_DATE = ?, PERIODS = ?, FINE = ? WHERE ISSUE_ID = ?";
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setDate(1, java.sql.Date.valueOf(date));
					preparedStatement.setInt(2, period);
					preparedStatement.setInt(3, fine);
					preparedStatement.setString(4, issueId);
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
					System.out.println("Updated Successfully!!!");
					getReturnDetails();

				} else {
					System.out.println("Not Updated Successfully!!!");
				}
			case 2:
				userDao.userMainMenu();
				break;
			default:
				userDao.userMainMenu();
				break;
			}
		}
	}

	public List<Issue> getReturnDetails() {
		List<Issue> issues = new ArrayList<Issue>();
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT * FROM ISSUE WHERE RETURN_DATE IS NOT NULL";
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
				LocalDate returnDate = resultSet.getDate("RETURN_DATE").toLocalDate();
				int periods = resultSet.getInt("PERIODS");
				int fine = resultSet.getInt("FINE");
				Issue issue = new Issue(issueId, userId, bookId, issueDate, returnDate, periods, fine);
				issues.add(issue);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return issues;

	}

	private int calculateFine(int period) {
		int totalFine = 0;
		if (period <= 10) {
			totalFine = 0;
		}
		if (period > 10) {
			int remainingDays = period - 10;
			totalFine = remainingDays * 10;
		}
		return totalFine;
	}

}
