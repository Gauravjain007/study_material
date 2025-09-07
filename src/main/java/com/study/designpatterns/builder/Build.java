package com.study.designpatterns.builder;

public class Build {
    public static void main(String[] args) {
        House house1 = House.HouseBuilder.builder()
                .foundation("Concrete")
                .structure("Brick")
                .roof("Tile")
                .addGarage()
                .build();

        System.out.println("House1: " + house1);

        House house2 = House.HouseBuilder
                .builder("Cement", "Brick", "Wooden")
                .interior("Modern")
                .addGarden()
                .addPool()
                .build();

        System.out.println("House2: " + house2);

        UserDetails user = UserDetails.UserDetailsBuilder
                .addUser("John", "Doe")
                .age(30)
                .email("a5n8o@example.com")
                .phoneNumber("+91 123-456-7890")
                .address("123 Main St, Anytown, USA")
                .occupation("Engineer")
                .build();

        System.out.println("User: " + user);
    }
}
