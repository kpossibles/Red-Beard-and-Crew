package gui;

public class Command {
	private final String name;
	private boolean selected;
	
	public Command(String name){
		this.name = name;
	}
	
	public Command(String name, boolean selected){
		this.name = name;
		this.selected = selected;
	}

	public String getName() {
		return name;
	}
	
	public String getSelectName() {
		return "> " + name;
	}

	public boolean isSelected() {
		return selected;
	}

	public String setSelected(boolean selected) {
		this.selected = selected;
		return "> " + name;
	}
	
	/* 
	 * returns custom Command string
	 */
	@Override
	public String toString(){
		return selected ? "> " + name : name;
		
	}
	
}
