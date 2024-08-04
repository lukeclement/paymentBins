package org.lukario.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeWindowTest {
    @Test
    void givenARatioICanGetAWindow() throws TimeWindowException {
        Double ratio = 1.0;
        assertEquals(TimeWindow.valueOf(ratio), TimeWindow.YEARLY);
    }

    @Test
    void givenAnInvalidRatioIGetAnException() {
        Double invalidRatio = 1.5;
        assertThrows(TimeWindowException.class, () -> TimeWindow.valueOf(invalidRatio));
    }
}
