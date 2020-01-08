import java.util.ArrayList;
import java.io.Serializable;

//serializing drawn objects thru Cvs class
@SuppressWarnings("CanBeFinal")
public class Cvs implements Serializable {
    private ArrayList<Line> lines;
    private ArrayList<Circle> circles;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<ImageClass> imageClasses;
    private ArrayList<Text> texts;

    //constructor
    public Cvs(ArrayList<Line> lines, ArrayList<Circle> circles, ArrayList<Rectangle> rectangles,
               ArrayList<ImageClass> imageClasses, ArrayList<Text> texts) {
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
    public ArrayList<Circle> getCircles() {
        return this.circles;
    }
    public ArrayList<Rectangle> getRectangles() {
        return this.rectangles;
    }
    public ArrayList<ImageClass> getImageClasses() {
        return this.imageClasses;
    }
    public ArrayList<Text> getTexts() {
        return this.texts;
    }
}
