package be.pxl.parking.service;

import be.pxl.parking.api.input.ParkingSessionStartCommand;
import be.pxl.parking.api.input.ParkingSessionStopCommand;
import be.pxl.parking.api.output.ParkingSessionDto;
import be.pxl.parking.config.SystemClock;
import be.pxl.parking.domain.LicensePlate;
import be.pxl.parking.domain.Parking;
import be.pxl.parking.domain.ParkingSession;
import be.pxl.parking.domain.ParkingSessionStatus;
import be.pxl.parking.exception.ResourceNotFoundException;
import be.pxl.parking.external.SmsService;
import be.pxl.parking.repository.LicensePlateRepository;
import be.pxl.parking.repository.ParkingRepository;
import be.pxl.parking.repository.ParkingSessionRepository;
import be.pxl.parking.servlet.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingService {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
    private final ParkingRepository parkingRepository;
    private final LicensePlateRepository licensePlateRepository;
    private final SystemClock systemClock;
    private final ParkingSessionRepository parkingSessionRepository;
    private final SmsService smsService;

    public ParkingService(ParkingRepository parkingRepository,
                          LicensePlateRepository licensePlateRepository,
                          SystemClock systemClock,
                          ParkingSessionRepository parkingSessionRepository,
                          SmsService smsService) {
        this.parkingRepository = parkingRepository;
        this.licensePlateRepository = licensePlateRepository;
        this.systemClock = systemClock;
        this.parkingSessionRepository = parkingSessionRepository;
        this.smsService = smsService;
    }

    public List<UUID> getParkingsUUID() {
        return parkingRepository.findAll().stream().map(Parking::getUuid).toList();
    }

    @Transactional
    public void startParkingSession(ParkingSessionStartCommand command) {
        LocalDateTime now = systemClock.getCurrentTime();
        LicensePlate licensePlate = licensePlateRepository.findLicensePlateByPlateNumber(command.licensePlate()).orElseThrow(() -> new ResourceNotFoundException("LicensePlate", "licensePlate", command.licensePlate()));
        Parking parking = parkingRepository.findById(command.parking()).orElseThrow(() -> new ResourceNotFoundException("Parking", "uuid", command.parking().toString()));
        ParkingSession parkingSession = new ParkingSession(licensePlate, parking, now);
        parkingSessionRepository.save(parkingSession);
        smsService.sendSms(licensePlate.getUser().getContactInfo().getPhoneNumber(), "Parking session start for " + command.licensePlate() + " at " + DATE_TIME_FORMAT.format(now));
    }

    @Transactional
    public void stopParkingSession(ParkingSessionStopCommand command) {
        LocalDateTime now = systemClock.getCurrentTime();
        ParkingSession parkingSession = parkingSessionRepository.findParkingSessionByLicensePlate_PlateNumberAndStatus(command.licensePlate(), ParkingSessionStatus.STARTED)
                .orElseThrow(() -> new ResourceNotFoundException("parking session started", "licensePlate", command.licensePlate()));
        parkingSession.endSession(now);
    }


    public Page<ParkingSessionDto> findParkingSessions(Filter filter, Pageable pageable) {

        Page<ParkingSession> all = parkingSessionRepository.findAll(createParkingSessionSpecification(filter), pageable);
        return all.map(p -> new ParkingSessionDto(p.getParking().getName(), p.getLicensePlate().getPlateNumber(), p.getStart(), p.getEnd(), p.getPrice(), p.getStatus()));

    }

    private Specification<ParkingSession> createParkingSessionSpecification(Filter filter) {
        return new Specification<ParkingSession>() {
            @Override
            public Predicate toPredicate(Root<ParkingSession> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> allPredicates = new ArrayList<>();
                if (filter.licensePlate() != null) {
                    allPredicates.add(builder.like(builder.lower(root.join("licensePlate").get("licensePlate")), filter.licensePlate()));
                }
                if (filter.parkingName() != null) {
                    allPredicates.add(builder.equal(root.join("parking").get("name"), filter.parkingName()));
                }
                if (filter.parkingSessionStatus() != null) {
                    allPredicates.add(builder.equal(root.get("status"), filter.parkingSessionStatus()));
                }
                return builder.and(allPredicates.toArray(new Predicate[0]));
            }
        };
    }

}
