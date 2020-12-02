package UseCase.Filter;

import Entity.Event;

import java.util.List;

/**
 * A filter that does nothing.
 */
public class NullFilter implements EventFilter {

    @Override
    public List<Event> filter(List<Event> events, String by) {
        return events;
    }
}
