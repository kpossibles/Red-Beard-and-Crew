import java.util.*;
import java.lang.reflect.Type;
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

	public MainDirectory(){
		employees = new ArrayList<>();
		g = new Gson();
	}
	
	public void add(String json){
		Type type = new TypeToken<Collection<Employee>>(){}.getType();
		Collection<Employee> list = g.fromJson(json, type);
		for(Employee e: list){
			//Insert in place to maintain sort
			for (int i = 0; i < employees.size(); i++)
				if (e.lname.compareTo(employees.get(i).lname) < 0){
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
		MainDirectory directory = new MainDirectory();
		// set up a simple HTTP server on our local host
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// create a context to get the request to display the results
		server.createContext("/displayresults", directory.new DisplayHandler());

		// create a context to get the request for the POST
		server.createContext("/sendresults", directory.new PostHandler());
		server.setExecutor(null); // creates a default executor

		// get it going
		System.out.println("Starting Server...");
		server.start();
	}

	class DisplayHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {

			String response = "Begin of response\n";
			response += print() + '\n';
			response += "End of response\n";
			System.out.println(response);
			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	class PostHandler implements HttpHandler {
		public void handle(HttpExchange transmission) throws IOException {
			InputStream inputStr = transmission.getRequestBody();
			OutputStream outputStream = transmission.getResponseBody();
			
			StringBuilder sb = new StringBuilder();
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				sb=sb.append((char)nextChar);
				nextChar=inputStr.read();
			}
			parseText(sb.toString());
			System.out.print(sb.toString());
			System.out.print(print());
			
			String postResponse = "ROGER COMMAND RECEIVED";
			transmission.sendResponseHeaders(300, postResponse.length());
			outputStream.write(postResponse.getBytes());
			outputStream.close();
		}
	}
}
