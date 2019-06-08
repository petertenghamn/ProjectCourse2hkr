# ProjectCourse2hkr

# Description
This was a project created in a 4 person group for a school course. We decided to make an application with pokemon.
There is a Trainer user who is a regular user, collecting pokemon, which they can add onto their team, and then battle AI opponents.
The other user is a Professor, who is like an admin/moderator, they have their own scenes where they can edit/remove trainers, aswell
as being able to edit/add/remove pokemon.

# Programs used
Intellij Idea - was used to run the program.
MySQL Workbench v8.0.15 - was used for the database.
Connector/J v8.0.15 - was added to intellij in order to communicate with the server.

# Things needed to run the application
First open MySQL Workbench
  -launch a local instance to create the database within
  -open the script included in the repository/MySQL
  -run the script once
Now open up the project in Intellij Idea
You will need to add the Connector/J to the project
  -goto File/Project Structure
  -under project settings go to libraries
  -add the java file Connector/J here
One last step needs to be taken to make sure you are able to connect to the database
In the class DatabaseLoader, make sure the username and password matches with the username and password for your local MySQL server instance
