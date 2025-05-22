package be.pxl.parking.builder;

import be.pxl.parking.domain.Parking;

import java.util.UUID;

public final class ParkingBuilder {
    private double weekendHourlyRate;
    private double weekdayHourlyRate;
    private double dailyRate;
    private UUID uuid;
    private String name;
    private String street;
    private int zipCode;
    private String city;

    private ParkingBuilder() {
    }

    public static ParkingBuilder aParking() {
        return new ParkingBuilder();
    }

    public ParkingBuilder withWeekendHourlyRate(double weekendHourlyRate) {
        this.weekendHourlyRate = weekendHourlyRate;
        return this;
    }

    public ParkingBuilder withWeekdayHourlyRate(double weekdayHourlyRate) {
        this.weekdayHourlyRate = weekdayHourlyRate;
        return this;
    }

    public ParkingBuilder withDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
        return this;
    }

    public ParkingBuilder withUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public ParkingBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ParkingBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public ParkingBuilder withZipCode(int zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public ParkingBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public Parking build() {
        Parking parking = new Parking(uuid, name, street, zipCode, city);
        parking.setWeekendHourlyRate(weekendHourlyRate);
        parking.setWeekdayHourlyRate(weekdayHourlyRate);
        parking.setDailyRate(dailyRate);
        return parking;
    }
}
