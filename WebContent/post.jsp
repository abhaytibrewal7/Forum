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
	<title>Forum : Post a Question</title>
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

	%>
	<div class="container">				
		<div class="row top-buffer">
			<div id="main-body">
				<div class="col-md-12 white">
					<div class="row">
						<div class="col-md-12">
							<h3>Post A Question</h3>
						</div>
					</div>
					<div class="row"><p>
						<div class="col-md-7 col-md-offset-1">
							<form method="post" action="uploadServlet" enctype="multipart/form-data">
								<input type="hidden" name="forum_id" value="<% out.print(forum_id); %>">
								<div class="form-group row">
									<label for="inputEmail3" class="col-sm-2 form-control-label">Forum</label>
									<div class="col-sm-10">
										<input name="forum_name" type="text" class="form-control" id="inputEmail3" value="<% out.print(forumName); %>">
									</div>
								</div>
								<div class="form-group row">
									<label for="inputPassword3" class="col-sm-2 form-control-label">Question</label>
									<div class="col-sm-10">
										<textarea name="question" class="form-control" rows="10" id="question"></textarea>
										<br>
									</div>
								</div>
								<div class="form-group row">
									<label for="attachment" class="col-sm-2 form-control-label">Choose Attachment:</label>
									<div class="col-sm-10">
										<input type="file" name="attachment" size="50" id="attachment" />
										<br>
									</div>
								</div>
								<div class="form-group row pull-right">
									<input type="submit" class="btn green" value="Submit">&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn green" onclick="questionSubmit(<% out.print(forum_id); %>)">Cancel</button>
								</div>
							</form>
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