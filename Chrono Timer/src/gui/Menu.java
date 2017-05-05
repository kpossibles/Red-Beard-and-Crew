package gui;

import java.util.ArrayList;

import javax.swing.JTextArea;

public class Menu {
	private final CommandList commands;
	private int currentSelection;
	
	public Menu(JTextArea menuText){
		commands = new CommandList();
		commands.add(new Command("RESET", true));
		commands.add(new Command("TIME"));
		commands.add(new Command("TOG"));
		commands.add(new Command("CONN"));
		commands.add(new Command("EVENT"));
		commands.add(new Command("NEWRUN"));
		commands.add(new Command("ENDRUN"));
		commands.add(new Command("PRINT"));
		commands.add(new Command("EXPORT"));
		commands.add(new Command("NUM"));
		commands.add(new Command("CLR"));
		commands.add(new Command("DNF"));
		commands.add(new Command("TRIG"));
		commands.add(new Command("START"));
		commands.add(new Command("FINISH"));
		
		String options = commands.toString();
		menuText.setText(options);
	}
	
	@SuppressWarnings("serial")
	private class CommandList extends ArrayList<Command>{
		@Override
		public String toString(){
			String temp = "";
			for(Command c:this){
				temp+=c.toString()+'\n';
			}
			temp = temp.substring(0, temp.length()-2);
			System.out.println(temp);
			return temp;
		}
	}
}


