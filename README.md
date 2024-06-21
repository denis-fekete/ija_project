# Simple 2D robot collision simulator

### Author: Denis Fekete (xfeket01@fit.vutbr.cz)

### Quick summary
This project is a simulation of robots and rectangle-shaped obstacles in 2D 
space. Visualization of simulation is built as a GUI (graphical user interface) 
application and allows easy manipulation with objects within it.

### Build:
In current directory use commands (windows):
```
	mvn clean package
	java -jar .\target\ija_project-1.0-SNAPSHOT.jar
```
In current directory use commands (linux):
```
	mvn clean package
	java -jar target/target\ija_project-1.0-SNAPSHOT.jar
```

### Documentation:
In current directory use command to generate documentation:
```
	mvn javadoc:javadoc
```
In directory target\site\apidocs (linux target/site/apidocs) open index.html

### Sources used in project:
	Line circle intersect: https://www.youtube.com/watch?v=ebq7L2Wtbl4
	Rectangle-rectangle collision: https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
