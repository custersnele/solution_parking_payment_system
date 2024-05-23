package be.pxl.parking.job;

import be.pxl.parking.domain.ParkingSession;
import be.pxl.parking.domain.ParkingSessionStatus;
import be.pxl.parking.domain.User;
import be.pxl.parking.external.PaymentRequestService;
import be.pxl.parking.repository.ParkingSessionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class SendPaymentRequests {
    private static final Logger LOGGER = LogManager.getLogger(SendPaymentRequests.class);

    private final PaymentRequestService paymentRequestService;
    private final ParkingSessionRepository parkingSessionRepository;

    public SendPaymentRequests(ParkingSessionRepository parkingSessionRepository,
                               PaymentRequestService paymentRequestService) {
        this.parkingSessionRepository = parkingSessionRepository;
        this.paymentRequestService = paymentRequestService;
    }

    @Async
    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 2)
    @Transactional
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        LOGGER.info("------    Starting job ----");
        List<ParkingSession> sessions = parkingSessionRepository.findParkingSessionsByStatus(ParkingSessionStatus.ENDED);
        for (ParkingSession session : sessions) {
            User user = session.getLicensePlate().getUser();
            paymentRequestService.sendPaymentRequest(user.getBankAccountDetails().getBank(), user.getBankAccountDetails().getAccountNumber(), session.getPrice());
            session.setPaymentRequested();
        }
    }
}