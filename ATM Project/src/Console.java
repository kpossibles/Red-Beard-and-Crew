import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Console implements Printer
{
	private Scanner in;
	private ATM atm;
	
	public Console(){
		in = new Scanner(System.in);
		atm = new ATM(this);
	}
	
	public boolean input(String line){
		String command = line.substring(0,line.indexOf(' '));
		String argument = line.substring(line.indexOf(' ') + 1);
		
		if(command.equalsIgnoreCase("CARDREAD"))
			atm.insertCard(new Card(Integer.parseInt(argument)));
		else if(command.equalsIgnoreCase("NUM"))
			atm.inputNum(Integer.parseInt(argument));
		else if(command.equalsIgnoreCase("DIS"))
			System.out.println(argument.substring(0, argument.length()-2));
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
		File f = new File(path);
		try{
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
	public void print(String s) {
		System.out.println(s);
	}
}

interface Printer{
	public void print(String s);
}
