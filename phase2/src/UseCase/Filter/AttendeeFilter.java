package UseCase.Filter;

import Entity.Event;

import java.util.ArrayList;
import java.util.List;

public class AttendeeFilter implements EventFilter {
    @Override
    public List<Event> filter(List<Event> events, String by) {
        List<Event> filtered = new ArrayList<>();

        for (Event event: events) {
            if (event.getAttendees().contains(by)) {
                filtered.add(event);
            }
        }

        return filtered;
    }
}
