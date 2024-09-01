package org.lukario.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.lukario.model.TimeWindow.Window.*;

public class TimeWindowTest {
    @Test
    void givenARatioICanGetAWindow() throws TimeWindowException {
        Double ratio = 1.0;
        assertEquals(TimeWindow.valueOf(ratio), new TimeWindow(YEARLY, 1.));
    }


    static Stream<Arguments> ratioAmounts() {
        return Stream.of(
                arguments(1., new TimeWindow(YEARLY, 1.)),
                arguments(0.2, new TimeWindow(YEARLY, 5.)),
                arguments(12., new TimeWindow(MONTHLY, 1.)),
                arguments(12./5., new TimeWindow(MONTHLY, 5.)),
                arguments(52., new TimeWindow(WEEKLY, 1.)),
                arguments(52./23., new TimeWindow(WEEKLY, 23.)),
                arguments(69./24., new TimeWindow(WEEKLY, 416./23.))
        );
    }


    @ParameterizedTest
    @MethodSource("ratioAmounts")
    void givenARatioICanGetATimeWindow(Double ratio, TimeWindow expected) {
        assertEquals(expected, TimeWindow.valueOf(ratio));
    }
}
