package se.iths.nextdeparturesl.util;

import java.util.HashMap;
import java.util.Map;

public class VehicleTypeConverter {
    private Map<String, String> vehicleCodeToString = new HashMap<String, String>();

    public VehicleTypeConverter() {
        vehicleCodeToString.put("100", "Railway");
        vehicleCodeToString.put("101", "High Speed Rail");
        vehicleCodeToString.put("102", "Long Distance Rail");
        vehicleCodeToString.put("103", "Inter Regional Rail");
        vehicleCodeToString.put("105", "Sleeper Rail");
        vehicleCodeToString.put("106", "Regional Rai");
        vehicleCodeToString.put("400", "Metro");
        vehicleCodeToString.put("700", "Buss");
        vehicleCodeToString.put("714", "Rail Replacement Bus");
        vehicleCodeToString.put("900", "Tram");
        vehicleCodeToString.put("1000", "Water Transport");
        vehicleCodeToString.put("1501", "Communal Taxi");
    }

    public String convert(String vehicleCode) {
        return vehicleCodeToString.get(vehicleCode);
    }

}
