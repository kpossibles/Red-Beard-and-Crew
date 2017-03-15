import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import com.google.gson.*;

public class Directory {
	private Gson g = new Gson();
	private DirectoryProxy proxy;
	static boolean isC = false;
	/**
	 * Instantiates a new directory.
	 */
	public Directory(){
		proxy = new DirectoryProxy();
	}
	
	/**
	 * Adds the employee collection via proxy.
	 *
	 * @param e the employee collection
	 */
	public void add(Collection<Employee> e){
		String json = g.toJson(e);
		proxy.add(json);
	}
	
	/**
	 * Clears the list of employees via proxy.
	 */
	public void clear(){
		proxy.clear();
	}
	
	/**
	 * Prints via proxy.
	 */
	public void print(){
		proxy.print();
	}
	
	/**
	 * Convert to Collection.
	 *
	 * @param s the input
	 * @param c the list of employees
	 */
	public static void toCollection(String s, Collection<Employee> c){
		String[] list = s.split("\n");
		for(String e: list){
			String[] val = e.split(" ");
			Employee person = new Employee(val[0], val[1], val[2], val[3]);
			c.add(person);
		}
		
	}
	
	private static void parseText(String s, Directory dir, Scanner file_in){
		Collection<Employee> employees = new ArrayList<>();
		if(s.equalsIgnoreCase("Add")){
			if(isC){
				System.out.println("Enter below in this format: <FIRSTNAME> <LASTNAME> <DEPT> <PHONENUMBER> or END to save");
			}
			s = file_in.nextLine();
			String str = "";
			while(!s.equalsIgnoreCase("END") || s.equalsIgnoreCase("EXIT")){
				str += (s + "\n");
				if(isC && str.length() == 0){
					System.out.println("Error. Please enter employee info in correct format.");
				}
				s = file_in.nextLine();
			}
			toCollection(str, employees);
			dir.add(employees);
			if(isC) System.out.println("added!");
		}
		if(s.equalsIgnoreCase("CLR")){
			dir.clear();
			if(isC) System.out.println("cleared");
		}
		if(s.equalsIgnoreCase("PRINT")){
			System.out.println("\nPrinting...");
			dir.print();
		}
	}
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		// TODO
		String nextline = "";
		Directory dir = new Directory();
		
		Scanner input = new Scanner(System.in);
		System.out.println("WELCOME TO EMPLOYEE DIRECTORY!");
		System.out.print("[f]ile source / [c]onsole / [e]xit: ");
		nextline = input.nextLine();
		
		while(!nextline.equalsIgnoreCase("E")){
			// read from file
			if(nextline.equalsIgnoreCase("f")){
				try {
					String path = "TestData.txt";
					File f = new File(path);
					Scanner file_in = new Scanner(f);
					nextline = file_in.nextLine();
					while(file_in.hasNextLine()){
						parseText(nextline,dir,file_in);
						nextline = file_in.nextLine();
					}
					file_in.close();
				}
				catch (FileNotFoundException e){
					e.printStackTrace();
				}
				nextline = "e";
			}
			// Console
			else if(nextline.equalsIgnoreCase("c")){
				isC = true;
				System.out.println("Welcome! Commands are CLR, ADD, PRINT, or [e]xit: ");
				nextline = input.nextLine();
				while(!nextline.equalsIgnoreCase("E")){
					parseText(nextline,dir,input);
					nextline = input.nextLine();	
				}
				input.close();
			}
			// invalid entry
			else {
				System.out.println("Invalid entry");
				System.out.print("[f]ile source / [c]onsole / [e]xit: ");
				nextline = input.next();
			}
		}
		
		// exit
		System.out.println("\nThank you for using the directory.");
	}
}
