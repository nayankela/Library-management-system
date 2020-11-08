package model;

import java.time.LocalDate;

public class Issue {

	private String issueId;
	private String userId;
	private String bookId;
	private LocalDate issueDate;
	private LocalDate returnDate;
	private int periods;
	private int fine;

	public Issue() {
		super();
	}

	public Issue(String issueId, String userId, String bookId, LocalDate issueDate, LocalDate returnDate, int periods,
			int fine) {
		super();
		this.issueId = issueId;
		this.userId = userId;
		this.bookId = bookId;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.periods = periods;
		this.fine = fine;
	}
	public Issue(String issueId, String userId, String bookId, LocalDate issueDate) {
		super();
		this.issueId = issueId;
		this.userId = userId;
		this.bookId = bookId;
		this.issueDate = issueDate;

	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public int getPeriods() {
		return periods;
	}

	public void setPeriods(int periods) {
		this.periods = periods;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	@Override
	public String toString() {
		return "Issue [issueId=" + issueId + ", userId=" + userId + ", bookId=" + bookId + ", issueDate=" + issueDate
				+ ", returnDate=" + returnDate + ", periods=" + periods + ", fine=" + fine + "]";
	}

}
