# CoasterCounter

Description

This program is a Java Swing GUI over a SQL table of rollercoasters.  Weird people who like rollercoasters (like myself), call themselves enthuasiasts and like to keep track of the coasters that they have ridden and their ranks for each ride.  My CoasterCounter allows for the user to have easy access to read/write from/to a SQL table that stores information such as number of rides on a coaster, rating of the coaster, date of first ride, date of latest ride.  I used an MVC architecture for this program, although in retrospect this may not have been the best way tolay the code out.

Model

The model of the code handles the queries to the SQL table.  There is a Read Only version that is used by the view to access information to display and another version that allows the user to update and delete entries.  The model stores all information entered by the user into a single SQL table that is created whenthe program first runs.  There is a second table that is not accessible to the user that contains the name and park of all current valid rollercoasters to ensure that an invalid coaster is not entered.  This also helps with collision resolution as many rides share a name, so the park must also be used as an identifier.  As of now, the Valid Coaster table contains only US coasters that I pulled from a web database when I first started the project, but I may attempt to write a script to allow it to auto-update in the future.

View

For the view, I decided to do a GUI with a paged format.  The default page is the Home Page.  This page contains a search bar that cointains a drop-down that autofills entered coaster names and parks (it also enforces that an invlaid name cannot be entered).  It also contains a JTable that allows the user to sort their entered coasters by attributes such as rating, times ridden, and date of first ride.  The second page type is the Add Page.  This allows the user to enter a ride that they have not ridden before.  This page is triggered by searching the name of an unridden coaster.  This page allows the user to add a ride as well as a rating and date if they so desire.  The final page is an Update Page that allows the user to update an existing entry with a new ride, as well as change the rating and the date of the most recent ride.  This page also allows the user to delete the ride from their ride history but prompts them twice to ensure that accidental deletion does not occur.

Running the Code

Currently, the only way to run the code is to install mySQL and create a database.  Once this code is pulled, you can enter the url, username, and password for the mySQL server into the StartTasks class. (A useful guide on mySQL setup can be found here: https://www.ntu.edu.sg/home/ehchua/programming/java/JDBC_Basic.html).  In the future I hope to find a way to make this process somewhat, if not fully, automated.
