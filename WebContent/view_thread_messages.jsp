<%@page import="model.thread_messages"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="model.user"%>	
<%@page import="model.forum_name"%>
<%@page import="model.threads"%>
<%@page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta content="width=device-width, initial-scale=1" name="viewport">
	<title>Forum : View Threads</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700,700italic' rel='stylesheet' type='text/css'>
	<link href="style.css" rel="stylesheet">
	
	<script type="text/javascript" src="js.js"></script>
</head>
<body>
	<%
	int forum_id = Integer.valueOf(request.getParameter("forum_id"));
	int thread_id = Integer.valueOf(request.getParameter("thread_id"));
	ListIterator forum_li = null;
	ListIterator thread = null;
	ListIterator thread_messages = null;
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
	%>
	<div class="container">						
		<div id="main-body">
			<div id="title">
				<div class="row top-buffer">
					<h3><%  											
						if(session.getAttribute("forum") != null){							
							while(forum_li.hasNext()){
								
								forum_name f = new forum_name();
								f = (forum_name)forum_li.next();
									
								int id = f.getId();								
								if(id == forum_id){
									out.print(f.getName());
								}
							}
						}
					 %></h3>
				</div>
			</div>
			<div id="post-nav">
				<div class="row top-buffer">
					<div class="col-md-2">
						<a href="view_threads.jsp?forum_id=<% out.print(forum_id); %>" class="btn green">
							<span class="glyphicon glyphicon-arrow-left" aria-hidder="true"></span>&nbsp;&nbsp;Go Back
						</a>
					</div>										
				</div>
			</div>	
			<div id="post-body">
				<div class="row top-buffer">
					<div class="col-md-9 light-gray">
						<div id="post-title">
							<div class="row">
								<div class="col-md-7" style="font-weight:bold">
									Question : <% while(thread.hasNext()) {
						
											threads t = (threads)thread.next();
											if(t.getId() == thread_id){
												out.print(t.getName()+"<br/>");
												int user_id = t.getAuthor();
												%>
												<script>
													getUser(<% out.print(user_id); %>,<% out.print(thread_id); %>);
												</script>
												<br/>												
												<span style="color: #AA0000;" class="username-coloured" id="user_name<% out.print(thread_id); %>"></span>																								
								</div>								
							</div>
						</div>
						<hr>
					</div>
					<div class="col-md-3 light-dark-gray">
						<br/>
						<dl class="postprofile" id="profile4">							
							<dd><strong>Posts:</strong><% out.print(t.getNo_of_messages()); %> </dd>
							<dd><strong>Last Post:</strong> <% out.print(t.getLast_post()); %></dd>
							<hr>							
						</dl>
					</div>
					<% }										
					}						
				%>			
				</div>
			</div>			
			<%
				while(thread_messages.hasNext()){
					
					thread_messages t = (thread_messages)thread_messages.next();
				
			%>
			<div id="post-body">
				<div class="row top-buffer">
					<div class="col-md-9 white">
						<div id="post-title">
							<div class="row">																
								<div class="col-md-offset-7 col-md-4">
									<div id="post-opts">
										<ul class="post-options text-right">											
											<li><span class="glyphicon glyphicon-text-color" aria-hidden="true"></span>&nbsp;
											
												<a href="#"  >Reply</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>						
						<div id="post-content">
							<div class="row">
								<div class="col-md-12">
									<b>Reply : </b><% out.print(t.getMessage_content()); %>
									<br/><br/>
									<div class="col-md-offset-9">
										<span class="glyphicon glyphicon-file" aria-hidden="true"></span>&nbsp;<% out.print(t.getPosting_date()); %>
									</div>		
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3 light-dark-gray">
						<br/>
						<dl class="postprofile" id="profile4">
							<dt>
								<% 	int user_id = t.getPosted_by(); %>
								<%  int thread_msg_id = t.getId(); %>							
								<script>
									getUser(<% out.print(user_id); %>,<% out.print(thread_msg_id); %>);
								</script>								
								<a href="#" style="color: #AA0000;" class="username-coloured" id="user_name<% out.print(thread_msg_id); %>"></a>
							</dt>																					
						</dl>
					</div>
				</div>
			</div>
			<%
				}
			%>
			
			<div id="post-body">
				<div class="row top-buffer">
					<div class="col-md-9 light-gray">
						<div class="form-group">
							<label for="replyQuestion">Reply:</label>
							<textarea class="form-control" rows="5" placeholder="Reply to this Question!" id="replyQuestion"></textarea>
						</div>						
					</div>						
					<br/>
				</div>
				<div class="row top-buffer">
					<div class="col-md-4">
						<button type="button" class="btn green" onclick="replySubmit(<% out.print(thread_id); %>,<% out.print(forum_id); %>)">Submit</button>&nbsp;&nbsp;&nbsp;
						<button type="button" class="btn green" onclick="location.href='view_threads.jsp?forum_id=<% out.print(forum_id); %>'">Cancel</button>
					</div>
				</div>									
			</div>
		</div>		
	</div>
	<footer class="footer">
		<div class="container text-center">
			<p style="margin-top: 20px;">Powered by <h4>Aunwesha Pvt Ltd</h4></p>
		</div>
	</footer>
</body>
</html>