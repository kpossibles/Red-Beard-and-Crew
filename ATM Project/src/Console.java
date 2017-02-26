import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Console implements Printer, Display, Dispensor
{
	private ATM atm;
	
	public Console(){
		atm = new ATM(this, this, this);
	}
	
	public boolean input(String line){
		if (line.indexOf(' ') == -1)
			line = "DIS \"INVALID INPUT\"";
		String command = line.substring(0,line.indexOf(' '));
		String argument = line.substring(line.indexOf(' ') + 1);
		
		if(command.equalsIgnoreCase("CARDREAD"))
			atm.insertCard(new Card(Integer.parseInt(argument)));
		else if(command.equalsIgnoreCase("NUM"))
			atm.inputNum(Integer.parseInt(argument));
		else if(command.equalsIgnoreCase("DIS"))
			System.out.println(argument.substring(1, argument.length()-1));
		else if(command.equalsIgnoreCase("PRINT"))
			System.out.println(argument);
		else if(command.equalsIgnoreCase("BUTTON"))
			if(argument.equalsIgnoreCase("W"))
				atm.withdraw();
			else if(argument.equalsIgnoreCase("CB"))
				atm.checkBalance();
			else if(argument.equalsIgnoreCase("CANCEL"))
				atm.cancel();
			else
				return false;
		else
			return false;
		
		return true;
	}
	
	public boolean readFromText(String path){
		try{
			File f = new File(path);
			Scanner file_in = new Scanner(f);
			while(file_in.hasNextLine()){
				input(file_in.nextLine());
			}
			file_in.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void print(String text) {
		input(String.format("PRINT \"%s\"", text));
	}

	@Override
	public void dispense(int amount) {
		// Does nothing in simulator.
	}

	@Override
	public void display(String text) {
		input(String.format("DIS \"%s\"", text));
	}
	
	public static void main(String[] args){
		//TODO: If an argument is passed in with a file name, read from file, otherwise loop through receiving input until cancel. 
		Console simulator = new Console();
		if (args.length>0){
			simulator.readFromText(args[0]);
		}
		else{
			Scanner input = new Scanner(System.in);
			System.out.print(": ");
			String nextLine = input.nextLine();
			while(!nextLine.equals("EXIT")){
				simulator.input(nextLine);
				System.out.print(": ");
				nextLine = input.nextLine();
			}
			input.close();
		}
	}
	
	
}


interface Printer{
	public void print(String text);
}

interface Display{
	public void display(String text);
}

interface Dispensor{
	public void dispense(int amount);
}
