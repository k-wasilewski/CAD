package draw;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Shape;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import java.util.Iterator;
import java.util.LinkedList;

public class Canvas extends JPanel implements MouseListener, ActionListener, MouseWheelListener {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private ArrayList<Line> lines = new ArrayList();
    private ArrayList<Circle> circles = new ArrayList();
    private ArrayList<Rectangle> rectangles = new ArrayList();
    private String input="null";
    private String inputH="null";
    private boolean drawing=false;
    private Color dCol=Color.BLACK;
    private Color bCol=Color.WHITE;
    private boolean ortoX;
    private boolean ortoY;
    private int xo;
    private int yo;
    private int r;
    private int xd;
    private int yd;
    private int d;
    private int x1r;
    private int x2r;
    private int y1r;
    private int y2r;
    private int x1s;
    private int x2s;
    private int y1s;
    private int y2s;
    private int dx;
    private int dy;
    private boolean selection=false;
    private static Timer timer=null;
    private int xs=0;
    private int ys=0;
    private int ws=0;
    private int hs=0;
    private Rectangle2D.Double selRec;
    private int xr;
    private int yr;
    private int wr;
    private int hr;
    private int screenx;
    private int screeny;
    private int x1rec;
    private int y1rec;
    private int x2rec;
    private int y2rec;
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private double xOffset=1;
    private double yOffset=1;
    private boolean moving=false;
    private int x1m;
    private int x2m;
    private int y1m;
    private int y2m;
    private int dxm;
    private int dym;
    private int x01;
    private int x02;
    private int y01;
    private int y02;
    private int x0r;
    private int y0r;
    private int w0r;
    private int h0r;
    private boolean movingC=false;
    private int x2sh;
    private int y2sh;
    private int x2mh;
    private int y2mh;
    private int xdyn;
    private int ydyn;
    private String dir;
    private String filename;
    private double xrec;
    private double yrec;
    private double xcirc;
    private Image image;
    private BufferedImage img;
    private Graphics2D g2;
    private int ximg=0;
    private int yimg=0;
    private double xcont;
    private double ycont;
    private Rectangle contour=null; 
    private ArrayList<ImageClass> imageClasses = new ArrayList();
    private double xRel;
    private double yRel;
    private double zoomDiv;
    private AffineTransform at;
    private Point2D p;
    private Point2D p2;
    private Point2D p3;
    private Point2D p1sel;
    private Point2D p2sel;
    private Point2D p1s;
    private Point2D pbs;
    private Point2D pam;
    private Point2D pbm;
    private Point2D pa;
    private Point2D pb;
    private Point2D pas;
    private AffineTransform atinverted;
    public boolean popup;
    private Robot robot;
    private int xsnap;
    private int ysnap;
    public static boolean snapMode;
    private boolean snapExecuted=false;
    private Rectangle2D contour2;
    private Shape contour666;
    private ImgPopup menu;
    private Popup menu1;
    private Line2D.Double l2d;
    private Rectangle2D.Double r2d;
    private boolean intersection1;
    private boolean intersection2;
    private Ellipse2D.Double c2d;
    private int xocirc;
    private int yocirc;
    private int rcirc;
    private Rectangle2D.Double contourSel;
    private BufferedImage bufferedImage;
    private Graphics2D g2d;
    private ImageClass imageClass;
    private boolean overImage = false;
    private int noOfOvers;
    private boolean copying;
    private boolean readyToCopy;
    private int x1c;
    private int y1c;
    private Point2D pac;
    private Point2D pbc;
    private int x2ch;
    private int x2c;
    private int y2ch;
    private int y2c;
    private int dxc;
    private int dyc;
    private ArrayList<Line> linesToCopy;
    private ArrayList<Circle> circlesToCopy;
    private ArrayList<ImageClass> imagesToCopy;
    private ArrayList<Rectangle> rectanglesToCopy;
    private ArrayList<Text> textsToCopy;
    private Line l2;
    private ImageClass i2;
    private Circle c2;
    private Rectangle r2;
    private boolean readyToMove;
    private static boolean readyToInputText;
    private tInput ti;
    private static String inputText;
    private ArrayList<Text> texts = new ArrayList();
    private static boolean readyToDrawText;
    private Rectangle2D txtRec;
    private Text t2;
    private Font font;
    private static boolean gridMode;
    private ArrayList<Line> gridLines = new ArrayList();
    private int gridSize;
    private int gridX;
    private int gridY;
    private Point2D pGrid1;
    private Point2D p2Grid1;
    private Point2D pGrid2;
    private Point2D p2Grid2;
    private AffineTransform atGrid;
    
