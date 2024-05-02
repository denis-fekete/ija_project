/**
 * Implementation of SaveManager class. SaveManger is class for storing and
 * loading Simulator space with its objects into a human-readable form (XML)
 *
 * @author Denis Fekete (xfeket01@fit.vutbr.cz)
 */

package ija.ija2023.ija_project.JavaSpecific;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import javafx.stage.Stage;

public class SaveManager {
    /**
     * Reference to the Simulator where objects will be stored/read from
     */
    Simulator simulator;

    /**
     * Reference to the main window stage for popup windows
     */
    Stage mainWindowStage;

    /**
     * Constructor of SaveManger
     * @param simulator Simulator object to manipulate simulation
     * @param mainWindowStage Stage of main window to show popup windows and warnings
     */
    public SaveManager(Simulator simulator, Stage mainWindowStage)
    {
        this.simulator = simulator;
        this.mainWindowStage = mainWindowStage;
    }

    /**
     * Loads simulation from xml file to current simulation
     * @throws ParserConfigurationException
     */
    public void loadFromFile() throws ParserConfigurationException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        // get current directory of app
        File currentDir = new File(System.getProperty("user.dir") + "/data");

        // create file chooser to open dialog menu for choosing files and set default directory
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(currentDir);

        // open file chooser
        File file = fileChooser.showOpenDialog(mainWindowStage);

        // open file as document
        Document doc;
        try {
            doc = builder.parse(file);
        }
        catch (Exception e)
        {
            parsingError("XML file couldn't be opened");
            return;
        }


        Element root = doc.getDocumentElement();
        if(!root.getTagName().equals("Simulation_space"))
        {
            parsingError("XML file doesn't contain <Simulation_space>");
            return;
        }

        // check if simulation has dimensions
        if(root.getAttribute("width").isEmpty())
        {
            parsingError("<Simulation_space> does not contain width attribute");
            return;
        }
        if(root.getAttribute("height").isEmpty())
        {
            parsingError("<Simulation_space> does not contain height attribute");
            return;
        }

        double newWidth = Double.parseDouble(root.getAttribute("width"));
        double newHeight = Double.parseDouble(root.getAttribute("height"));

        simulator.setSize(newWidth, newHeight);

