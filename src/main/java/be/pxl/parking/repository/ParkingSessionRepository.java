package be.pxl.parking.repository;

import be.pxl.parking.domain.ParkingSession;
import be.pxl.parking.domain.ParkingSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long>, JpaSpecificationExecutor<ParkingSession> {

    Optional<ParkingSession> findParkingSessionByLicensePlate_PlateNumberAndStatus(String licensePlate, ParkingSessionStatus status);

    List<ParkingSession> findParkingSessionsByStatus(ParkingSessionStatus parkingSessionStatus);
}
