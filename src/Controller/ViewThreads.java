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
import model.threads;

@WebServlet("/view_thread_page")
public class ViewThreads extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				
		HttpSession session = req.getSession();								
		
		int forum_id = Integer.valueOf(req.getParameter("forum_id"));
		String forum_name = "";
		String html = "", html2 = "", footer = "";
		ListIterator<forum_name> forum_li = null;
		ListIterator<threads> thread = null;			
		if(session.getAttribute("forum") != null){
			ArrayList<forum_name> forum = (ArrayList<forum_name>)session.getAttribute("forum");
			forum_li = forum.listIterator();
		}
		if(session.getAttribute("thread") != null){
			ArrayList<threads> t = (ArrayList<threads>)session.getAttribute("thread");
			thread = t.listIterator();
		}
		if(session.getAttribute("forum") != null){							
			while(forum_li.hasNext()){
				
				forum_name f = new forum_name();
				f = (forum_name)forum_li.next();
					
				int id = f.getId();								
				if(id == forum_id){
					forum_name = f.getName();
				}
			}
		}
		
		html = "<div class='container'>"+
				"<div id='header'>"+
					"<div class='row'>"+
						"<div class='col-md-12'>"+
							"<h2>Forum Threads</h2>"+
						"</div></div></div>";
		
		html = html + "<div id='title'>"+
						"<div class='row top-buffer'>"+
							"<h3>"+forum_name+"</h3>"
						  +"</div>"
						+"</div>";
					
		
		html = html + "<div id='post-nav'>" +
						"<div class='row top-buffer'>"+
							"<div class='col-md-2'>" +
								"<a href='index.jsp' class='btn green'>" +
									"<span class='glyphicon glyphicon-arrow-left' aria-hidder='true'></span>&nbsp;&nbsp;Go Back" +
								"</a>"+
							"</div>" +
							"<div class='col-md-2'>"+
								"<button type='button' class='btn green' onclick='postQuestion("+forum_id+")';>"+
									"<span class='glyphicon glyphicon-check' aria-hidder='true'></span>&nbsp;&nbsp;Post a New Question"+
								"</button>"+
							"</div>"+										
						"</div>"+
					"</div>";
																								
		html = html + "<div id='topics'>"
						+ "<div id='topics-title'>"
							+ "<div class='row green top-buffer'>"
								+ "<div class='col-md-6'>Questions</div>"
								+ "<div class='col-md-2'>Views</div>"
								+ "<div class='col-md-2'>No of Messages</div>"
								+ "<div class='col-md-2'>Last Post</div>"
							+ "</div>"
						+ "</div>"
						+ "<div id='topics-body'>";
		while(thread.hasNext()) {
			
			threads t = (threads)thread.next();
			int user_id = t.getAuthor(); 						
			int thread_id = t.getId();
			
			html2 += "<div class='row white'>"
					+ "<div class='col-md-1'>"
					+ "<span class='glyphicon glyphicon-film' aria-hidden='true' style='font-size: 50px;'></span>"
					+ "</div>"
					+ "<div class='col-md-5'>";								
			
			html2 = html2 + "<a href='javascript:thread_msg("+thread_id+","+forum_id+")'>"+t.getName()+"</a><br/>"
							+ "<script>"
								+ "getUser("+user_id+","+thread_id+");"
							+ "</script>"
							+ "<span style='color: #AA0000;' class='username-coloured' id='user_name"+thread_id+"'></span>"
						+ "</div>"
						+ "<div class='col-md-2' style='text-align:left'>"
							+t.getNo_of_views()
						+ "</div>"
						+ "<div class='col-md-2' style='text-align:left'>"
							+ t.getNo_of_messages()
						+ "</div>"
						+ "<div class='col-md-2' style='text-align:left'>"
							+t.getLast_post() 
						+ "</div>"
						+ "<br/><br/><hr>"
						+ "</div>";								
		}
		html2 += "</div>"
				+ "</div>"					
				+ "</div>";
	
		footer = "<footer class='footer'>"
					+ "<div class='container text-center'>"
						+ "<p style='margin-top: 20px;'>Powered by <h4>Aunwesha Pvt Ltd</h4></p>"
					+ "</div>"
				+ "</footer>";
								
		resp.getWriter().write(html+html2+footer);
	}
}
