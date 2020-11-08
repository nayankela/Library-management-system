package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import dao.AdminDao;
import dao.LibrarianDao;
import dao.UserDao;

public class MainApp {

	public void start() throws Exception {
		System.out.println("\t\t\t\t\t\t\t----------");
		System.out.println("\t\t\t\t\t\t\t MAIN-MENU");
		System.out.println("\t\t\t\t\t\t\t----------\n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("\t\t\t\t\t____________________________________________________");
		System.out.println(
				"\t\t\t\t\t\t\t 1.Admin\n\t\t\t\t\t\t\t 2.Librarian\n\t\t\t\t\t\t\t 3.User \n\t\t\t\t\t\t\t 4.Exit");
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
			AdminDao adminDao = new AdminDao();
			adminDao.validateAdminLogin();
			break;
		case 2:
			LibrarianDao librarianDao = new LibrarianDao();
			librarianDao.validateLibrarianLogin();
			break;
		case 3:
			UserDao userDao = new UserDao();
			userDao.validateUserLogin();
			break;
		case 4:
			System.out.println("\n\t\t\t\t======>THANK YOU FOR VISITING<======");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid Choice!!!");
			start();
			break;
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println(
				"\t\t\t*****************************************************************************************");
		for (int i = 0; i < 4; i++) {
			System.out.println("\t\t\t* \t\t\t\t\t\t\t\t\t\t\t*");
		}
		System.out.println("\t\t\t*\t\t <<<<<<==|WELCOME! TO LIBRARY MANAGEMENT SYSTEM|==>>>>>>\t\t*");
		System.out.println("\t\t\t*\t\t\t __________________________________________\t\t\t*");
		for (int i = 0; i < 4; i++) {
			System.out.println("\t\t\t* \t\t\t\t\t\t\t\t\t\t\t*");
		}
		System.out.println(
				"\t\t\t*****************************************************************************************\n");
		Thread.sleep(2000);
		System.out.println("\t\t\t\t________________________________________________________________________");
		System.out.println("\t\t\t\t\t\t\tLIBRARY MANAGEMENT SYSTEM");
		System.out.println("\t\t\t\t________________________________________________________________________\n\n");
		MainApp app = new MainApp();
		app.start();
	}
}
