import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import com.google.gson.*;

public class Directory {
	private Gson g = new Gson();
	private DirectoryProxy proxy;
	static boolean isRec = false;
	/**
	 * Instantiates a new directory.
	 */
	public Directory(){
		// TODO
		proxy = new DirectoryProxy();
	}
	
	/**
	 * Adds the employee collection via proxy.
	 *
	 * @param e the employee collection
	 */
	public void add(Collection<Employee> e){
		// TODO
		String json = g.toJson(e);
		proxy.add(json);
	}
	
	/**
	 * Clears the list of employees via proxy.
	 */
	public void clear(){
		// TODO
		proxy.clear();
	}
	
	/**
	 * Prints via proxy.
	 */
	public void print(){
		// TODO
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
	
	private static void parseText(String s, Directory dir, Collection<Employee> employees){
		if(!isRec){
			if(s.equalsIgnoreCase("CLR")){
				// TODO
				dir.clear();
			}
			if(s.equalsIgnoreCase("ADD")){
				// TODO
				isRec = true;
			}
			if(s.equalsIgnoreCase("PRINT")){
				// TODO
				dir.print();
			}
		}
		// adding
		else{
			toCollection(s, employees);
			dir.add(employees);
			isRec = false;
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
		Collection<Employee> employees = new ArrayList<>();
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
					while(file_in.hasNextLine()){
						if(isRec){
							String nextline1 = "";
							@SuppressWarnings("unused")
							String str = "";
							while(!nextline1.equalsIgnoreCase("END") || nextline1.equalsIgnoreCase("EXIT")){
								nextline1 = file_in.nextLine();
								str += nextline1;
							}
							if(nextline.equalsIgnoreCase("Exit")){ break; };
						}
						else{
							String nextline1 = file_in.nextLine();
							if(nextline1.equals("EXIT"))
								break;
							parseText(nextline1,dir,employees);
						}
					}
					file_in.close();
				}
				catch (FileNotFoundException e){
					e.printStackTrace();
				}
			}
			// Console
			else if(nextline.equalsIgnoreCase("c")){
				System.out.println("Welcome! Commands are CLR, ADD, PRINT, or [e]xit: ");
				nextline = input.nextLine();
				while(!nextline.equalsIgnoreCase("E")){
					if(nextline.equalsIgnoreCase("CLR")){
						dir.clear();
						System.out.println("cleared");
					}
					if(nextline.equalsIgnoreCase("ADD")){
						// TODO
						String str = "";
						System.out.println("Enter below in this format: <FIRSTNAME> <LASTNAME> <DEPT> <PHONENUMBER> or END to save");
						nextline = input.nextLine();
						while(!nextline.equalsIgnoreCase("END")){					
							str += (nextline + "\n");
							if(str.length() == 0){
								System.out.println("Error. Please enter employee info in correct format.");
							}
							nextline = input.nextLine();
						}
						toCollection(str, employees);
						dir.add(employees);
						System.out.println("added!");
					}
					if(nextline.equalsIgnoreCase("PRINT")){
						// TODO
						dir.print();
					}
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
