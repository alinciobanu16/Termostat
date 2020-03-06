import java.io.*;
import java.util.Scanner;
import java.sql.Timestamp;
/***
 * 
 * @author alin_matei.ciobanu
 * Ciobanu Alin-Matei 325CB
 *
 */
public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner input = new Scanner("./therm.in");
		File file  = new File(input.nextLine());
		input = new Scanner(file);
        PrintStream o = new PrintStream(new File("./therm.out")); 
        System.setOut(o);

		House house = new House();
		int nrOfRooms = 0, index = 0;
		long ref = 0;
		double globalTemp = 0, globalHum = 0;
		Timestamp timeRef = new Timestamp(0);
		String firstLine = input.nextLine();
		String firstLine2[] = firstLine.split(" ");
		if (firstLine2.length == 3) {
			/***
			 * nu am umiditate
			 */
			House.setIsH(0);
			nrOfRooms = Integer.parseInt(firstLine2[0]);
			house.setNrOfRooms(nrOfRooms);
			globalTemp = Double.parseDouble(firstLine2[1]);
			house.setGlobalTemperature(globalTemp);
			ref = Integer.parseInt(firstLine2[2]);
			timeRef = new Timestamp(ref * 1000);
			house.setTimeRef(timeRef);
		}
		
		if (firstLine2.length == 4) {
			House.setIsH(1); /*setez indicele care imi spune ca se ia in considerare umiditatea*/
			nrOfRooms = Integer.parseInt(firstLine2[0]);
			house.setNrOfRooms(nrOfRooms);
			globalTemp = Double.parseDouble(firstLine2[1]);
			house.setGlobalTemperature(globalTemp);
			globalHum = Double.parseDouble(firstLine2[2]);
			house.setGlobalHumidity(globalHum);
			ref = Integer.parseInt(firstLine2[3]);
			timeRef = new Timestamp(ref * 1000);
			house.setTimeRef(timeRef);
		}
		
		while (input.hasNextLine()) {
			index++;
			String line = input.nextLine();
			String line2[] = line.split(" ");
			house.addRoom(line2[0], line2[1], Integer.parseInt(line2[2]));
			if (index == house.getNrOfRooms()) {
				break;
			}
		}
		
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String line2[] = line.split(" ");
			
			if (line2[0].equals("OBSERVE")) {
				if (Integer.parseInt(line2[2]) < ref) {
					for (int i = 0; i < index; i++) {
						if (house.rooms.get(i).getDeviceId().equals(line2[1])) {
							String deviceId = line2[1];
							long time = Integer.parseInt(line2[2]);
							double temperature = Double.parseDouble(line2[3]);
							house.addTemperature(deviceId, time, temperature, ref);
						}
					}
				}
			}
			
			if (line2[0].equals("OBSERVEH")) {
				if (Integer.parseInt(line2[2]) < ref) {
					for (int i = 0; i < index; i++) {
						if (house.rooms.get(i).getDeviceId().equals(line2[1])) {
							String deviceId = line2[1];
							long time = Integer.parseInt(line2[2]);
							double humidity = Double.parseDouble(line2[3]);
							house.addHumidity(deviceId, time, humidity, ref);
						}
					}
				}
			}
			
			if (line2[0].equals("TEMPERATURE")) {
				house.setGlobalTemperature(Double.parseDouble(line2[1]));
			}
			
			if (line2[0].equals("TRIGGER")) {
				house.triggerHeat();
			}
			
			if (line2[0].equals("LIST")) {
				String roomName = line2[1];
				long startInterval = Integer.parseInt(line2[2]);
				long stopInterval = Integer.parseInt(line2[3]);
				house.list(roomName, startInterval, stopInterval);
				System.out.println();
			}
		}
		
		input.close();
		o.close();
		
	}
}
