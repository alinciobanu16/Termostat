import java.util.ArrayList;
import java.util.Vector;
/****
 * 
 * @author alin_matei.ciobanu
 * Ciobanu Alin-Matei 325CB
 *
 */
public class Room extends House {
	private String roomName;
	private String deviceId;
	private int area;

	/*
	 * vector de ore in care fiecare element este format dintr-un arraylist pentru stocarea 
	 * temperaturilor pe intervale orare
	 * 
	 * */
	Vector<ArrayList<DeviceT>> hours;
	Vector <ArrayList<DeviceH>> hoursH;
	
	public Room() {
		
	}
	
	/***
	 * 
	 * @param roomName - camera din care observa senzorul
	 * @param deviceId - id-ul senzorului din camera respectiva
	 * @param area - suprafata camerei
	 */
	public Room(String roomName, String deviceId, int area) {
		hours = new Vector<ArrayList<DeviceT>>(24);
		for (int i = 0; i < 24; i++) {
			hours.add(new ArrayList<DeviceT>());
		}
		setRoomName(roomName);
		setDeviceId(deviceId);
		setArea(area);
		
		if (getIsH() == 1) {
		 	hoursH = new Vector<ArrayList<DeviceH>>();
		 	for (int i = 0; i < 24; i++) {
		 		hoursH.add(new ArrayList<DeviceH>());
		 	}
		}
	}
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}
}
