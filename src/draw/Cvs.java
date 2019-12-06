package draw;

import java.util.ArrayList;
import java.io.Serializable;

//serializing drawn objects thru Cvs class
public class Cvs implements Serializable {
    private ArrayList<Line> lines;
    private ArrayList<draw.Circle> circles;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<draw.ImageClass> imageClasses;
    private ArrayList<Text> texts;
    private int n;

    //constructor
    public Cvs(ArrayList<Line> lines, ArrayList<draw.Circle> circles, ArrayList<Rectangle> rectangles,
               ArrayList<draw.ImageClass> imageClasses, ArrayList<Text> texts) {
        this.lines = lines;
        this.circles = circles;
        this.rectangles = rectangles;
        this.imageClasses = imageClasses;
        this.texts = texts;
    }

    //getters and setters
    public ArrayList<Line> getLines() {
        return this.lines;
    }
    public ArrayList<draw.Circle> getCircles() {
        return this.circles;
    }
    public ArrayList<Rectangle> getRectangles() {
        return this.rectangles;
    }
    public ArrayList<draw.ImageClass> getImageClasses() {
        return this.imageClasses;
    }
    public ArrayList<Text> getTexts() {
        return this.texts;
    }
}
