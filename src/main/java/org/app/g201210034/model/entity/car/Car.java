package org.app.g201210034.model.entity.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "plate")
    private String plate;

    @Column(name = "year")
    private String year;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "gear_type")
    private String gearType;

//    @Column(name = "km")
//    private String km;

//    @Column(name = "price")
//    private String price;

    @Column(name = "description")
    private String description;

    @Column(name = "max_passenger")
    private int maxPassenger;

    @Column(name = "max_luggage")
    private int maxLuggage;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status")
    private Boolean status;

}
