package ruiseki.jfi.jfmuy.bigreactors.fuel;

import java.util.Collections;
import java.util.List;

public class FuelEntry {

    private final List<String> fuel;

    private final List<String> waste;

    public FuelEntry(String fuel, String waste) {
        this(Collections.singletonList(fuel), Collections.singletonList(waste));
    }

    public FuelEntry(List<String> fuel, List<String> waste) {
        this.fuel = fuel;
        this.waste = waste;
    }

    public List<String> getFuel() {
        return fuel;
    }

    public List<String> getWaste() {
        return waste;
    }
}
