package geo;

import ru.netology.entity.Location;
import ru.netology.geo.GeoService;

public class GeoServiceMock implements GeoService {
    private Location location;

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Location byIp(String ip) {
        return location;
    }

    @Override
    public Location byCoordinates(double latitude, double longitude) {
        throw new RuntimeException("Not implemented");
    }
}
