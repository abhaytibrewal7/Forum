package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.databaseConnect;

/**
 * Servlet implementation class newFIleDownloadServlet
 */
@WebServlet("/newFIleDownloadServlet")
public class newFIleDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;
	
	Connection con;
	PreparedStatement pst;
	
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		
		databaseConnect obj = databaseConnect.getInstance();
		con = obj.getConnection();
		
		int uploadId = Integer.parseInt(request.getParameter("id"));
		String sql = "Select * from documents where doc_id=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1,uploadId);
			
			ResultSet result = pst.executeQuery();
            if (result.next()) {
            	int id = result.getInt("doc_id");
                String fileName = result.getString("fileName");
                Blob blob = result.getBlob("content");
                InputStream inputStream = blob.getBinaryStream();
                int fileLength = inputStream.available();
                String type = result.getString("type");
                 
                System.out.println("fileLength = " + fileLength);
 
                ServletContext context = getServletContext();
 
                // sets MIME type for the file download
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {        
                    //mimeType = "application/octet-stream";
                	mimeType = type;
                }              
                
                
                // Changing the extension
                String ext = "";
                if(type.equals("text/plain")){
                	ext = ".txt";
                }else if(type.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
                	ext = ".docx";
                }else if(type.equals("application/vnd.ms-excel")){
                	ext = ".xls";
                }else if(type.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")){
                	ext = ".ppt";
                }else if(type.equals("application/pdf")){
                	ext = ".pdf";
                }
                
                // set content properties and header attributes for the response
                response.setContentType(mimeType);
                response.setContentLength(fileLength);
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", fileName+"_"+id+""+ext);
                response.setHeader(headerKey, headerValue);
 
                // writes the file to the client
                OutputStream outStream = response.getOutputStream();
                 
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                 
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                 
                inputStream.close();
                outStream.close();             
            } else {
                // no file found
                response.getWriter().print("File not found for the id: " + uploadId);  
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
