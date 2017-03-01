
public class Sensor {
	String type;
	boolean active;
	String[] times;
	Timer timer;
	
	public Sensor(Timer t){
		type="";
		active = false;
		timer = t;
		times = new String[3];
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
	public void trigger(){
		// I'm not exactly sure how this should
		if(times[0] == null)
			times[0] = timer.getTime();
		else if (times[1] == null)
			times[1] = timer.getTime();
	}
}
