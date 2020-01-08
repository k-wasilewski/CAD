import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.Serializable;

@SuppressWarnings({"SpellCheckingInspection", "FieldCanBeLocal", "CanBeFinal", "unused", "rawtypes"})
public class ImageClass extends JPanel implements Serializable, ImageObserver {
    private transient java.awt.Image image;
    private transient java.awt.image.BufferedImage img;
    private int ximg;
    private int yimg;
    private Rectangle contour;
    private boolean selected;
    private boolean marked;
    private int width;
    private int height;
    private transient BufferedImage imgcont;
    private transient Graphics graphics;
    private int xr;
    private int yr;
    private int wr;
    private int hr;
    private Color col;
    private boolean back;
    private ArrayList<Rectangle> snapRecs;
    private boolean contains1;
    private boolean contains2;
    private boolean contains3;
    private boolean contains4;
    private Rectangle snapRec1;
    private Rectangle snapRec2;
    private Rectangle snapRec3;
    private Rectangle snapRec4;
    private boolean overImage;
    private boolean copied;
    private ImageIcon ii;

    //----------CONSTRUCTOR-------------------------------------------------------------------------------------
    public ImageClass(java.awt.Image image, BufferedImage img, int ximg, int yimg, Rectangle contour, Color col) {
        this.image=image;
        this.img=img;
        this.ximg=ximg;
        this.yimg=yimg;
        this.contour=contour;
        this.col=col;
        if (ii!=null) width=ii.getIconWidth(); else width=img.getWidth();
        if (ii!=null) height=ii.getIconHeight(); else height=img.getHeight();
        //noinspection unchecked,rawtypes
        snapRecs=new ArrayList();
        
        //............snapRecs.................................
        snapRec1 = new Rectangle(this.ximg-8, this.ximg+8, this.yimg-8, this.yimg+8, Color.BLACK);
        snapRec2 = new Rectangle(this.ximg+width-8, this.ximg+width+8, this.yimg-8, this.yimg+8, Color.BLACK);
        snapRec3 = new Rectangle(this.ximg-8, this.ximg+8, this.yimg+height-8, this.yimg+height+8, Color.BLACK);
        snapRec4 = new Rectangle(this.ximg+width-8, this.ximg+width+8, this.yimg+height-8, this.yimg+height+8, Color.BLACK);
        snapRecs.add(snapRec1);     
        snapRecs.add(snapRec2);
        snapRecs.add(snapRec3);
        snapRecs.add(snapRec4);

        //..........image and contour on the same layer............
        imgcont=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        paint();
    }

    //-------------UPDATE XIMG AND YIMG IN SNAPRECS--------------------------------------------------------------
    public void updatesnapRecs() {
        snapRecs.set(0, new Rectangle(this.ximg-8, this.ximg+8, this.yimg-8, this.yimg+8, Color.BLACK));
        snapRecs.set(1, new Rectangle(this.ximg+width-8, this.ximg+width+8, this.yimg-8, this.yimg+8, Color.BLACK));
        snapRecs.set(2, new Rectangle(this.ximg-8, this.ximg+8, this.yimg+height-8, this.yimg+height+8, Color.BLACK));
        snapRecs.set(3, new Rectangle(this.ximg+width-8, this.ximg+width+8, this.yimg+height-8, this.yimg+height+8, Color.BLACK));
    }

    //---------------PAINT THE IMG AND ITS CONTOUR ON BUFFEREDIMAGE-----------------------------------------------
    public void paint() {
        graphics=imgcont.createGraphics();
        if (ii!=null) ii.paintIcon(null, graphics, 0, 0);
        //...........draw image.....................
        graphics.drawImage(img, 0, 0, null);
        //...........draw marked-on contour........
        if (marked) {
            graphics.setColor(Color.GRAY);
            graphics.drawRect(0, 0, width-1, height-1);
        //..........draw contour................
        } else {
            graphics.setColor(Color.BLACK);
        }
        //.........draw bold contour.............
        if (selected) {
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, 0, width-1, height-1);
            graphics.drawRect(+1, +1, width-3, height-3);
            graphics.drawRect(+2, +2, width-5, height-5);
        }
    }

    //-----------WHEN READING SERIALIZED FILE - CONVERTING IMAGEICON TO BUFFEREDIMAGE-----------------------
    public void iiToBuffImg(ImageIcon ii) {
        imgcont=new BufferedImage(ii.getIconWidth(), ii.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        paint();
    }

    //----------GETTERS AND SETTERS--------------------------------------------------------------------------
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
    public Rectangle getSr1() {
        return this.snapRecs.get(0);
    }
    public Rectangle getSr2() {
        return this.snapRecs.get(1);
    }
    public Rectangle getSr3() {
        return this.snapRecs.get(2);
    }
    public Rectangle getSr4() {
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
    public void copiedOn() {this.copied=true;}
    public void copiedOff() {
        this.copied=false;
    }
    public boolean isCopied() {return this.copied;}

    //SERIALIZATION
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();   //all non-transient fields
        out.writeObject(new ImageIcon(imgcont));    //custom serialization BufferedImage (imgCont) -> ImageIcon
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); //all non-transient fields
        ii = (ImageIcon) in.readObject();   //...
        iiToBuffImg(ii);    //custom deserialization ImageIcon -> BufferedImage (imgCont)
        }
    }

