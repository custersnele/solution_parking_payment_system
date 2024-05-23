package be.pxl.parking.external.implementation;

import be.pxl.parking.external.SmsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DummySmsService implements SmsService {

    private static final Logger logger = LogManager.getLogger(DummySmsService.class);

    @Override
    public void sendSms(String phoneNumber, String message) {
        logger.info("Dummy SMS Service: Sending SMS to {} with message: {}", phoneNumber, message);
    }
}