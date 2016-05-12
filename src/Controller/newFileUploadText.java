package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import database.databaseConnect;

@WebServlet("/newFileUploadText")
@MultipartConfig(maxFileSize = 16177215)
public class newFileUploadText extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	PreparedStatement pst;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			String rtempfile = File.createTempFile("temp", "1").getParent();

			// get the file from the previous page form
			// save the file in temporary directory of server
			// specify the max size = 15MB
			MultipartRequest multi = new MultipartRequest(request, rtempfile,
					15 * 1024 * 1024);

			Enumeration files = multi.getFileNames();
			String st = "insert into documents(fileName, type,content, thread_id) values (?,?,?,1)";

			// get the connection object from another class MyConnection's
			// method getConnection();
			// and create the prepareStatement
			databaseConnect obj = databaseConnect.getInstance();
			con = obj.getConnection();

			pst = con.prepareStatement(st);

			String name = "";
			String fileExtesion = "";
			File ff = null;
			FileInputStream fin = null;

			while (files.hasMoreElements()) {
				name = (String) files.nextElement();
				System.out.println("name : "+name);
				ff = multi.getFile(name);
				fileExtesion = ff.getName().substring(
						ff.getName().lastIndexOf("."));

				// check user has select the correct file or not
				boolean fileAllowed = fileExtesion.equalsIgnoreCase(".txt")
						|| fileExtesion.equalsIgnoreCase(".pdf")
						|| fileExtesion.equalsIgnoreCase(".doc")
						|| fileExtesion.equalsIgnoreCase(".docx")
						|| fileExtesion.equalsIgnoreCase(".xls")
						|| fileExtesion.equalsIgnoreCase(".xlsx");

				if ((ff != null) && fileAllowed) {

					try {
						fin = new FileInputStream(ff);
						pst.setString(1, ff.getName());
						pst.setString(2, fileExtesion);
						pst.setBinaryStream(3, (InputStream) fin,
								(int) (ff.length()));
						boolean sss = pst.execute();

						System.out.print("uploaded successfully.."+ff.getName());
					}

					catch (Exception e) {
						System.out.print("Failed due to " + e);
					}

					finally {
						// next statement is must otherwise file will not be
						// deleted from the temp as fin using f.
						// its necessary to put outside otherwise at the time of
						// exception file will not be closed.
						fin.close();
						ff.delete();
					}
				} else {
					System.out.print("Please select the correct file...");
				}// end of if and else
			}// end of while

			pst.close();
			con.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
