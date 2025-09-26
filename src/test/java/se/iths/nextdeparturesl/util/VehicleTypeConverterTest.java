package se.iths.nextdeparturesl.util;

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
        assertEquals("Railway",vehicleTypeConverter.convert("100"));
        assertNotEquals("Metro", vehicleTypeConverter.convert("100"));
    }
}