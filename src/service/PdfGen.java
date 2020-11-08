package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGen {

	public static void generatePdfForIssue(String issueId, String userId, String bookId) {
		try {

			OutputStream file = new FileOutputStream(new File("E:\\PDF_Issue_Details.pdf"));
			Document document = new Document();
			PdfWriter.getInstance(document, file);

			// Inserting Image in PDF
			Image image = Image.getInstance("Pdf/PROGRAD_LOGO.png");
			image.scaleAbsolute(180f, 40f);// image width,height

			// Inserting Table in PDF
			PdfPTable table = new PdfPTable(4);

			PdfPCell cell = new PdfPCell(new Paragraph("ISSUE DETAILS"));

			cell.setColspan(4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(12.0f);
			cell.setBackgroundColor(new BaseColor(140, 221, 8));

			table.addCell(cell);

			table.addCell("ISSUE ID:-");
			table.addCell(issueId);
			table.addCell("USER ID:-");
			table.addCell(userId);
			table.addCell("BOOK ID:-");
			table.addCell(bookId);
			table.addCell("DATE:-");
			table.addCell(LocalDate.now().toString());
			table.setSpacingBefore(30.0f); // Space Before table starts, like margin-top in CSS
			table.setSpacingAfter(30.0f); // Space After table starts, like margin-Bottom in CSS

			// Now Insert Every Thing Into PDF Document
			document.open();// PDF document opened........

			document.add(image);

			document.add(Chunk.NEWLINE); 

			document.add(new Paragraph("DEAR  " + userId + ","));
			document.add(new Paragraph("Document Generated On - " + new Date().toString()));

			document.add(table);

			document.add(Chunk.NEWLINE);

			document.newPage(); // Opened new page

			document.close();

			file.close();

			System.out.println("PDF CREATED SUCCESSFULLY!!!..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}