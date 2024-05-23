package be.pxl.parking.api.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ParkingSessionStartCommand(@NotBlank String licensePlate,
                                         @NotNull UUID parking) {
}
