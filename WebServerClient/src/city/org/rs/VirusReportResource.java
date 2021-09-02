package city.org.rs;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reports")
public class VirusReportResource {

    // DAO class creates and returns its single instance used by all APIs below.
    private VirusReportDAO dao = VirusReportDAO.getInstance();

    // API that gets all reports from the database.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
	return dao.getAll();
    }

    // API that gets all reports given a specific date period.
    @GET
    @Path("search/{start}/{end}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String getByDate(@PathParam("start") String startDate, @PathParam("end") String endDate) {
	LocalDate newStartDate = LocalDate.parse(startDate);
	LocalDate newEndDate = LocalDate.parse(endDate);
	return dao.getByDate(newStartDate, newEndDate);
    }

    // API that gets the mean of a value from reports given with a specific date
    // period.
    @GET
    @Path("search/mean/{value}/{start}/{end}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Double getMean(@PathParam("value") String value, @PathParam("start") String startDate,
	    @PathParam("end") String endDate) {
	LocalDate newStartDate = LocalDate.parse(startDate);
	LocalDate newEndDate = LocalDate.parse(endDate);
	return dao.mean(value, newStartDate, newEndDate);
    }

    // API that gets the median of a value from reports given with a specific date
    // period.
    @GET
    @Path("search/median/{value}/{start}/{end}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Double getMedian(@PathParam("value") String value, @PathParam("start") String startDate,
	    @PathParam("end") String endDate) {
	LocalDate newStartDate = LocalDate.parse(startDate);
	LocalDate newEndDate = LocalDate.parse(endDate);
	return dao.median(value, newStartDate, newEndDate);
    }

    // API that adds a report to the database.
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(VirusReport report) throws URISyntaxException {

	String newReportDate = dao.add(report);
	if (newReportDate == null)
	    return Response.status(Response.Status.NOT_FOUND).build();
	URI uri = new URI("/reports/" + newReportDate);
	return Response.created(uri).build();
    }

    // API that update's a single specified value an existing report in the
    // database. Expects a date in a String format, a new value to be added and the
    // specified field to be changed in String format. The accepted values for
    // fields must match the names of the columns in the database, specifically:
    // "cases", "icu" or "deaths".
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update/{date}/{value}/{valueOf}")
    public Response updateValues(@PathParam("date") String date, @PathParam("value") String newValue,
	    @PathParam("valueOf") String valueToChange) {

	// Parses the date from a String into date object.
	LocalDate newDate = LocalDate.parse(date);

	if (dao.update(newDate, newValue, valueToChange))
	    return Response.ok().build();
	else
	    return Response.notModified().build();
    }

    // API that deletes a report from the database.
    @DELETE
    @Path("delete/{date}")
    public Response delete(@PathParam("date") String date) {

	// Parses the date from a String into date object.
	LocalDate newDate = LocalDate.parse(date);

	if (dao.remove(newDate))
	    return Response.ok().build();
	else
	    return Response.notModified().build();
    }
}
