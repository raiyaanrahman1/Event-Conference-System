package UseCase.Filter;

import Entity.Event;

import java.util.ArrayList;
import java.util.List;

public class SpeakerFilter implements EventFilter {
    @Override
    public List<Event> filter(List<Event> events, String by) {
        List<Event> filtered = new ArrayList<>();

        for(Event event: events) {
            if (event.getSpeaker().contains(by)) {
                filtered.add(event);
            }
        }

        return filtered;
    }
}
