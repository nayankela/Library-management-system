package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import controller.MainApp;
import interfacesImpl.LibrarianImpl;
import utility.ConnectionManager;

public class AdminDao {
	MainApp mainApp = new MainApp();

	public void validateAdminLogin() throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = System.out;

		out.println("Enter the Name");
		String userName = br.readLine();
		out.println("Enter the Password");
		String userPassword = br.readLine();

		Connection connection = ConnectionManager.getConnection();
		String sql = " SELECT ADMIN_USERNAME, ADMIN_PASSWORD FROM ADMIN ";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			String name = resultSet.getString("ADMIN_USERNAME");
			String pasword = resultSet.getString("ADMIN_PASSWORD");

			if (userName.equals(name) && userPassword.equals(pasword)) {
				out.println("Login Successfull!!!");
				adminMenu();
			} else {
				out.println("Invalid Credentials!!!\n Try Again...");
				mainApp.start();

			}
		}
	}

	public void adminMenu() {
		System.out.println("\t\t\t\t\t\t\t-------------");
		System.out.println("\t\t\t\t\t\t\t ADMIN-MENU");
		System.out.println("\t\t\t\t\t\t\t-------------\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("\t\t\t\t\t____________________________________________________");
		System.out.println(
				"\t\t\t\t\t\t\t 1.Role\n\t\t\t\t\t\t\t 2.Librarian\n\t\t\t\t\t\t\t 3.Books\n\t\t\t\t\t\t\t 4.Exit");
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
			RoleDao roleDao = new RoleDao();
			roleDao.roleMenu();
			break;
		case 2:
			LibrarianImpl librarianImpl = new LibrarianImpl();
			librarianImpl.librarianMenu();
			break;
		case 3:
			BooksDao booksdao = new BooksDao();
			booksdao.booksMenu();
			break;
		case 4:
			try {
				mainApp.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			adminMenu();
			break;
		}

	}

}
