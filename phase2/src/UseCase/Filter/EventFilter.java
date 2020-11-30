package UseCase.Filter;

import Entity.Event;

import java.util.List;

public interface EventFilter {
    /**
     * Sorts a given list of events in place, i.e mutates it.
     * @param events  the list of events
     * @param by  the string representation for the filter
     */
    List<Event> filter(List<Event> events, String by);
}
