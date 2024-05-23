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

@WebServlet("/ParkingSessions")
public class ParkingDetailsServlet extends HttpServlet {

	private final ParkingService parkingService;
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	public ParkingDetailsServlet(ParkingService parkingService) {
		this.parkingService = parkingService;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		Filter filter = createFilter(req);
		PrintWriter writer = resp.getWriter();

		Page<ParkingSessionDto> sessions = parkingService.findParkingSessions(filter, createPageble(req));

		writeParkingSessionDetails(writer, sessions);
	}

	private static Filter createFilter(HttpServletRequest request) {
		String parking = request.getParameter("parking");
		String licensePlate = request.getParameter("licensePlate");

		ParkingSessionStatus status = null;
		if (request.getParameter("status") != null) {
			status = ParkingSessionStatus.valueOf(request.getParameter("status"));
		}
		return new Filter(status, licensePlate, parking);
	}

	private static PageRequest createPageble(HttpServletRequest request) {
		int page = request.getParameter("page") == null? 0 : Integer.parseInt(request.getParameter("page"));
		int size = request.getParameter("size") == null? 100 : Integer.parseInt(request.getParameter("size"));
		return PageRequest.of(page, size, Sort.by("start").descending());
	}

	private static void writeParkingSessionDetails(PrintWriter writer, Page<ParkingSessionDto> sessions) {
		writer.println("<html>");
		writer.println("<body>");
		writer.println("<table>");
		for (ParkingSessionDto parkingSession : sessions.getContent()) {
		   writer.println("<tr><td>" + parkingSession.parking() + "</td><td>" + parkingSession.licensePlate() + "</td><td>" + DATE_TIME_FORMATTER.format(parkingSession.start()) + "</td><td>" + (parkingSession.end() != null? TIME_FORMATTER.format(parkingSession.end()) : "") + "</td><td>" + parkingSession.price() + "</td><td>" + parkingSession.status() + "</td></tr>");
		}

		writer.println("</table>");

		writer.println("</body>");
		writer.println("</html>");
	}
}
