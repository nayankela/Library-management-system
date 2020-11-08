package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import controller.MainApp;
import interfacesImpl.BooksImpl;
import interfacesImpl.LibrarianImpl;
import model.Issue;
import service.BooksFuntionality;
import utility.ConnectionManager;

public class LibrarianDao {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintStream out = System.out;
	MainApp mainApp = new MainApp();

	public void validateLibrarianLogin() throws Exception {
		LibrarianImpl librarianImpl = new LibrarianImpl();
		String libRole = librarianImpl.getLibrarianId();
		out.println("Enter the Id");
		String librarianName = br.readLine();
		out.println("Enter the Password");
		String librarianPassword = br.readLine();

		Connection connection = ConnectionManager.getConnection();
		String sql = " SELECT USER_ID, USER_PASSWORD FROM USERS WHERE ROLE = '"+libRole+"'";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		boolean result = true;
		while (resultSet.next()) {
			String name = resultSet.getString("USER_ID");
			String pasword = resultSet.getString("USER_PASSWORD");

			if (librarianName.equals(name) && librarianPassword.equals(pasword)) {
				out.println("Login Successfull!!!");
				result = false;
				librarianMainMenu();
			}
		}
		if (result) {
			out.println("Invalid Credentials!!!\n Try Again...");
			mainApp.start();

		}

	}

	public void librarianMainMenu() {
		BooksImpl booksImpl = new BooksImpl();
		IssueDao issueDao = new IssueDao();
		ReturnDao returnDao = new ReturnDao();

		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t----------------");
			System.out.println("\t\t\t\t\t\t\t LIBRARIAN-MENU");
			System.out.println("\t\t\t\t\t\t\t----------------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\t\t\t\t\t\t\t1.USER CRUD\n\t\t\t\t\t\t\t2.ISSUE BOOK\n\t\t\t\t\t\t\t3.RETURN BOOK\n\t\t\t\t\t\t\t4.DISPLAY ALL BOOKS\n\t\t\t\t\t\t\t5.SEARCH BOOKS\n\t\t\t\t\t\t\t6.ISSUED BOOKS LIST\n\t\t\t\t\t\t\t7.RETURN BOOKS LIST\n\t\t\t\t\t\t\t8.EXIT");
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
				UserDao userDao = new UserDao();
				userDao.userMenu();
				break;
			case 2:
				try {
					issueDao.issueBook();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			case 3:
				returnDao.returnBook();
				break;
			case 4:
				booksImpl.displayAll();
				break;
			case 5:
				BooksFuntionality booksFuntionality = new BooksFuntionality();
				booksFuntionality.searchBookMenu();
				break;
			case 6:
				List<Issue> issues = issueDao.getIssueDetails();
				// Print the list objects in tabular format.
				System.out.println("\t\t\t\t <<<==| ISSUE DETAILS |==>>>");
				System.out.println(
						"-----------------------------------------------------------------");
				System.out.printf("%5s %10s %14s %20s  ", "ISSUE ID", "USER ID", "BOOK ID",
						"ISSUE DATE");
				System.out.println();
				System.out.println(
						"-----------------------------------------------------------------");
				for (Issue issue : issues) {
					System.out.format("%5s %14s %14s %17s ", issue.getIssueId(),
							issue.getUserId(), issue.getBookId(), issue.getIssueDate());
					System.out.println();
				}
				System.out.println(
						"-----------------------------------------------------------------");

				break;
			case 7:
				List<Issue> returns = returnDao.getReturnDetails();
				// Print the list objects in tabular format.
				System.out.println("\t\t\t\t\t <<<==| RETURN DETAILS |==>>>");
				System.out.println(
						"------------------------------------------------------------------------------------------------------------");
				System.out.printf("%5s %10s %14s %20s %14s %12s %12s ", "ISSUE ID", "USER ID", "BOOK ID",
						"ISSUE DATE", "RETURN DATE", "PERIOD", "FINE");
				System.out.println();
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------");
				for (Issue issue : returns) {
					System.out.format("%5s %14s %14s %17s %12s %10d %13d", issue.getIssueId(),
							issue.getUserId(), issue.getBookId(), issue.getIssueDate(), issue.getReturnDate(),
							issue.getPeriods(), issue.getFine());
					System.out.println();
				}
				System.out.println(
						"---------------------------------------------------------------------------------------------------------------");

				break;
			case 8:
				result = false;
				try {
					mainApp.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				out.println("Invalid Pressed keys");
				break;
			}
		}
	}
}
