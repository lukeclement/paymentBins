package org.lukario.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.lukario.model.Bucket;
import org.lukario.model.TimeWindow;

import java.util.Arrays;

@Jacksonized
@Builder
@Value
public class BucketDto implements Dto{
    String name;
    Double amount;
    TimeWindowDto paymentRate;
    TimeWindowDto resetRate;
    Double target;

    //TODO: hypermedia
}
