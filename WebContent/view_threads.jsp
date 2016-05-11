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
	<!-- <script type="text/javascript">
		thread_doing();
	</script> -->
</head>
<body>
	<div class="container">
		<div id="header">
			<div class="row">
				<div class="col-md-12">
					<h2>Forum Threads</h2>
				</div>
			</div>
		</div>		
		<div id="main-body">
		<% 
			int forum_id = Integer.valueOf(request.getParameter("forum_id"));
			ListIterator forum_li = null;
			ListIterator thread = null;			
			if(session.getAttribute("forum") != null){
				ArrayList<forum_name> forum = (ArrayList<forum_name>)session.getAttribute("forum");
				forum_li = forum.listIterator();
			}
			if(session.getAttribute("thread") != null){
				ArrayList<threads> t = (ArrayList<threads>)session.getAttribute("thread");
				thread = t.listIterator();
			}			
		%>
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
						<a href="index.jsp" class="btn green">
							<span class="glyphicon glyphicon-arrow-left" aria-hidder="true"></span>&nbsp;&nbsp;Go Back
						</a>
					</div>
					<div class="col-md-2">
						<button type="button" class="btn green" onclick="postQuestion(<% out.print(forum_id); %>);">
							<span class="glyphicon glyphicon-check" aria-hidder="true"></span>&nbsp;&nbsp;Post a New Question
						</button>
					</div>										
				</div>
			</div>
			<div id="topics">
				<div id="topics-title">
					<div class="row green top-buffer">						
						<div class="col-md-6">Questions</div>						
						<div class="col-md-2">Views</div>
						<div class="col-md-2">No of Messages</div>
						<div class="col-md-2">Last Post</div>						
					</div>
				</div>
				<div id="topics-body">
					<% while(thread.hasNext()) {
						
						threads t = (threads)thread.next();
						
					%>
					<div class='row white'>	
						<div class='col-md-1'>
							<span class='glyphicon glyphicon-film' aria-hidden='true' style='font-size: 50px;'></span>
						</div>																		
						<div class='col-md-5'>
							<% 	int user_id = t.getAuthor(); %>							
							<% 	int thread_id = t.getId(); %>
							<a href='javascript:thread_msg(<% out.print(thread_id); %>,<% out.print(forum_id); %>)'><% out.print(t.getName()); %></a><br/>							
								<script>
									getUser(<% out.print(user_id); %>,<% out.print(thread_id); %>);
								</script>																					
							<span style='color: #AA0000;' class='username-coloured' id='user_name<% out.print(thread_id); %>'></span> 
							
						</div>												
						<div class='col-md-2' style='text-align:left'>
							<% out.print(t.getNo_of_views()); %>						
						</div>
						<div class='col-md-2' style='text-align:left'>
							<% out.print(t.getNo_of_messages()); %>													
						</div>
						<div class='col-md-2' style='text-align:left'>
							<% out.print(t.getLast_post()); %>											
						</div>
						<br/>
						<br/>
						<hr>
					</div>
				<%
					}
				%>
				</div>				
			</div>
		</div>		
	</div>
	<footer class='footer'>
		<div class='container text-center'>
			<p style='margin-top: 20px;'>Powered by <h4>Aunwesha Pvt Ltd</h4></p>
		</div>
	</footer>
</body>
</html>
