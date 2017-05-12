package chronoview;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Menu {
	private CommandList commands;
	private int currentSelection;
	private String previous="";
	private String name;
	
	public Menu(){
		commands = new CommandList();
		commands.add(new Command("RESET", true));//✔
		commands.add(new Command("TIME"));//✔
		commands.add(new Command("TOG"));//✔
		commands.add(new Command("CONN"));//✔
		commands.add(new Command("DISC"));//✔
		commands.add(new Command("EVENT"));//✔
		commands.add(new Command("NEWRUN"));//✔
		commands.add(new Command("ENDRUN"));//✔
		commands.add(new Command("PRINT"));//✔
		commands.add(new Command("EXPORT"));
		commands.add(new Command("NUM"));//✔
		commands.add(new Command("CLR"));//✔
		commands.add(new Command("SWAP"));//✔
		commands.add(new Command("DNF"));//✔
		commands.add(new Command("TRIG"));//✔
		commands.add(new Command("START"));//✔
		commands.add(new Command("FINISH"));//✔
		commands.add(new Command("CLOSE"));//✔
		
		name="Main Menu";
	}
	public Menu (String previous, String newName, ArrayList<String> arrayList){
		this.previous = previous;
		commands = new CommandList();
		for(String s:arrayList){
			commands.add(new Command(s));
		}
		commands.get(0).setSelected(true);
		name = newName;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name, if sub-menu, then grab sub-menu substring
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMenu(){
		return name+"\n\n"+commands.toString();
	}
	
	public String getSelected() {
		return commands.get(currentSelection).getName();
	}
	public void setSelected(int key){
		commands.get(currentSelection).setSelected(false);
		switch(key){
			case KeyEvent.VK_UP:
//				System.out.println("UP was pressed");
				if(currentSelection>0)
					currentSelection--;
				else
					currentSelection = commands.size()-1;
				break;
			case KeyEvent.VK_DOWN:
//				System.out.println("DOWN was pressed");
				if(currentSelection<commands.size()-1)
					currentSelection++;
				else
					currentSelection = 0;
				break;
		}
		
		commands.get(currentSelection).setSelected(true);
	}
	
	public void pressLeft(){
//		System.out.println("LEFT was pressed");
		if(previous!=""){
			previous="";
			commands=new Menu().commands;
			name = "Main Menu";
			currentSelection=0;
		}		
	}
	public String getPrevious() {
		return previous;
	}
	public void setPrevious(String previous) {
		this.previous = previous;
	}

	@SuppressWarnings("serial")
	private class CommandList extends ArrayList<Command>{
		@Override
		public String toString(){
			String temp = "";
			for(Command c:this){
				temp+=c.toString()+'\n';
			}
			temp = temp.substring(0, temp.length()-1);
			return temp;
		}
	}
}

