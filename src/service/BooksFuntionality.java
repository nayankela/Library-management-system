package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dao.LibrarianDao;
import dao.UserDao;
import interfacesImpl.BooksImpl;
import model.Books;
import utility.ConnectionManager;

public class BooksFuntionality {

	LibrarianDao librarianDao = new LibrarianDao();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void searchBookMenu() {
		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t--------------------");
			System.out.println("\t\t\t\t\t\t\t SEARCH-BOOK-MENU");
			System.out.println("\t\t\t\t\t\t\t--------------------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\n\t\t\t\t\t\t\t1.SEARCH BOOK BY ID\n\t\t\t\t\t\t\t2.SEARCH BOOK BY NAME\n\t\t\t\t\t\t\t3.SEARCH BOOK BY AUTHOR\n\t\t\t\t\t\t\t4.SEARCH BOOK BY GENERE\n\t\t\t\t\t\t\t5.SEARCH BOOK BY PRICE\n\t\t\t\t\t\t\t6.Exit");
			System.out.println("\t\t\t\t\t____________________________________________________\n");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\t\t\t\t\tEnter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(reader.readLine());
			} catch (Exception e) {
				System.out.println("Please enter Valid key");
			}

			BooksImpl books = new BooksImpl();
			List<Books> list = books.getAllBooks();
			switch (choice) {
			case 1:
				System.out.println("Enter the ID");
				String id = "";
				try {
					id = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, id);

				break;

			case 2:
				System.out.println("Enter the Name");
				String name = "";
				try {
					name = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, name);

				break;
			case 3:
				System.out.println("Enter the Author Name");
				String authorName = "";
				try {
					authorName = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, authorName);

				break;
			case 4:
				System.out.println("Enter the Genere");
				String genere = "";
				try {
					genere = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, genere);

				break;
			case 5:
				System.out.println("Enter the Price");
				String price = "";
				try {
					price = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, price);

				break;
			case 6:
				result = false;
				librarianDao.librarianMainMenu();
				break;
			default:
				System.out.println("Invalid Pressed keys");
				break;
			}
		}

	}

	public void searchUserBookMenu() throws Exception {
		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t--------------------");
			System.out.println("\t\t\t\t\t\t\t SEARCH-BOOK-MENU");
			System.out.println("\t\t\t\t\t\t\t--------------------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\n\t\t\t\t\t\t\t1.SEARCH BOOK BY ID\n\t\t\t\t\t\t\t2.SEARCH BOOK BY NAME\n\t\t\t\t\t\t\t3.SEARCH BOOK BY AUTHOR\n\t\t\t\t\t\t\t4.SEARCH BOOK BY GENERE\n\t\t\t\t\t\t\t5.SEARCH BOOK BY PRICE\n\t\t\t\t\t\t\t6.Exit");
			System.out.println("\t\t\t\t\t____________________________________________________\n");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\t\t\t\t\tEnter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(reader.readLine());
			} catch (Exception e) {
				System.out.println("Please enter Valid key");
			}

			BooksImpl books = new BooksImpl();
			List<Books> list = books.getAllBooks();
			switch (choice) {
			case 1:
				System.out.println("Enter the ID");
				String id = "";
				try {
					id = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, id);

				break;

			case 2:
				System.out.println("Enter the Name");
				String name = "";
				try {
					name = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, name);

				break;
			case 3:
				System.out.println("Enter the Author Name");
				String authorName = "";
				try {
					authorName = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, authorName);

				break;
			case 4:
				System.out.println("Enter the Genere");
				String genere = "";
				try {
					genere = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, genere);

				break;
			case 5:
				System.out.println("Enter the Price");
				String price = "";
				try {
					price = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchBookByDiffrentParameters(list, choice, price);

				break;
			case 6:
				result = false;
				UserDao userDao = new UserDao();
				userDao.userMainMenu();
				break;
			default:
				System.out.println("Invalid Pressed keys");
				break;
			}
		}

	}

	private void searchBookByDiffrentParameters(List<Books> list, int choice, String parameter) {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "";
		switch (choice) {
		case 1:
			sql = "SELECT * FROM BOOKS WHERE BOOK_ID = " + "'" + parameter + "'";
			break;
		case 2:
			sql = "SELECT * FROM BOOKS WHERE BOOK_NAME = " + "'" + parameter + "'";
			break;
		case 3:
			sql = "SELECT * FROM BOOKS WHERE AUTHOR_NAME = " + "'" + parameter + "'";
			break;
		case 4:
			sql = "SELECT * FROM BOOKS WHERE GENERE = " + "'" + parameter + "'";
			break;
		case 5:
			double price = 0;
			try {
				price = Double.parseDouble(parameter);
			} catch (Exception p) {
				System.out.println("Enter Valid Price");
				searchBookMenu();
			}
			sql = "SELECT * FROM BOOKS WHERE PRICE = " + price;
			break;

		}
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
			System.out.println("\t\t\t <<<==| BOOKS DETAILS |==>>>");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------");
			System.out.printf("%7s %17s %24s %17s  %11s ", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "GENERE", "PRICE");
			System.out.println();
			System.out.println(
					"-----------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {
				String id = resultSet.getString("BOOK_ID");
				String bookName = resultSet.getString("BOOK_NAME");
				String authorName = resultSet.getString("AUTHOR_NAME");
				String genere = resultSet.getString("GENERE");
				double price = resultSet.getDouble("PRICE");

				System.out.format("%7s %20s %22s %18s     %.2f ", id, bookName, authorName, genere, price);
				System.out.println();

			}
			System.out.println(
					"-----------------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void sortBooksMenu() throws Exception {
		boolean result = true;
		while (result) {
			System.out.println("\t\t\t\t\t\t\t------------------");
			System.out.println("\t\t\t\t\t\t\t SORT-BOOKS-MENU");
			System.out.println("\t\t\t\t\t\t\t------------------\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("\t\t\t\t\t____________________________________________________");
			System.out.println(
					"\n\t\t\t\t\t\t\t1.SORT BOOK BY ID\n\t\t\t\t\t\t\t2.SORT BOOK BY NAME\n\t\t\t\t\t\t\t3.SORT BOOK BY AUTHOR\n\t\t\t\t\t\t\t4.SORT BOOK BY GENERE\n\t\t\t\t\t\t\t5.SORT BOOK BY PRICE\n\t\t\t\t\t\t\t6.Exit");
			System.out.println("\t\t\t\t\t____________________________________________________\n");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("\t\t\t\t\tEnter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(reader.readLine());
			} catch (Exception e) {
				System.out.println("Please enter Valid key");
			}

			BooksImpl books = new BooksImpl();
			List<Books> list = books.getAllBooks();
			switch (choice) {
			case 1:
				sortById(list);

				break;

			case 2:
				sortByName(list);

				break;
			case 3:
				sortByAuthor(list);

				break;
			case 4:
				sortByGenere(list);

				break;
			case 5:
				sortByPrice(list);

				break;
			case 6:
				result = false;
				UserDao userDao = new UserDao();
				userDao.userMainMenu();
				break;
			default:
				System.out.println("Invalid Pressed keys");
				break;
			}
		}

	}

	private void sortByPrice(List<Books> list) {
		Collections.sort(list, new Comparator<Books>() {
			@Override
			public int compare(Books c1, Books c2) {
				return Double.compare(c1.getPrice(), c2.getPrice());
			}
		});
		// Print the list objects in tabular format.
		System.out.println("\t\t\t <<<==| SORT BOOKS  BY PRICE |==>>>");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");
		System.out.printf("%7s %17s %24s %17s  %11s ", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "GENERE", "PRICE");
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");
		for (Books book : list) {
			displaySortingData(book);
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

	}

	private void sortByGenere(List<Books> list) {
		System.out.println("\t\t\t<<<==| SORT BOOKS  BY GENERE |==>>>");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");
		System.out.printf("%7s %17s %24s %17s  %11s ", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "GENERE", "PRICE");
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

		list.sort((Books s1, Books s2) -> s1.getGenere().compareTo(s2.getGenere()));
		list.forEach((s) -> displaySortingData(s));
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

	}

	private void sortByAuthor(List<Books> list) {
		System.out.println("\t\t\t <<<==| SORT BOOKS  BY AUTHOR |==>>>");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");
		System.out.printf("%7s %17s %24s %17s  %11s ", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "GENERE", "PRICE");
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

		list.sort((Books s1, Books s2) -> s1.getAuthorName().compareTo(s2.getAuthorName()));
		list.forEach((s) -> displaySortingData(s));
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

	}

	private void sortByName(List<Books> list) {
		System.out.println("\t\t\t<<<==| SORT BOOKS  BY NAME |==>>>");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");
		System.out.printf("%7s %17s %24s %17s  %11s ", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "GENERE", "PRICE");
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

		list.sort((Books s1, Books s2) -> s1.getBookName().compareTo(s2.getBookName()));
		list.forEach((s) -> displaySortingData(s));
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

	}

	private void sortById(List<Books> list) {
		list.sort((Books s1, Books s2) -> s1.getBookId().compareTo(s2.getBookId()));
		System.out.println("\t\t\t <<<==| SORT BOOKS  BY ID |==>>>");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");
		System.out.printf("%7s %17s %24s %17s  %11s ", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "GENERE", "PRICE");
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

		list.forEach((s) -> displaySortingData(s));
		System.out.println(
				"-----------------------------------------------------------------------------------------------------");

	}

	private void displaySortingData(Books s) {
		System.out.format("%7s %20s %22s %18s     %.2f ", s.getBookId(), s.getBookName(), s.getAuthorName(),
				s.getGenere(), s.getPrice());
		System.out.println();
	}

}
