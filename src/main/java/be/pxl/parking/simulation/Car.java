package be.pxl.parking.simulation;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Car extends Thread{
    private static final Random RANDOM = new Random();
    private String licensePlate;
    private List<String> possibleGarages;
    private ParkingPaymentSystemApiHelper parkingPaymentSystemApiHelper;

    public Car(String licensePlate, List<String> possibleGarages, ParkingPaymentSystemApiHelper parkingPaymentSystemApiHelper) {
        this.licensePlate = licensePlate;
        this.possibleGarages = possibleGarages;
        this.parkingPaymentSystemApiHelper = parkingPaymentSystemApiHelper;
    }

    @Override
    public void run() {
        while (true) {
            String garage = possibleGarages.get(RANDOM.nextInt(possibleGarages.size()));
            if (parkingPaymentSystemApiHelper.makeRequest("start", licensePlate, garage)) {
                try {
                    Thread.sleep(RANDOM.nextInt(1000, 8000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                parkingPaymentSystemApiHelper.makeRequest("stop", licensePlate, garage);
                try {
                    Thread.sleep(RANDOM.nextInt(30000, 50000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
