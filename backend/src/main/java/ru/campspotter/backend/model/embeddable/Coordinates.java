package ru.campspotter.backend.model.embeddable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Represents geographical coordinates of a location.
 * Used as an embedded value object.
 * <p>
 * Coordinates are stored as latitude and longitude
 * and can later be replaced with PostGIS geometry type.
 */
@Embeddable
public class Coordinates {

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    protected Coordinates() {
        // Required by JPA
    }

    public Coordinates(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

}
