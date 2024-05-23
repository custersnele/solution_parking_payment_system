package be.pxl.parking.api.output;

import be.pxl.parking.domain.ParkingSessionStatus;

import java.time.LocalDateTime;

public record ParkingSessionDto(String parking,
                                String licensePlate,
                                LocalDateTime start,
                                LocalDateTime end,
                                double price,
                                ParkingSessionStatus status) {
}
