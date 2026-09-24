package co.wethinkcode.logisticsconnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HubCsvReader {

    private final HubCleaner cleaner = new HubCleaner();

    public List<Hub> readHubs() {
        List<Hub> hubs = new ArrayList<>();
        Set<String> seenIds = new HashSet<>();

        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("hubs-global.csv");

        if (inputStream == null) {
            throw new RuntimeException("Could not find hubs-global.csv");
        }

        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(",", -1);

                String id = cleaner.cleanId(values[0]);
                String province = cleaner.cleanProvince(values[1]);
                String sortingCenter = cleaner.cleanSortingCenter(values[2]);
                Boolean active = cleaner.cleanActiveFlag(values[3]);

                if (id == null || seenIds.contains(id)) {
                    continue;
                }

                seenIds.add(id);

                hubs.add(new Hub(
                        id,
                        province,
                        sortingCenter,
                        active
                ));
            }

            return hubs;
        } catch (IOException e) {
            throw new RuntimeException("Could not read hubs-global.csv", e);
        }
    }
}
