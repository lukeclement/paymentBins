package org.lukario.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum TimeWindow {
    YEARLY(1),
    MONTHLY(12),
    WEEKLY(52);

    private final Integer ratio;

    TimeWindow(Integer ratio) {
        this.ratio = ratio;
    }

    public Flow flow(Double amount) {
        return new Flow(amount * ratio);
    }

    public static TimeWindow findWindow(Double ratio) {
        //TODO: test! Make a better exception too
        int ratioAsInt = ratio.intValue();
        if (!Double.valueOf(ratioAsInt).equals(ratio)) {
            throw new UnsupportedOperationException(); //TODO better exception
        }
        Integer targetRatio = ratioAsInt;
        Optional<TimeWindow> foundWindow = Arrays.stream(TimeWindow.values())
                .filter(timeWindow -> timeWindow.getRatio().equals(targetRatio))
                .findFirst();
        return foundWindow.orElseThrow(UnsupportedOperationException::new); //TODO better exception
    }
}
