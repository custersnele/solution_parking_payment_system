package be.pxl.parking.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Parking {

    @Id
    private UUID uuid;
    private String name;
    private String street;
    private int zipCode;
    private String city;
    private double weekendHourlyRate;
    private double weekdayHourlyRate;
    private double dailyRate;

    public Parking() {
        // JPA only
    }

    public Parking(UUID uuid, String name, String street, int zipCode, String city) {
        this.uuid = uuid;
        this.name = name;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setWeekdayHourlyRate(double weekdayHourlyRate) {
        this.weekdayHourlyRate = weekdayHourlyRate;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setWeekendHourlyRate(double weekendHourlyRate) {
        this.weekendHourlyRate = weekendHourlyRate;
    }

    public double getWeekendHourlyRate() {
        return weekendHourlyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public double getWeekdayHourlyRate() {
        return weekdayHourlyRate;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
