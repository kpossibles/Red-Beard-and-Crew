package chronoserver;

import com.google.gson.*;
import com.google.gson.reflect.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import chronotimer.Racer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Evan on 5/6/2017.
 *
 * This is a Web Server for the Chronotimer class.
 */
public class ChronoServer {
    private Map<Integer, String> NumNameMap;
    private int refreshTime=5;
    
    public ChronoServer () throws IOException {
        // set up a simple HTTP server on our local host
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		// create a context to get the request to display the results
		server.createContext("/displayresults", new DisplayHandler());

		// create a context to get css
		server.createContext("/style.css", new cssHandler());
		
		// create a context to get background image
		server.createContext("/bgd.png", new imageHandler());

        server.start();

        NumNameMap = new HashMap<>();

        fill_map();
    }

    private void fill_map(){
        NumNameMap.put(100, "Evan");
        NumNameMap.put(200, "Kim");
        NumNameMap.put(300, "James");
        NumNameMap.put(400, "Biscuit");
    }

    public class DisplayHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "";
//			System.out.println("\nBeginning of response\n");

            //define a HTML String Builder
            StringBuilder htmlStringBuilder=new StringBuilder();

            //set up content
            htmlStringBuilder.append("<html><head><title>Race Results</title>");
            htmlStringBuilder.append("<meta http-equiv=\"refresh\" content=\""+refreshTime+"\">");
            htmlStringBuilder.append("<link href=\"https://fonts.googleapis.com/css?family=Scope+One\" rel=\"stylesheet\">");
            htmlStringBuilder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head>");
            htmlStringBuilder.append("<body><div class=\"wrapper\">");
            htmlStringBuilder.append("<h1>Race Results</h1>");
            htmlStringBuilder.append("<table class=\"center\">");
            //append row: Attributes
            htmlStringBuilder.append("<tr class=\"header\"><th><b>Place</b></th>"
                    + "<th><b>Number</b></th>"
                    + "<th><b>Name</b></th>"
                    + "<th><b>Time</b></th>");
            //append with content from directory
            boolean clientActive = racerToHtml(htmlStringBuilder);
            if(!clientActive){
            	htmlStringBuilder.append("<tr class=\"light\"><td>---</td><td>---</td><td>---</td><td>---</td></tr>");
            }
            //close html file
            htmlStringBuilder.append("</table>");
            if(!clientActive){
            	htmlStringBuilder.append("<h2>Client is not active!</h2>");
            	htmlStringBuilder.append("<h3>Press refresh to get the most recent additions and results.</h3>");
            	refreshTime=100;
            }else{
            	refreshTime=2;
            }
            htmlStringBuilder.append("<div></body></html>");
            response+=htmlStringBuilder.toString();

//			System.out.println(response);
//			System.out.println("\nEnd of response\n");

            // write out the response
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private boolean racerToHtml(StringBuilder htmlStringBuilder) {
            ArrayList<chronotimer.Racer> racers = new ArrayList<>();
            try {
                // Connect to the Chronotimer Server
                URL site = new URL("http://localhost:8000/results");
                HttpURLConnection conn = (HttpURLConnection) site.openConnection();

                // now create a POST request
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());

                // write out string to output buffer to request the race results
                out.writeBytes("REQUEST_RUN");
                out.flush();
                out.close();

                InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

                // string to hold the result of reading in the response
                StringBuilder sb = new StringBuilder();

                // read the characters from the request byte by byte and build up
                // the Response
                int nextChar;
                while ((nextChar = inputStr.read()) > -1) {
                    sb = sb.append((char) nextChar);
                }
                String JsonToParse = sb.toString();
                Gson g = new Gson();
                Type type = new TypeToken<Collection<chronotimer.Racer>>(){}.getType();
                Collection<chronotimer.Racer> list = g.fromJson(JsonToParse, type);
                for(Racer r:list){
                    racers.add(r);
                }
            } catch(MalformedURLException e){
            	return false;
//                e.printStackTrace();
            } catch(IOException e){
            	return false;
//                e.printStackTrace();
            }

            // First, sort the racers in a new list based on place.
            // Separate into two lists. One for those that have finished and one for DNF and unfinished
            ArrayList<chronotimer.Racer> finishedRacers = new ArrayList<>();
            ArrayList<chronotimer.Racer> DNFandIncompleteRacers = new ArrayList<>();
            for(chronotimer.Racer r: racers){
                if (r.getFinish() > 0){
                    int i;
                    for(i = 0; i<finishedRacers.size(); i++){
                        if(r.getTimeAsLong() < finishedRacers.get(i).getTimeAsLong()){
                            break;
                        }

                    }
                    finishedRacers.add(i, r);
                } else {
                    if (r.DNF()) { // Put the racers that have not finished before the racers that DNF!
                        DNFandIncompleteRacers.add(r);
                    } else{
                        DNFandIncompleteRacers.add(0, r);
                    }
                }
            }
            finishedRacers.addAll(DNFandIncompleteRacers);

            racers = finishedRacers;

            // Finally, display the results
            if(racers.size() > 0) {
                String res = "";

                for(int i=0;i<racers.size();i++){
                    chronotimer.Racer r= racers.get(i);
                    if(i%2==0)
                        res += "<tr class=\"light\">";
                    else
                        res += "<tr class=\"dark\">"; //Place, Number, Name, Time
                    res    += "<td>" + (i+1) + "</td>"
                            + "<td>" + r.getId() + "</td>"
                            + "<td>" + (NumNameMap.containsKey(r.getId())?NumNameMap.get(r.getId()):"") + "</td>"
                            + "<td>" + (r.getDNF()?"DNF":r.getTime()=="-2"?"":r.getTime()) + "</td>";
                    res += "</tr>";
                }

                htmlStringBuilder.append(res);
            }
            return true;
        }
    }

    public class cssHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange t) throws IOException {
        	File css = new File("style.css"); // load a css file
        	Scanner in = new Scanner(css);
			String response = "";
			while(in.hasNextLine()) {
				response += in.nextLine();
			}
			in.close();

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    public class imageHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange t) throws IOException {
        	File file = new File("bgd.png"); // load an image
        	Scanner in = new Scanner(file);
			String response = "";
			while(in.hasNextLine()) {
				response += in.nextLine();
			}
			in.close();
			
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            Files.copy(file.toPath(), os);
            os.close();
        }
    }

    public static void main(String[] args){
        try {
            new ChronoServer();
            System.out.println("ChronoServer IS ON!!!");
        }
        catch(IOException e){
            System.out.println("Could not bind to port 80.  Is there a server already started?");
        }
    }
}