        NodeList nodes = root.getChildNodes();

        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if(element.getTagName().equals("Robots"))
                {
                    if(!readRobots(element))
                    {
                        parsingError("Unknown element found in <Robots>");
                        return;
                    }
                }
                else if (element.getNodeName().equals("Obstacles"))
                {
                    if(!readObstacles(element))
                    {
                        parsingError("Unknown element found in <Obstacles>");
                        return;
                    }
                }
                else
                {
                    parsingError("Unknown element found in <Simulation_space>");
                    return;
                }
            }

        }
    }

    /**
     * Reads <Robots> from xml file
     * @param root Root/parent element
     * @return True on success, false on error
     */
    private boolean readRobots(Element root)
    {
        NodeList nodes = root.getChildNodes();

        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                if(node.getNodeName().equals("Robot"))
                {
                    if(!readRobot((Element) node))
                    {
                        return false;
                    }
                }
                else
                {
                    return false; // TODO:
                }
            }

        }

        return true;
    }

    /**
     * Reads single <Robot> from xml file
     * @param root Root/parent element
     * @return True on success, false on error
     */
    private boolean readRobot(Element root)
    {
        NodeList nodes = root.getChildNodes();
        double x = -1;
        double y = -1;
        double radius = -1;
        double detRad = -1;
        double rot = 0;
        int colorRed = -1;
        int colorGreen = -1;
        int colorBlue = -1;
        double speed = -1;
        double turnAngle = 0;
        int turnDirection = 1;
        boolean spinClockwise = false;
        boolean spinAnticlockwise = false;
        double desiredAngle = 0;

        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element element = (Element) node;

            switch (element.getTagName())
            {
                case "x": x = Double.parseDouble(element.getTextContent());
                    break;
                case "y": y = Double.parseDouble(element.getTextContent());
                    break;
                case "radius": radius = Double.parseDouble(element.getTextContent());
                    break;
                case "detection_radius": detRad = Double.parseDouble(element.getTextContent());
                    break;
                case "speed": speed = Double.parseDouble(element.getTextContent());
                    break;
                case "turn_angle": turnAngle = Double.parseDouble(element.getTextContent());
                    break;
                case "turn_direction": turnDirection = Integer.parseInt(element.getTextContent());
                    break;
                case "rotation": rot = Double.parseDouble(element.getTextContent());
                    break;
                case "color_red": colorRed = Integer.parseInt(element.getTextContent());
                    break;
                case "color_green": colorGreen = Integer.parseInt(element.getTextContent());
                    break;
                case "color_blue": colorBlue = Integer.parseInt(element.getTextContent());
                    break;
                    // Manual robot specific ------------------------------------------------
                case "spin_clockwise": spinClockwise = Boolean.parseBoolean(element.getTextContent());
                    break;
                case "spin_anticlockwise": spinAnticlockwise = Boolean.parseBoolean(element.getTextContent());
                    break;
                case "desired_angle":desiredAngle = Double.parseDouble(element.getTextContent());
                    break;
                default:
                    return false;
            }
        }

    if(x >= 0 && y >= 0 && radius > 0 && detRad > 0 && colorRed >= 0
            && colorGreen >= 0 && colorBlue >= 0 && speed >= 0)
    {
        if(root.getAttribute("type").equals("automatic"))
        {
            simulator.addAutomaticRobot(x, y, radius, rot, detRad,
                    Color.color(colorRed/255.0, colorGreen/255.0, colorBlue/255.0),
                    speed, turnAngle, turnDirection);
        }
        else if(root.getAttribute("type").equals("manual"))
        {
            simulator.addManualRobot(x, y, radius, rot, detRad,
                    Color.color(colorRed / 255.0, colorGreen / 255.0, colorBlue / 255.0),
                    speed, turnAngle, spinClockwise, spinAnticlockwise, desiredAngle);
        }
        else
        {
            return false;
        }
    }
    else
    {
        return false;
    }


        return true;
    }

    /**
     * Reads <Obstacle> from xml file
     * @param root Root/parent element
     * @return True on success, false on error
     */
    boolean readObstacles(Element root)
    {
        NodeList nodes = root.getChildNodes();

        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                if(node.getNodeName().equals("Obstacle"))
                {
                    if(!readObstacle((Element) node))
                    {
                        return false;
                    }
                }
                else
                {
                    return false; // TODO:
                }
            }

        }

        return true;
    }

    /**
     * Reads <Obstacle> from xml file
     * @param root Root/parent element
     * @return True of success, false on error
     */
    boolean readObstacle(Element root)
    {
        NodeList nodes = root.getChildNodes();
        double x = -1;
        double y = -1;
        double w = -1;
        double h = -1;
        double rot = 0;
        int colorRed = -1;
        int colorGreen = -1;
        int colorBlue = -1;

        for(int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element element = (Element) node;

            switch (element.getTagName())
            {
                case "x": x = Double.parseDouble(element.getTextContent());
                    break;
                case "y": y = Double.parseDouble(element.getTextContent());
                    break;
                case "width": w = Double.parseDouble(element.getTextContent());
                    break;
                case "height": h = Double.parseDouble(element.getTextContent());
                    break;
                case "rotation": rot = Double.parseDouble(element.getTextContent());
                    break;
                case "color_red": colorRed = Integer.parseInt(element.getTextContent());
                    break;
                case "color_green": colorGreen = Integer.parseInt(element.getTextContent());
                    break;
                case "color_blue": colorBlue = Integer.parseInt(element.getTextContent());
                    break;
                default:
                    return false;
            }
        }

        if(x >= 0 && y >= 0 && w > 0 && h > 0 && colorRed >= 0 && colorGreen >= 0 && colorBlue >= 0)
        {
            simulator.addObstacle(x, y, w, h, rot,
                    Color.color(colorRed/255.0, colorGreen/255.0, colorBlue/255.0));
        }
        else
        {
            return  false;
        }


        return true;
    }

    /**
     * Saves current simulation state into a XML file
     * @throws ParserConfigurationException
     */
    public void saveToFile () throws ParserConfigurationException
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();

        // create <Simulation_space>
        Element root = doc.createElement("Simulation_space");

        // set <Simulation_space> attributes
        root.setAttribute("width", Double.toString(simulator.getSpaceWidth()));
        root.setAttribute("height", Double.toString(simulator.getSpaceHeight()));

        // add robots to root
        Element robots = loadRobotsFromScene(doc);
        root.appendChild(robots);

        // add obstacles to root
        Element obstacles = loadObstaclesFromScene(doc);
        root.appendChild(obstacles);

        // add root to document
        doc.appendChild(root);

        // get current directory of app
        File currentDir = new File(System.getProperty("user.dir") + "/data");

        // create file chooser to open dialog menu for choosing files and set default directory
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(currentDir);

        // open file chooser
        File file = fileChooser.showOpenDialog(mainWindowStage);

        Transformer transformer;
        try
        {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            parsingError("XML file for writing couldn't be opened");
        }
    }

    /**
     * Loads Robots from simulation and returns Element containing these
     * robots as sub elements
     * @param doc Parent Document object
     * @return Returns element containing all robots from simulation
     */
    Element loadRobotsFromScene(Document doc)
    {
        Element robots = doc.createElement("Robots");

        for(BaseRobot robot : simulator.getRobots())
        {
            Element robotElem = doc.createElement("Robot");

            Element x = doc.createElement("x");
            x.setTextContent(Double.toString(robot.getSim().getX()));
            robotElem.appendChild(x);

            Element y = doc.createElement("y");
            y.setTextContent(Double.toString(robot.getSim().getY()));
            robotElem.appendChild(y);

            Element radius = doc.createElement("radius");
            radius.setTextContent(Double.toString(robot.getSim().getRadius()));
            robotElem.appendChild(radius);

            Element detRad = doc.createElement("detection_radius");
            detRad.setTextContent(Double.toString(robot.getSim().getDetRadius()));
            robotElem.appendChild(detRad);

            Element rot = doc.createElement("rotation");
            rot.setTextContent(Double.toString(robot.getSim().getRotation()));
            robotElem.appendChild(rot);

            Element colorRed = doc.createElement("color_red");
            colorRed.setTextContent(Integer.toString((int) (robot.color.getRed() * 255)));
            robotElem.appendChild(colorRed);

            Element colorGreen = doc.createElement("color_green");
            colorGreen.setTextContent(Integer.toString((int) (robot.color.getGreen() * 255)));
            robotElem.appendChild(colorGreen);

            Element colorBlue = doc.createElement("color_blue");
            colorBlue.setTextContent(Integer.toString((int) (robot.color.getBlue() * 255)));
            robotElem.appendChild(colorBlue);

            Element speed = doc.createElement("speed");
            speed.setTextContent(Double.toString(robot.getSpeed()));
            robotElem.appendChild(speed);

            Element turnAngle = doc.createElement("turn_angle");
            turnAngle.setTextContent(Double.toString(robot.turnSpeed));
            robotElem.appendChild(turnAngle);

            Element turnDirection = doc.createElement("turn_direction");
            turnDirection.setTextContent(Integer.toString(robot.getTurnDirection()));
            robotElem.appendChild(turnDirection);

            if(robot instanceof AutoRobot)
            {
                robotElem.setAttribute("type", "automatic");
            }
            else
            {
                robotElem.setAttribute("type", "manual");
                ManualRobot mRobot = (ManualRobot) robot;
                Element spinClockwise = doc.createElement("spin_clockwise");
                spinClockwise.setTextContent(Boolean.toString(mRobot.isSpinClockwise()));
                robotElem.appendChild(spinClockwise);

                Element spinAnticlockwise  = doc.createElement("spin_anticlockwise");
                spinAnticlockwise.setTextContent(Boolean.toString(mRobot.isSpinAnticlockwise()));
                robotElem.appendChild(spinAnticlockwise);

                Element desiredAngle = doc.createElement("desired_angle");
                desiredAngle.setTextContent(Double.toString(mRobot.getDesiredAngle()));
                robotElem.appendChild(desiredAngle);
            }

            robots.appendChild(robotElem);
        }

        return robots;
    }

    /**
     * Loads Obstacle from simulation and returns Element containing these
     * robots as sub elements
     * @param doc Parent Document object
     * @return Returns element containing all obstacles from simulation
     */
    Element loadObstaclesFromScene(Document doc)
    {
        Element obstacles = doc.createElement("Obstacles");

        for(Obstacle obstacle : simulator.getObstacles())
        {
            Element obstacleElem = doc.createElement("Obstacle");

            Element x = doc.createElement("x");
            x.setTextContent(Double.toString(obstacle.getSim().getX()));
            obstacleElem.appendChild(x);

            Element y = doc.createElement("y");
            y.setTextContent(Double.toString(obstacle.getSim().getY()));
            obstacleElem.appendChild(y);

            Element w = doc.createElement("width");
            w.setTextContent(Double.toString(obstacle.getSim().getWidth()));
            obstacleElem.appendChild(w);

            Element h = doc.createElement("height");
            h.setTextContent(Double.toString(obstacle.getSim().getHeight()));
            obstacleElem.appendChild(h);

            Element rot = doc.createElement("rotation");
            rot.setTextContent(Double.toString(obstacle.getSim().getRotation()));
            obstacleElem.appendChild(rot);

            Element colorRed = doc.createElement("color_red");
            colorRed.setTextContent(Integer.toString((int) (obstacle.color.getRed() * 255)));
            obstacleElem.appendChild(colorRed);

            Element colorGreen = doc.createElement("color_green");
            colorGreen.setTextContent(Integer.toString((int) (obstacle.color.getGreen() * 255)));
            obstacleElem.appendChild(colorGreen);

            Element colorBlue = doc.createElement("color_blue");
            colorBlue.setTextContent(Integer.toString((int) (obstacle.color.getBlue() * 255)));
            obstacleElem.appendChild(colorBlue);


            obstacles.appendChild(obstacleElem);
        }

        return obstacles;
    }

    /**
     * Opens waring popup window with given message
     * @param text Message that window will contain
     */
    public void parsingError(String text)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Failed to load file");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
