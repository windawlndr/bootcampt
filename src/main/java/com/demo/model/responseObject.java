package com.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseObject {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private ProductData data;

    @Data
    public static class ProductData {
        @JsonProperty("year")
        private String year;

        @JsonProperty("price")
        private int price;

        @JsonProperty("cpu_model")
        private String cpuModel;

        @JsonProperty("hard_disk_size")
        private String hardDiskSize;

        @JsonProperty("color")
        private String color;

        @JsonProperty("capacity")
        private String capacity;

        @JsonProperty("screen_size")
        private String screenSize;
    }
}
