package UseCase.Filter;

import Entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateFilter implements EventFilter {
    DateTimeFormatter formatter;

    public DateFilter() {
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    }

    @Override
    public List<Event> filter(List<Event> events, String by) {
        List<Event> filtered = new ArrayList<>();

        LocalDate date = LocalDate.parse(by, this.formatter);

        LocalDateTime day = date.atStartOfDay();
        LocalDateTime dayAfter = day.plusDays(1);

        for (Event event: events) {
            LocalDateTime dateTime = event.getDateTime();
            if (dateTime.isAfter(day) && dateTime.isBefore(dayAfter)) {
                filtered.add(event);
            }
        }

        return filtered;
    }
}
