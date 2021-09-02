package city.org.rc;

import java.text.MessageFormat;

public class VirusReport {

    // Attributes
    private int cases;
    private int admissionsICU;
    private int deaths;
    // Date is stored as a String for Jersey serialisation. The date object is
    // however parsed back into a LocalDate object when processing logic to ensure
    // correctness of input.
    private String date;

    // Constructors

    // Empty Constructor, accepts updates only.
    public VirusReport() {
	cases = 0;
	admissionsICU = 0;
	deaths = 0;
	date = "";
    }

    // Full Constructor, the average use case.
    public VirusReport(int newCases, int newAdmissionsICU, int newDeaths, String newDate) {
	cases = newCases;
	admissionsICU = newAdmissionsICU;
	deaths = newDeaths;
	date = newDate;
    }

    // Getters & Setters
    public int getCases() {
	return cases;
    }

    public void setCases(int cases) {
	this.cases = cases;
    }

    public int getAdmissionsICU() {
	return admissionsICU;
    }

    public void setAdmissionsICU(int admissionsICU) {
	this.admissionsICU = admissionsICU;
    }

    public int getDeaths() {
	return deaths;
    }

    public void setDeaths(int deaths) {
	this.deaths = deaths;
    }
    
    public String getDate() {
	return date;
    }
    
    public void setDate(String date) {
	this.date = date;
    }

    // Other Default Methods
    @Override
    public String toString() {
	return MessageFormat.format(
		"Covid-19 on {0} infected: {1} person/s, degraded to ICU: {2} person/s and claimed the lives of: {3} person/s.\n",
		date, cases, admissionsICU, deaths);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + admissionsICU;
	result = prime * result + cases;
	result = prime * result + ((date == null) ? 0 : date.hashCode());
	result = prime * result + deaths;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	VirusReport other = (VirusReport) obj;
	if (admissionsICU != other.admissionsICU)
	    return false;
	if (cases != other.cases)
	    return false;
	if (date == null) {
	    if (other.date != null)
		return false;
	}
	else if (!date.equals(other.date))
	    return false;
	if (deaths != other.deaths)
	    return false;
	return true;
    }

}