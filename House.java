import java.sql.Timestamp;
import java.util.ArrayList;
/***
 * 
 * @author alin_matei.ciobanu
 * Ciobanu Alin-Matei 325CB
 *
 */
public class House {
	private int nrOfRooms;
	private double globalTemperature;
	private double globalHumidity;
	private Timestamp timeRef;
	private static int isH;

	//vector de camere
	ArrayList<Room> rooms;
	public House() {
		rooms = new ArrayList<Room>();
	}
	
	public int getNrOfRooms() {
		return nrOfRooms;
	}

	public void setNrOfRooms(int nrOfRooms) {
		this.nrOfRooms = nrOfRooms;
	}

	public double getGlobalTemperature() {
		return globalTemperature;
	}

	public void setGlobalTemperature(double globalTemperature) {
		this.globalTemperature = globalTemperature;
	}

	public double getGlobalHumidity() {
		return globalHumidity;
	}

	public void setGlobalHumidity(double globalHumidity) {
		this.globalHumidity = globalHumidity;
	}
	
	public int getIsH() {
		return isH;
	}
	
	public static void setIsH(int isH) {
		House.isH = isH;
	}
	
	public Timestamp getTimeRef() {
		return timeRef;
	}

	public void setTimeRef(Timestamp timeRef) {
		this.timeRef = timeRef;
	}	
	
	/***
	 * adaug o noua camera
	 * @param roomName - numele camerei adaugate
	 * @param DeviceTId - id-ul camerei adaugate
	 * @param area - suprafata camerei adaugate
	 */
	
	public void addRoom(String roomName, String DeviceTId, int area) {
		rooms.add(new Room(roomName, DeviceTId, area));
	}
	
	/***
	 * adaug temperatura de la ora specificata la camera data
	 * @param DeviceTId - id-ul camerei in care inserez temperatura
	 * @param time - ora la care temperatura este inregistrata
	 * @param temperature - temperatura pe care o adaug
	 * @param ref - timpul de referinta in secunde
	 */
	public void addTemperature(String deviceId, long time, double temperature, long ref) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getDeviceId().equals(deviceId)) {
				/***
				 * calculez pozitia pe care trebuie inserata noua temperatura in functie de timpul dat
				 * temperatura va fi inserata in intervalul de timp dat de pozitia poz
				 */
				long dif = ref - time;
				float pozf = 24 - ((float)dif / 3600);
				int poz = (int)pozf;
				int poz_sort = 0;
				/***
				 * inserez temperatura pe pozitia poz_sort astfel incat temperaturile
				 * din intervalul de timp sa fie sortate
				 */
				for (int k = 0; k < rooms.get(i).hours.get(poz).size(); k++) {
					if (temperature == rooms.get(i).hours.get(poz).get(k).getTemperature()) {
						return;
						/***
						 * daca temperatura este inregistrata deja in acelasi interval 
						 * nu o mai adaug
						 */
					}
					if (temperature < rooms.get(i).hours.get(poz).get(k).getTemperature()) {
						poz_sort = k;
						break;
					} else {
						poz_sort = k + 1;
					}
				}
				rooms.get(i).hours.get(poz).add(poz_sort, new DeviceT(temperature, new Timestamp(time * 1000)));
				break;
			}
		}
	}
	
	public void addHumidity(String deviceId, long time, double humidity, long ref) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getDeviceId().equals(deviceId)) {
				/***
				 * calculez pozitia pe care trebuie inserata noua umiditate in functie de timpul dat
				 * umiditatea va fi inserata in intervalul de timp dat de pozitia poz
				 */
				long dif = ref - time;
				float pozf = 24 - ((float)dif / 3600);
				int poz = (int)pozf;
				int poz_sort = 0;
				/***
				 * inserez umiditatea pe pozitia poz_sort astfel incat umiditatile
				 * din intervalul de timp sa fie sortate
				 */
				for (int k = 0; k < rooms.get(i).hoursH.get(poz).size(); k++) {
					if (humidity == rooms.get(i).hoursH.get(poz).get(k).getHumidity()) {
						return;
						/***
						 * daca umiditatea este inregistrata deja in acelasi interval 
						 * nu o mai adaug
						 */
					}
					if (humidity < rooms.get(i).hoursH.get(poz).get(k).getHumidity()) {
						poz_sort = k;
						break;
					} else {
						poz_sort = k + 1;
					}
				}
				rooms.get(i).hoursH.get(poz).add(poz_sort, new DeviceH(humidity, new Timestamp(time * 1000)));
				break;
			}
		}
	}
	
	
	/***
	 * calculez temperatura medie a casei ca medie ponderata intre temperaturile minime
	 * inregistrate in ultimul interval orar
	 */
	public void triggerHeat() {
		double min = 0, sum = 0, medie = 0;
		int p = 0; // p = pondere
		for (int i = 0; i < rooms.size(); i++) {
			for (int j = rooms.get(i).hours.size() - 1; j >= 0; j--) {
				/***
				 * caut cel mai recent interval in care s-a inregistrat o temperatura
				 */
				if (rooms.get(i).hours.get(j).size() > 0) {
					min = rooms.get(i).hours.get(j).get(0).getTemperature();
					sum += min * rooms.get(i).getArea();
					p += rooms.get(i).getArea();
					j = -1;
				}
			}
		}
		
		if (getIsH() == 1) { /*daca se ia in considerare umiditatea*/
			double max = 0, sumH = 0, medieH = 0;
			int pHum = 0;
			for (int i = 0; i < rooms.size(); i++) {
				for (int j = rooms.get(i).hoursH.size() - 1; j >= 0; j--) {
					if (rooms.get(i).hoursH.get(j).size() > 0) {
						int lastHum = rooms.get(i).hoursH.get(j).size() - 1;
						max = rooms.get(i).hoursH.get(j).get(lastHum).getHumidity();
						sumH += max * rooms.get(i).getArea();
						pHum += rooms.get(i).getArea();
						j = -1;
					}
				}
			}
			
			medieH = sumH / pHum;
			if (medieH > this.globalHumidity) {
				System.out.println("NO");
				return;
			}
		}
		
		medie = sum / p;
		if (medie < this.globalTemperature) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}
	
	/***
	 * 
	 * @param roomName - camera in care se face listarea temperaturilor
	 * @param startInterval - ora de start de la care se incepe listarea
	 * @param stopInterval - ora de sfarsit la care se termina listarea
	 */
	public void list(String roomName, long startInterval, long stopInterval) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getRoomName().equals(roomName)) {
				System.out.print(roomName);
				for (int j =  rooms.get(i).hours.size() - 1; j >= 0; j--) {
					for (int k = 0; k < rooms.get(i).hours.get(j).size(); k++) {
						if (rooms.get(i).hours.get(j).get(k).getTimeRef().getTime() >= startInterval * 1000 &&
								rooms.get(i).hours.get(j).get(k).getTimeRef().getTime() <= stopInterval * 1000) {
							System.out.printf(" %.2f", rooms.get(i).hours.get(j).get(k).getTemperature());
						}
					}
				}
				break;
			}
		}
	}
}
