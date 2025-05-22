package be.pxl.parking.serivce;

import be.pxl.parking.api.input.ParkingSessionStartCommand;
import be.pxl.parking.builder.*;
import be.pxl.parking.config.SystemClock;
import be.pxl.parking.domain.*;
import be.pxl.parking.exception.ResourceNotFoundException;
import be.pxl.parking.external.SmsService;
import be.pxl.parking.repository.LicensePlateRepository;
import be.pxl.parking.repository.ParkingRepository;
import be.pxl.parking.repository.ParkingSessionRepository;
import be.pxl.parking.service.ParkingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceStartParkingSessionTest {

    @Mock
    private SystemClock systemClock;

    @Mock
    private LicensePlateRepository licensePlateRepository;

    @Mock
    private ParkingSessionRepository parkingSessionRepository;

    @Mock
    private ParkingRepository parkingRepository;

    @Mock
    private SmsService smsService;

    @InjectMocks
    private ParkingService parkingService;

    @Test
    void startParkingSession_shouldThrowException_whenLicensePlateNotFound() {
        // Arrange
        LocalDateTime now = LocalDateTime.of(2025, 5, 23, 12, 0);
        String plateNumber = "ABC123";
        UUID parkingId = UUID.randomUUID();

        ParkingSessionStartCommand command = ParkingSessionStartCommandBuilder.aParkingSessionStartCommand()
                .withParking(parkingId)
                .withLicensePlate(plateNumber)
                .build();
        when(systemClock.getCurrentTime()).thenReturn(now);
        when(licensePlateRepository.findLicensePlateByPlateNumber(plateNumber)).thenReturn(Optional.empty());

        // Act
        assertThrows(ResourceNotFoundException.class, () -> parkingService.startParkingSession(command));

        // Assert
        Mockito.verifyNoInteractions(parkingSessionRepository);
    }

    @Test
    void startParkingSession_shouldSaveParkingSessionAndSendSms_whenValidCommand() {
        // Arrange
        LocalDateTime now = LocalDateTime.of(2025, 5, 23, 12, 0);
        String plateNumber = "ABC123";
        UUID parkingId = UUID.randomUUID();

        User user = UserBuilder.aUser().withFirstName("Nele").withContactInfo(ContactInfoBuilder.aContactInfo().withPhoneNumber("123456789").build()).build();
        LicensePlate licensePlate = LicensePlateBuilder.aLicensePlate().withPlateNumber(plateNumber).withUser(user).build();
        Parking parking = ParkingBuilder.aParking().withUuid(parkingId).build();
        ParkingSessionStartCommand command = new ParkingSessionStartCommand(plateNumber, parkingId);

        when(systemClock.getCurrentTime()).thenReturn(now);
        when(licensePlateRepository.findLicensePlateByPlateNumber(plateNumber)).thenReturn(Optional.of(licensePlate));
        when(parkingRepository.findById(parkingId)).thenReturn(Optional.of(parking));

        // Act
        parkingService.startParkingSession(command);

        // Assert
        verify(parkingSessionRepository).save(argThat(session ->
                session.getLicensePlate().equals(licensePlate) &&
                        session.getParking().equals(parking) &&
                        session.getStart().equals(now) &&
                        session.getStatus().equals(ParkingSessionStatus.STARTED)
        ));

        verify(smsService).sendSms(eq("123456789"), contains("Parking session start for " + plateNumber));
    }



}
