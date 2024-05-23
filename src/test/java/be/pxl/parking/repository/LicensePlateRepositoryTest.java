package be.pxl.parking.repository;

import be.pxl.parking.builder.LicensePlateBuilder;
import be.pxl.parking.domain.LicensePlate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class LicensePlateRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LicensePlateRepository licensePlateRepository;

    @BeforeEach
    public void init() {
        LicensePlate licensePlate = LicensePlateBuilder.aLicensePlate().withPlateNumber("2-ABC-123").build();
        licensePlateRepository.save(licensePlate);
        entityManager.flush();
        entityManager.clear();
    }


    @Test
    public void findLicensePlateByPlateNumber() {
        Optional<LicensePlate> licensePlateByPlateNumber = licensePlateRepository.findLicensePlateByPlateNumber("2-ABC-123");
        Assertions.assertTrue(licensePlateByPlateNumber.isPresent());
        Assertions.assertEquals("2-ABC-123", licensePlateByPlateNumber.get().getPlateNumber());
    }

    @Test
    public void findLicensePlateByPlateNumberReturnsOptionEmptyWhenNotAvailable() {
        Optional<LicensePlate> licensePlateByPlateNumber = licensePlateRepository.findLicensePlateByPlateNumber("2-ABC-124");
        Assertions.assertTrue(licensePlateByPlateNumber.isEmpty());
    }
}
