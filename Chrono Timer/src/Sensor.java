
public class Sensor {
	String type;
	Channel channel;
	
	/**
	 * Instantiates a new sensor.
	 *
	 * @param c the c
	 * @param _type the type
	 */
	public Sensor(Channel c, String _type){
		type = _type;
		channel = c;
	}
	
	/**
	 * Sets the type to EYE, GATE, or PAD.
	 *
	 * @param type the new type
	 */
	public void setType(String type){
		this.type = type;
	}
	public void trigger(){
		channel.trigger();
	}
}
