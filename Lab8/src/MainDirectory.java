import java.util.*;
import java.lang.reflect.Type;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collection;

import com.google.gson.*;
import com.google.gson.reflect.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class MainDirectory {
	private ArrayList<Employee> employees;
	private Gson g;
	static String sharedResponse = "";

	public MainDirectory() throws IOException{
		employees = new ArrayList<>();
		g = new Gson();
		
		// set up a simple HTTP server on our local host
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// create a context to get the request to display the results
		server.createContext("/displayresults", new DisplayHandler());
		
		// create a context to get the request to display the results
		server.createContext("/style.css", new cssHandler());

		// create a context to get the request for the POST
		server.createContext("/sendresults", new PostHandler());
		server.setExecutor(null); // creates a default executor

		// get it going
		System.out.println("Starting Server...");
		server.start();
	}
	
	public void add(String json){
		Type type = new TypeToken<Collection<Employee>>(){}.getType();
		Collection<Employee> list = g.fromJson(json, type);
		for(Employee e: list){
			//Insert in place to maintain sort
			for (int i = 0; i < employees.size(); i++)
				if (e.lastname.compareTo(employees.get(i).lastname) < 0){
					employees.add(i, e);
					break;
				}
			if (!employees.contains(e))
				employees.add(e);
		}
	}
	
	public void clear(){
		employees = new ArrayList<>();
	}
	
	public String print(){
		if(employees.size() == 0)
			return "<empty directory>";
		else {
			String res = "";
			for(Employee e : employees)
				res += e.toString() + '\n';
			return res;
		}
	}

	public String toString(){
		if(employees.size() == 0)
			return "<empty directory>";
		else {
			String res = "";
			for(Employee e : employees)
				res += e.toString() + '\n';
			return res;
		}
	}

	@SuppressWarnings("unused")
	private void parseText(String s){
		if(s.length() > 3 && s.substring(0,3).equalsIgnoreCase("Add"))
			this.add(s.substring(4));
		if(s.equalsIgnoreCase("CLR")){
			this.clear();
			System.out.println("Database Cleared");
		}
		if(s.equalsIgnoreCase("PRINT"))
			this.print();
	}

	public static void main(String[] args) throws Exception {
		new MainDirectory();
	}

	public class DisplayHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			String response = "";
			System.out.println("\nBeginning of response\n");
			
			//define a HTML String Builder
            StringBuilder htmlStringBuilder=new StringBuilder();
			
            //set up content
			try {
				if (!sharedResponse.isEmpty()) {
		            htmlStringBuilder.append("<html><head><title>Directory</title>");
		            htmlStringBuilder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head>");
		            htmlStringBuilder.append("<body>");
		            htmlStringBuilder.append("<h1>Company Directory</h1>");
		            htmlStringBuilder.append("<table>");
		            //append row: Attributes
		            htmlStringBuilder.append("<tr class=\"header\"><th><b>Title</b></th>"
		            		+ "<th><b>First Name</b></th>"
		            		+ "<th><b>Last Name</b></th>"
		            		+ "<th><b>Department</b></th>"
		            		+ "<th><b>Phone</b></th>"
		            		+ "<th><b>Gender</b></th></tr>");
		            //append with content from directory
		            employeeToHtml(htmlStringBuilder);
		            
		            //close html file
		            htmlStringBuilder.append("</table></body></html>");
		            response+=htmlStringBuilder.toString();
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
			
			System.out.println(response);
			System.out.println("\nEnd of response\n");
			
			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}

		private void employeeToHtml(StringBuilder htmlStringBuilder) {
			if(employees.size() > 0) {
				String res = "";
				for(int i=0;i<employees.size();i++){
					if(i%2==0)
						res += "<tr class=\"light\">";
					else
						res += "<tr class=\"dark\">";
					res += "<td>"+employees.get(i).title+"</td>"
		            		+ "<td>"+employees.get(i).firstname+"</td>"
            				+ "<td>"+employees.get(i).lastname+"</td>"
		            		+ "<td>"+employees.get(i).department+"</td>"
		            		+ "<td>"+employees.get(i).phone+"</td>"
		            		+ "<td>"+employees.get(i).gender+"</td>";
					res += "</tr>";
				}
				
				htmlStringBuilder.append(res);
			}
		}
	}

	public class PostHandler implements HttpHandler {
		public void handle(HttpExchange transmission) throws IOException {
			//  shared data that is used with other handlers
            sharedResponse = "";

            // set up a stream to read the body of the request
			InputStream inputStr = transmission.getRequestBody();
			OutputStream outputStream = transmission.getResponseBody();
			
			StringBuilder sb = new StringBuilder();
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				sb=sb.append((char)nextChar);
				nextChar=inputStr.read();
			}
			// create our response String to use in other handler
            sharedResponse = "["+sharedResponse+sb.toString()+"]";
            ArrayList<Employee> fromJson = g.fromJson(sharedResponse,
					new TypeToken<Collection<Employee>>() {
					}.getType());

			employees.addAll(fromJson);
			
			String postResponse = "ROGER COMMAND RECEIVED";
			System.out.println("response: " + sharedResponse);
			
			transmission.sendResponseHeaders(300, postResponse.length());
			outputStream.write(postResponse.getBytes());
			outputStream.close();
		}
	}

	public class cssHandler implements HttpHandler{
		@Override
		public void handle(HttpExchange t) throws IOException {
			String response=".tg {border-collapse:collapse;border-spacing:0;}"
            		+"table, th, td {border: 1px solid black;font-family:Arial, sans-serif;font-size:14px;}"
					+".header{background-color:#333333;color:white;}"
            		+".dark{background-color: #E0E0E0;color:black;}"
            		+".light{background-color: white;color:black;}";

            t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
