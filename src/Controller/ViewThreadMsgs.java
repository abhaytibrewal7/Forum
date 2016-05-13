package Controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.attachment;
import model.forum_name;
import model.thread_messages;
import model.threads;

@WebServlet("/view_thread_msg_page")
public class ViewThreadMsgs extends HttpServlet {	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();	
		int forum_id = Integer.valueOf(req.getParameter("forum_id"));
		int thread_id = Integer.valueOf(req.getParameter("thread_id"));
			
		ListIterator<forum_name> forum_li = null;
		ListIterator<threads> thread = null;
		ListIterator<thread_messages> thread_messages = null;
		ListIterator<attachment> attachments = null;
		String forum_name = "", html = "", html2 = "", thread_name = "";
		if(session.getAttribute("forum") != null){
			ArrayList<forum_name> forum = (ArrayList<forum_name>)session.getAttribute("forum");
			forum_li = forum.listIterator();
		}
		if(session.getAttribute("thread") != null){
			ArrayList<threads> t = (ArrayList<threads>)session.getAttribute("thread");
			thread = t.listIterator();
		}
		if(session.getAttribute("thread_messages") != null){
			ArrayList<thread_messages> t_m = (ArrayList<thread_messages>)session.getAttribute("thread_messages");
			thread_messages = t_m.listIterator();
		}
		
		if(session.getAttribute("attachment") != null){
			ArrayList<attachment> att = (ArrayList<attachment>)session.getAttribute("attachment");
			attachments = att.listIterator();					
		}
		if(session.getAttribute("forum") != null){							
			while(forum_li.hasNext()){
								
				forum_name f = new forum_name();
				f = (forum_name)forum_li.next();
									
				int id = f.getId();								
				if(id == forum_id){
					forum_name=f.getName();
				}
			}
		}
			
		html = "<div class='container'>"+
				"<div id='main-body'>"+
					"<div id='title'>"+
						"<div class='row top-buffer'>"+
							"<h3>"+forum_name+"</h3>"+
						"</div>"+
					"</div>"+
					"<div id='post-nav'>"+
						"<div class='row top-buffer'>"+
							"<div class='col-md-2'>"+
								"<a href='view_threads.html?id="+forum_id+"' class='btn green'>"+
									"<span class='glyphicon glyphicon-arrow-left' aria-hidder='true'></span>&nbsp;&nbsp;Go Back"+
								"</a>"+
							"</div>"+										
						"</div>"+
					"</div>"+
					"<div id='post-body'>"+
						"<div class='row top-buffer'>"+
							"<div class='col-md-9 light-gray'>"+
								"<div id='post-title'>"+
									"<div class='row'>"+
										"<div class='col-md-7' style='font-weight:bold'>Question :";
	
										int notOfMsgs = 0;
										Timestamp noOfPosts = null;

										String fileName = "";
										String type = "";
										int doc_id = 0;
										String ext="";
										while(thread.hasNext()) {											
												
												while(attachments.hasNext()){
													attachment at_at = attachments.next();
													int at_thread_id = at_at.getThread_id();
													if(at_thread_id == thread_id){
														fileName = at_at.getFilename();
														type = at_at.getType();
										                
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
										                
														doc_id = at_at.getDoc_id();
														break;
													}
												}
							
												threads t = (threads)thread.next();
												if(t.getId() == thread_id){
																									
													thread_name = t.getName();
													int user_id = t.getAuthor();
													notOfMsgs = t.getNo_of_messages();
													noOfPosts = t.getLast_post();
													String document="";
													if(fileName!=""){
														document = "<a href='newFIleDownloadServlet?id="+doc_id+"'>"+
													"<span class='glyphicon glyphicon-file' aria-hidden='true'></span>&nbsp;&nbsp;"+
																fileName+""+doc_id+""+ext+"</a><br>";
													}
	
													html2 += thread_name+"<br/><br>"+document+
															"<script>"+
																"getUser("+user_id+","+thread_id+");"+
															"</script>"+
															"<br/>"+												
															"<span style='color: #AA0000;' class='username-coloured' id='user_name"+thread_id+"'></span>";
							}										
						}											
					html2 += "</div>"+								
							"</div>"+
						"</div>"+
						"<hr>"+
						"</div>"+
						"<div class='col-md-3 light-dark-gray'>"+
						"<br/>"+
						"<dl class='postprofile' id='profile4'>"+							
							"<dd><strong>Posts:</strong>"+notOfMsgs+"</dd>"+
							"<dd><strong>Last Post:</strong> "+noOfPosts+"</dd>"+
							"<hr>"+							
						"</dl>"+
					"</div>"
					+ "</div>"+
				"</div>";
					while(thread_messages.hasNext()){
						
						thread_messages t = (thread_messages)thread_messages.next();
	
	
						int user_id = t.getPosted_by(); 
						int thread_msg_id = t.getId();
					
				
						html2 += "<div id='post-body'>"+
							"<div class='row top-buffer'>"+
								"<div class='col-md-9 white'>"+
									"<div id='post-title'>"+
										"<div class='row'>"+																
											"<div class='col-md-offset-7 col-md-4'>"+
												"<div id='post-opts'>"+
													"<ul class='post-options text-right'>"+											
														"<li><span class='glyphicon glyphicon-text-color' aria-hidden='true'></span>&nbsp;"+
													
															"<a href='#' >Reply</a>"+
														"</li>"+
													"</ul>"+
												"</div>"+
											"</div>"+
										"</div>"+
									"</div>"+					
									"<div id='post-content'>"+
										"<div class='row'>"+
											"<div class='col-md-12'>"+
												"<b>Reply : </b>"+t.getMessage_content()+
												"<br/><br/>"+
												"<div class='col-md-offset-9'>"+
													"<span class='glyphicon glyphicon-file' aria-hidden='true'></span>&nbsp;"+t.getPosting_date()+
												"</div>"+		
											"</div>"+
										"</div>"+
									"</div>"+
								"</div>"+
								"<div class='col-md-3 light-dark-gray'>"+
									"<br/>"+
									"<dl class='postprofile' id='profile4'>"+
										"<dt>"+
											"<script>"+
												"getUser("+user_id+","+thread_msg_id+");"+
											"</script>"+
											"<a href='#' style='color: #AA0000;' class='username-coloured' id='user_name"+thread_msg_id+"'></a>"+
										"</dt>"+																					
									"</dl>"+
								"</div>"+
							"</div>"+
						"</div>";			
					}
				
				html2 += "<div id='post-body'>"+
							"<div class='row top-buffer'>"+
								"<div class='col-md-9 light-gray'>"+
									"<div class='form-group'>"+
										"<label for='replyQuestion'>Reply:</label>"+
											"<textarea name='replyQuestion' class='form-control' rows='5' placeholder='Reply to this Question!' id='replyQuestion'></textarea>"+
									"</div>"+
								"</div>"+					
							"<br/>"+
							"</div>"+
							"<div class='row top-buffer'>"+
								"<div class='col-md-4'>"+									
									"<button type='button' class='btn green' onclick='replySubmit("+thread_id+","+forum_id+")'>Submit</button>&nbsp;&nbsp;&nbsp;"+
									"<a href='view_threads.html?id="+forum_id+"' class='btn green'>"+
										"Cancel"+
									"</a>"+
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
				
	resp.getWriter().write(html+html2);			
	}
}