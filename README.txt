Detailed Description:
    It is a client-server application for persisting and reporting COVID-19 data. It has two clients however, a Java and a web client. In both cases the server has to run in a Tomcat 9 environment. Many clients can operate at the same time. Special consideration has been given to UI and UX given the time constraints. The server allows to find the daily reported cases, deaths and ICU admissions. There will be statistical operations allowed too like calculating the mean and median. All data is associated with a date. The Java version allows for the above operations to be performed through a CLI and the web version through an HTML web app.

Aims:
    ▪ Design and develop a RESTful server application using Java Jersey.
    ▪ Connect the RESTful server application to a simple back-end database and perform CRUD operations.
    ▪ Design and develop a Java client application that issues CRUD requests to the server application and displays the
    results.
    ▪ Design and develop a RESTful HTML client application that issues CRUD requests to the server application and
    displays the results in a suitable UI.

How to run:
    - Use a Tomcat 9 server and the Eclipse Jax-RS compatible IDE
    - Open the project and any HTML page using Tomcat 9 for the web client.
    - Open the VirusReportClientMain.java file and interact with the provided CLI GUI.