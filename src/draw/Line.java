package draw;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public class Line implements Serializable {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Color c;
    private Color ch;
    private boolean marked;
    private boolean selected=false;
    private boolean polyline;
    private Rectangle2D snapRec1;
    private Rectangle2D snapRec2;
    private boolean contains1;
    private boolean contains2;
    private boolean copied;
    private boolean coloured;
    private ArrayList<Rectangle2D> snapRecs=new ArrayList();
    private boolean horizontal;
    private ArrayList<Boolean> srContains=new ArrayList();
    
    public Line(int x1, int x2, int y1, int y2, Color c, boolean p) {
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        this.c=c;
        this.ch=c;
        this.polyline=p;
    }
    public Line(int x1, int x2, int y1, int y2, Color c, boolean p, boolean h) {
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        this.c=c;
        this.ch=c;
        this.polyline=p;
        this.horizontal=h;
    }
    public int getx1() {
        return this.x1;
    }
    public int getx2() {
        return this.x2;
    }
    public int gety1() {
        return this.y1;
    }
    public int gety2() {
        return this.y2;
    }
    public void setx1(int x) {
        this.x1=x;
    }
    public void setx2(int x) {
        this.x2=x;
    }
    public void sety1(int y) {
        this.y1=y;
    }
    public void sety2(int y) {
        this.y2=y;
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
    public boolean isPolyline() {
        return polyline;
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
    public void setSr1(Rectangle2D r) {
        this.snapRec1=r;
    }
    public Rectangle2D getSr1() {
        return this.snapRec1;
    }
    public void contains1on() {
        this.contains1=true;
    }
    public void contains1off() {
        this.contains1=false;
    }
    public void setSr2(Rectangle2D r) {
        this.snapRec2=r;
    }
    public Rectangle2D getSr2() {
        return this.snapRec2;
    }
    public void contains2on() {
        this.contains2=true;
    }
    public void contains2off() {
        this.contains2=false;
    }
    public void polylineOff() {
        this.polyline=false;
    }
    public boolean getContains1() {
        return this.contains1;
    }
    public boolean getContains2() {
        return this.contains2;
    }
    public Color getCol() { return this.c; }
    public Color getColH() { return this.ch; }
    public boolean isPoly() {
        return this.polyline;
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
    public void setColoured() {this.coloured=true;}
    public boolean getColoured() {return this.coloured;}
    public void addSnapRec(Rectangle2D r) {
        this.snapRecs.add(r);
        this.srContains.add(false);
    }
    public ArrayList<Rectangle2D> getSnapRecs() {return this.snapRecs;}
    public boolean isHorizontal() {return this.horizontal;}
    public void srContainsOn(int i) {this.srContains.set(i, true);}
    public void srContainsOff(int i) {this.srContains.set(i, false);}
    public boolean getSrContains(int i) {return this.srContains.get(i);}
}
