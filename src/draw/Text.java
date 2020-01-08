import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

@SuppressWarnings({"SpellCheckingInspection", "CanBeFinal", "unused"})
public class Text implements Serializable {
    private String txt;
    private int x;
    private int y;
    private Color c;
    private boolean marked;
    private boolean selected=false;
    private boolean polyline;
    private Rectangle2D snapRec;
    private boolean contains;
    private boolean copied;

    //constructor
    public Text(String t, int x, int y, Color c) {
        this.txt=t;
        this.x=x;
        this.y=y;
        this.c=c;
    }

    //getters and setters
    public int getx() {
        return this.x;
    }
    public int gety() {
        return this.y;
    }
    public void setx(int x) {
        this.x=x;
    }
    public void sety(int y) {
        this.y=y;
    }
    public void setCol(Color c) {
        this.c=c;
    }
    public void markedOn() {
        this.marked=true;
    }
    public void markedOff() {
        this.marked=false;
    }
    public void selectedOff() {
        this.selected=false;
    }
    public boolean isSelected() {
        return this.selected;
    }
    public boolean isMarked() {
        return this.marked;
    }
    public void selectedOn() {
        this.selected=true;
    }
    public void setSr(Rectangle2D r) {
        this.snapRec=r;
    }
    public void containsOn() {
        this.contains=true;
    }
    public void containsOff() {
        this.contains=false;
    }
    public boolean getContains() {
        return this.contains;
    }
    public Color getCol() {
        return this.c;
    }
    public void copiedOn() {
        this.copied=true;
    }
    public void copiedOff() {
        this.copied=false;
    }
    public boolean isCopied() {
        return this.copied;
    }
    public String getText() {
        return this.txt;
    }
    public Rectangle2D getSr() {
        return this.snapRec;
    }
}
