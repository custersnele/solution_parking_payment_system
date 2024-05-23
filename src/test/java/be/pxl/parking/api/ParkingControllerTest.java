package be.pxl.parking.api;

import be.pxl.parking.api.input.ParkingSessionStartCommand;
import be.pxl.parking.builder.ParkingSessionStartCommandBuilder;
import be.pxl.parking.service.ParkingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ParkingController.class)
public class ParkingControllerTest {

		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private ParkingService parkingService;

		@Autowired
		private ObjectMapper objectMapper;

		@Captor
		private ArgumentCaptor<ParkingSessionStartCommand> captor;

		@Test
		public void validateLicensePlateNumberIsRequired() throws Exception {
			ParkingSessionStartCommand command = ParkingSessionStartCommandBuilder.aParkingSessionStartCommand()
					.withLicensePlate("").withParking(UUID.randomUUID()).build();
			String json = objectMapper.writeValueAsString(command);
			mockMvc.perform(post("/parking/start")
							.contentType(MediaType.APPLICATION_JSON)
							.content(json))
					.andExpect(status().isBadRequest());
			Mockito.verifyNoInteractions(parkingService);

		}

	@Test
	public void verifyParkingServiceIsCalled() throws Exception {
		ParkingSessionStartCommand command = ParkingSessionStartCommandBuilder.aParkingSessionStartCommand()
				.withLicensePlate("2-ABC-123").withParking(UUID.randomUUID()).build();
		String json = objectMapper.writeValueAsString(command);
		mockMvc.perform(post("/parking/start")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isAccepted());
	Mockito.verify(parkingService).startParkingSession(Mockito.eq(command));
	//	Mockito.verify(parkingService).startParkingSession(captor.capture());
//		ParkingSessionStartCommand capturedCommand = captor.getValue();
//		Assertions.assertEquals("2-ABC-123", capturedCommand.licensePlate());

	}


	}
