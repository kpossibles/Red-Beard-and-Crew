
public class Sensor {
	String type;
	boolean active;
	
	public Sensor(){
		type="";
		active = false;
	}
	public Sensor(String type){
		type = this.type;
		active = false;
	}
	
	// Sensor type can be EYE, GATE, or PAD
	public void setType(String type){
		this.type = type;
	}
	
	public void setActive(){
		active = true;
	}
	public boolean isActive(){
		return active;
	}
}
