package org.lukario.model.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.lukario.model.TimeWindow;

@Jacksonized
@Builder
@Value
public class TimeWindowDto implements Dto {
    TimeWindow.Window window;
    Double frequency;
}
