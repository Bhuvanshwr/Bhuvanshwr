import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MyDbConnection {
	
	public static void main(String[] args) {
		String url="jdbc:sqlserver://LAPTOP-PNCDSAI4\\sqlexpress01:1434;databaseName=New";
		String username="final";
		String password="bhuvi";
		getdata(url,username,password);
		
	}
	

		
		private static void getdata(String url,String username,String password)
		{
			List<String> data = new ArrayList<>();
			data.add("id,fist,timestamp,contactNo");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
			LocalDateTime now = LocalDateTime.now();  
			String filename="E:\\Jars\\New folder\\Employee"+dtf.format(now)+".csv";
			Boolean status;
			int deletestatus=0;
			System.out.println(filename);
			
		try (Connection connection =DriverManager.getConnection(url, username, password))
		{   
			Statement stmt  = connection.createStatement();
			String sql = "SELECT * FROM [Activity] where timestamp>\'2020-01-30 18:34:00.0000000\'";
			ResultSet rs    = stmt.executeQuery(sql);
	
			while (rs.next()) {
				String id = rs.getString("Name");
				String fist = rs.getString("fist");
				String timestamp = rs.getString("timestamp");
				String contactNo = rs.getString("contactNo");
				data.add(id + "," + fist + "," + timestamp + "," + contactNo);
				System.out.println(data);
			}
	
			status=writeToFile(data, filename);
			sendemail(status,deletestatus);
			deletestatus=deletedata(status,url, username, password);
			sendemail(status,deletestatus);
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		private static Boolean writeToFile(List<String> list, String path) {
			BufferedWriter out = null;
			try {
				File file = new File(path);
				out = new BufferedWriter(new FileWriter(file, true));
				for (String s : list) {
					out.write(s);
					out.newLine();
				}
				out.close();
				return true;
			} catch (IOException e) {
				return false;
			}
		
		}
		
		private static void sendemail(Boolean status,int deletestatus)
		{
			/*
			 * String to = "@gmail.com"; String from = "@gmail.com"; String host =
			 * "localhost";//or IP address MimeMessage message=new MimeMessage(session);
			 * message.setFrom(new InternetAddress("sonoojaiswal@sssit.org"));
			 * message.addRecipient(Message.RecipientType.To, new
			 * InternetAddress("sonoojaiswal@javatpoint.com"));
			 * message.setHeader("Hi, everyone");
			 * message.setText("Hi, This mail is to inform you...");
			 */
		}
		
		private static int deletedata(Boolean status,String url,String username,String password)
		{
			if(status)
			{
			try (Connection connection =DriverManager.getConnection(url, username, password))
			{   
				Statement stmt  = connection.createStatement();
				String sql = "delete FROM [Activity] where timestamp=\'2020-12-19 18:34:00.0000000\'";
	            return stmt.executeUpdate(sql);
			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			
		}
			return 0;
		}
	}


