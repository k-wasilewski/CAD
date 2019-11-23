package draw;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Rectangle implements Serializable {
    private int x1r;
    private int x2r;
    private int y1r;
    private int y2r;
    private Color c;
    private boolean marked;
    private boolean selected=false;
    private ImageClass imageClass=null;
    private Rectangle2D snapRec1;
    private Rectangle2D snapRec2;
    private Rectangle2D snapRec3;
    private Rectangle2D snapRec4;
    private boolean contains1;
    private boolean contains2;
    private boolean contains3;
    private boolean contains4;
    private boolean copied;
    
    public Rectangle(int x1, int x2, int y1, int y2, Color c) {
        this.x1r=x1;
        this.x2r=x2;
        this.y1r=y1;
        this.y2r=y2;
        this.c=c;
    }
    public int getx1() {
        return this.x1r;
    }
    public int getx2() {
        return this.x2r;
    }
    public int gety1() {
        return this.y1r;
    }
    public int gety2() {
        return this.y2r;
    }
    public void setx1(int x) {
        this.x1r=x;
    }
    public void setx2(int x) {
        this.x2r=x;
    }
    public void sety1(int y) {
        this.y1r=y;
    }
    public void sety2(int y) {
        this.y2r=y;
    }
    public void setCol(Color c) {
        this.c=c;
    }
    public void setImageClass(ImageClass ic) {
        this.imageClass=ic;
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
    public ImageClass getImageClass() {
        return this.imageClass;
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
    public void setSr3(Rectangle2D r) {
        this.snapRec3=r;
    }
    public Rectangle2D getSr3() {
        return this.snapRec3;
    }
    public void contains3on() {
        this.contains3=true;
    }
    public void contains3off() {
        this.contains3=false;
    }
    public void setSr4(Rectangle2D r) {
        this.snapRec4=r;
    }
    public Rectangle2D getSr4() {
        return this.snapRec4;
    }
    public void contains4on() {
        this.contains4=true;
    }
    public void contains4off() {
        this.contains4=false;
    }
    public boolean getContains1() {
        return this.contains1;
    }
    public boolean getContains2() {
        return this.contains2;
    }
    public boolean getContains3() {
        return this.contains3;
    }
    public boolean getContains4() {
        return this.contains4;
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
