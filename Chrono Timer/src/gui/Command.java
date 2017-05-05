package gui;

public class Command {
	private final String name;
	private final String selectName;
	private boolean selected;
	
	public Command(String name){
		this.name = name;
		selectName = "> "+name;
	}
	
	public Command(String name, boolean selected){
		this.name = name;
		selectName = "> "+name;
		this.selected = selected;
	}

	public String getName() {
		return name;
	}
	
	public String getSelectName() {
		return selectName;
	}

	public boolean isSelected() {
		return selected;
	}

	public String setSelected(boolean selected) {
		this.selected = selected;
		return selectName;
	}
	
	/* 
	 * returns custom Command string
	 */
	@Override
	public String toString(){
		return selected ? selectName : name;
		
	}
	
}
