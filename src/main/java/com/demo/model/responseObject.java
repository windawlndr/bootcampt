package com.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class responseObject {
    /*
     * {
        "id": 521,
        "name": "Ipad Winda",
        "data": {
            "year": "2022",
            "price": 599,
            "cpu_model": "Chipset A13",
            "hard_disk_size": "64GB",
            "color": "Silver",
            "capacity": "1",
            "screen_size": "10.9 Inch"
        }
    }
     */
@JsonProperty
public int id;

@JsonProperty
public String name;

@JsonProperty
public Data data;

public static class Data {
    @JsonProperty
    public String year;

    // Constructor for Data
    public Data() {}

    @JsonProperty
    public double price;

    @JsonProperty
    public String cpu_model;

    @JsonProperty
    public String hard_disk_size;

    @JsonProperty
    public String color;

    @JsonProperty
    public String capacity;

    @JsonProperty
    public String screen_size;

}

// Constructor for responseObject
public responseObject() {};




}
