package be.pxl.parking.domain;

import be.pxl.parking.util.ParkingSessionPriceCalculator;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ParkingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private LicensePlate licensePlate;
    @ManyToOne
    private Parking parking;

    @Column(name = "session_start")
    private LocalDateTime start;

    @Column(name = "session_end")
    private LocalDateTime end;
    private double price;

    @Enumerated(EnumType.STRING)
    private ParkingSessionStatus status;

    public ParkingSession(LicensePlate licensePlate, Parking parking, LocalDateTime start) {
        this.licensePlate = licensePlate;
        this.parking = parking;
        this.start = start;
        this.status = ParkingSessionStatus.STARTED;
    }

    public ParkingSession() {
        // JPA only
    }

    public void endSession(LocalDateTime end) {
        if (status == ParkingSessionStatus.STARTED) {
            this.end = end;
            this.status = ParkingSessionStatus.ENDED;
            this.price = ParkingSessionPriceCalculator.calculatePrice(this);
        }
    }

    public void setPaymentRequested() {
        if (status == ParkingSessionStatus.ENDED) {
            this.status = ParkingSessionStatus.PAYMENT_REQUESTED;
        }
    }

    public Parking getParking() {
        return parking;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public double getPrice() {
        return price;
    }

    public ParkingSessionStatus getStatus() {
        return status;
    }
}
