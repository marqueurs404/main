package seedu.address.stubs;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.internal.gmaps.ProcessVenues;
import seedu.address.model.gmaps.Location;

public class ProcessVenuesStub extends ProcessVenues {
    public ProcessVenuesStub() {
        super();
    }

    @Override
    public ArrayList<Location> getLocations() {
        Location location1 = new Location("Foo");
        location1.setGoogleRecognisedLocation("NUS_FOO");
        Location location2 = new Location("Foo-1234");
        location2.setGoogleRecognisedLocation("NUS_FOO");
        Location location3 = new Location("Foo_1234");
        location3.setGoogleRecognisedLocation("NUS_FOO");
        return new ArrayList<Location>(Arrays.asList(location1, location2, location3));
    }

    @Override
    public ArrayList<String> getGmapsRecognisedLocationList() {
        return new ArrayList(Arrays.asList("NUS_FOO","NUS_BAR", "NUS_FOOBAR"));
    }
}
