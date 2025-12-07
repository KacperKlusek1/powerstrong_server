#           POWER STRONG
## Spring Boot server application

This description contains instructions regarding: 
- Application Programming Interface (API)
- Running application for standard usage (using Docker)
- Running application for tests usage (using XAMPP and Intelij)

###           1. API
The API of this application allows connection by endpoints given below:
- ####  POST api/auth/register:
&emsp; this endpoint requires the following body: <br />
&emsp; { <br />
&emsp;&emsp; "username" : "{username}", <br />
&emsp;&emsp; "email" : "{email}", <br />
&emsp;&emsp; "password" : "{password}" <br />
&emsp; } <br />
&emsp; and gives the following response: <br />
&emsp; { <br />
&emsp;&emsp; "token" : "{JWT}", <br />
&emsp;&emsp; "username" : "{username}" <br />
&emsp;&emsp; "userID" : "{userID}" <br />
&emsp; } <br />

- ####  POST api/auth/login:
&emsp; this endpoint requires the following body: <br />
&emsp; { <br />
&emsp;&emsp; "login" : "{login}", <br />
&emsp;&emsp; "password" : "{password}" <br />
&emsp; } <br />
&emsp; (where username or e-mail can be used as login) <br />
&emsp; and gives the following response: <br />
&emsp; { <br />
&emsp;&emsp; "token" : "{JWT}", <br />
&emsp;&emsp; "username" : "{username}" <br />
&emsp;&emsp; "userid" : "{userID}" <br />
&emsp; } <br />

- ####  GET api/reference/exercises:
&emsp; this endopint returns the list of the following objects:
&emsp; { <br />
&emsp;&emsp; "id" : "{exerciseID}", <br />
&emsp;&emsp; "name" : "{exerciseName}", <br />
&emsp;&emsp; "description" : "{exerciseDescription}", <br />
&emsp;&emsp; "isbodyweight" : "{true/false}", <br />
&emsp;&emsp; "categoryname" : "{exerciseCategoryName}", <br />
&emsp;&emsp; "movementpatternsids" : { movementPatternsIds }, <br />
&emsp;&emsp; "targetmusclegroupsids" : { targetMuscleGroupsIds }, <br />
&emsp; }

- ####  GET api/reference/categories:
&emsp; this endopint returns the list of the following objects:
&emsp; { <br />
&emsp;&emsp; "id" : "{exerciseCategoryId}", <br />
&emsp;&emsp; "name" : "{exerciseCategoryName}", <br />
&emsp; }

- ####  GET api/reference/movement-patterns:
&emsp; this endopint returns the list of the following objects:
&emsp; { <br />
&emsp;&emsp; "id" : "{movementPatternId}", <br />
&emsp;&emsp; "name" : "{movementPatternName}", <br />
&emsp; }

- ####  GET api/reference/target-muscle-groups:
&emsp; this endopint returns the list of the following objects:
&emsp; { <br />
&emsp;&emsp; "id" : "{targetMuscleGroupId}", <br />
&emsp;&emsp; "name" : "{targetMuscleGroupName}", <br />
&emsp; }

- ####  GET api/reference/effort-types:
&emsp; this endopint returns the list of the following objects:
&emsp; { <br />
&emsp;&emsp; "id" : "{effortTypeId}", <br />
&emsp;&emsp; "name" : "{effortTypeName}", <br />
&emsp;&emsp; "description" : "{effortTypeDescription}", <br />
&emsp; }

- ####  GET api/reference/training-methods:
&emsp; this endopint returns the list of the following objects:
&emsp; { <br />
&emsp;&emsp; "id" : "{trainingMethodId}", <br />
&emsp;&emsp; "name" : "{trainingMethodName}", <br />
&emsp;&emsp; "durationofcycle" : "{durationOfCycle}" (Integer), <br />
&emsp;&emsp; "description" : "{trainingMethodDescription}", <br />
&emsp; }

- ####  GET api/plans/templates:
&emsp; this endopint returns the list of the following objects:
&emsp; { <br />
&emsp;&emsp; "id" : "{trainingPlanId}", <br />
&emsp;&emsp; "name" : "{trainingPlanName}", <br />
&emsp;&emsp; "startdate" : "{yyyy-mm-dd}", <br />
&emsp;&emsp; "durationofcycle" : "{durationOfCycle}" (Integer), <br />
&emsp;&emsp; "planstatus" : "{planStatus}", <br />
&emsp;&emsp; "trainingmethodid" : "{trainingMethodId}", <br />
&emsp;&emsp; "trainingdays" : { trainingDays }, <br />
&emsp; }

- ####  POST api/plans/{templateId}/assign:
&emsp; this endpoint assigns training plan template using templateId <br />
&emsp; and requires header {"Authorization" : "Bearer {JWT}"}

- ####  POST api/plans/custom:
&emsp; this endopint requires the following body:
&emsp; { <br />
&emsp;&emsp; "id" : "{trainingPlanId}", <br />
&emsp;&emsp; "name" : "{trainingPlanName}", <br />
&emsp;&emsp; "startdate" : "{yyyy-mm-dd}", <br />
&emsp;&emsp; "durationofcycle" : "{durationOfCycle}" (Integer), <br />
&emsp;&emsp; "planstatus" : "{planStatus}", <br />
&emsp;&emsp; "trainingmethodid" : "{trainingMethodId}", <br />
&emsp;&emsp; "trainingdays" : { trainingDays }, <br />
&emsp; } <br />
&emsp; and a header {"Authorization" : "Bearer {JWT}"}

