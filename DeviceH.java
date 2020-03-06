import java.sql.Timestamp;
/***
 * 
 * @author alin_matei.ciobanu
 * Ciobanu Alin-Matei 325CB
 *
 */
public class DeviceH {
	private double humidity;
	private Timestamp timeRef;
	
	public DeviceH() {
		
	}
	/***
	 * 
	 * @param humidity - umiditatea observata de senzor
	 * @param timeRef - ora la care s-a inregistrat umiditatea
	 */
	public DeviceH(double humidity, Timestamp timeRef) {
		setHumidity(humidity);
		setTimeRef(timeRef);
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public Timestamp getTimeRef() {
		return timeRef;
	}

	public void setTimeRef(Timestamp timeRef) {
		this.timeRef = timeRef;
	}
}
