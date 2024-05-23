package be.pxl.parking.builder;

import be.pxl.parking.domain.LicensePlate;
import be.pxl.parking.domain.User;

public final class LicensePlateBuilder {
    private User user;
    private String plateNumber;

    private LicensePlateBuilder() {
    }

    public static LicensePlateBuilder aLicensePlate() {
        return new LicensePlateBuilder();
    }

    public LicensePlateBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public LicensePlateBuilder withPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public LicensePlate build() {
        return new LicensePlate(user, plateNumber);
    }
}
