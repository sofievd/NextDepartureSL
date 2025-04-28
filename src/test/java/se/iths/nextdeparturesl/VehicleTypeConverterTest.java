package se.iths.nextdeparturesl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTypeConverterTest {
    private VehicleTypeConverter vehicleTypeConverter;

    @BeforeEach
    void setUp() {
        vehicleTypeConverter = new VehicleTypeConverter();
    }

    @Test
    void convert() {
        assertEquals(vehicleTypeConverter.convert("100"),"Railway");
        assertNotEquals(vehicleTypeConverter.convert("100"), "Metro");
    }
}