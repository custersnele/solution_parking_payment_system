package be.pxl.parking.api;

import be.pxl.parking.api.input.ParkingSessionStartCommand;
import be.pxl.parking.api.input.ParkingSessionStopCommand;
import be.pxl.parking.service.ParkingService;
import com.sun.jdi.VoidValue;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/parkings")
    public ResponseEntity<List<UUID>> getParkingsUUIDs() {
       return ResponseEntity.ok(parkingService.getParkingsUUID());

    }

    @PostMapping("/parking/start")
    public ResponseEntity<Void> startParkingSession(@RequestBody @Valid ParkingSessionStartCommand command) {
        parkingService.startParkingSession(command);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/parking/stop")
    public void stopParkingSession(@RequestBody @Valid ParkingSessionStopCommand command) {
        parkingService.stopParkingSession(command);
    }
}
