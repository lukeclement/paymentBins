package org.lukario.model;

import lombok.Getter;
import lombok.ToString;
import org.lukario.model.dto.TimeWindowDto;

import java.util.Arrays;

@ToString
@Getter
public class TimeWindow implements Model<TimeWindowDto> {
    private final Double modifier;
    private final Window window;

    public enum Window {
        YEARLY(1.),
        MONTHLY(12.),
        WEEKLY(52.);

        private final Double ratio;

        Window(Double ratio) {
            this.ratio = ratio;
        }

        private Double getModifier(Double source) {
            return ratio / source;
        }

    }
    public Flow flow(Double windowAmount) {
        return new Flow(windowAmount * getRatio());
    }

    public TimeWindow(Window window, Double modifier) {
        this.modifier = modifier;
        this.window = window;
    }

    public static TimeWindow yearly() {
        return TimeWindow.create(Window.YEARLY);
    }

    public static TimeWindow monthly() {
        return TimeWindow.create(Window.MONTHLY);
    }

    public static TimeWindow weekly() {
        return TimeWindow.create(Window.WEEKLY);
    }

    public static TimeWindow create(Window window) {
        return new TimeWindow(window, 1.);
    }


    public static TimeWindow valueOf(Double ratio) {
        Window chosen = Arrays.stream(Window.values())
                .reduce(Window.YEARLY, (a, b) ->
                        {
                            var distanceFromA = getDistanceFromDecimal(ratio, a);
                            var distanceFromB = getDistanceFromDecimal(ratio, b);
                            if (distanceFromA.equals(distanceFromB)) {
                                return a.getModifier(ratio) < b.getModifier(ratio) ? a : b;
                            } else {
                                return distanceFromA > distanceFromB ? a : b;
                            }
                        });
        return new TimeWindow(chosen, chosen.getModifier(ratio));
    }

    private static Double getDistanceFromDecimal(Double ratio, Window window) {
        Double modifier = window.getModifier(ratio);
        int decimal = modifier.intValue();
        double fraction = modifier - decimal;
        return Math.abs(fraction - 0.5);
    }

    public Double getRatio() {
        return window.ratio / modifier;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof TimeWindow comparison) {
            return getRatio().equals(comparison.getRatio()) && window.equals(comparison.window);
        }
        return false;
    }

    @Override
    public TimeWindowDto toDto() {
        return TimeWindowDto.builder()
                .frequency(modifier)
                .window(window)
                .build();
    }
}