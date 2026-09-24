package co.wethinkcode.logisticsconnect;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HubCsvReaderTest {

    private final HubCsvReader reader = new HubCsvReader();

    @Test
    void shouldReadCleanedHubs() {
        List<Hub> hubs = reader.readHubs();

        assertFalse(hubs.isEmpty());
    }

    @Test
    void shouldCleanHubValues() {
        List<Hub> hubs = reader.readHubs();

        Hub capeTown = hubs.stream()
                .filter(hub -> hub.getId().equals("H-501"))
                .findFirst()
                .orElseThrow();

        assertEquals("Western Cape", capeTown.getProvince());
        assertEquals("Cape Town Port", capeTown.getSortingCenter());
        assertTrue(capeTown.getActive());
    }

    @Test
    void shouldNormaliseProvinceVariants() {
        List<Hub> hubs = reader.readHubs();

        Hub durban = hubs.stream()
                .filter(hub -> hub.getSortingCenter().equals("Durban Harbour"))
                .findFirst()
                .orElseThrow();

        assertEquals("KwaZulu-Natal", durban.getProvince());
    }

    @Test
    void shouldHandleMissingAndUnknownValues() {
        List<Hub> hubs = reader.readHubs();

        Hub polokwane = hubs.stream()
                .filter(hub -> hub.getId().equals("H-511"))
                .findFirst()
                .orElseThrow();

        assertNull(polokwane.getActive());
    }

    @Test
    void shouldCollapseDuplicateSortingCenters() {
        List<Hub> hubs = reader.readHubs();

        long johannesburgCount = hubs.stream()
                .filter(hub -> hub.getSortingCenter().equals("Johannesburg Central"))
                .count();

        assertEquals(1, johannesburgCount);
    }
}