package city.org.rc;

import java.text.MessageFormat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class VirusReportClientMain {
    private static String baseURI = "http://localhost:8080/WebServerClient/rest/reports";

    public static void main(String[] args) {

	// Print the user menu.
	menu();
    }

    // creates client object with any filters/interceptors registered with the
    // config object (here null)
    // returns baseURI (i.e. servlet URI) as the client's target URI
    static WebTarget getWebTarget() {
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);

	return client.target(baseURI);
    }

    // GET all request
    static void getAll() {
	WebTarget target = getWebTarget();

	String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);

	System.out.println(response);
    }

    // GET all with given start and end date request
    static void getByDate(String startDate, String endDate) {
	WebTarget target = getWebTarget();
	String path = MessageFormat.format("search/{0}/{1}", startDate, endDate);
	String reports = target.path(path).request().accept(MediaType.APPLICATION_JSON).get(String.class);

	System.out.println(reports);
    }

    // GET mean with given value, start and end date request
    static void getMean(String onValue, String startDate, String endDate) {
	WebTarget target = getWebTarget();
	String path = MessageFormat.format("search/mean/{0}/{1}/{2}", onValue, startDate, endDate);
	Double mean = target.path(path).request().accept(MediaType.APPLICATION_JSON).get(Double.class);

	System.out.println(mean);
    }

    // GET median with given value, start and end date request
    static void getMedian(String onValue, String startDate, String endDate) {
	WebTarget target = getWebTarget();
	String path = MessageFormat.format("search/median/{0}/{1}/{2}", onValue, startDate, endDate);
	Double mean = target.path(path).request().accept(MediaType.APPLICATION_JSON).get(Double.class);

	System.out.println(mean);
    }

    // ADD report request
    static void add(VirusReport report) {
	WebTarget target = getWebTarget();
	Response response = target.request().post(Entity.entity(report, MediaType.APPLICATION_JSON), Response.class);

	System.out.println(response.getLocation().toString());
    }

    // DELETE report request
    private static void delete(String date) {
	WebTarget target = getWebTarget();
	String path = MessageFormat.format("delete/{0}", date);
	Response response = target.path(path).request().delete(Response.class);
	System.out.println(response);
    }

    // UPDATE report request
    private static void update(String date, String newValue, String valueToChange) {
	WebTarget target = getWebTarget();
	String path = MessageFormat.format("update/{0}/{1}/{2}", date, newValue, valueToChange);
	Response response = target.path(path).request().put(Entity.entity(date, MediaType.APPLICATION_JSON),
		Response.class);
	System.out.println(response);
    }

    // Menus
    private static void menu() {
	Byte choice = 0;

	// Print Welcome Message
	System.out.println("Welcome to the Rest Web Service Java Client for the best Covid Reports!");

	do {
	    // Actions Menu
	    System.out.println(
		    "\nWhat can I do for you? Available options:\n'0' -> Exit the Program.\n'1' -> List all reports.\n'2' -> List all reports in a specified date period.\n'3' -> Find the mean of a field from a specified subset of reports in a specified date period.\n'4' -> Find the median of a field from a specified subset of reports in a specified date period.\n'5' -> Add a report to the database.\n'6' -> Delete a report from the database.\n'7' -> Update a value from a report stored in the database.\n");
	    choice = EasyIn.getByte();

	    // Adds a space for better visual design
	    System.out.println();

	    // Choice Processing
	    menuOptions(choice);
	}
	while (choice != 0);
    }

    private static void menuOptions(byte choice) {
	// Switches between the input choice to the correct operation
	switch (choice) {
	// Choice for listing all reports from the database.
	case 1: {
	    getAll();
	    break;
	}
	// Choice for listing the reports with the specified date from the database.
	case 2: {
	    // Variables
	    String startDate = "";
	    String endDate = "";
	    boolean startCheck = false;
	    boolean endCheck = false;

	    // Input check and retrieval of start date
	    do {
		System.out.println(
			"Provide the start date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		startDate = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(startDate.length() == 10)) {
		    System.out.println(
			    "\n** Start date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    startCheck = true;
		}
	    }
	    while (startCheck == false);

	    // Input check and retrieval of end date
	    do {
		System.out.println(
			"Provide the end date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		endDate = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(endDate.length() == 10)) {
		    System.out.println(
			    "\n** End date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    endCheck = true;
		}
	    }
	    while (endCheck == false);

	    // Actual processing
	    getByDate(startDate, endDate);
	    break;
	}
	// Choice for retrieving the mean of a field from the database given a date
	// period for reports.
	case 3: {
	    // Variables
	    String value = "";
	    String startDate = "";
	    String endDate = "";
	    boolean startCheck = false;
	    boolean endCheck = false;

	    // Retrieval of the value to change
	    System.out.println(
		    "Provide the value from the reports you wish to change. Accepted inputs: 'cases', 'icu' or 'deaths'.");
	    value = EasyIn.getString();
	    // Adds a space for better visual design
	    System.out.println();

	    // Input check and retrieval of start date
	    do {
		System.out.println(
			"Provide the start date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		startDate = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(startDate.length() == 10)) {
		    System.out.println(
			    "\n** Start date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    startCheck = true;
		}
	    }
	    while (startCheck == false);

	    // Input check and retrieval of end date
	    do {
		System.out.println(
			"Provide the end date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		endDate = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(endDate.length() == 10)) {
		    System.out.println(
			    "\n** End date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    endCheck = true;
		}
	    }
	    while (endCheck == false);

	    // Actual processing
	    getMean(value, startDate, endDate);
	    break;
	}
	// Choice for retrieving the median of a field from the database given a date
	// period for reports.
	case 4: {
	    // Variables
	    String value = "";
	    String startDate = "";
	    String endDate = "";
	    boolean startCheck = false;
	    boolean endCheck = false;

	    // Retrieval of the value to change
	    System.out.println(
		    "Provide the value from the reports you wish to change. Accepted inputs: 'cases', 'icu' or 'deaths'.");
	    value = EasyIn.getString();
	    // Adds a space for better visual design
	    System.out.println();

	    // Input check and retrieval of start date
	    do {
		System.out.println(
			"Provide the start date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		startDate = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(startDate.length() == 10)) {
		    System.out.println(
			    "\n** Start date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    startCheck = true;
		}
	    }
	    while (startCheck == false);

	    // Input check and retrieval of end date
	    do {
		System.out.println(
			"Provide the end date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		endDate = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(endDate.length() == 10)) {
		    System.out.println(
			    "\n** End date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    endCheck = true;
		}
	    }
	    while (endCheck == false);

	    // Actual processing
	    getMedian(value, startDate, endDate);
	    break;
	}
	// Choice for adding a report to the database.
	case 5: {
	    // Variables
	    VirusReport report = new VirusReport();
	    String date = "";
	    int deaths = 0;
	    int icu = 0;
	    int cases = 0;
	    boolean dateCheck = false;

	    // Input check and retrieval of date
	    do {
		System.out.println(
			"Provide the date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		date = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(date.length() == 10)) {
		    System.out.println(
			    "\n** Start date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    // Add the value to the report
		    report.setDate(date);
		    dateCheck = true;
		}
	    }
	    while (dateCheck == false);

	    // Retrieve the numeric value for deaths
	    System.out.println(
		    "Provide the value for the amount of deaths for the report.\nMust be an integer (without decimal points).\nIf the data is ot currently available, you may input the value '0'.");
	    deaths = EasyIn.getInt();
	    // Adds a space for better visual design
	    System.out.println();
	    // Adds the value to the report
	    report.setDeaths(deaths);

	    // Retrieve the numeric value for icu admissions
	    System.out.println(
		    "Provide the value for the amount of icu addmisions for the report.\nMust be an integer (without decimal points).\nIf the data is ot currently available, you may input the value '0'.");
	    icu = EasyIn.getInt();
	    // Adds a space for better visual design
	    System.out.println();
	    // Adds the value to the report
	    report.setAdmissionsICU(icu);

	    // Retrieve the numeric value for deaths
	    System.out.println(
		    "Provide the value for the amount of cases for the report.\nMust be an integer (without decimal points).\nIf the data is ot currently available, you may input the value '0'.");
	    cases = EasyIn.getInt();
	    // Adds a space for better visual design
	    System.out.println();
	    // Adds the value to the report
	    report.setCases(cases);

	    // Actual processing
	    add(report);
	    break;
	}
	// Choice for removing a report from the database
	case 6: {
	    // Variables
	    String date = "";
	    boolean dateCheck = false;

	    // Input check and retrieval of date
	    do {
		System.out.println(
			"Provide the date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		date = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(date.length() == 10)) {
		    System.out.println(
			    "\n** Start date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    dateCheck = true;
		}
	    }
	    while (dateCheck == false);

	    // Actual processing
	    delete(date);
	    break;
	}
	// Choice for updating a value from a report stored in the database
	case 7: {
	    // Variables
	    String date = "";
	    String newValue = "";
	    String valueToChange = "";
	    boolean dateCheck = false;

	    // Input check and retrieval of date
	    do {
		System.out.println(
			"Provide the date in the format: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
		date = EasyIn.getString();

		// Adds a space for better visual design
		System.out.println();

		if (!(date.length() == 10)) {
		    System.out.println(
			    "\n** Start date input is invalid, an unexpected amount of characters where found in processing. Please try again. **\n");
		}
		else {
		    dateCheck = true;
		}
	    }
	    while (dateCheck == false);
	    
	    // Retrieve the String of the value to add to the database
	    System.out.println(
		    "Provide the value to add to the database.\nMust be either an integer (without decimal points) or a date.\nIf it's a date the format is: YYYY-MM-DDD where 'Y' is a digit from the year, 'M' is a digit from the month and 'D' is a digit from the day.\nIf any of the year, month or day values are under the maximum amount of digits assigned enter a '0' in the empty spaces.\nExample: July 1st 2020 -> 2020-07-01");
	    newValue = EasyIn.getString();
	    // Adds a space for better visual design
	    System.out.println();
	    
	    // Retrieve the String of which value to change from the report
	    System.out.println(
		    "Provide the field of the report that needs to change.\nAccepted Values: 'icu', 'date', 'deaths' or 'cases'.");
	    valueToChange = EasyIn.getString();
	    // Adds a space for better visual design
	    System.out.println();
	    
	    // Actual processing
	    update(date, newValue, valueToChange);
	    break;
	}
	default: {
	    // Responsible for giving the appropriate exit output.
	    if (choice != 0) {
		System.out.println("The input given is not valid. Please check the accepted values again.");
	    }
	    else {
		System.out.println("Program exited successfully.");
	    }
	}
	}
    }
}
