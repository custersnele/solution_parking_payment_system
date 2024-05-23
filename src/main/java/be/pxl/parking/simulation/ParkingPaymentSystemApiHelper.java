package be.pxl.parking.simulation;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

public class ParkingPaymentSystemApiHelper {

    private final WebClient webClient;

    public ParkingPaymentSystemApiHelper() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public Boolean makeRequest(String action, String licensePlate, String parking) {
        String jsonBody = "{\"licensePlate\": \"" + licensePlate + "\", " +
                    "\"parking\" : \"" + parking + "\"}";


        System.out.println("Sending result!");
        System.out.println(jsonBody);

        return  webClient.post()
                .uri("/parking/" + action)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonBody)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return Mono.just(true);
                    } else {
                        System.out.println("An error occurred while sending the result.");
                        return Mono.just(false);
                    }
                }).block();
    }
}
