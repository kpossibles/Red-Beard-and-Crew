
public class Channel {
	Sensor sensor;
	ChronoTimer timer;
	boolean on;
	public Channel(ChronoTimer t){
		on = false;
		timer = t;
	}
	public void setSensor(Sensor s){
		sensor = s;
	}
	public void toggle(){
		on = !on;
	}
	public void trigger(){
		// I'm not exactly sure how this should work. 
	}
	
}
