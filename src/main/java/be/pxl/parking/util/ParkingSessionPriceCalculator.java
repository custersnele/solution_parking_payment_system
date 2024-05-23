package be.pxl.parking.util;

import be.pxl.parking.domain.ParkingSession;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingSessionPriceCalculator {

    public static double calculatePrice(ParkingSession parkingSession) {
        LocalDateTime start = parkingSession.getStart();
        LocalDateTime end = parkingSession.getEnd();
        long days = ChronoUnit.DAYS.between(start, end);
            if (days > 1) {
                return days * parkingSession.getParking().getDailyRate();
            } else {
                long hours = ChronoUnit.HOURS.between(start, end);
                if (start.query(new IsWeekendQuery()) || end.query(new IsWeekendQuery())) {
                    return hours * parkingSession.getParking().getWeekendHourlyRate();
                } else {
                    return hours * parkingSession.getParking().getWeekdayHourlyRate();
            }
        }
    }
}
