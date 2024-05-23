package be.pxl.parking.domain;

import jakarta.persistence.*;

@Entity
public class LicensePlate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String plateNumber;

    public LicensePlate(User user, String plateNumber) {
        this.user = user;
        this.plateNumber = plateNumber;
    }

    public LicensePlate() {
        // JPA only
    }

    public User getUser() {
        return user;
    }

    public String getPlateNumber() {
        return plateNumber;
    }
}
