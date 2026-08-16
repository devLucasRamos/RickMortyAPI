package com.devLucasRamos.RickMortyAPI.data.model;

import java.util.List;

public class Character {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String image;
    private Origin origin;
    private Location location;
    private List<String> episode;

    public int getId() {
        return id;
    }

    public String getName() {
        return getValueOrDefault(name, "unknown");
    }

    public String getStatus() {
        return getValueOrDefault(status, "unknown");
    }

    public String getSpecies() {
        return getValueOrDefault(species, "unknown");
    }

    public String getType() {
        return getValueOrDefault(type, "");
    }

    public String getGender() {
        return getValueOrDefault(gender, "unknown");
    }

    public String getImage() {
        return image;
    }

    public String getOriginName() {
        if (origin == null) {
            return "unknown";
        }
        return getValueOrDefault(origin.getName(), "unknown");
    }

    public String getLocationName() {
        if (location == null) {
            return "unknown";
        }
        return getValueOrDefault(location.getName(), "unknown");
    }

    public int getEpisodeCount() {
        return episode != null ? episode.size() : 0;
    }

    private String getValueOrDefault(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    public static class Origin {
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class Location {
        private String name;

        public String getName() {
            return name;
        }
    }
}