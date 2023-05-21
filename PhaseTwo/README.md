**How to build the project:**
1. Clone the repository
```
git clone git@csil-git1.cs.surrey.sfu.ca:cmpt276s23_group1/group-project.git
cd  group-project/PhaseTwo
```
2. Build the project with Maven (also executes tests):
```
mvn package
```

**How to run the project:**
- Run the .jar with java:
```
java -jar target\PhaseTwo-1.0-SNAPSHOT.jar HeistGame.java
```
- The game should create its own separate window and run on your computer
- Navigate the menu using "W", "S", and "Enter" keys
- When playing the game, there are instruction in a small window at the bottom of the screen


**How to test the project independently of build:**
- Test the project with Maven:

```
mvn test
```
- This runs all unit tests

**How to create and navigate documentation:**
- Create project documentation with Maven:

```
mvn site
mvn javadoc:javadoc
```
- Navigate to target/site/apidocs/allclasses-index.html
- Open the html file in your browser of choice
- The documentation should open in your browser

Link to video: https://www.youtube.com/watch?v=KUTg42o_Vy4