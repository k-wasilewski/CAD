package draw;

import java.util.ArrayList;
import java.io.Serializable;

public class Cvs implements Serializable {
    private ArrayList<Line> lines;
    private ArrayList<Circle> circles;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<ImageClass> imageClasses;

    public Cvs(ArrayList<Line> lines, ArrayList<Circle> circles, ArrayList<Rectangle> rectangles,
               ArrayList<ImageClass> imageClasses) {
        this.lines=lines;
        this.circles=circles;
        this.rectangles=rectangles;
        this.imageClasses=imageClasses;
    }
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
}
