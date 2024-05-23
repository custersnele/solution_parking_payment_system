package be.pxl.parking.external.implementation;

import be.pxl.parking.domain.Bank;
import be.pxl.parking.external.PaymentRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DummyPaymentRequestService implements PaymentRequestService {

    private static final Logger logger = LogManager.getLogger(DummyPaymentRequestService.class);

    @Override
    public void sendPaymentRequest(Bank bank, String accountNumber, double amount) {
        logger.info("Dummy Payment Request: Sending payment of €{} to bank {} for account {}", amount, bank, accountNumber);
    }
}