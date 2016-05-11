package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.user;
import database.databaseConnect;

@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)		// upload file's size up to 16mb
public class fileUpload extends HttpServlet{
	private static final long serialVersionUID = 10283197323L;
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// gets values of text fields
		HttpSession session = req.getSession();
		user u =(user)session.getAttribute("user");
		int user_id = u.getId();
        String forum_id = req.getParameter("forum_id");
        String question = req.getParameter("question");
        String message = null;
         
        InputStream inputStream = null; // input stream of the upload file
         
        // obtains the upload file part in this multipart request
        Part filePart = req.getPart("attachment");
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
        
        //Connect to database
        try{
        	databaseConnect obj = databaseConnect.getInstance();
        	con=obj.getConnection();
        	pst=con.prepareStatement("Insert into threads(name,author,forum_id,no_of_messages,no_of_views,attachment,last_post) values (?,?,?,?,?,?,now())");
        	pst.setString(1, question);
        	pst.setInt(2, user_id);
        	pst.setInt(3, Integer.parseInt(forum_id));
        	pst.setInt(4, 0);
        	pst.setInt(5, 0);
             
            if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
                pst.setBlob(6, inputStream);
            }else{
            	pst.setNull(6, Types.NULL);
            }
 
            // sends the statement to the database server
            int row = pst.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        }catch(SQLException ex){
        	 message = "ERROR: " + ex.getMessage();
             ex.printStackTrace();
        }finally{
        	if (con != null) {
                // closes the database connection
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // sets the message in request scope
            req.setAttribute("Message", message);
             
            // forwards to the message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(req, resp);
        }
	}
}
