package be.pxl.parking.servlet;


import be.pxl.parking.api.output.ParkingSessionDto;
import be.pxl.parking.domain.ParkingSessionStatus;
import be.pxl.parking.service.ParkingService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

@WebServlet("/RaceResults")
public class ParkingDetailsServlet extends HttpServlet {

	private final ParkingService parkingService;
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
	private static final Logger LOGGER = LogManager.getLogger(ParkingDetailsServlet.class);

	public ParkingDetailsServlet(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		String parking = req.getParameter("parking");
		String licensePlate = req.getParameter("licensePlate");
		int page = Integer.parseInt(req.getParameter("page"));
		int size = Integer.parseInt(req.getParameter("size"));
		ParkingSessionStatus status = null;
		if (req.getParameter("status") != null) {
			status = ParkingSessionStatus.valueOf(req.getParameter("status"));
		}
		Filter filter = new Filter(status, licensePlate, parking);

		PrintWriter writer = resp.getWriter();

		Pageable pageable = PageRequest.of(page, size, Sort.by("start").ascending());

		Page<ParkingSessionDto> sessions = parkingService.findParkingSessions(filter, pageable);

		writer.println("<html>");
		writer.println("<body>");
		writer.println("<table>");
			for (ParkingSessionDto parkingSession : sessions.getContent()) {
			   writer.println("<tr><td>" + parkingSession.parking() + "</td><td>" + parkingSession.licensePlate() + "</td><td>" + DATE_TIME_FORMATTER.format(parkingSession.start()) + "</td><td>" + TIME_FORMATTER.format(parkingSession.end()) + "</td><td>" + parkingSession.price() + "</td><td>" + parkingSession.status() + "</td></tr>");
			}

			writer.println("</table>");

		writer.println("</body>");
		writer.println("</html>");


	}
}
