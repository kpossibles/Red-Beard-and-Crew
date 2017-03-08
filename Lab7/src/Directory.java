import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import com.google.gson.*;

public class Directory {
	private Gson g = new Gson();
	private DirectoryProxy proxy;
	
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
		nextline = input.next();
		System.out.println();
		
		while(!nextline.equalsIgnoreCase("E")){
			// read from file
			if(nextline.equalsIgnoreCase("f")){
				// TODO
			}
			// Console
			else if(nextline.equalsIgnoreCase("c")){
				while(!nextline.equalsIgnoreCase("E")){
					if(nextline.equalsIgnoreCase("CLR")){
						// TODO
					}
					if(nextline.equalsIgnoreCase("ADD")){
						// TODO
						String str = "";
						System.out.println("Enter below in this format: <FIRSTNAME> <LASTNAME> <DEPT> <PHONENUMBER>");
						while(!nextline.equalsIgnoreCase("END")){
							nextline = input.nextLine();
							str += nextline;
							if(str.length() == 0){
								System.out.println("Error. Please enter employee info in correct format.");
							}
						}
						toCollection(str, employees);
						dir.add(employees);
					}
					if(nextline.equalsIgnoreCase("PRINT")){
						// TODO
					}
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
		System.out.println("\n\nThank you for using the directory.");
	}
}
