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
import model.thread_messages;
import model.threads;
import model.user;
import Controller.pojo;

import com.google.gson.Gson;

@WebServlet("/ControllerPojo")
public class Requests extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {				
		
			    
		HttpSession session = req.getSession();
		pojo obj = new pojo();
		
		String function_name = req.getParameter("function");
		switch(function_name){
			case "login":
						String username = req.getParameter("username");
						String password = req.getParameter("password");
						user user = obj.login(username,password);
						if(user != null && user.getName() != null){
							session.setAttribute("user", user);
							resp.getWriter().write("true");
						}else
							resp.getWriter().write("false");
						break;
			case "forum":						
						ArrayList<forum_name> forum = obj.forum();						
						if(forum != null){							
							session.setAttribute("forum", forum);		
							resp.getWriter().write("true");
						}																		
						break;
			case "threads":
						int forum_id = Integer.valueOf(req.getParameter("forum_id"));
						ArrayList<threads> thread = obj.threads(forum_id);
						if(thread != null){
							session.setAttribute("thread",thread);
							resp.getWriter().write("true");
							//String json = new Gson().toJson(thread);

						    //resp.getWriter().write(json);
						    //System.out.println(json);
						}
												
						
						ArrayList<forum_name> forum1 = obj.forum();						
						if(forum1 != null){							
							session.setAttribute("forum", forum1);									
						}
						
						
						break;			
			case "getUser":
						int id = Integer.valueOf(req.getParameter("user_id"));
						String name = obj.getUser(id);
						resp.getWriter().write(name);
						break;
			case "replySubmit":
						int thread_id = Integer.valueOf(req.getParameter("thread_id"));
						String answer = req.getParameter("answer");
						int forum_id3 = Integer.valueOf(req.getParameter("forum_id"));
						user u2 =(user)session.getAttribute("user");
						int user_id2 = u2.getId();
						
						boolean status2 = obj.replySubmit(thread_id,answer,user_id2,forum_id3);
						if(status2 == true){
							ArrayList<thread_messages> thread_msg2 = obj.thread_msg(thread_id);
							if(thread_msg2 != null){
								session.setAttribute("thread_messages",thread_msg2);
								resp.getWriter().write("true");
							}
						}						
						break;
			case "thread_msg":
						int thread_id2 = Integer.valueOf(req.getParameter("thread_id"));
						ArrayList<thread_messages> thread_msg = obj.thread_msg(thread_id2);
						if(thread_msg != null){
							session.setAttribute("thread_messages", thread_msg);
							resp.getWriter().write("true");
						}
						
						ListIterator<thread_messages> thread_messages = thread_msg.listIterator();
						int thread_forum_id = 0;
						while(thread_messages.hasNext()){							
							thread_messages t = (thread_messages)thread_messages.next();
							thread_forum_id = t.getForum_id();
							break;
						}
						ArrayList<threads> thread1 = obj.threads(thread_forum_id);
						if(thread1 != null){
							session.setAttribute("thread",thread1);
							//resp.getWriter().write("true");
							//String json = new Gson().toJson(thread1);

						    //resp.getWriter().write(json);
						}											
						break;												
			}
		}

	}
