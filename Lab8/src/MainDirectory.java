import java.util.*;
import java.lang.reflect.Type;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Collections;


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

			String response = "Begin of response\n";
			
			//set up the header
			try {
				if (!sharedResponse.isEmpty()) {
					response += "Before sort\n";
					response += print();
					Collections.sort(employees);
					response += "\nAfter sort\n";
					response += print();
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
			
			response += "\nEnd of response\n";
			System.out.println(response);
			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
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
            sharedResponse = sharedResponse+sb.toString();
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
}
