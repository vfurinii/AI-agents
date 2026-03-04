package com.camarim.agent.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "camarim")
public class CamarimProperties {

    private Store store = new Store();
    private Assistant assistant = new Assistant();
    private List<Service> services = new ArrayList<>();

    @Data
    public static class Store {
        private String name;
        private String location;
        private String instagram;
    }

    @Data
    public static class Assistant {
        private String tone;
        private String style;
    }

    @Data
    public static class Service {
        private String name;
        private Double price;
        private String description;
    }
}

