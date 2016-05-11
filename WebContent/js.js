var thread_list = null;
function login(){
	
	var username = $('#username').val();
	var password = $('#password').val();
	console.log(username+" "+password);
	
	$.ajax({
		url:'ControllerPojo',
		type:'POST',
		data:'function=login&username='+username+'&password='+password,
		success:function(data){
			console.log(data);
			if($.trim(data) == 'true'){
				forum(function(){
					location.href = "index.jsp";
				});
			}
		}
	});
	return false;
}

function forum(callback){
			
	$.ajax({
		url:"ControllerPojo",
		type:"POST",
		data:"function=forum",		
		success:function(data){
			if($.trim(data) == 'true')
				callback();
			
		}
	});
	return false;	
}

function threads(forum_id){
	
	$.ajax({
		url:"ControllerPojo",
		type:"POST",
		data:"function=threads&forum_id="+forum_id,				
		success:function(data){																															
			thread_list = data;
			var responseData = JSON.parse(data);
			console.log(responseData);
			location.href = "view_threads.html";
			/*$.each(data,function(idx,obj){
				
			});*/
			//if($.trim(data).indexOf('true') >= 0){				
				//location.href = "view_threads.jsp?forum_id="+forum_id;
			//}
			/*function thread_doing(){				
				$('#topics-body').html("Whaat");
			};*/
		}
	});
	return false;
}

function view_thread_page(){
	
	$.ajax({
		url:"view_thread_page",
		type:"post",
		data:"thread_list",
		success:function(data){
			$("#abc").html(data);
		}
	});
	return false;
}


function postQuestion(forum_id){
	
	location.href = "post.jsp?forum_id="+forum_id;
}

function questionSubmit(forum_id){
	
	var question = $('#question').val();
	$.ajax({
		url:"ControllerPojo",
		type:"POST",
		data:"function=questionSubmit&question="+question+"&forum_id="+forum_id,
		success:function(data){
			if($.trim(data) == "true")
				location.href  = "view_threads.jsp?forum_id="+forum_id;
		}
	});
	return false;	
}

function getUser(user_id,thread_id){
	$.ajax({
		url:"ControllerPojo",
		type:"POST",
		data:"function=getUser&user_id="+user_id,
		success:function(data){
			$("#user_name"+thread_id).html("Posted BY : "+$.trim(data));
		}
	});
	return false;
}

function replySubmit(thread_id,forum_id){
	
	var answer = $('#replyQuestion').val();
	$.ajax({
		url:"ControllerPojo",
		type:"POST",
		data:"function=replySubmit&thread_id="+thread_id+"&answer="+answer+"&forum_id="+forum_id,
		success:function(data){
			//
			if($.trim(data) == "true")
				location.reload();		
		}
	});
	return false;
}

function thread_msg(thread_id,forum_id){
	
	$.ajax({
		url:"ControllerPojo",
		type:"POST",
		data:"function=thread_msg&thread_id="+thread_id,
		success:function(data){
			if($.trim(data).indexOf("true") >=  0){
				location.href = "view_thread_messages.jsp?thread_id="+thread_id+"&forum_id="+forum_id;
			}
		}
	});
	return false;	
}

function getQueryParams(qs) {
    qs = qs.split('+').join(' ');

    var params = {},
        tokens,
        re = /[?&]?([^=]+)=([^&]*)/g;

    while (tokens = re.exec(qs)) {
        params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
    }

    return params;
}

function testing(){
	$("#divId").html("Hello Abhay");
}