package be.pxl.parking.repository;

import be.pxl.parking.domain.LicensePlate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicensePlateRepository extends JpaRepository<LicensePlate, Long> {

    Optional<LicensePlate> findLicensePlateByPlateNumber(String licensePlate);
}
