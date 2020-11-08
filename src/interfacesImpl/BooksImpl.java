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
import java.util.ArrayList;
import java.util.List;

import dao.AdminDao;
import dao.BooksDao;
import interfaces.BaseInterface;
import model.Books;
import utility.ConnectionManager;
import utility.RegistrationValidation;

public class BooksImpl implements BaseInterface {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintStream out = System.out;
	Books books = new Books();
	AdminDao adminDao = new AdminDao();
	BooksDao booksDao = new BooksDao();
	List<Books> bookList = new ArrayList<Books>();
	RegistrationValidation validation = new RegistrationValidation();

	@Override
	public void add() {
		String bookId = "";
		boolean bookFlag = true;
		while (bookFlag) {
			out.println("Enter the Book Id");
			try {
				bookId = br.readLine();
				if (validation.id(bookId)) {
					bookFlag = false;
				} else {
					System.out.println(
							" 1. Left part has fixed 4 capital English letters.\n 2. Right part has digits where length of digits between 2 and 5.");
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}

		String bookName = "";
		boolean bookNameFlag = true;
		while (bookNameFlag) {
			out.println("Enter the Book name");

			try {
				bookName = br.readLine();
				if (validation.name(bookName)) {
					bookNameFlag = false;
				} else {
					System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		String authorName = "";
		boolean flagAuthor = true;
		while (flagAuthor) {
			out.println("Enter the Autor name");
			try {
				authorName = br.readLine();
				if (validation.name(authorName)) {
					flagAuthor = false;
				} else {
					System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		String genere = "";
		boolean genereFlag = true;
		while (genereFlag) {
			out.println("Enter Genere");
			try {
				genere = br.readLine();
				if (validation.name(genere)) {
					genereFlag = false;
				} else {
					System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		boolean pric = true;
		double price = 0;
		while (pric) {
			out.println("Enter the price");
			price = 0;
			try {
				price = Integer.parseInt(br.readLine());
				pric = false;
			} catch (Exception e1) {
				System.out.println("Enter valid Price");
				pric = true;
			}
		}
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO BOOKS VALUES(?,?,?,?,?)";
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bookId);
			preparedStatement.setString(2, bookName);
			preparedStatement.setString(3, authorName);
			preparedStatement.setString(4, genere);
			preparedStatement.setDouble(5, price);
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
			out.println("Inserted Successfully!!!");
		} else {
			out.println("Not Inserted Successfully!!!");
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

		String sql = "select * from books";
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
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.printf("%7s %17s %24s %17s  %11s ", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "GENERE", "PRICE");
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {
				String id = resultSet.getString(1);
				String bookName = resultSet.getString(2);
				String authorName = resultSet.getString(3);
				String genere = resultSet.getString(4);
				double price = resultSet.getDouble(5);
				System.out.format("%7s %20s %22s %18s     %.2f ", id, bookName, authorName, genere, price);
				System.out.println();
			}
			System.out.println("-----------------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// SEARCH BOOK BY ID
	@Override
	public void displayById() {
		out.println("Enter the Books Id");
		String bookId = "";
		try {
			bookId = br.readLine();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT * FROM BOOKS WHERE BOOK_ID = " + "'" + bookId + "'";
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
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.printf("%7s %17s %24s %17s  %11s ", "BOOK ID", "BOOK NAME", "AUTHOR NAME", "GENERE", "PRICE");
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {
				String id = resultSet.getString("BOOK_ID");
				String bookName = resultSet.getString("BOOK_NAME");
				String authorName = resultSet.getString("AUTHOR_NAME");
				String genere = resultSet.getString("GENERE");
				double price = resultSet.getDouble("PRICE");
				// out.println(id + " " + bookName + " " + authorName + " " + genere + " " +
				// price);
				System.out.format("%7s %20s %22s %18s     %.2f ", id, bookName, authorName, genere, price);
				System.out.println();
			}
			System.out.println("-----------------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update() {
		out.println("Enter the Book Id which you want to update??");
		String bookId = "";
		try {
			bookId = br.readLine();
		} catch (IOException e2) {
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
					"\t\t\t\t\t\tEnter the choice which you want to Update?\n\t\t\t\t\t\t\t 1.Book Name\n\t\t\t\t\t\t\t 2.Author Name\n\t\t\t\t\t\t\t 3.Genere\n\t\t\t\t\t\t\t 4.Price\n\t\t\t\t\t\t\t 5.Exit");
			out.println("\t\t\t\t\t Enter Choice:");
			int choice = 0;
			try {
				choice = Integer.parseInt(br.readLine());
			} catch (Exception e2) {
				System.out.println("Invalid Choice Pless Valid Key");
			}

			switch (choice) {
			case 1:
				String bookName = "";
				boolean bookNameFlag = true;
				while (bookNameFlag) {
					out.println("Enter the Updated Book name");

					try {
						bookName = br.readLine();
						if (validation.name(bookName)) {
							bookNameFlag = false;
						} else {
							System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				PreparedStatement statement = null;
				try {
					statement = connection.prepareStatement("UPDATE BOOKS SET BOOK_NAME = ? where BOOK_ID = ?");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.setString(1, bookName);
					statement.setString(2, bookId);
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
					System.out.println(" Invalid Book Id Try Again...");
				}
				break;
			case 2:

				String authorName = "";
				boolean flagAuthor = true;
				while (flagAuthor) {
					out.println("Enter the Updated Autor name");
					try {
						authorName = br.readLine();
						if (validation.name(authorName)) {
							flagAuthor = false;
						} else {
							System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				String sql = "UPDATE BOOKS SET AUTHOR_NAME = ? where BOOK_ID = ?";
				PreparedStatement statement1 = null;
				try {
					statement1 = connection.prepareStatement(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement1.setString(1, authorName);
					statement1.setString(2, bookId);
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
					System.out.println(" Invalid Book Id Try Again...");
				}
				break;
			case 3:

				String genere = "";
				boolean genereFlag = true;
				while (genereFlag) {
					out.println("Enter the Updated Genere");
					try {
						genere = br.readLine();
						if (validation.name(genere)) {
							genereFlag = false;
						} else {
							System.out.println("Please Enter Valid Type.. Contains Only Letters and Spaces");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				PreparedStatement statement2 = null;
				try {
					statement2 = connection.prepareStatement("UPDATE BOOKS SET GENERE = ? where BOOK_ID = ?");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement2.setString(1, genere);
					statement2.setString(2, bookId);
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
					System.out.println(" Invalid Book Id Try Again...");
				}
				break;
			case 4:
				boolean pric = true;
				double price = 0;
				while (pric) {
					out.println("Enter the Updated book price");
					price = 0;
					try {
						price = Integer.parseInt(br.readLine());
						pric = false;
					} catch (Exception e1) {
						System.out.println("Enter valid Price");
						pric = true;
					}
				}
				PreparedStatement statement3 = null;
				try {
					statement3 = connection.prepareStatement("UPDATE BOOKS SET PRICE = ? where BOOK_ID = ?");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement3.setDouble(1, price);
					statement3.setString(2, bookId);
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
					System.out.println(" Invalid Book Id Try Again...");
				}
				break;
			case 5:
				result = false;
				booksDao.booksMenu();
				break;
			default:
				result = false;
				booksDao.booksMenu();
				break;
			}
		}

	}

	@Override
	public void delete() {
		out.println("Enter the Book Id");
		String bookId = "";
		try {
			bookId = br.readLine();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "DELETE FROM BOOKS WHERE BOOK_ID = " + "'" + bookId + "'";
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
			e.printStackTrace();
		}

		if (rows > 0) {
			System.out.println("Deleted Successfully!!!");
		} else {
			System.out.println("Invalid ID Try Again..!!!");
		}
	}

	public List<Books> getAllBooks() {
		Connection connection = null;
		try {
			connection = ConnectionManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "select * from books";
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
				String id = resultSet.getString(1);
				String bookName = resultSet.getString(2);
				String authorName = resultSet.getString(3);
				String genere = resultSet.getString(4);
				double price = resultSet.getDouble(5);
				Books books = new Books(id, bookName, authorName, genere, price);
				bookList.add(books);
				// out.println(id + " " + bookName + " " + authorName + " " + genere + " " +
				// price);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

}
