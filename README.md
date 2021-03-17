# memsourcefirstproject
First project for Memsource interview

## Assignment
- Create a Grails or Spring Boot application
 - Grails 2.5.x or 3.x
 - The application will have 2 pages

### Setup page
- Memsource account configuration
 - Configuration should be presented as Grails domain class (or Spring Boot equivalent)  
 - Two text fields for username and password
 - Configuration can be edited and must be saved on a persistent storage (H2 database for example)
 - No need to care about the security of a password

### Projects page
- List projects retrieved from memsource API
- Name, status, source language and target languages should be displayed
- You should load and render the projects in JavaScript
 - You will need to implement an endpoint in your application that will provide the data for AJAX call

### Requirements
- Provide instructions how to compile and run
- You should create **production quality code.**
- Besides the funcional requirements described above, please try to show that you:
 * Understand AJAX
 * Can work with Git by providing us your solution as a link to your Git repo

### Notes
- Inform us about the amount of time it took you to finnish
- Any extra features going beyond the requirements are appreciated

## How to build, test and run

### Basic environment setup (see setup.sh)
- install JDK 8
- install grails 2.5.0

### Configure environment
- copy `.env` file to `.env-local` and edit contents to match your environment (see `.env` file comments for more information)
- cd to root project folder and `source .env-local`
- 
### Run tests
- cd from root folder to memsourceFirstProject
- run grails `test-app -unit` (to run only unit tests)
- or run `grails test-app` to run unit and integration test (you need to have your Memsource account credentials configured in .env-local for integration tests to run correctly)

### Run application
- cd from root folder to memsourceFirstProject
- run `grails run-app`

### Try application
- open your favorite browser (developed on Google Chrome)
- go to http://localhost:8080/memsourceFirstProject
- on the first page, set your Memsource credentials and click login button
- you will be redirected to Project list page where you will see paginated list of your Memsource projects
- if you need to change password for communication with Memsource API, click on Account setup link in the top menu, where you can change the password that the application is using to communicate with Memsource API
- when you are done using the application, click on logout link in the top menu to release currently held login token and logout from application

## Closing notes
- If you run into any problems during any stage of application setup on your environment, feel free to contact me via email, create an issue for this project or write a message via project discussion.
- Development environment:
 * Ubuntu Mate 20.04
 * Java OpenJDK 8
 * Grails 2.5.0 (via sdkman)
 * System architecture: arm64
