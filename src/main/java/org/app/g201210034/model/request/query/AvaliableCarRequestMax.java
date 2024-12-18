package org.app.g201210034.model.request.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvaliableCarRequestMax {

    int luggageCount;
    int passengerCount;
    LocalDateTime startDate;
    LocalDateTime endDate;

}
