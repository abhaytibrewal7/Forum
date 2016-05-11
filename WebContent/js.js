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
	
	var thread_list = null;
	$.ajax({
		url:"ControllerPojo",
		type:"POST",
		data:"function=threads&forum_id="+forum_id,				
		success:function(data){
			if($.trim(data) == 'true'){
				$.ajax({
					url:"view_thread_page",
					data:"forum_id="+forum_id,
					type:"post",
					success:function(response){
						$("#body").html(response);
					}
				});
			}
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
				$.ajax({
					url:"view_thread_msg_page",
					type:"POST",
					data:"thread_id="+thread_id+"&forum_id="+forum_id,
					success:function(response){
						$("#body").html(response);
					}
				});
			}
		}
	});
	return false;	
}

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}
