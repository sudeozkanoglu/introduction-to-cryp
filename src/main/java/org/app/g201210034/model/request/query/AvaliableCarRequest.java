package org.app.g201210034.model.request.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvaliableCarRequest {

    int luggageCount;
    int passengerCount;
}
