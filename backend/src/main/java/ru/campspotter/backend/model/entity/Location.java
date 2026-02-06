package ru.campspotter.backend.model.entity;


import jakarta.persistence.*;
import ru.campspotter.backend.model.embeddable.Coordinates;
import ru.campspotter.backend.model.enums.LocationSource;
import ru.campspotter.backend.model.enums.TerrainType;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a physical camping location.
 * <p>
 * A location belongs to a specific post and describes
 * a point on a route or a camping spot.
 * <p>
 * Contains geographical data, terrain characteristics,
 * and metadata required for map-based search.
 */
@Entity
@Table(name = "locations")
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Geographical coordinates of the location.
     * Implemented as embeddable value object.
     */
    @Embedded
    private Coordinates coordinates;

    /**
     * Human-readable name of the location.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Detailed description of the camping spot.
     */
    @Column(length = 2000)
    private String description;

    /**
     * Optional region or area name.
     */
    private String region;

    /**
     * Elevation above sea level (in meters).
     */
    private Integer elevation;

    /**
     * Terrain types of the location.
     * A location may have multiple terrain characteristics.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "location_terrain_types",
            joinColumns = @JoinColumn(name = "location_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "terrain_type")
    private Set<TerrainType> terrainTypes = new HashSet<>();

    /**
     * Source of the location data (user-generated or official).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationSource source;

    /**
     * Order of the location within a route or post.
     */
    @Column(nullable = false)
    private Integer orderIndex;


    protected Location() {
        // Required by JPA
    }

    public Location(
            Coordinates coordinates,
            String name,
            LocationSource source,
            Integer orderIndex
    ) {
        this.coordinates = coordinates;
        this.name = name;
        this.source = source;
        this.orderIndex = orderIndex;
    }

    // Getters (без сеттеров — хорошая практика для entity)

    public Long getId() {
        return id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRegion() {
        return region;
    }

    public Integer getElevation() {
        return elevation;
    }

    public Set<TerrainType> getTerrainTypes() {
        return terrainTypes;
    }

    public LocationSource getSource() {
        return source;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }



}
