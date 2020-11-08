package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import interfacesImpl.BooksImpl;

public class BooksDao {

	public void booksMenu() {
		PrintStream out = System.out;
		AdminDao adminDao = new AdminDao();
		BooksImpl booksImpl = new BooksImpl();

		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t-------------");
			System.out.println("\t\t\t\t\t\t\t BOOKS-MENU");
			System.out.println("\t\t\t\t\t\t\t-------------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\t\t\t\t\t\t\t1.ADD BOOK\n\t\t\t\t\t\t\t2.UPDATE BOOK\n\t\t\t\t\t\t\t3.SEARCH BOOK By Id\n\t\t\t\t\t\t\t4.DISPLAY ALL BOOKS \n\t\t\t\t\t\t\t5.DELETE BOOK\n\t\t\t\t\t\t\t6.Exit");
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
				booksImpl.add();
				break;
			case 2:
				booksImpl.update();
				break;
			case 3:
				booksImpl.displayById();
				break;
			case 4:
				booksImpl.displayAll();
				break;
			case 5:
				booksImpl.delete();
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

}
