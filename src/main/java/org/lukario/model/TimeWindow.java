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

    public Income income(Double amount) {
        return new Income(amount * ratio);
    }
}
