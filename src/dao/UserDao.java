package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import controller.MainApp;
import interfacesImpl.UserImpl;
import service.BooksFuntionality;
import utility.ConnectionManager;

public class UserDao {

	MainApp main = new MainApp();

	public void userMenu() {
		PrintStream out = System.out;
		LibrarianDao librarianDao = new LibrarianDao();
		UserImpl userImpl = new UserImpl();
		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t----------");
			System.out.println("\t\t\t\t\t\t\t USER-MENU");
			System.out.println("\t\t\t\t\t\t\t----------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\t\t\t\t\t\t\t1.ADD USER\n\t\t\t\t\t\t\t2.UPDATE USER\n\t\t\t\t\t\t\t3.DISPLAY USER BY ID\n\t\t\t\t\t\t\t4.DISPLAY USER\n\t\t\t\t\t\t\t5.DELETE USER\n\t\t\t\t\t\t\t6.EXIT");
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
				userImpl.add();
				break;
			case 2:
				userImpl.update();
				break;
			case 3:
				userImpl.displayById();
				break;
			case 4:
				userImpl.displayAll();
				break;
			case 5:
				userImpl.delete();
				break;
			case 6:
				result = false;
				librarianDao.librarianMainMenu();
				;
				break;
			default:
				out.println("Invalid Pressed keys");
				break;
			}
		}
	}

	public void validateUserLogin() throws Exception {
		UserImpl userImpl = new UserImpl();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String roleId = userImpl.getUserRoleId();
		System.out.println("Enter the Id");
		String userName = br.readLine();
		System.out.println("Enter the Password");
		String userPassword = br.readLine();

		Connection connection = ConnectionManager.getConnection();
		String sql = " SELECT USER_ID, USER_PASSWORD FROM USERS WHERE ROLE = '" + roleId + "'";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		boolean result = true;
		while (resultSet.next()) {
			String name = resultSet.getString("USER_ID");
			String pasword = resultSet.getString("USER_PASSWORD");

			if (userName.equals(name) && userPassword.equals(pasword)) {
				System.out.println("Login Successfull!!!");
				result = false;
				userMainMenu();
			}
		}
		if (result) {
			System.out.println("Invalid Credentials!!!\n Try Again...");
			main.start();
		}
	}

	public void userMainMenu() throws Exception {
		PrintStream out = System.out;

		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t------------------");
			System.out.println("\t\t\t\t\t\t\t USER-MAIN-MENU");
			System.out.println("\t\t\t\t\t\t\t------------------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\t\t\t\t\t\t\t1.SEARCH BOOKS\n\t\t\t\t\t\t\t2.ISSUE BOOK\n\t\t\t\t\t\t\t3.RETURN BOOK\n\t\t\t\t\t\t\t4.SORT BOOKS\n\t\t\t\t\t\t\t5.EXIT");
			System.out.println("\t\t\t\t\t____________________________________________________\n");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\t\t\t\t\tEnter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(reader.readLine());
			} catch (Exception e) {
				System.out.println("Please enter Valid key");
			}
			BooksFuntionality booksFuntionality = new BooksFuntionality();
			switch (choice) {
			case 1:
				booksFuntionality.searchUserBookMenu();

				break;
			case 2:
				IssueDao issueDao = new IssueDao();
				issueDao.issueBookByUser();
				break;
			case 3:
				ReturnDao returnDao = new ReturnDao();
				returnDao.returnBookByUser();
				break;
			case 4:
				booksFuntionality.sortBooksMenu();
				break;
			case 5:
				result = false;
				main.start();

				break;
			default:
				out.println("Invalid Pressed keys");
				break;
			}
		}
	}
}
