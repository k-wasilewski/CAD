package draw;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Circle implements Serializable {
    private int x;
    private int y;
    private int r;
    private Color c;
    private boolean marked;
    private boolean selected=false;
    private Rectangle2D snapRec;
    private boolean contains;
    private boolean copied;
    
    public Circle(int x, int y, int r, Color c) {
        this.x=x;
        this.y=y;
        this.r=r;
        this.c=c;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getR() {
        return r;
    }
    public void setX(int x) {
        this.x=x;
    }
    public void setY(int y) {
        this.y=y;
    }
    public void setR(int r) {
        this.r=r;
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
    public boolean isSelected() {
        return this.selected;
    }
    public void selectedOff() {
        this.selected=false;
    }
    public boolean isMarked() {
        return this.marked;
    }
    public void selectedOn() {
        this.selected=true;
    }
    public Rectangle2D getSnapRec() {
        return this.snapRec;
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
    public void setSr(Rectangle2D sr) {
        this.snapRec=sr;
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
}