    public Canvas() {   //the actual canvas is at (8, 54) 
        addMouseListener(this);
        addMouseWheelListener(this);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));    
        repaint();  
    }  
    
    //TIMER (DYNAMIC OPERATIONS)
    @Override
    @SuppressWarnings("empty-statement")
    public void actionPerformed(ActionEvent evt) {
        //zooming the coords dynamically
                if (MouseInfo.getPointerInfo().getLocation().x!=0&&MouseInfo.getPointerInfo().getLocation().y!=0) 
                    p = new Point2D.Double(Math.round((MouseInfo.getPointerInfo().getLocation().x-8-screenx)),     
                        Math.round((MouseInfo.getPointerInfo().getLocation().y-54-screeny)));
                p3 = new Point2D.Double();  //save file and save as and new file to code
                if (at!=atinverted) {
                    try {atinverted=at.createInverse();} catch (NoninvertibleTransformException hugf) {;}
                    at=atinverted;
                }    
                at.transform(p, p3);    
                xdyn=(int)p3.getX()+dx;    
                ydyn=(int)p3.getY()+dy;   
      
        if ((command("l")||command("pl"))&&x1!=0&&y1!=0) {
            if (ortoY) x2=x1;
            else x2=xdyn;
            if (ortoX) y2=y1;
            else y2=ydyn;
            repaint();
        } else if ((command("c"))&&xo!=0&&yo!=0) {
            r =(int)Math.sqrt(Math.pow((xdyn-xo), 2)+Math.pow((yo-ydyn), 2));
            repaint();
        } else if ((command("dist"))&&xd!=0&&yd!=0) {
            d =(int)Math.sqrt(Math.pow((xdyn-xd), 2)+Math.pow((yd-ydyn), 2));
            repaint();
        } else if ((command("rec"))&&x1r!=0&&y1r!=0) {   
            x2r=xdyn;
            y2r=ydyn;
            repaint();
        }
        
        //selection rec using timer
        if (selection) {
            safelyRepaint();
            if (MouseInfo.getPointerInfo().getLocation().x!=0&&MouseInfo.getPointerInfo().getLocation().y!=0)
            p = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x-8-screenx, 
                    MouseInfo.getPointerInfo().getLocation().y-54-screeny);
            p2sel = new Point2D.Double();   
            at.transform(p, p2sel);
            if (p1sel.getX()!=0&&p2sel.getY()!=0) {
                p1sel.setLocation(p1sel.getX()+dx, p1sel.getY()-dy);    //moving while selecting
                if (p2sel.getX()>p1sel.getX()) {
                    xs=(int)p1sel.getX();
                    ws=(int)p2sel.getX()-(int)p1sel.getX();
                } else {
                    xs=(int)p2sel.getX();
                    ws=(int)p1sel.getX()-(int)p2sel.getX();
                }
                if (p2sel.getY()>p1sel.getY()) {
                    ys=(int)p1sel.getY();
                    hs=(int)p2sel.getY()-(int)p1sel.getY();
                } else {
                    ys=(int)p2sel.getY();
                    hs=(int)p1sel.getY()-(int)p2sel.getY();
                }
            }
            repaint();
            dx=0;
            dy=0;
        }
        
        //moving objects
        if (movingC) moveC();   
        else if (moving) move();
        else if (copying) copy();
        
        snap();
        grid();
        repaint();
    }
  
    //DRAWING LOGIC 
    @Override
    @SuppressWarnings("empty-statement")
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        g2 = (Graphics2D) g;
        g.setColor(bCol);   //drawing image
        
        //open image
        if (!imageClasses.isEmpty()) for (ImageClass imageClass : imageClasses) {
            if (imageClass.getImage()!=null) {   
                imageClass.setWidth(imageClass.getImg().getWidth());
                imageClass.setHeight(imageClass.getImg().getHeight());
            
                //intersection
                if (imageSelection(imageClass)) {   
                    imageClass.markedOn();
                    imageClass.getContour().setImageClass(imageClass);
                    imageClass.getGraphics().setColor(Color.GRAY);
                    imageClass.getGraphics().setColor(Color.GRAY);
                    imageClass.paint(); 
                } else {
                    imageClass.getContour().markedOff();
                    imageClass.markedOff();
                    imageClass.paint();
                }
            } else {
                ximg=0;
                yimg=0;
            }
        }
        
        //zoom
        at = new AffineTransform();    
        xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();   
        yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();
        zoomDiv = zoomFactor / prevZoomFactor;   
        
        xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
        yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
        at.translate(xOffset, yOffset);
        at.scale(zoomFactor, zoomFactor);
        prevZoomFactor = zoomFactor;
        g2.transform(at);

        //images with their contours on the same layers
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f); 
        g2.setComposite(ac);
        for (ImageClass i : imageClasses) {
            g2.drawImage(i.getImgCont(), i.getXimg(), i.getYimg(), null);
        }
        
        //moving the window
        screenx=this.getLocationOnScreen().x-8;
        screeny=this.getLocationOnScreen().y-54;
    
        //background col 
        setBackground(bCol);
        g.setColor(bCol);
        g2.setBackground(bCol);
        
        //snap localization
        g2.setColor(dCol);
        for (Circle c : circles) if (c.getContains()) {
            if (c.getContains()&&snapMode) g2.draw(c.getSnapRec());    
        }
        for (ImageClass i : imageClasses) {
            if (i.getContains1()) g2.draw(i.getSr1());   
            else if (i.getContains2()&&snapMode) g2.draw(i.getSr2());
            else if (i.getContains3()&&snapMode) g2.draw(i.getSr3());
            else if (i.getContains4()&&snapMode) g2.draw(i.getSr4());
        }
        for (Line l : lines) {
            if (l.getContains1()&&snapMode) g2.draw(l.getSr1());
            else if (l.getContains2()&&snapMode) g2.draw(l.getSr2());
        }
        for (Rectangle r : rectangles) {
            if (r.getContains1()&&snapMode) g2.draw(r.getSr1());   
            else if (r.getContains2()&&snapMode) g2.draw(r.getSr2());
            else if (r.getContains3()&&snapMode) g2.draw(r.getSr3());
            else if (r.getContains4()&&snapMode) g2.draw(r.getSr4());
        }
        for (Text t : texts) {
            if (t.getContains()&&snapMode) g2.draw(t.getSr());
        }
        
        //drawing a line dynamically
        g.setColor(dCol);
        if (x1!=0&&y1!=0) g.drawLine(x1,y1,x2,y2);
        for (Line l : lines) {
            //intersection
            if (l.getx1()!=0&&l.getx2()!=0&&lineSelection(l)) {   
                l.setCol(Color.GRAY);
                l.markedOn();
            } else {
                l.markedOff();
                l.setCol(dCol);}
        
            //drawing a line statically
            g.setColor(l.getCol());
            if (l.getx1()!=0&&l.gety1()!=0&&l.getx2()!=0&&l.gety2()!=0&&!l.isSelected()) g.drawLine(l.getx1(), l.gety1(), l.getx2(), l.gety2());
            else if (l.getx1()!=0&&l.gety1()!=0&&l.getx2()!=0&&l.gety2()!=0&&l.isSelected()) {
                //intersection
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(l.getx1(), l.gety1(), l.getx2(), l.gety2());
                g2.setStroke(new BasicStroke(1));
            }
        } 
        //drawing not-zoomed grid
        g.setColor(Color.GRAY); 
        if (atGrid==null||atGrid!=atinverted) { 
            try {atGrid=(AffineTransform)at.createInverse().clone();} catch (NoninvertibleTransformException hugf) {;}
        }
        if (!gridLines.isEmpty()) for (Line l : gridLines) {
            pGrid1 = new Point2D.Double(l.getx1(), l.gety1());
            p2Grid1 = new Point2D.Double();
            pGrid2 = new Point2D.Double(l.getx2(), l.gety2());
            p2Grid2 = new Point2D.Double();
            atGrid.transform(pGrid1, p2Grid1); 
            atGrid.transform(pGrid2, p2Grid2);
            try {g2.setStroke(new TransformedStroke(new BasicStroke(1), g2.getTransform()));} 
            catch (NoninvertibleTransformException idfhg) {};
            java.awt.geom.Line2D line = new java.awt.geom.Line2D.Double(p2Grid1.getX(), p2Grid1.getY(), p2Grid2.getX(), p2Grid2.getY());
            g2.draw(line);
            try {at.invert();} catch (NoninvertibleTransformException trgt) {}
        }
        //drawing a rec dynamically
        if (x2r>x1r) {
            xr=x1r;
            wr=x2r-x1r;
        } else { 
            xr=x2r;
            wr=x1r-x2r;
        }
        if (y2r>y1r) {
            yr=y1r;
            hr=y2r-y1r;
        } else {
            yr=y2r;
            hr=y1r-y2r;
        }
        g.setColor(dCol);
        if (xr!=0&&yr!=0) g.drawRect(xr, yr, wr, hr);   
        if (!rectangles.isEmpty()) for (Rectangle r : rectangles) {
            //intersection
            if (r.getx1()!=0&&r.getx2()!=0&&recSelection(r)) {
                r.setCol(Color.GRAY);
                    r.markedOn();
                } else {
                    r.markedOff();
                    r.setCol(dCol); 
            }
            if (!imageClasses.isEmpty()) for (ImageClass imageClass : imageClasses) {
                if (r.getImageClass()!=null) if (imageSelection(r.getImageClass())) {   
                    r.setCol(Color.GRAY);
                    r.markedOn();
                } else {
                    r.markedOff();
                    r.setCol(dCol);
                }
            }
            //drawing a rec statically
            g.setColor(r.getCol());   
            x1rec=r.getx1();
            x2rec=r.getx2();
            y1rec=r.gety1();
            y2rec=r.gety2();

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
            if (r.getx1()!=0&&r.gety1()!=0&&r.getx2()!=0&&r.gety2()!=0&&!r.isSelected()) g.drawRect(xr, yr, wr, hr);
            else if (r.getx1()!=0&&r.gety1()!=0&&r.getx2()!=0&&r.gety2()!=0&&r.isSelected()) {  
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(xr, yr, wr, hr);
                g2.setStroke(new BasicStroke(1));
            }
        } 
        //drawing a circle dynamically
        g.setColor(dCol);
        if (xo!=0&&yo!=0&&r!=0) g.drawOval(xo-r, yo-r, 2*r, 2*r);
        for (Circle c : circles) {
            //intersection
            if (c.getX()!=0&&c.getY()!=0&&circleSelection(c)) {   
                c.setCol(Color.GRAY);
                c.markedOn();
            } else {
                c.markedOff();
                c.setCol(dCol);}
        
            //drawing a circle statically
            g.setColor(c.getCol());
            if (c.getX()!=0&&c.getY()!=0&&!c.isSelected()) g.drawOval(c.getX()-c.getR(), c.getY()-c.getR(), 2*c.getR(), 2*c.getR());
            else if (c.getX()!=0&&c.getY()!=0&&c.isSelected()) {
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(c.getX()-c.getR(), c.getY()-c.getR(), 2*c.getR(), 2*c.getR());
                g2.setStroke(new BasicStroke(1));
            }
        }
        //drawing dynamically using timer
        /*
        if ((!selection)&&(!movingC)&&(!moving)&&(!copying)&&(!drawing)&&(!snapMode)&&(!readyToInputText)&&(!gridMode)) {   
            if (timer!=null) {
                timer.stop();
            }
        */
        if (command("null")&&(selection||movingC||moving||copying)) {  
            if (timer==null||(timer!=null&&!timer.isRunning())) {
                timer = new Timer(1,this);
                timer.setRepeats(true);
                timer.start();
            }
        } else if ((drawing)||snapMode||gridMode) {
            if (timer==null||(timer!=null&&!timer.isRunning())) {
                timer = new Timer(1,this);
                timer.setRepeats(true);
                timer.start();
            }
        }
        
        if ((!command("l"))&&(!command("pl"))&&(!command("c"))&&(!command("dist"))&&(!command("rec"))) input="null";
        
        //drawing text
        if (inputText!=null&&p2!=null&&readyToDrawText) {
            texts.add(new Text(inputText, (int)p2.getX(), (int)p2.getY(), dCol));
            readyToDrawText=false;
        }
        for (Text t : texts) {
            g.setColor(t.getCol());
            if (!t.isSelected()) {
                g2.setColor(t.getCol());
                g2.drawString(t.getText(), t.getx(), t.gety());
            }
            else if (t.isSelected()) {
                //intersection
                font = super.getFont();
                g2.setColor(t.getCol());
                g2.setFont(new Font("default", Font.BOLD, font.getSize()));
                g2.drawString(t.getText(), t.getx(), t.gety());
                g2.setFont(font);
                repaint();
            }
            if (textSelection(t)) {
                t.setCol(Color.GRAY);
                t.markedOn();
            } else {
                t.setCol(dCol);
                t.markedOff();
            }
        }    
        
        if (selection) {
            g.setColor(Color.GRAY);
            if (xs!=0&&ys!=0&&ws!=0&&hs!=0) g.drawRect(xs, ys, ws, hs);
        }
    }
  
  //DRAWING COMMANDS
    public void commandLineInput(String s) {
        safelyRepaint();
        input=s;    
        if (input.contains("\n")) input=input.replace("\n", "");
        selection=false;
        drawing=false;
        safelyRepaint();
        repaint();
        if (command("")) {
            input=inputH;
        } 
        if (command("regen")) {
            safelyRepaint();
            repaint();
        } else if (command("cl")) {
            lines.clear();
            circles.clear();
            rectangles.clear();
            input="null";
            image=null;
            imageClasses.removeAll(imageClasses);
            ximg=0;
            yimg=0;
            prevZoomFactor = 1;
            zoomFactor=1;
            xOffset=1;
            yOffset=1;
            safelyRepaint();
            repaint();
        } else if (command("bcol:bla")) {
            bCol=(Color.BLACK);
            input="null";
            safelyRepaint();
            repaint();
        }
        else if (command("bcol:blu")) {
            bCol=(Color.BLUE);
            input="null";
            safelyRepaint();
            repaint();
        }    
        else if (command("bcol:w")) {
            bCol=(Color.WHITE);
            input="null";
            safelyRepaint();
            repaint();
        }
        else if (command("bcol:g")) {
            bCol=(Color.GREEN);
            input="null";
            safelyRepaint();
            repaint();
        }
        else if (command("bcol:y")) {
            bCol=(Color.YELLOW);
            input="null";
            safelyRepaint();
            repaint();
        }
        else if (command("bcol:r")) {
            bCol=(Color.RED);
            input="null";
            repaint();
        }
        else if (command("dcol:bla")) {
            dCol=(Color.BLACK);
            input="null";
        }
        else if (command("dcol:blu")) {
            dCol=(Color.BLUE);
            input="null";
        }    
        else if (command("dcol:w")) {
            dCol=(Color.WHITE);
            input="null";
        }
        else if (command("dcol:g")) {
            dCol=(Color.GREEN);
            input="null";
        }
        else if (command("dcol:y")) {
            dCol=(Color.YELLOW);
            input="null";
        }
        else if (command("dcol:r")) {
            dCol=(Color.RED);
            input="null";
        }
        else if (command("ortoX")) {
            if (ortoX) ortoX=false;
            else if (!ortoX) ortoX=true;
            input="null";
        }
        else if (command("ortoY")) {
            if (ortoY) ortoY=false;
            else if (!ortoY) ortoY=true;
            input="null";
        } else if (command("co")) {
            readyToCopy=true;
            input="null";
        } else if (command("m")) {
            readyToMove=true;
            input="null";
        } else if (command("t")) {
            readyToInputText=true;
            input="null";
            timer = new Timer(1,this);
            timer.setRepeats(true);
            timer.start();
        } else if (command("esc")) {
            if (ti!=null&&ti.isVisible()) ti.setVisible(false);
            input="null";
            for (Line l : lines) if (l.isSelected()) l.selectedOff();
            Iterator<Line> linesIt = lines.iterator();
            while (linesIt.hasNext()) {
                Line l = linesIt.next();
                if (l.isPolyline()) linesIt.remove();
            } 
            for (Text t : texts) if (t.isSelected()) t.selectedOff();
            for (Rectangle r : rectangles) if (r.isSelected()) r.selectedOff();
            for (Circle c : circles) if (c.isSelected()) c.selectedOff();
            for (ImageClass i : imageClasses) if (i.isSelected()) i.selectedOff();
            esc();
            safelyRepaint();
            repaint();
        } else if (command("null")) {
            input="null";
            readyToCopy=false;
            readyToMove=false;
            safelyRepaint();    
            repaint();
        } else if (command("l")||command("c")||command("dist")||command("rec")||command("pl")) {
        } else {
            Draw.unknownOn();
            Draw.setText("unknown command");
        }
        inputH=input;
        if (!drawing) safelyRepaint();
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        //zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public void mousePressed(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
            for (Line l : lines) {
                if (l.isSelected()&&(readyToMove)) moving=true;
            }
            for (Text t : texts) {
                if (t.isSelected()&&(readyToMove)) moving=true;
            }
            for (ImageClass i : imageClasses) {
                if (i.isSelected()&&(readyToMove)) moving=true;
            }
            for (Rectangle r : rectangles) {
                if (r.isSelected()&&(readyToMove)) moving=true;
            }
            for (Circle c : circles) {
                if (c.isSelected()&&(readyToMove)) moving=true;
            }
            for (Line l : lines) {
                if (l.isSelected()&&(readyToCopy)) copying=true;
            }
            for (Text t : texts) {
                if (t.isSelected()&&(readyToCopy)) copying=true;
            }
            for (ImageClass i : imageClasses) {
                if (i.isSelected()&&(readyToCopy)) copying=true;
            }
            for (Rectangle r : rectangles) {
                if (r.isSelected()&&(readyToCopy)) copying=true;
            }
            for (Circle c : circles) {
                if (c.isSelected()&&(readyToCopy)) copying=true;
            }
            if ((command("l")||command("pl")||command("c")||command("dist")||command("rec")||moving||copying)&&!drawing) {
                if (!selection||(x1!=0&&y1!=0&&(command("l")||command("pl")))||(xo!=0&&yo!=0&&command("c"))||
                        (xd!=0&&yd!=0&&command("dist"))||(x1r!=0&&y1r!=0&&command("rec")||moving||copying)) drawing=true;
                if (MouseInfo.getPointerInfo().getLocation().x!=0&&MouseInfo.getPointerInfo().getLocation().y!=0) {
                    pa = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x-8-screenx, MouseInfo.getPointerInfo().getLocation().y-54-screeny);
                    p2 = new Point2D.Double();
                    try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                    at.transform(pa, p2);
                    drawA((int)p2.getX(), (int)p2.getY());  
                }
            } else if (command("null")&&!selection&&!moving&&!copying&&!readyToInputText&&!drawing) {
                if (MouseInfo.getPointerInfo().getLocation().x!=0&&MouseInfo.getPointerInfo().getLocation().y!=0)
                    p = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x-8-screenx, MouseInfo.getPointerInfo().getLocation().y-54-screeny);
                p1sel = new Point2D.Double();
                try {at.invert();} catch (Exception r) {;}
                at.transform(p, p1sel);
                selection=true;
                repaint();
                safelyRepaint();
                return;
            } else if (drawing) {
                if (!command("pl")) drawing=false;
                if (MouseInfo.getPointerInfo().getLocation().x!=0&&MouseInfo.getPointerInfo().getLocation().y!=0)
                    pb = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                p3 = new Point2D.Double();
                try {at.invert();} catch (Exception r) {;}
                at.transform(pb, p3);
                drawB((int)p3.getX(), (int)p3.getY());  
                if (command("dist")) Draw.setText("dist: "+d+"p");
            }
            if (command("null")&&selection) {
                selection=false;
                for (ImageClass i : imageClasses) {
                    if (i.isMarked()) i.selectedOn();  
                    i.paint();
                }
                for (Text t : texts) {
                    if (t.isMarked()) t.selectedOn();
                }
                for (Line l : lines) {
                    if (l.isMarked()) l.selectedOn();
                }
                for (Rectangle r : rectangles) {
                    if (r.isMarked()) r.selectedOn();
                    if (!imageClasses.isEmpty()) for (ImageClass imageClass : imageClasses) 
                        if (r.getImageClass()==imageClass&&imageSelection(imageClass)) r.selectedOn();
                }
                for (Circle c : circles) {
                    if (c.isMarked()) c.selectedOn();
                }
                repaint();
                xs=0;
                ys=0;
                ws=0;
                hs=0;
            }
            if (readyToInputText) { //show text input window
                pa = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x-screenx, 
                        MouseInfo.getPointerInfo().getLocation().y-screeny);
                p2 = new Point2D.Double();
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(pa, p2);
                if (ti!=null) ti.dispose();
                ti = new tInput();
                ti.setLocation((int)p2.getX(), (int)p2.getY());
                ti.dispose();
                ti.setUndecorated(true);
                ti.setVisible(true);
            }
        } 
        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            if (MouseInfo.getPointerInfo().getLocation().x!=0&&MouseInfo.getPointerInfo().getLocation().y!=0)
                p = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x, 
                    MouseInfo.getPointerInfo().getLocation().y);
            p1s = new Point2D.Double();
            try {at.invert();} catch (Exception r) {;}
            at.transform(p, p1s);
            x1s=(int)p1s.getX();
            y1s=(int)p1s.getY();    
            movingC=true;
            repaint();
        }
        if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {   
            popup=true; 
            menu = new ImgPopup(this);
            menu1 = new Popup();
            for (ImageClass i : imageClasses) {
                if (overImage(i)) {
                    noOfOvers++;
                    i.overImageOn();
                    menu.setImageClass(i);  
                    menu.show(e.getComponent(), e.getX(), e.getY());
                } else i.overImageOff();
            }
            for (ImageClass i : imageClasses) {
                if (noOfOvers==0) menu1.show(e.getComponent(), e.getX(), e.getY());
            }
            if (imageClasses.isEmpty()) menu1.show(e.getComponent(), e.getX(), e.getY());
            noOfOvers=0;
        }
        repaint();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) { 
            movingC=false;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
       
    }

    public void mouseClicked(MouseEvent e) {
       
    }
    
    //CHECK WHETHER THE COMMAND IS S
    public boolean command(String s) {
        boolean active=false;
        Pattern p1 = Pattern.compile("\n"+s);
        Pattern p2 = Pattern.compile(s);
        Matcher m1 = p1.matcher(input);
        Matcher m2 = p2.matcher(input);
        if (m1.matches()||m2.matches()) active=true;
        return active;
    }
    
    //FIRST MOUSECLICK
    public void drawA (int x, int y) {
        if (x!=0&&y!=0) {
            if (command("l")||command("pl")) {
                x1=x;
                y1=y;
            } else if (command("c")) {
                xo=x;
                yo=y;
            } else if (command("dist")) {
                xd=x;
                yd=y;
            } else if (command("rec")) {
                x1r=x;
                y1r=y;
            } else if (moving) {  
                x1m=x;
                y1m=y; 
            } else if (copying) {
                x1c=x;
                y1c=y;
            }
        repaint();
        }
    }
    
    //SECOND MOUSECLICK
    public void drawB (int x, int y) {
        if (x1!=0&&x2!=0&&!command("pl")) lines.add(new Line(x1,x2,y1,y2,dCol,false)); 
        else if (x1!=0&&x2!=0&&command("pl")) lines.add(new Line(x1,x2,y1,y2,dCol,true)); 
        if (x1r!=0&&x2r!=0) rectangles.add(new Rectangle(x1r,x2r,y1r,y2r,dCol)); 
        if (xo!=0&&yo!=0) circles.add(new Circle(xo, yo, r, dCol));
        
        if (x!=0&&y!=0) {
            if (command("l")||command("pl")) {
                if (ortoY) x2=x1;
                else x2=x;
                if (ortoX) y2=y1;
                else y2=y;
            } else if (command("c")) {
                r =(int)Math.sqrt(Math.pow((x-xo), 2)+Math.pow((yo-y), 2));
            } else if (command("dist")) {
                d =(int)Math.sqrt(Math.pow((x-xd), 2)+Math.pow((yd-y), 2));
            } else if (command("rec")) {
                x2r=x;
                y2r=y;
            } else if (moving||copying) {
                moving=false;
                copying=false;
                for (ImageClass i : imageClasses) if (i.isSelected()) i.selectedOff();
                for (Text t : texts) if (t.isSelected()) t.selectedOff();
                for (Line l : lines) if (l.isSelected()) {
                    l.selectedOff();
                    l.copiedOff();
                }
                for (Rectangle r : rectangles) if (r.isSelected()) r.selectedOff();
                for (Circle c : circles) if (c.isSelected()) c.selectedOff();
            }
            repaint();
            if (!command("pl")) safelyRepaint();    
            else drawA(xdyn, ydyn);    //polyline
            selection=false;
            if (!command("pl")) input="null";   
        }
        readyToCopy=false;
        readyToMove=false;
    }
    
    //EXPLORING THE CANVAS WITH MOUSEWHEEL
    public void moveC() {
        pas = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x,  
                MouseInfo.getPointerInfo().getLocation().y); 

        pbs = new Point2D.Double();
        at.transform(pas, pbs);
        //------------------
        x2s=(int)pbs.getX();
        y2s=(int)pbs.getY();
        
        boolean dif=(x2s!=x2sh||y2s!=y2sh); 
        
        dx=x2s-x1s;     
        dy=y1s-y2s;
        
        //dynamic moving
        if (command("l")||(command("pl"))) {
                x1+=dx;
                y1-=dy;
            } else if (command("c")) {
                xo+=dx;
                yo-=dy;
            } else if (command("dist")) {
                xd+=dx;
                yd-=dy;
            } else if (command("rec")) {
                x1r+=dx;
                y1r-=dy;
                } else if (moving) { 
                x1m+=dx;
                y1m-=dy; 
            } else if (copying) {
                x1c+=dx;
                y1c-=dy;
            }
        
        //static moving
        if (dif) {
            if (!imageClasses.isEmpty()) for (ImageClass imageClass : imageClasses) {
                imageClass.setXimg(imageClass.getXimg()+dx);    
                imageClass.setYimg(imageClass.getYimg()-dy);
            }
            for (Text t : texts) {
                t.setx(t.getx()+dx);
                t.sety(t.gety()-dy);
            }
            for (Line l : lines) {
                l.setx1(l.getx1()+dx);
                l.setx2(l.getx2()+dx);
                l.sety1(l.gety1()-dy);
                l.sety2(l.gety2()-dy);
            }
            for (Circle c : circles) {
                c.setX(c.getX()+dx);
                c.setY(c.getY()-dy);
            }
            for (Rectangle r : rectangles) {
                r.setx1(r.getx1()+dx);
                r.setx2(r.getx2()+dx);
                r.sety1(r.gety1()-dy);
                r.sety2(r.gety2()-dy);
            }
            repaint();  
            x2sh=x2s;
            y2sh=y2s;
            x1s=x2s;
            y1s=y2s;
        }
    }  
    
    //MOVING SELECTED DRAWN OBJECTS
    public void move() {    
        pam = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x-8-screenx, 
                MouseInfo.getPointerInfo().getLocation().y-54-screeny); 
        pbm = new Point2D.Double();
        at.transform(pam, pbm);
        x2m=(int)pbm.getX();
        y2m=(int)pbm.getY();
        boolean dif=x2m!=x2mh||y2m!=y2mh;
        
        dxm=x2m-x1m;
        dym=y1m-y2m;
        
        if (dif) {
            for (Line l : lines) {
                if (l.isSelected()) {   
                    l.setx1(l.getx1()+dxm);
                    l.setx2(l.getx2()+dxm);
                    l.sety1(l.gety1()-dym);
                    l.sety2(l.gety2()-dym);
                }
            }
            for (Text t : texts) {
                if (t.isSelected()) {
                        t.setx(t.getx()+dxm);
                        t.sety(t.gety()-dym);
                }
            }
            for (Circle c : circles) {
                if (c.isSelected()) {
                    c.setX(c.getX()+dxm);
                    c.setY(c.getY()-dym);
                }
            }
            if (!imageClasses.isEmpty()) for (ImageClass i : imageClasses) {
                    if (i.isSelected()) {
                        i.setXimg(i.getXimg()+dxm);    
                        i.setYimg(i.getYimg()-dym);
                    }
            }
            for (Rectangle r : rectangles) {
                if (r.isSelected()) {
                    r.setx1(r.getx1()+dxm);
                    r.setx2(r.getx2()+dxm);
                    r.sety1(r.gety1()-dym);
                    r.sety2(r.gety2()-dym);
                }
            }
            repaint();  
            x2mh=x2m;
            y2mh=y2m;
            x1m=x2m;
            y1m=y2m;
        }
    }
    
    //COPYING SELECTED DRAWN OBJECTS
    public void copy() {  
        pac = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x-8-screenx, 
                MouseInfo.getPointerInfo().getLocation().y-54-screeny); 
        pbc = new Point2D.Double();
        at.transform(pac, pbc);
        x2c=(int)pbc.getX();
        y2c=(int)pbc.getY();
        boolean dif=x2c!=x2ch||y2c!=y2ch;
        
        dxc=x2c-x1c;
        dyc=y1c-y2c;
        
        linesToCopy = new ArrayList();
        circlesToCopy = new ArrayList();
        imagesToCopy = new ArrayList();
        rectanglesToCopy = new ArrayList();
        textsToCopy = new ArrayList();
        
        if (dif) {
            for (Line l : lines) {
                if (l.isSelected()) {
                    l2 = new Line(l.getx1(), l.getx2(), l.gety1(), l.gety2(), l.getCol(), l.isPoly());
                    if (!l.isCopied()) {   
                        linesToCopy.add(l2);
                        l.copiedOn();
                    } 
                }
            }
            for (Line l : linesToCopy) lines.add(l);
            for (Line l : lines) {  
                if (l.isSelected()) {
                    l.setx1(l.getx1()+dxc);
                    l.setx2(l.getx2()+dxc);
                    l.sety1(l.gety1()-dyc);
                    l.sety2(l.gety2()-dyc);
                }
            }
            for (Text t : texts) {
                if (t.isSelected()) {
                    t2 = new Text(t.getText(), t.getx(), t.gety(), t.getCol());
                    if (!t.isCopied()) {
                        textsToCopy.add(t2);
                        t.copiedOn();
                    }
                }
            }
            for (Text t : textsToCopy) texts.add(t);
            for (Text t : texts) {
                if (t.isSelected()) {
                    t.setx(t.getx()+dxc);
                    t.sety(t.gety()-dyc);
                }
            }
            for (Circle c : circles) {
                if (c.isSelected()) {
                    c2 = new Circle(c.getX(), c.getY(), c.getR(), c.getCol());
                    if (!c.isCopied()) {
                        circlesToCopy.add(c2);
                        c.copiedOn();
                    }
                    c2.setX(c2.getX()+dxc);
                    c2.setY(c2.getY()-dyc);
                }
            }
            for (Circle c : circlesToCopy) circles.add(c);
            for (Circle c : circles) {
                if (c.isSelected()) {
                    c.setX(c.getX()+dxc);
                    c.setY(c.getY()-dyc);
                }
            }
            if (!imageClasses.isEmpty()) for (ImageClass i : imageClasses) {
                if (i.isSelected()) {
                    i2 = new ImageClass(i.getImage(), i.getImg(), i.getXimg(), i.getYimg(), i.getContour(), i.getCol());
                    if (!i.isCopied()) {
                        imagesToCopy.add(i2);
                        i.copiedOn();
                    }
                    i2.setXimg(i2.getXimg()+dxc);    
                    i2.setYimg(i2.getYimg()-dyc);
                }
            }
            for (ImageClass i : imagesToCopy) imageClasses.add(i);
            for (ImageClass i : imageClasses) {
                if (i.isSelected()) {
                    i.setXimg(i.getXimg()+dxc);    
                    i.setYimg(i.getYimg()-dyc);
                }
            }
            for (Rectangle r : rectangles) {
                if (r.isSelected()) {   
                    r2 = new Rectangle(r.getx1(), r.getx2(), r.gety1(), r.gety2(), r.getCol());
                    if (!r.isCopied()) {
                        rectanglesToCopy.add(r2);
                        r.copiedOn();
                    }
                    r2.setx1(r2.getx1()+dxc);
                    r2.setx2(r2.getx2()+dxc);
                    r2.sety1(r2.gety1()-dyc);
                    r2.sety2(r2.gety2()-dyc);
                }
            }
            for (Rectangle r : rectanglesToCopy) rectangles.add(r);
            for (Rectangle r : rectangles) {
                if (r.isSelected()) {
                    r.setx1(r.getx1()+dxc);
                    r.setx2(r.getx2()+dxc);
                    r.sety1(r.gety1()-dyc);
                    r.sety2(r.gety2()-dyc);
                }
            }
            repaint();  
            x2ch=x2c;
            y2ch=y2c;
            x1c=x2c;
            y1c=y2c;
        }
    }
    
    //CLEAN UP THE VARIABLES
    public void safelyRepaint() {
        x1=0;
        x2=0;
        y1=0;
        y2=0;
        xo=0;
        yo=0;
        r=0;
        y1r=0;
        y2r=0;
        x1r=0;
        x2r=0;
        selRec=null;
        xs=0;
        ys=0;
        ws=0;
        hs=0;
        p2=null;
        inputText=null;
    }
    
    //CHECK WHETHER THE SELECTION REC AND DRAWN LINE INTERSECT
    public boolean lineSelection(Line l) {  
        x01=l.getx1();
        x02=l.getx2();
        y01=l.gety1();
        y02=l.gety2();

        if (xs!=0&&ys!=0&&x01!=0&&y01!=0&&x02!=0&&y02!=0) {
            selRec=new Rectangle2D.Double(xs,ys,ws,hs);
            l2d = new Line2D.Double(x01,y01,x02,y02); 
            return selRec.intersectsLine(l2d);
        } 
        if (command("esc")) return false;
        return false;
    }
    
    //CHECK WHETHER THE SELECTION REC AND DRAWN REC INTERSECT
    public boolean recSelection(Rectangle r) {
        intersection1=false;
        intersection2=false;
        xrec=0;
        yrec=0;
        if (xs!=0&&ys!=0) { 
            if (r.getx2()>r.getx1()) {
                x0r=r.getx1();
                w0r=r.getx2()-r.getx1();
            } else { 
                x0r=r.getx2();
                w0r=r.getx1()-r.getx2();
            }
            if (r.gety2()>r.gety1()) {
                y0r=r.gety1();
                h0r=r.gety2()-r.gety1();
            } else {
                y0r=r.gety2();
                h0r=r.gety1()-r.gety2();
            }
            selRec=new Rectangle2D.Double(xs,ys,ws,hs);
            r2d = new Rectangle2D.Double(x0r,y0r,w0r,h0r);
            
            //contours' intersection condition
            for (xrec=r2d.getMinX(); xrec<=r2d.getMaxX(); xrec++) {
                if (selRec.contains(xrec, r2d.getMinY())||selRec.contains(xrec, r2d.getMaxY())) intersection1=true;
            }
            for (yrec=r2d.getMinY(); yrec<=r2d.getMaxY(); yrec++) {
                if (selRec.contains(r2d.getMinX(), yrec)||selRec.contains(r2d.getMaxX(), yrec)) intersection2=true;
            }
        } 
        if (command("esc")) return false;
        return intersection1||intersection2;
    }
    
    //CHECK WHETHER THE SELECTION REC AND DRAWN CIRCLE INTERSECT
    public boolean circleSelection(Circle c) {
        intersection1=false;
        intersection2=false;
        if (xs!=0&&ys!=0&&ws!=0&&hs!=0&&c.getX()!=0&&c.getY()!=0&&c.getR()!=0) {   
            selRec=new Rectangle2D.Double(xs,ys,ws,hs);
            c2d = new Ellipse2D.Double(c.getX()-c.getR(), c.getY()-c.getR(), 2*c.getR(), 2*c.getR());
            xocirc=c.getX();
            yocirc=c.getY();
            rcirc=c.getR();
            for (xcirc=c2d.getMinX(); xcirc<=c2d.getMaxX(); xcirc++) {
                if ((selRec.contains(xcirc, Math.sqrt(Math.pow(rcirc, 2)-Math.pow((xcirc-xocirc), 2))+yocirc))||
                        (selRec.contains(xcirc, -Math.sqrt(Math.pow(rcirc, 2)-Math.pow((xcirc-xocirc), 2))+yocirc))) 
                    intersection1=true;
            }
        } 
        if (command("esc")) return false;
        return intersection1||intersection2;
    }
    
    //CHECK WHETHER THE SELECTION REC AND DRAWN TEXT INTERSECT
    public boolean textSelection(Text t) {
        if (t!=null) {
            selRec=new Rectangle2D.Double(xs,ys,ws,hs);
            txtRec=new Rectangle2D.Double(t.getx(), t.gety()-12, g2.getFontMetrics().stringWidth(t.getText()), 
                    g2.getFontMetrics().getHeight());
            if (selRec.intersects(txtRec)) return true; 
        }
        return false;
    }
    
    //CHECK WHETHER THE SELECTION REC AND IMPORTED IMAGE INTERSECT
    public boolean imageSelection(ImageClass imageClass) {  
        if (xs!=0&&ys!=0&&image!=null) {   
            selRec=new Rectangle2D.Double(xs,ys,ws,hs);
            contourSel = new Rectangle2D.Double(xrec,yrec,imageClass.getImage().getWidth(this),
                    imageClass.getImage().getHeight(this));

            //contours' intersection condition
            for (xcont=contourSel.getMinX(); xcont<=contourSel.getMaxX(); xcont++) {
                for (ycont=contourSel.getMinY(); ycont<=contourSel.getMaxY(); ycont++) {
                    if (selRec.contains(xcont+imageClass.getXimg(),ycont+imageClass.getYimg())) return true;       
                }
            }
        }
        if (command("esc")) return false;
        if (imageClass==null) return false;
        return false;
    }
    
    //CHECK WHETHER THE RIGHT MOUSECLICK IS OVER AN IMAGE
    public boolean overImage(ImageClass imageClass) {
        p = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x-8-screenx,    
                MouseInfo.getPointerInfo().getLocation().y-54-screeny);
        
        contour2 = new Rectangle2D.Double(imageClass.getXimg(), imageClass.getYimg(), 
            imageClass.getImage().getWidth(this), imageClass.getImage().getHeight(this)); 
        contour666=at.createTransformedShape(contour2);
        
        if (contour666.contains(p)) return true;
        return false;   
    }
    
    //EXPORT AS IMAGE
    public void export() throws IOException, AWTException {
        if (dir!=null&&filename!=null) {
            bufferedImage = new Robot().createScreenCapture(this.bounds());  
            g2d = bufferedImage.createGraphics();    
            this.print(g2d);    
            ImageIO.write(bufferedImage,"jpeg", new File(dir+"\\"+filename+".jpeg"));
        } else Draw.setText("Error");
    } 
    
    //OPEN FILE
    public void open() throws IOException {
        image = Toolkit.getDefaultToolkit().getImage(dir+"\\"+filename);  
        img = ImageIO.read(new File(dir+"\\"+filename));
        contour = new Rectangle(ximg-2, ximg+img.getWidth(this)+1, yimg-2, yimg+img.getHeight(this)+1, dCol);
        imageClass = new ImageClass(image, img, ximg, yimg, contour, bCol);
        contour.setImageClass(imageClass);  //drawing contour
        imageClasses.add(imageClass);   
        safelyRepaint();
        repaint();
    }
    
    //ESC KEY PRESSED
    public void esc() {     
        for (Line l : lines) {
            l.markedOff();
            l.setCol(dCol);
            l.selectedOff();
        }
        for (Circle c : circles) {
            c.setCol(dCol);
            c.markedOff();
            c.selectedOff();
        }
        for (Rectangle r : rectangles) {
            r.setCol(dCol);
            r.markedOff();
            r.selectedOff();
        }
        moving=false;
        drawing=false;
        copying=false;
        safelyRepaint();
        repaint();  
    }
    
    //WRITE A ONE-LINE TEXT AT A SPECIFIED POINT
    public static void drawText(String s) {
        inputText=s;
        readyToInputText=false;
        readyToDrawText=true;
    }
    
    //SNAP MODE
    public void snap() {    
        p = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x-7-screenx,    
                MouseInfo.getPointerInfo().getLocation().y-53-screeny); 
        p2 = new Point2D.Double();
        at.transform(p, p2);
        
        for (Circle c : circles) {   
            c.setSr(new Rectangle2D.Double(c.getX()-8, c.getY()-8, 16, 16));
            if (c.getSnapRec().contains((int)p2.getX(),(int)p2.getY())) {   
                xsnap=c.getX();   
                ysnap=c.getY(); 
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(xsnap,ysnap), p);
                c.containsOn();
            } else c.containsOff();
        }
        for (Text t : texts) {
            t.setSr(new Rectangle2D.Double(t.getx()-8, t.gety()-8, 16, 16));
            if (t.getSr().contains((int)p2.getX(),(int)p2.getY())) {
                xsnap=t.getx();
                ysnap=t.gety();
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(xsnap,ysnap), p);
                t.containsOn();
            } else t.containsOff();
        }
        for (ImageClass i : imageClasses) {
            i.updatesnapRecs();
            
            if (i.getSr1().contains((int)p2.getX(),(int)p2.getY())) {   
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(i.getXimg(), i.getYimg()), p);
                i.contains1on();
            } else i.contains1off();
            if (i.getSr2().contains((int)p2.getX(),(int)p2.getY())) {  
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(i.getXimg()+i.getWidth(), i.getYimg()), p);
                i.contains2on();
            } else i.contains2off();
            if (i.getSr3().contains((int)p2.getX(),(int)p2.getY())) {   
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(i.getXimg(), i.getYimg()+i.getHeight()), p);
                i.contains3on();
            } else i.contains3off();
            if (i.getSr4().contains((int)p2.getX(),(int)p2.getY())) {   
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(i.getXimg()+i.getWidth(), i.getYimg()+i.getHeight()), p);
                i.contains4on();
            } else i.contains4off();
        }
        for (Line l : lines) {
            l.setSr1(new Rectangle2D.Double(l.getx1()-8, l.gety1()-8, 16, 16)); 
            if (l.getSr1().contains((int)p2.getX(),(int)p2.getY())) {   
                xsnap=l.getx1();  
                ysnap=l.gety1();
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(xsnap,ysnap), p);
                l.contains1on();
            } else l.contains1off();
            l.setSr2(new Rectangle2D.Double(l.getx2()-8, l.gety2()-8, 16, 16)); 
            if (l.getSr2().contains((int)p2.getX(),(int)p2.getY())) {   
                xsnap=l.getx2();  
                ysnap=l.gety2();
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(xsnap,ysnap), p);
                l.contains2on();
            } else l.contains2off();
        }
        for (Rectangle r : rectangles) {
            r.setSr1(new Rectangle2D.Double(r.getx1()-8, r.gety1()-8, 16, 16)); 
            if (r.getSr1().contains((int)p2.getX(),(int)p2.getY())) {   
                xsnap=r.getx1();  
                ysnap=r.gety1();
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(xsnap,ysnap), p);
                r.contains1on();
            } else r.contains1off();    
            r.setSr2(new Rectangle2D.Double(r.getx2()-8, r.gety1()-8, 16, 16)); 
            if (r.getSr2().contains((int)p2.getX(),(int)p2.getY())) {   
                xsnap=r.getx2();  
                ysnap=r.gety1();
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(xsnap,ysnap), p);
                r.contains2on();
            } else r.contains2off();
            r.setSr3(new Rectangle2D.Double(r.getx1()-8, r.gety2()-8, 16, 16)); 
            if (r.getSr3().contains((int)p2.getX(),(int)p2.getY())) {   
                xsnap=r.getx1();  
                ysnap=r.gety2();
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(xsnap,ysnap), p);
                r.contains3on();
            } else r.contains3off();
            r.setSr4(new Rectangle2D.Double(r.getx2()-8, r.gety2()-8, 16, 16)); 
            if (r.getSr4().contains((int)p2.getX(),(int)p2.getY())) {   
                xsnap=r.getx2();  
                ysnap=r.gety2();
                try {at.invert();} catch (NoninvertibleTransformException grtg) {;} 
                at.transform(new Point2D.Double(xsnap,ysnap), p);
                r.contains4on();
            } else r.contains4off();
            for (Line l : gridLines) {
                //set grid snapRecs !!!
            }
        }
        try {
            robot=new Robot();
            if (p.getX()!=0&&p.getY()!=0&&snapMode&&!snapExecuted) {
                robot.mouseMove((int)p.getX()+7+screenx,(int)p.getY()+53+screeny); 
                snapExecuted=true;
                try {waittt();} catch (InterruptedException ferv) {;}
            }
        } catch (AWTException efrv) {;}
    }
    public void waittt() throws InterruptedException {
        Thread.sleep(10);
        xsnap=0;
        ysnap=0;
        snapExecuted=false;
    }
    public void grid() {
        gridX = -20;
        gridY = -20;
        gridSize = 10;  //make it var
        if (gridMode) {
            for (int i=0; i<110; i++) {
                gridX += gridSize;
                gridY += gridSize; 
                gridLines.add(new Line(-10, 1000, gridY, gridY, Color.GRAY, false));
                gridLines.add(new Line(gridX, gridX, -10, 500, Color.GRAY, false));
            } 
        } else gridLines.removeAll(gridLines);
        repaint();
    }
    public void popupOff() {
        this.popup=false;
    }
    public ImageClass getImageClass(int i) {
        return this.imageClasses.get(i);
    }
    public void addImageClass(int i, ImageClass ic) {
        this.imageClasses.add(i, ic);
    }
    public void addImageClass(ImageClass ic) {
        this.imageClasses.add(ic);
    }
    public void removeImageClass(ImageClass ic) {
        this.imageClasses.remove(ic);
    }
    public void setFilename(String s) {
        this.filename=s;
    }
    public void setDir(String s) {
        this.dir=s;
    }
    public ArrayList getLines() {
        return this.lines;
    }
    public void removeLine(int i) {
        this.lines.remove(i);
    }
    public ArrayList getRectangles() {
        return this.rectangles;
    }
    public void removeRec(int i) {
        this.rectangles.remove(i);
    }
    public ArrayList getCircles() {
        return this.circles;
    }
    public void removeCircle(int i) {
        this.circles.remove(i);
    }
    public ArrayList getImageClasses() {
        return this.imageClasses;
    }
    public void setImage(Image i) {
        this.image=i;
    }
    public static boolean isSnapMode() {
        return snapMode;
    }
    public static void snapModeOn() {
        snapMode=true;
    }
    public static void snapModeOff() {
        snapMode=false;
    }
    public tInput getTextInput() {
        return this.ti;
    }
    public static Timer getTimer() {
        return timer;
    }
    public static boolean isGridMode() {
        return gridMode;
    }
    public static void gridModeOn() {
        gridMode=true;
    }
    public static void gridModeOff() {
        gridMode=false;
    }
}
