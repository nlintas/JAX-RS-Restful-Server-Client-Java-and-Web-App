package city.org.rs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.MessageFormat;

public class Database {

    // Attributes
    
    // Database Connection Information
    private final String HOST;
    private final String PORT;
    private final String DATABASE_NAME;
    private final String USERNAME;
    private final String USER_PASSWORD;
    
    // Tables & Columns
    public final String REPORT_TABLE;
    
    public final String REPORT_DATE;
    public final String REPORT_CASES;
    public final String REPORT_DEATHS;
    public final String REPORT_ICU;

    // Constructors
    public Database() {
	HOST = "localhost";
	PORT = "5432";
	DATABASE_NAME = "postgres";
	USERNAME = "postgres";
	USER_PASSWORD = "erWjD5^wCbeV";
	
	REPORT_TABLE = "report";
	REPORT_DATE = "date";
	REPORT_CASES = "cases";
	REPORT_DEATHS = "deaths";
	REPORT_ICU = "icu";
    }

    // Methods
    public Connection connectDatabase() {
	
	try {
	    
	    // Point to the JDBC postgresSQL driver
	    Class.forName("org.postgresql.Driver");
	    
	    // Assemble the URL for the connection and create it using the Drive Manager object. 
	    String url = MessageFormat.format("jdbc:postgresql://{0}:{1}/{2}?user={3}&password={4}", HOST, PORT, DATABASE_NAME, USERNAME, USER_PASSWORD);
	    Connection connection = DriverManager.getConnection(url);
	    
	    // (Optional) Logging that the database is connected whenever used, showing the ID of the connections separately for debugging.
//	    if (connection != null) {
//		System.out.println(MessageFormat.format("Connected with Postgress Database! Connection ID: [ {0} ]", connection));
//	    }
	    
	    return connection;
	}
	
	// The connection to the database can fail, so the exception is caught here.
	catch (Exception e) {
	    e.printStackTrace();
	}
	
	// Return null if the process failed.
	return null;
    }
    
}
