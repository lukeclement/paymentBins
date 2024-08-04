package org.lukario.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum TimeWindow {
    YEARLY(1.),
    MONTHLY(12.),
    WEEKLY(52.);

    private final Double ratio;

    TimeWindow(Double ratio) {
        this.ratio = ratio;
    }

    public Flow flow(Double amount) {
        return new Flow(amount * ratio);
    }

    public static TimeWindow valueOf(Double ratio) throws TimeWindowException {
        Optional<TimeWindow> foundWindow = Arrays.stream(TimeWindow.values())
                .filter(timeWindow -> timeWindow.getRatio().equals(ratio))
                .findFirst();
        return foundWindow.orElseThrow(() -> new TimeWindowException("No window exists for ratio " + ratio));
    }
}
