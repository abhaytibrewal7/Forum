package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.forum_name;

@WebServlet("/view_post_page")
public class Post extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();	
		int forum_id = Integer.valueOf(req.getParameter("forum_id"));
		String forumName = "";
	
		if(session.getAttribute("forum") != null){
			ArrayList<forum_name> forum = (ArrayList<forum_name>)session.getAttribute("forum");
			ListIterator forum_li = forum.listIterator();

			while(forum_li.hasNext()){
			
				forum_name f = new forum_name();
				f = (forum_name)forum_li.next();
				
				int id = f.getId();								
				if(id == forum_id){
					forumName = f.getName();
				}
			}
		}
			String html="<div class='container'>"+				
			"<div class='row top-buffer'>"+
				"<div id='main-body'>"+
					"<div class='col-md-12 white'>"+
						"<div class='row'>"+
							"<div class='col-md-12'>"+
								"<h3>Post A Question</h3>"+
							"</div>"+
						"</div>"+
						"<div class='row'><p>"+
							"<div class='col-md-7 col-md-offset-1'>"+
								"<form enctype='multipart/form-data' action='newFileUploadText' method='post'>"+
									"<input type='hidden' name='forum_id' value='"+forum_id+"'>"+
									"<div class='form-group row'>"+
										"<label for='inputEmail3' class='col-sm-2 form-control-label'>Forum</label>"+
										"<div class='col-sm-10'>"+
											"<input name='forum_name' type='text' class='form-control' id='inputEmail3' value='"+forumName+"'>"+
										"</div>"+
									"</div>"+
									"<div class='form-group row'>"+
										"<label for='inputPassword3' class='col-sm-2 form-control-label'>Question</label>"+
										"<div class='col-sm-10'>"+
											"<textarea name='question' class='form-control' rows='10' id='question'></textarea>"+
											"<br>"+
										"</div>"+
									"</div>"+
									"<div class='form-group row'>"+
										"<label for='attachment' class='col-sm-2 form-control-label'>Choose Attachment:</label>"+
										"<div class='col-sm-10'>"+
											"<input type='file' name='attachment' id='filename' />"+
											"<br>"+
										"</div>"+
									"</div>"+
									"<div class='form-group row pull-right'>"+
										"<input type='submit' class='btn green' value='Save'>&nbsp;&nbsp;&nbsp;&nbsp;"+
										"<a href='view_threads.html?id="+forum_id+"' class='btn green'>"+
											"<span class='glyphicon glyphicon-arrow-left' aria-hidder='true'></span>Cancel"+
										"</a>"+
									"</div>"+
								"</form>"+
							"</div>"+
						"</div>"+
					"</div>"+
				"</div>"+
			"</div>"+
		"</div>"+
		"<footer class='footer'>"+
			"<div class='container text-center'>"+
				"<p style='margin-top: 20px;'>Powered by <h4>Aunwesha Pvt Ltd</h4></p>"+
			"</div>"+
		"</footer>"+
	"</body>"+
	"</html>";
			
			resp.getWriter().write(html);
	}
}