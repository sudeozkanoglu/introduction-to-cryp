package org.app.g201210034.model.request.car;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    private String brand;

    private String model;

    private String plate;

    private String year;

    private String fuelType;

    private String gearType;

    private int maxPassenger;

    private int maxLuggage;

//    @Column(name = "km")
//    private String km;

//    @Column(name = "price")
//    private String price;

    private String description;

    private String imageUrl;

    private Boolean status;

}
