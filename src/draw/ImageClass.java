/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics;
import java.io.Serializable;


/**
 *
 * @author lenovo
 */
public class ImageClass implements Serializable {
    private java.awt.Image image;
    private java.awt.image.BufferedImage img;
    private int ximg=0;
    private int yimg=0;
    private Rectangle contour;
    private boolean selected;
    private boolean marked;
    private int width;
    private int height;
    private BufferedImage imgcont;
    private Graphics2D graphics;
    private int xr;
    private int yr;
    private int wr;
    private int hr;
    private Color col;
    private boolean back;
    private ArrayList<Rectangle2D> snapRecs;
    private boolean contains1;
    private boolean contains2;
    private boolean contains3;
    private boolean contains4;
    private Rectangle2D snapRec1;
    private Rectangle2D snapRec2;
    private Rectangle2D snapRec3;
    private Rectangle2D snapRec4;
    private boolean overImage;
    private boolean copied;
    
    public ImageClass(java.awt.Image image, BufferedImage img, int ximg, int yimg, Rectangle contour, Color col) {
        this.image=image;
        this.img=img;
        this.ximg=ximg;
        this.yimg=yimg;
        this.contour=contour;
        this.col=col;
        width=img.getWidth();
        height=img.getHeight();
        snapRecs=new ArrayList();
        
        //snap
        snapRec1 = new Rectangle2D.Double(this.ximg-8, this.yimg-8, 16, 16); 
        snapRec2 = new Rectangle2D.Double(this.ximg+width-8, this.yimg-8, 16, 16); 
        snapRec3 = new Rectangle2D.Double(this.ximg-8, this.yimg+height-8, 16, 16); 
        snapRec4 = new Rectangle2D.Double(this.ximg+width-8, this.yimg+height-8, 16, 16);
        snapRecs.add(snapRec1);     
        snapRecs.add(snapRec2);
        snapRecs.add(snapRec3);
        snapRecs.add(snapRec4);
        
        int x1rec=contour.getx1();
        int x2rec=contour.getx2();
        int y1rec=contour.gety1();
        int y2rec=contour.gety2();

            if (x2rec>x1rec) {
                xr=x1rec;
                wr=x2rec-x1rec;
            } else { 
                xr=x2rec;
                wr=x1rec-x2rec;
            }
            if (y2rec>y1rec) {
                yr=y1rec;
                hr=y2rec-y1rec;
            } else {
                yr=y2rec;
                hr=y1rec-y2rec;
            }
            
        imgcont=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        paint();
    }
    public void updatesnapRecs() {//update ximg and yimg in snapRecs
        snapRecs.get(0).setRect(new Rectangle2D.Double(this.ximg-8, this.yimg-8, 16, 16)); 
        snapRecs.get(1).setRect(new Rectangle2D.Double(this.ximg+width-8, this.yimg-8, 16, 16)); 
        snapRecs.get(2).setRect(new Rectangle2D.Double(this.ximg-8, this.yimg+height-8, 16, 16)); 
        snapRecs.get(3).setRect(new Rectangle2D.Double(this.ximg+width-8, this.yimg+height-8, 16, 16));
    }
    public void paint() {
        graphics=imgcont.createGraphics();
        graphics.setBackground(col);
        graphics.drawImage(img, 0, 0, null);
        if (marked) {
            graphics.setColor(Color.GRAY);
            graphics.drawRect(0, 0, width-1, height-1);
        } else {
            graphics.setColor(Color.BLACK);
        }
        if (selected) {
            graphics.setColor(Color.BLACK);
            graphics.setStroke(new BasicStroke(3));
            graphics.drawRect(0, 0, width-1, height-1);
            graphics.setStroke(new BasicStroke(1));
        }
    }
    public java.awt.Image getImage() {
        return this.image;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public void setWidth(int w) {
        this.width=w;
    }
    public void setHeight(int h) {
        this.height=h;
    }
    public void markedOn() {
        this.marked=true;
    }
    public void markedOff() {
        this.marked=false;
    }
    public Rectangle getContour() {
        return this.contour;
    }
    public Graphics getGraphics() {
        return this.graphics;
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
    public void setXimg(int x) {
        this.ximg=x;
    }
    public void setYimg(int y) {
        this.yimg=y;
    }
    public int getXimg() {
        return this.ximg;
    }
    public int getYimg() {
        return this.yimg;
    }
    public Rectangle2D getSr1() {
        return this.snapRecs.get(0);
    }
    public Rectangle2D getSr2() {
        return this.snapRecs.get(1);
    }
    public Rectangle2D getSr3() {
        return this.snapRecs.get(2);
    }
    public Rectangle2D getSr4() {
        return this.snapRecs.get(3);
    }
    public void contains1on() {
        this.contains1=true;
    }
    public void contains1off() {
        this.contains1=false;
    }
    public void contains2on() {
        this.contains2=true;
    }
    public void contains2off() {
        this.contains2=false;
    }
    public void contains3on() {
        this.contains3=true;
    }
    public void contains3off() {
        this.contains3=false;
    }
    public void contains4on() {
        this.contains4=true;
    }
    public void contains4off() {
        this.contains4=false;
    }
    public void backOn() {
        this.back=true;
    }
    public void backOff() {
        this.back=false;
    }
    public java.awt.image.BufferedImage getImg() {
        return this.img;
    }
    public BufferedImage getImgCont() {
        return this.imgcont;
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
    public void overImageOn() {
        this.overImage=true;
    }
    public void overImageOff() {
        this.overImage=false;
    }
    public Color getCol() {
        return this.col;
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
