package com.deity.location.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "neighborhood_manizales")
public class Neighborhood {

    @Id
    private String id; // MongoDB utiliza cadenas para los IDs

    @Field("FID")
    private Long fid;

    @Field("OBJECTID_1")
    private Long objectid1;

    @Field("BARRIO")
    private String barrio;

    @Field("X")
    private Double x;

    @Field("Y")
    private Double y;

    @Field("NOMBRE_JUN")
    private String nombreJun;

    @Field("nro")
    private Long nro;

    @Field("COMUNA")
    private String comuna;

    @Field("Shape_Leng")
    private Double shapeLeng;

    @Field("Shape_Area")
    private Double shapeArea;

    @Field("Shape__Area")
    private Double neighborhoodShapeArea;

    @Field("Shape__Length")
    private Double shapeLength;

    @Field("city")
    private Long city;
}
