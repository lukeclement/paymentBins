package org.lukario.model;

import lombok.Getter;

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
}
