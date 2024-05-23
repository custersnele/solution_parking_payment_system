package be.pxl.parking.simulation;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ParkingSimulation {

    public static void main(String[] args) {
        // create 4 cars that park in
        List<String> parkings = Arrays.asList("2993795c-e648-4526-8011-67c7891c6eb2","b1728334-de9d-4458-b4eb-e362c8470f82","94a8e143-3755-46ea-9e92-f9386d30ce67","10d10f17-29c3-42c9-9ef8-744ba43cc52b","00588b89-3030-4c0b-8027-bf631cc28cef");
        ParkingPaymentSystemApiHelper apiHelper = new ParkingPaymentSystemApiHelper();
        Car car1 = new Car("1-QPT-587", parkings, apiHelper);
        Car car2 = new Car("2-ECB-542", parkings, apiHelper);
        Car car3 = new Car("BAT-542", parkings, apiHelper);
        Car car4 = new Car("123GO", parkings, apiHelper);
        List<Car> cars = Arrays.asList(car1, car2, car3, car4);
        for (Car car : cars) {
            car.start();
        }
    }
}
