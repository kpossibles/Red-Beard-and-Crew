
public class Sensor {
	String type;
	Channel channel;
	
	public Sensor(Channel c, String _type){
		type = _type;
		channel = c;
	}
	// Sensor type can be EYE, GATE, or PAD
	public void setType(String type){
		this.type = type;
	}
	public void trigger(){
		channel.trigger();
	}
}
