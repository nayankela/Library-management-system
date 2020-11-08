package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import interfacesImpl.RoleImpl;

public class RoleDao {
	public void roleMenu() {

		PrintStream out = System.out;
		AdminDao adminDao = new AdminDao();
		RoleImpl roleImpl = new RoleImpl();

		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t----------");
			System.out.println("\t\t\t\t\t\t\t ROLE-MENU");
			System.out.println("\t\t\t\t\t\t\t----------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\t\t\t\t\t\t\t1.ADD ROLE\n\t\t\t\t\t\t\t2.UPDATE ROLE\n\t\t\t\t\t\t\t3.DISPLAY ROLE By Id\n\t\t\t\t\t\t\t4.DISPLAY ROLE\n\t\t\t\t\t\t\t5.DELETE ROLE\n\t\t\t\t\t\t\t6.Exit");
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
				roleImpl.add();
				break;
			case 2:
				roleImpl.update();
				break;
			case 3:
				roleImpl.displayById();
				break;
			case 4:
				roleImpl.displayAll();
				break;
			case 5:
				roleImpl.delete();
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
