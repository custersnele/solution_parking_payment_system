package be.pxl.parking.external;

import be.pxl.parking.domain.Bank;

public interface PaymentRequestService {
    void sendPaymentRequest(Bank bank, String accountNumber, double amount);
}

