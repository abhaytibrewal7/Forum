package Controller;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.databaseConnect;
import model.forum_name;
import model.threads;
import model.user;
import model.thread_messages;

public class pojo {
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
			
	public user login(String username,String password){
		
		user u = new user();
		try{
			databaseConnect obj = databaseConnect.getInstance();
			con=obj.getConnection();
			
			pst=con.prepareStatement("Select * from user where username = ? and password = ?");
			pst.setString(1,username);
			pst.setString(2,password);

			rs=pst.executeQuery();			
			while(rs.next()){				
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				u.setType(rs.getString("type"));
				u.setUsername(rs.getString("username"));
			}
			
			pst.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return u;
	}
	
	public ArrayList<forum_name> forum(){
		
		ArrayList<forum_name> forum = new ArrayList<forum_name>();
		try{
			databaseConnect obj = databaseConnect.getInstance();
			con=obj.getConnection();			
			pst=con.prepareStatement("Select * from forum_name");									
			rs=pst.executeQuery();
			
			while(rs.next()){
				forum_name f = new forum_name();
				f.setId(rs.getInt("id"));
				f.setName(rs.getString("name"));
				f.setNo_of_messages(rs.getInt("no_of_messages"));
				f.setLast_post(rs.getTimestamp("last_post"));
				f.setStart_date(rs.getTimestamp("start_date"));
				f.setNo_of_posts(rs.getInt("no_of_posts"));
				f.setNo_of_threads(rs.getInt("no_of_threads"));
				f.setNo_of_views(rs.getInt("no_of_views"));
				
				forum.add(f);
			}
			
			pst.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}			
		return forum;
	}
	
	public ArrayList<threads> threads(int forum_id){
		
		ArrayList<threads> thread = new ArrayList<threads>();
		int no_of_views = 0;
		int id = 0;		
		int no_of_threads = 0;
		int no_of_messages = 0;
		try{
			databaseConnect obj = databaseConnect.getInstance();
			con=obj.getConnection();
		
			pst=con.prepareStatement("Select * from threads where forum_id = ?");
			pst.setInt(1,forum_id);
			rs=pst.executeQuery();
			
			while(rs.next()){
				
				threads t = new threads();
				t.setId(rs.getInt("id"));
				t.setAuthor(rs.getInt("author"));
				t.setName(rs.getString("name"));
				t.setForum_id(rs.getInt("forum_id"));
				t.setLast_post(rs.getTimestamp("last_post"));
				t.setNo_of_messages(rs.getInt("no_of_messages"));
				t.setNo_of_views(rs.getInt("no_of_views"));
								
				
				thread.add(t);
				
				no_of_threads++;
				no_of_messages += rs.getInt("no_of_messages");
				
			}
			pst = con.prepareStatement("Select no_of_views from forum_name where id = ?");
			pst.setInt(1, forum_id);
			rs = pst.executeQuery();
			while(rs.next()){
				no_of_views = rs.getInt("no_of_views")+1;
			}
			
			pst = con.prepareStatement("Update forum_name set no_of_views = ?, no_of_threads = ?, no_of_messages = ?, no_of_posts = ? where id = ?");
			pst.setInt(1, no_of_views);
			pst.setInt(2, no_of_threads);
			pst.setInt(3, no_of_messages);
			pst.setInt(4, no_of_threads);
			pst.setInt(5, forum_id);
			pst.executeUpdate();
			
			pst.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return thread;
	}
		
	
	public String getUser(int id){
		
		String name="";
		try{
			databaseConnect obj = databaseConnect.getInstance();
			con=obj.getConnection();
			
			pst = con.prepareStatement("Select * from user where id = ?");
			pst.setInt(1, id);
			
			rs = pst.executeQuery();
			while(rs.next()){				
				name = rs.getString("name");
			}
			
			pst.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return name;
	}
	
	public boolean replySubmit(int thread_id,String answer,int user_id,int forum_id){
		boolean status = false;
		try{
			databaseConnect obj = databaseConnect.getInstance();
			con = obj.getConnection();
			
			pst = con.prepareStatement("Insert into thread_messages(message_content,forum_id,thread_id,posted_by,posting_date) values (?,?,?,?,now())");
			
			Blob blob = con.createBlob();
			blob.setBytes(1, answer.getBytes());
			pst.setBlob(1, blob);
			
			pst.setInt(2, forum_id);
			pst.setInt(3, thread_id);
			pst.setInt(4, user_id);

			int temp = pst.executeUpdate();			
			pst.close();
			con.close();
			if(temp > 0)
				status = true;
			else
				status = false;
			
			pst.close();
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();			
		}
		
		return status;
		
	}
	
	public ArrayList<thread_messages> thread_msg(int thread_id){
		
		ArrayList<thread_messages> t_m = new ArrayList<thread_messages>();
		int no_of_views = 0;
		int no_of_messages = 0;
			
		try{
			databaseConnect obj = databaseConnect.getInstance();
			con = obj.getConnection();
			
			pst = con.prepareStatement("Select * from thread_messages where thread_id = ?");
			pst.setInt(1, thread_id);
			
			rs = pst.executeQuery();
			while(rs.next()){
				
				thread_messages t = new thread_messages();
				
				t.setId(rs.getInt("id"));
				t.setForum_id(rs.getInt("forum_id"));
				t.setPosted_by(rs.getInt("posted_by"));
				t.setThread_id(rs.getInt("thread_id"));
				t.setPosting_date(rs.getTimestamp("posting_date"));
				
				Blob blob = rs.getBlob("message_content");
				byte[] bdata = blob.getBytes(1, (int) blob.length());
				String s = new String(bdata);
				t.setMessage_content(s);										
				
				t_m.add(t);
				
				no_of_messages++;
				
			}
						
			pst = con.prepareStatement("Select no_of_views from threads where id = ?");
			pst.setInt(1, thread_id);
			rs = pst.executeQuery();
			while(rs.next()){
				no_of_views = rs.getInt("no_of_views")+1;
			}
			
			pst = con.prepareStatement("Update threads set no_of_views = ?, no_of_messages = ? where id = ?");
			pst.setInt(1, no_of_views);
			pst.setInt(2, no_of_messages);
			pst.setInt(3, thread_id);
			pst.executeUpdate();
			
			pst.close();
			con.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return t_m;
	}
}
























/*public Map<String,String> loginCheck(String username,String password){

Map<String, String> data = new HashMap<String, String>();		
try{
	databaseConnect obj=databaseConnect.getInstance();
	con=obj.getConnection();
	
	pst=con.prepareStatement("Select * from user where username = ? and password = ?");
	pst.setString(1,username);
	pst.setString(2,password);

	rs=pst.executeQuery();			
	while(rs.next()){	
		data.put( "id", (String.valueOf(rs.getInt("id"))));
	    data.put( "name", rs.getString("name") );
	    data.put( "username", rs.getString("username") );
	    data.put( "type", rs.getString("type") );			    
	    
	}
}catch(Exception e){
	e.printStackTrace();
}			

return data;
}*/
