package be.pxl.parking.servlet;

import be.pxl.parking.domain.ParkingSessionStatus;

public record Filter(ParkingSessionStatus parkingSessionStatus, String licensePlate, String parkingName) {
}
