import java.sql.Timestamp;
/***
 * 
 * @author alin_matei.ciobanu
 * Ciobanu Alin-Matei 325CB
 *
 */
public class DeviceT {
	private double temperature;
	private Timestamp timeRef;
	
	public DeviceT() {
		
	}
	
	/***
	 * 
	 * @param temperature - temperatura observata de senzor
	 * @param timeRef - ora la care s-a inregistrat temperatura
	 */
	public DeviceT(double temperature, Timestamp timeRef) {
		setTemperature(temperature);
		setTimeRef(timeRef);
	}
	
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public Timestamp getTimeRef() {
		return timeRef;
	}

	public void setTimeRef(Timestamp timeRef) {
		this.timeRef = timeRef;
	}
}