- ####  GET api/plans/active:
&emsp; This endpoint requires a header {"Authorization" : "Bearer {JWT}" <br />
&emsp; and returns the following body with the active training plan: <br />
&emsp; { <br />
&emsp;&emsp; "id" : "{trainingPlanId}", <br />
&emsp;&emsp; "name" : "{trainingPlanName}", <br />
&emsp;&emsp; "startdate" : "{yyyy-mm-dd}", <br />
&emsp;&emsp; "durationofcycle" : "{durationOfCycle}" (Integer), <br />
&emsp;&emsp; "planstatus" : "{planStatus}", <br />
&emsp;&emsp; "trainingmethodid" : "{trainingMethodId}", <br />
&emsp;&emsp; "trainingdays" : { trainingDays }, <br />
&emsp; } <br />

- ####  POST api/plans/active/complete:
&emsp; This endpoint requires a header {"Authorization" : "Bearer {JWT}" <br />
&emsp; and requires the following body: <br />
&emsp; { <br />
&emsp;&emsp; "trackingnutrition" : "{true/false}", <br />
&emsp;&emsp; "trackingsleep" : "{true/false}", <br />
&emsp;&emsp; "averagehoursofsleep" : "{averageHoursOfSleep}" (Double), <br />
&emsp;&emsp; "personalevaluation" : "{personalEvaluation}" (Integer), <br />
&emsp; } <br />

- ####  POST api/plans/active/cancel:
&emsp; This endpoint requires a header {"Authorization" : "Bearer {JWT}". <br />

- ####  POST api/executed-sets:
&emsp; This endpoint requires a header {"Authorization" : "Bearer {JWT}" <br />
&emsp; and requires a body containing the list of following objects: <br />
&emsp; { <br />
&emsp;&emsp; "plannedexerciseid" : "{plannedExerciseId}", <br />
&emsp;&emsp; "setnumber" : "{setNumber}" (Integer), <br />
&emsp;&emsp; "executedreps" : "{executedReps}" (Integer), <br />
&emsp;&emsp; "weightused" : "{weightUsed}" (Double), <br />
&emsp; } <br />

- ####  GET api/user/me:
&emsp; This endpoint requires a header {"Authorization" : "Bearer {JWT}" <br />
&emsp; and returns the following body: <br />
&emsp; { <br />
&emsp;&emsp; "id" : "{userId}", <br />
&emsp;&emsp; "username" : "{username}", <br />
&emsp;&emsp; "email" : "{email}", <br />
&emsp;&emsp; "role" : "{role}", <br />
&emsp;&emsp; "status" : "{userStatus}", <br />
&emsp;&emsp; "createdate" : "{yyyy-mm-dd}", <br />
&emsp;&emsp; "completedtrainingplanscount" : "{completedTrainingPlansCount}", <br />
&emsp;&emsp; "createdtrainingplanscount" : "{createdTrainingPlansCount}", <br />
&emsp; } <br />

- ####  GET api/user/records:
&emsp; This endpoint requires a header {"Authorization" : "Bearer {JWT}" <br />
&emsp; and returns the following body: <br />
&emsp; { <br />
&emsp;&emsp; "exerciseid" : "{exerciseId}", <br />
&emsp;&emsp; "exercisename" : "{exerciseName}", <br />
&emsp;&emsp; "currentonerepmax" : "{currentOneRepMax}", <br />
&emsp;&emsp; "lastupdatedate" : "{yyyy-mm-dd}", <br />
&emsp;&emsp; "isbodyweight" : "{true/false}", <br />
&emsp; } <br />

- ####  GET api/user/plans/history:
&emsp; This endpoint requires a header {"Authorization" : "Bearer {JWT}" <br />
&emsp; and returns the body containing the following objects: <br />
&emsp; { <br />
&emsp;&emsp; "id" : "{trainingPlanId}", <br />
&emsp;&emsp; "name" : "{trainingPlanName}", <br />
&emsp;&emsp; "startdate" : "{yyyy-mm-dd}", <br />
&emsp;&emsp; "durationofcycle" : "{durationOfCycle}" (Integer), <br />
&emsp;&emsp; "planstatus" : "{planStatus}", <br />
&emsp;&emsp; "trainingmethodid" : "{trainingMethodId}", <br />
&emsp;&emsp; "trainingdays" : { trainingDays }, <br />
&emsp; } <br />

- ####  DELETE api/user/me:
&emsp; This endpoint requires a header {"Authorization" : "Bearer {JWT}". <br />

###       2. Standard Usage

The standard usage of this application was designed for Docker in version 28.1.1.

Docker-Compose file attached to this project contains of: <br />
-- Server application container built with Dockerfile,<br />
-- Database container using MySQL:8.0 image,<br />
-- Database administration GUI using Adminer:latest image

To start the application user needs to perform "docker compose up -d --build"<br />
to start the application environment without viewing the logs in the user's console.

To start the application user with the ability to view the logs in the user's console<br />
the preferred command to perform is "docker compose up --build".<br />
To escape log-viewing application environment mode without turning it down<br /> 
user needs to use the CTRL+C keys combination. 

To turn down the application environment user needs to perform the<br /> 
"docker compose down" command.

To access the server application user needs to use the **http:\\localhost:8080** address.

To access the Adminer application user needs to use the **http:\\localhost:8081** address.

###        3. Tests Usage

Application's unit and integration tests were designed using the following platforms:<br />
--Java IDE 17,<br />
--Springboot 3.5.6,<br />
--InteliJ IDEA Community Edition 2024.1.2,<br />
--XAMPP control panel v.3.3.0.<br />
The rest of required dependencies can be found in the build.gradle file.

To perform tests in the "src/test/*" packages it is suggested to start<br />
the Apache server and MySQL database to test the functionalities of the application.

Alternatively, user can use the MySQL Docker container using the 3306 port.

To simplify the tests usage it is suggested to perform them with InteliJ IDEA.