package be.pxl.parking.repository;

import be.pxl.parking.domain.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {
}
