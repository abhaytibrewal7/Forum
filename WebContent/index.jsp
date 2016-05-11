<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="model.user"%>	
<%@page import="model.forum_name"%>
<%@page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta content="width=device-width, initial-scale=1" name="viewport">
	<title>Forum</title>	
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,700,700italic' rel='stylesheet' type='text/css'>
	<link href="style.css" rel="stylesheet">
	
	<script src="js.js" type="text/javascript"></script>	
	
</head>
<body>
	
	<%
		if(session.getAttribute("forum") != null){
			ArrayList<forum_name> forum = (ArrayList<forum_name>)session.getAttribute("forum");
			ListIterator forum_li = forum.listIterator();
		 
	%>
		
	<div class="container">
		<div id="header">
			<div class="row">
				<div class="col-md-12">
					<%
						if(session.getAttribute("user") != null){
							user u = (user)session.getAttribute("user");
							%>
								Hello <%  out.println(u.getName()); %>
							<%
						}else{
							
							%>
								No Hello
							<%	
						}
					%>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<h2>FORUM</h2>
				</div>
			</div>
		</div>				
		<div class="row top-buffer">
			<div id="main-body">
				<div class="col-md-12">
					<div id="categories">
						<div class="row green">
							<div class="col-md-2">Forum Name</div>
							<div class="col-md-2">Start Date</div>
							<div class="col-md-2">Last Post</div>
							<div class="col-md-2">No of Messages</div>
							<div class="col-md-1">No of Views</div>
							<div class="col-md-2">No of Threads</div>
							<div class="col-md-1">No of Posts</div>
						</div>
						<div class="row white">
							<div class="col-md-12">								
								<%
								while(forum_li.hasNext()){
									forum_name f = new forum_name();

									f = (forum_name)forum_li.next();								
								%>
									<div class="row">																			
									
										<div class="col-md-2">
											<a href="view_threads.html?id=<% out.print(f.getId()); %>"><% out.print(f.getName()); %></a>
											<!-- <a href="javascript:threads(<% out.print(f.getId()); %>)" ><% out.print(f.getName()); %></a> -->
										 </div> 										
										<div class="col-md-2"> <% out.print(f.getStart_date()); %></div>
										<div class="col-md-2"> <% out.print(f.getLast_post()); %></div>
										<div class="col-md-2"> <% out.print(f.getNo_of_messages()); %></div>
										<div class="col-md-1"> <% out.print(f.getNo_of_views()); %></div>
										<div class="col-md-2"> <% out.print(f.getNo_of_threads()); %></div>
										<div class="col-md-1"> <% out.print(f.getNo_of_posts()); %></div>
										
										<br/>										
										<hr>	
									</div>									
								<%
								}

					}
		%>
							</div>							
						</div>
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