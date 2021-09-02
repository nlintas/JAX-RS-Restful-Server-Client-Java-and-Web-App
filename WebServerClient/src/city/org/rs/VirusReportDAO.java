package city.org.rs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

//Singleton class
public class VirusReportDAO {

    static Database database;

    // Used for the singleton design.
    private static VirusReportDAO instance;

    // Static block
    static {
	database = new Database();
    }

    // Singleton class, can only be constructred by itself.
    // Private constructor
    private VirusReportDAO() {

    }

    // Singleton Instance Setter & Getter.
    public static VirusReportDAO getInstance() {
	if (instance == null) {
	    instance = new VirusReportDAO(); // call private constructor
	}

	return instance;
    }

    // * Servlet Operations *

    // Returns a list of all reports from the database.
    public String getAll() {

	Statement st = null;
	ResultSet rs = null;

	try {
	    // Make a statement after connecting with the database
	    st = database.connectDatabase().createStatement();

	    // Write and execute the associated query. Orders reports in ascending order.
	    rs = st.executeQuery(MessageFormat.format("SELECT * FROM {0} ORDER BY {1} ASC", database.REPORT_TABLE,
		    database.REPORT_DATE));

	    // Prepare and return the result in a String format.
	    String result = "";
	    while (rs.next()) {
		result += "[On Date: " + rs.getString(1).substring(0, 10) + ", Cases: " + rs.getString(2)
			+ ", ICU Admissions: " + rs.getString(3) + ", Deaths: " + rs.getString(4) + "]"
			+ System.lineSeparator();
	    }

	    return result;
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    // Catches any exception thrown from the database on failure.
	    return "Get All Query Failed! Check output console.";
	}
	finally {
	    // Clearing the connections/variables
	    if (rs != null) {
		try {
		    rs.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (st != null) {
		try {
		    st.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (database.connectDatabase() != null) {
		try {
		    database.connectDatabase().close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	}

    }

    // Returns the reports between two dates.
    public String getByDate(LocalDate startDate, LocalDate endDate) {

	Statement st = null;
	ResultSet rs = null;

	try {
	    // Make a statement after connecting with the database
	    st = database.connectDatabase().createStatement();

	    // Write and execute the associated query. Orders reports returned in ascending
	    // order.
	    rs = st.executeQuery(
		    MessageFormat.format("SELECT * FROM {0} WHERE {1} BETWEEN ''{2}'' AND ''{3}'' ORDER BY {1} ASC",
			    database.REPORT_TABLE, database.REPORT_DATE, startDate, endDate));

	    // Prepare and return the result.
	    String result = "";
	    while (rs.next()) {
		result += "[On Date: " + rs.getString(1).substring(0, 10) + ", Cases: " + rs.getString(2)
			+ ", ICU Admissions: " + rs.getString(3) + ", Deaths: " + rs.getString(4) + "]"
			+ System.lineSeparator();
	    }

	    return result;
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    // Catches any exception thrown from the database on failure.
	    return "Get by Date Report Query Failed! Check output console.";
	}
	finally {
	    // Clearing the connections/variables
	    if (rs != null) {
		try {
		    rs.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (st != null) {
		try {
		    st.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (database.connectDatabase() != null) {
		try {
		    database.connectDatabase().close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	}

    }

    // Adds a report to the database.
    public String add(VirusReport newReport) {

	Statement st = null;
	ResultSet rs = null;

	try {
	    // Make a statement after connecting with the database
	    st = database.connectDatabase().createStatement();

	    // Write and execute the associated query.
	    // The Integer.toString() calls are used to remove seperators from numbers with
	    // greater than 3 digits (the format method adds them for readability by
	    // default).
	    String sql = MessageFormat.format("INSERT INTO {0} VALUES (''{1}'', {2}, {3}, {4})", database.REPORT_TABLE,
		    newReport.getDate(), Integer.toString(newReport.getCases()),
		    Integer.toString(newReport.getAdmissionsICU()), Integer.toString(newReport.getDeaths()));
	    st.executeUpdate(sql);

	    // Prepare and return the date in a String format.
	    return newReport.getDate();
	}
	catch (SQLException e) {
	    // Catches any exception thrown from the database on failure.
	    e.printStackTrace();
	}
	finally {
	    // Clearing the connections/variables
	    if (rs != null) {
		try {
		    rs.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (st != null) {
		try {
		    st.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (database.connectDatabase() != null) {
		try {
		    database.connectDatabase().close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	}
	return newReport.getDate();
    }

    // Update report on the database. Returns a true or false confirmation depending
    // on the success or failure of the operation. Checks also if the new date is
    // available since its a primary key and will not allow the operation in such
    // case.
    public boolean update(LocalDate date, String newValue, String valueToChange) {

	// Check if the report exists.
	reportExists(date);

	Statement st = null;
	ResultSet rs = null;

	try {
	    // Make a statement after connecting with the database
	    st = database.connectDatabase().createStatement();

	    // Write and execute the associated query.
	    String sql = MessageFormat.format("UPDATE {0} SET {1} = ''{2}'' WHERE {3} = ''{4}''", database.REPORT_TABLE,
		    valueToChange, newValue, database.REPORT_DATE, date);
	    st.executeUpdate(sql);

	    return true;
	}
	catch (SQLException e) {
	    // Catches any exception thrown from the database on failure.
	    e.printStackTrace();
	}
	finally {
	    // Clearing the connections/variables
	    if (rs != null) {
		try {
		    rs.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (st != null) {
		try {
		    st.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (database.connectDatabase() != null) {
		try {
		    database.connectDatabase().close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	}
	return false;

    }

    // Remove report from database. Returns a true or false confirmation depending
    // on the success or failure of the operation.
    public boolean remove(LocalDate reportDate) {

	// Check if the report exists.
	reportExists(reportDate);

	Statement st = null;
	ResultSet rs = null;

	try {
	    // Make a statement after connecting with the database
	    st = database.connectDatabase().createStatement();

	    // Write and execute the associated query.
	    String sql = MessageFormat.format("DELETE FROM {0} WHERE {1} = ''{2}''", database.REPORT_TABLE,
		    database.REPORT_DATE, reportDate);
	    st.executeUpdate(sql);

	    return true;
	}
	catch (SQLException e) {
	    // Catches any exception thrown from the database on failure.
	    e.printStackTrace();
	    return false;
	}
	finally {
	    // Clearing the connections/variables
	    if (rs != null) {
		try {
		    rs.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (st != null) {
		try {
		    st.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (database.connectDatabase() != null) {
		try {
		    database.connectDatabase().close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	}

    }

    // Server side calcualtion of the mean.
    public double mean(String onValue, LocalDate startDate, LocalDate endDate) {
	// Gets the reports with the given dates.
	ArrayList<VirusReport> reports = getByDateInStructure(startDate, endDate);
	// Temporary variables
	double result = 0;
	int counter = 0;

	// Checks which value from the reports is to be calculated.
	switch (onValue) {
	// In each case traverse the reports of the database with the given date and add
	// their values to the sum 'result'.
	case "cases":
	    while (counter < reports.size()) {
		result += reports.get(counter).getCases();
		counter++;
	    }
	    break;
	case "deaths":
	    while (counter < reports.size()) {
		result += reports.get(counter).getDeaths();
		counter++;
	    }
	    break;
	case "icu":
	    while (counter < reports.size()) {
		result += reports.get(counter).getAdmissionsICU();
		counter++;
	    }
	    break;

	default:
	    // Given an unkown argument String throw an IllegalArgumentException.
	    throw new IllegalArgumentException(
		    "Mean cannot be calculated on this value as it doesn't match anything from the database or the value entered was mistyped.");
	}

	// Divides by the amount of reports
	result = result / counter;

	return result;
    }

    // Server side calculation of the median.
    public double median(String onValue, LocalDate startDate, LocalDate endDate) {
	// Gets the reports with the given dates.
	ArrayList<VirusReport> reports = getByDateInStructure(startDate, endDate);
	// Temporary variables/structures
	ArrayList<Double> values = new ArrayList<Double>();
	double result = 0;
	int counter = 0;

	// Checks which value from the reports is to be calculated.
	switch (onValue) {
	// In each case traverse the reports of the database with the given date and add
	// their values to collection 'values'.
	case "cases":
	    while (counter < reports.size()) {
		values.add((double) reports.get(counter).getCases());
		counter++;
	    }
	    break;
	case "deaths":
	    while (counter < reports.size()) {
		values.add((double) reports.get(counter).getDeaths());
		counter++;
	    }
	    break;
	case "icu":
	    while (counter < reports.size()) {
		values.add((double) reports.get(counter).getAdmissionsICU());
		counter++;
	    }
	    break;

	default:
	    // Given an unkown argument String throw an IllegalArgumentException.
	    throw new IllegalArgumentException(
		    "Median cannot be calculated on this value as it doesn't match anything from the database or the value entered was mistyped.");
	}

	// Sort the numbers
	Collections.sort(values);

	// Temporarily store the size since its reused frequently below
	int size = values.size();

	// If you have an odd number, divide by 2 and round up to get the position of
	// the median number. If you have an even number, divide by 2. Go to the number
	// in that position and average it with the number in the next higher position
	// to get the median.
	if (size % 2 == 1) {
	    result = values.get((size + 1) / 2 - 1);
	}
	else {
	    result = (values.get((size / 2 - 1)) + values.get(size / 2)) / 2;
	}

	return result;
    }

    // * Private Methods *

    // Checks if a report exists in the database, private for use in the methods
    // within this class.
    private boolean reportExists(LocalDate dateOfReport) {

	// Get all dates from the database
	ArrayList<LocalDate> savedDates = getDates();

	// Check from the dates received if the report requested is stored in the
	// database
	for (int i = 0; i < savedDates.size(); i++) {
	    if (savedDates.get(i).equals(dateOfReport))
		return true;
	}

	// If it does not, throw an exception
	throw new IllegalArgumentException("Report requested does not exist in the database");

    }

    // Returns the reports between two dates.
    public ArrayList<VirusReport> getByDateInStructure(LocalDate startDate, LocalDate endDate) {

	Statement st = null;
	ResultSet rs = null;

	try {
	    // Make a statement after connecting with the database
	    st = database.connectDatabase().createStatement();

	    // Write and execute the associated query. Orders reports returned in ascending
	    // order.
	    rs = st.executeQuery(
		    MessageFormat.format("SELECT * FROM {0} WHERE {1} BETWEEN ''{2}'' AND ''{3}'' ORDER BY {1} ASC",
			    database.REPORT_TABLE, database.REPORT_DATE, startDate, endDate));

	    // Prepare and return the result.
	    ArrayList<VirusReport> reports = new ArrayList<VirusReport>();
	    while (rs.next()) {
		int tempCases = Integer.parseInt(rs.getString(2));
		int tempICUAdmissions = Integer.parseInt(rs.getString(3));
		int tempDeaths = Integer.parseInt(rs.getString(4));
		String tempDate = rs.getString(1).substring(0, 10);
		VirusReport tempReport = new VirusReport(tempCases, tempICUAdmissions, tempDeaths, tempDate);
		reports.add(tempReport);
	    }

	    return reports;
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    // Catches any exception thrown from the database on failure.
	    return null;
	}
	finally {
	    // Clearing the connections/variables
	    if (rs != null) {
		try {
		    rs.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (st != null) {
		try {
		    st.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (database.connectDatabase() != null) {
		try {
		    database.connectDatabase().close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	}

    }

    // Returns a list of all dates which have reports from the database.
    private ArrayList<LocalDate> getDates() {

	Statement st = null;
	ResultSet rs = null;

	try {
	    // Make a statement after connecting with the database
	    st = database.connectDatabase().createStatement();

	    // Write and execute the associated query. Orders dates in ascending order.
	    rs = st.executeQuery(MessageFormat.format("SELECT {1} FROM {0} ORDER BY {1} ASC", database.REPORT_TABLE,
		    database.REPORT_DATE));

	    // Adds each date to a data structure after parsing the date from a String into
	    // a date object, ommiting the timezone information.
	    ArrayList<LocalDate> savedDates = new ArrayList<LocalDate>();
	    while (rs.next()) {
		LocalDate newDate = LocalDate.parse(rs.getString(1).substring(0, 10));
		savedDates.add(newDate);
	    }

	    return savedDates;
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    // Catches any exception thrown from the database on failure.
	    return null;
	}
	finally {
	    // Clearing the connections/variables
	    if (rs != null) {
		try {
		    rs.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (st != null) {
		try {
		    st.close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	    if (database.connectDatabase() != null) {
		try {
		    database.connectDatabase().close();
		}
		catch (SQLException e) {
		    /* ignored */}
	    }
	}

    }
}
