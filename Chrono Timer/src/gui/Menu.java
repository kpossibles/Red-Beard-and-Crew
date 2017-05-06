package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

//import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import chronotimer.Console;

public class Menu implements KeyListener, ActionListener{
	private CommandList commands;
	private int currentSelection;
	private Menu previous=null;
	private String name;
	
	public Menu(){
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
		name="Main Menu";
	}
	public Menu (Menu previous, String newName, String[]newCommands){
		this.previous = previous;
		commands = new CommandList();
		for(int i=0;i<newCommands.length;i++){
			if(i==0)
				commands.add(new Command(newCommands[i], true));
			else
				commands.add(new Command(newCommands[i]));
		}
		name = name+" > "+newName;
	}
	
	public String getMenu(){
		return name+"\n\n"+commands.toString();
	}
	
	public void setSelected(int i){
		commands.get(currentSelection).setSelected(false);
		switch(i){
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
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==38 || key==40){
			setSelected(key);
			((JTextComponent) e.getSource()).setText(getMenu());
		} else if(key==37 || key==39){
			String text = getCommand(key, null);
			if(text!="")
				((JTextComponent) e.getSource()).setText(text);
		}
	}

	private String getCommand(int key, Console c) {
		// TODO Auto-generated method stub
		String text="";
		switch(key){
			case KeyEvent.VK_LEFT:
//				System.out.println("LEFT was pressed");
				if(previous!=null){
					previous=null;
					commands=new Menu().commands;
					name = "Main Menu";
					text = getMenu();
					currentSelection=0;
				}
				break;
			// implement action
			case KeyEvent.VK_RIGHT:
//				System.out.println("RIGHT was pressed");
				if(previous==null){
					name=name+" > "+commands.get(currentSelection).getName();
					if(currentSelection ==)
					commands.get(currentSelection).setSelected(false);
					String [] temp = {"1", "2", "3", "4", "5", "6", "7", "8"};
					previous = this;
					commands = new Menu(this, commands.get(currentSelection).getName(), temp).commands;
					currentSelection=0;
					text+=getMenu();
				}
				break;
		}
		return text;
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e, JTextArea textbox, int key, Console c) {
		if(key==38 || key==40){
			setSelected(key);
			textbox.setText(getMenu());
		} else if(key==37 || key==39){
			String temp = getCommand(key, c);
			if(temp!="")
				textbox.setText(temp);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// does nothing
		
	}
}

