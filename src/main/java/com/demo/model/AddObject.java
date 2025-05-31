package com.demo.model;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
public class AddObject {

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private ProductData data;

    @Data
    public static class ProductData {
        @JsonProperty("year")
        private int year;

        @JsonProperty("price")
        private int price;

        @JsonProperty("cpu_model")
        private String cpuModel;

        @JsonProperty("hard_disk_size")
        private String hardDiskSize;

        @JsonProperty("capacity")
        private String capacity;

        @JsonProperty("screen_size")
        private String screenSize;

        @JsonProperty("color")
        private String color;
    }
}
