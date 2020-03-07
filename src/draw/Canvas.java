package draw;

import draw.paint.*;
import objs.*;
import java.awt.*;
import objs.Rectangle;
import ui.ImgPopup;
import ui.Popup;
import ui.TextInput;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@SuppressWarnings({"ALL", "ConstantConditions", "DuplicateExpressions"})
public class Canvas extends JPanel implements MouseListener, ActionListener, MouseWheelListener {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private ArrayList<Line> lines = new ArrayList();
    private ArrayList<Circle> circles = new ArrayList();
    private ArrayList<Rectangle> rectangles = new ArrayList();
    private String input = "null";
    private String inputH = "null";
    private boolean drawing = false;
    private Color dCol = Color.BLACK;
    private Color bCol = Color.WHITE;
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
    private int y1s;
    private int dx;
    private int dy;
    private boolean selection = false;
    private static Timer timer = null;
    private int xs = 0;
    private int ys = 0;
    private int ws = 0;
    private int hs = 0;
    private Rectangle2D.Double selRec;
    private int screenx;
    private int screeny;
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private double xOffset = 1;
    private double yOffset = 1;
    private boolean moving = false;
    private int x1m;
    private int y1m;
    private boolean movingC = false;
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
    private Image image;
    private BufferedImage img;
    private Graphics2D g2;
    private int ximg = 0;
    private int yimg = 0;
    private ArrayList<ImageClass> imageClasses = new ArrayList();
    private AffineTransform at;
    private Point2D p;
    private Point2D p2;
    private Point2D p3;
    private Point2D p1sel;
    private Point2D pb;
    private AffineTransform atinverted;
    private AffineTransform atinverted2;
    private Robot robot;
    private int xsnap;
    private int ysnap;
    public static boolean snapMode;
    private boolean snapExecuted = false;
    private boolean intersection1;
    private boolean intersection2;
    private Rectangle2D.Double contourSel;
    private int noOfOvers;
    private boolean copying;
    private boolean readyToCopy;
    private int x1c;
    private int y1c;
    private int x2ch;
    private int y2ch;
    private boolean readyToMove;
    private static boolean readyToInputText;
    private TextInput ti;
    private static String inputText;
    private ArrayList<Text> texts = new ArrayList();
    private static boolean readyToDrawText;
    private static boolean gridMode;
    private final ArrayList<Line> gridLines = new ArrayList();
    private static int gridSize;
    private AffineTransform atGrid;
    private static boolean snapToGridMode;
    private boolean gridAdded;
    private static boolean gridSRadded;
    private Point2D pSnap;
    private AffineTransform atSR;
    private String cmd;
    private int plindex = 0;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private boolean popup;
    private Zoom zoom = new Zoom(this);
    private double xRel;
    private double yRel;
    private double zoomDiv;
    private Snap snap = new Snap(this);
    private PaintingImages paintingImages = new PaintingImages(this);
    private int y2rec;
    private int x2rec;
    private int y1rec;
    private int x1rec;
    private int hr;
    private int wr;
    private int yr;
    private int xr;
    private PaintingLines paintingLines = new PaintingLines(this);
    private PaintingCircles paintingCircles = new PaintingCircles(this);
    private PaintingRectangles paintingRectangles = new PaintingRectangles(this);
    private PaintingTexts paintingTexts = new PaintingTexts(this);
    private PaintingGrid paintingGrid = new PaintingGrid(this);
    private PaintSelectionRec paintSelectionRec = new PaintSelectionRec(this);

    public Canvas() {   //the actual canvas is at (8, 54)
        addMouseListener(this);
        addMouseWheelListener(this);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        repaint();
    }

    //---------------TIMER (DYNAMIC OPERATIONS)--------------------------------------------------------------
    @Override
    @SuppressWarnings("empty-statement")
    public void actionPerformed(ActionEvent evt) {
        //...........no zoom on gridMode............................
        zoom.noZoom();

        //..........zooming the coords dynamically...................
        zoom.dynamicZoom();

        //.........selection rec using timer.......................
        paintSelectionRec.setupSelRecInterval();

        //................moving objects...............................
        if (movingC) moveC();
        else if (moving) move();
        else if (copying) copy();

        if (snapToGridMode) snapToGrid();
        grid();
        snap.snapOnIntervals();
        repaint();
    }

    //---------------DRAWING LOGIC -----------------------------------------------------------------------------------
    @Override
    @SuppressWarnings({"empty-statement", "DuplicatedCode"})
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g.setColor(bCol);

        //............open image.........................
        paintingImages.importImage();

        //...............zoom image.........................
        zoom.zoomImage();

        //..............images with their contours on the same layers
        paintingImages.drawImageLayers();

        //..............moving the window.............................
        screenx = this.getLocationOnScreen().x - 8;
        screeny = this.getLocationOnScreen().y - 54;

        //...............background col................................
        setBackground(bCol);
        g.setColor(bCol);
        g2.setBackground(bCol);

        //...............grid snapRecs..............................
        paintingGrid.drawSnapRecs();

        //................circles snapRec.......................
        paintingCircles.drawSnapRecs();

        //...............images snapRecs.......................
        paintingImages.drawSnapRecs();

        //..........lines snapRecs....................
        paintingLines.drawSnapRecs();

        //...........rectangles snapRecs..............
        paintingRectangles.drawSnapRecs();

        //...........text snapRec.....................
        paintingTexts.drawSnapRecs();

        paintingLines.drawLines(g);

        //..............drawing not-zoomed grid..........
        paintingGrid.drawGrid(g);

        paintingRectangles.drawRectangles(g);

        paintingCircles.drawCircles(g);

        //......................unconditional timer.............
         if (timer == null || (timer != null && !timer.isRunning())) {
            timer = new Timer(1, this);
            timer.setRepeats(true);
            timer.start();
            }

         //.....................resetting input...................
        if ((!command("l")) && (!command("pl")) && (!command("c"))
                && (!command("dist")) && (!command("rec")))
            input = "null";

        //.......................drawing text.....................
        paintingTexts.drawTexts(g);

        //.......................marking rectangle................
        if (selection) {
            g.setColor(Color.GRAY);
            if (xs != 0 && ys != 0 && ws != 0 && hs != 0) g.drawRect(xs, ys, ws, hs);
        }
    }

    //------------------------COMMAND LINE---------------------------------------------------------------------
    public void commandLineInput(String s) {
        safelyRepaint();
        input = s;
        if (input.contains("\n")) input = input.replace("\n", "");
        selection = false;
        drawing = false;
        safelyRepaint();
        repaint();
        if (command("")) {
            input = inputH;
        }
        boolean polyline;
        if (command("regen")) {
            safelyRepaint();
            repaint();
        } else if (command("pl")) {
            polyline =true;
        } else if (command("cl")) {
            lines.clear();
            circles.clear();
            rectangles.clear();
            input = "null";
            image = null;
            imageClasses.removeAll(imageClasses);
            ximg = 0;
            yimg = 0;
            prevZoomFactor = 1;
            zoomFactor = 1;
            xOffset = 1;
            yOffset = 1;
            safelyRepaint();
            repaint();
        } else if (command("bcol:bla")) {
            bCol = (Color.BLACK);
            input = "null";
            safelyRepaint();
            repaint();
        } else if (command("bcol:blu")) {
            bCol = (Color.BLUE);
            input = "null";
            safelyRepaint();
            repaint();
        } else if (command("bcol:w")) {
            bCol = (Color.WHITE);
            input = "null";
            safelyRepaint();
            repaint();
        } else if (command("bcol:g")) {
            bCol = (Color.GREEN);
            input = "null";
            safelyRepaint();
            repaint();
        } else if (command("bcol:y")) {
            bCol = (Color.YELLOW);
            input = "null";
            safelyRepaint();
            repaint();
        } else if (command("bcol:r")) {
            bCol = (Color.RED);
            input = "null";
            repaint();
        } else if (command("dcol:bla")) {
            dCol = (Color.BLACK);
            input = "null";
        } else if (command("dcol:blu")) {
            dCol = (Color.BLUE);
            input = "null";
        } else if (command("dcol:w")) {
            dCol = (Color.WHITE);
            input = "null";
        } else if (command("dcol:g")) {
            dCol = (Color.GREEN);
            input = "null";
        } else if (command("dcol:y")) {
            dCol = (Color.YELLOW);
            input = "null";
        } else if (command("dcol:r")) {
            dCol = (Color.RED);
            input = "null";
        } else if (command("ortoX")) {
            if (ortoX) ortoX = false;
            else if (!ortoX) ortoX = true;
            input = "null";
        } else if (command("ortoY")) {
            if (ortoY) ortoY = false;
            else if (!ortoY) ortoY = true;
            input = "null";
        } else if (command("co")) {
            readyToCopy = true;
            input = "null";
        } else if (command("m")) {
            readyToMove = true;
            input = "null";
        } else if (command("t")) {
            readyToInputText = true;
            input = "null";
            timer = new Timer(1, this);
            timer.setRepeats(true);
            timer.start();
        } else if (command("esc")) {
            if (ti != null && ti.isVisible()) ti.setVisible(false);
            input = "null";
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
            input = "null";
            readyToCopy = false;
            readyToMove = false;
            safelyRepaint();
            repaint();
        } else if (command("l") || command("c") || command("dist") || command("rec") || command("pl")) {
            if (command("pl")) {
                polyline =false;
                plindex++;
            }
        } else {
            Draw.unknownOn();
            Draw.setText("unknown command");
        }
        inputH = input;
        if (!drawing) safelyRepaint();
    }

    //---------------MOUSE SCROLL----------------------------------------------------------------------------
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //...........zoom in................................
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        //............zoom out.................................
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }
    }

    //---------------MOUSE PRESSED------------------------------------------------------------------------
    @Override
    @SuppressWarnings("empty-statement")
    public void mousePressed(MouseEvent e) {
        //............left mouse button.............................
        if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
            //..........moving......................................
            for (Line l : lines) {
                if (l.isSelected() && (readyToMove)) moving = true;
            }
            for (Text t : texts) {
                if (t.isSelected() && (readyToMove)) moving = true;
            }
            for (ImageClass i : imageClasses) {
                if (i.isSelected() && (readyToMove)) moving = true;
            }
            for (Rectangle r : rectangles) {
                if (r.isSelected() && (readyToMove)) moving = true;
            }
            for (Circle c : circles) {
                if (c.isSelected() && (readyToMove)) moving = true;
            }
            for (Line l : lines) {
                if (l.isSelected() && (readyToCopy)) copying = true;
            }
            for (Text t : texts) {
                if (t.isSelected() && (readyToCopy)) copying = true;
            }
            for (ImageClass i : imageClasses) {
                if (i.isSelected() && (readyToCopy)) copying = true;
            }
            for (Rectangle r : rectangles) {
                if (r.isSelected() && (readyToCopy)) copying = true;
            }
            for (Circle c : circles) {
                if (c.isSelected() && (readyToCopy)) copying = true;
            }
            //...........drawing...............................
            Point2D pa;
            if ((command("l") || command("pl") || command("c") || command("dist") || command("rec") || moving || copying) && !drawing) {
                if (!selection || (x1 != 0 && y1 != 0 && (command("l") || command("pl"))) || (xo != 0 && yo != 0 && command("c")) ||
                        (xd != 0 && yd != 0 && command("dist")) || (x1r != 0 && y1r != 0 && command("rec") || moving || copying))
                    drawing = true;
                //.......point A...............................
                if (MouseInfo.getPointerInfo().getLocation().x != 0 && MouseInfo.getPointerInfo().getLocation().y != 0) {
                    pa = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x - 8 - screenx, MouseInfo.getPointerInfo().getLocation().y - 54 - screeny);
                    p2 = new Point2D.Double();
                    try {
                        at.invert();
                    } catch (NoninvertibleTransformException ignored) {}
                    at.transform(pa, p2);
                    drawA((int) p2.getX(), (int) p2.getY());    //method point A
                }
            } else if (command("null") && !selection && !moving && !copying && !readyToInputText && !drawing) {
                //........selection rec........................
                if (MouseInfo.getPointerInfo().getLocation().x != 0 && MouseInfo.getPointerInfo().getLocation().y != 0)
                    p = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x - 8 - screenx, MouseInfo.getPointerInfo().getLocation().y - 54 - screeny);
                p1sel = new Point2D.Double();
                try {
                    at.invert();
                } catch (Exception ignored) {
                }
                at.transform(p, p1sel);
                selection = true;
                repaint();
                safelyRepaint();
                return;
            } else if (drawing) {
                //........point B...............................
                if (!command("pl")) drawing = false;
                if (MouseInfo.getPointerInfo().getLocation().x != 0 && MouseInfo.getPointerInfo().getLocation().y != 0)
                    pb = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                p3 = new Point2D.Double();
                try {
                    at.invert();
                } catch (Exception ignored) {}
                at.transform(pb, p3);
                drawB((int) p3.getX(), (int) p3.getY());    //method point B
            }
            if (command("null") && selection) {
                //.........selecting (when marked)..............
                selection = false;
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
                        if (r.getImageClass() == imageClass && imageSelection(imageClass)) r.selectedOn();
                }
                for (Circle c : circles) {
                    if (c.isMarked()) c.selectedOn();
                }
                repaint();
                xs = 0;
                ys = 0;
                ws = 0;
                hs = 0;
            }
            if (readyToInputText) {
                //...........show text input window............
                pa = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x - screenx,
                        MouseInfo.getPointerInfo().getLocation().y - screeny);
                p2 = new Point2D.Double();
                try {
                    at.invert();
                } catch (NoninvertibleTransformException ignored) {}
                at.transform(pa, p2);
                if (ti != null) ti.dispose();
                ti = new TextInput();
                ti.setLocation((int) p2.getX(), (int) p2.getY());
                ti.dispose();
                ti.setUndecorated(true);
                ti.setVisible(true);
            }
        }
        //....................scroll mouse button.......
        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            if (MouseInfo.getPointerInfo().getLocation().x != 0 && MouseInfo.getPointerInfo().getLocation().y != 0)
                //............moving thru canvas................
                p = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x,
                        MouseInfo.getPointerInfo().getLocation().y);
            Point2D p1s = new Point2D.Double();
            try {
                at.invert();
            } catch (Exception ignored) {
            }
            at.transform(p, p1s);
            x1s = (int) p1s.getX();
            y1s = (int) p1s.getY();
            movingC = true;
            repaint();
        }
        //...................right mouse button...............
        if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
            ImgPopup menu = new ImgPopup(this);
            ui.Popup menu1 = new Popup();
            //................ImgPopup (move to front/back)....
            for (ImageClass i : imageClasses) {
                if (overImage(i)) {
                    noOfOvers++;
                    i.overImageOn();
                    menu.setImageClass(i);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                } else i.overImageOff();
            }
            //...............Popup (right mouseclick menu).......
            for (ImageClass i : imageClasses) {
                if (noOfOvers == 0) menu1.show(e.getComponent(), e.getX(), e.getY());
            }
            if (imageClasses.isEmpty()) menu1.show(e.getComponent(), e.getX(), e.getY());
            noOfOvers = 0;
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            movingC = false;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    //-----------CHECK WHETHER THE COMMAND IS s---------------------------------------------------------------
    public boolean command(String s) {
        boolean active = false;
        Pattern p1 = Pattern.compile("\n" + s);
        Pattern p2 = Pattern.compile(s);
        Matcher m1 = p1.matcher(input);
        Matcher m2 = p2.matcher(input);
        if (m1.matches() || m2.matches()) active = true;
        return active;
    }

    //-----------FIRST MOUSECLICK (draw point A)------------------------------------------------------------------
    public void drawA(int x, int y) {
        if (x != 0 && y != 0) {
            if (command("l") || command("pl")) {
                x1 = x;
                y1 = y;
            } else if (command("c")) {
                xo = x;
                yo = y;
            } else if (command("dist")) {
                xd = x;
                yd = y;
            } else if (command("rec")) {
                x1r = x;
                y1r = y;
            } else if (moving) {
                x1m = x;
                y1m = y;
            } else if (copying) {
                x1c = x;
                y1c = y;
            }
            repaint();
        }
    }

    //---------------SECOND MOUSECLICK (draw point B)-------------------------------------------------
    public void drawB(int x, int y) {
        //............adding drawn objects to lists......
        if (x1 != 0 && x2 != 0 && !command("pl")) {
            lines.add(new Line(x1, x2, y1, y2, dCol, false));
            cmd="lineadd";
        }
        else if (x1 != 0 && x2 != 0 && command("pl")) {
            lines.add(new Line(x1, x2, y1, y2, dCol, true, plindex));
            cmd="plineadd";
        }
        if (x1r != 0 && x2r != 0) rectangles.add(new Rectangle(x1r, x2r, y1r, y2r, dCol));
        if (xo != 0 && yo != 0) circles.add(new Circle(xo, yo, r, dCol));

        if (x != 0 && y != 0) {
            if (command("l") || command("pl")) {
                if (ortoY) x2 = x1;
                else x2 = x;
                if (ortoX) y2 = y1;
                else y2 = y;
            } else if (command("c")) {
                r = (int) Math.sqrt(Math.pow((x - xo), 2) + Math.pow((yo - y), 2));
            } else if (command("dist")) {
                d = (int) Math.sqrt(Math.pow((x - xd), 2) + Math.pow((yd - y), 2));
                Draw.setText("dist: " + d + "p");
            } else if (command("rec")) {
                x2r = x;
                y2r = y;
            } else if (moving || copying) {
                moving = false;
                copying = false;
                for (ImageClass i : imageClasses) if (i.isSelected()) i.selectedOff();
                for (Text t : texts) if (t.isSelected()) t.selectedOff();
                for (Line l : lines)
                    if (l.isSelected()) {
                        l.selectedOff();
                        l.copiedOff();
                    }
                for (Rectangle r : rectangles) if (r.isSelected()) r.selectedOff();
                for (Circle c : circles) if (c.isSelected()) c.selectedOff();
            }
            repaint();
            if (!command("pl")) {
                safelyRepaint();
            }
            else drawA(xdyn, ydyn);    //polyline
            selection = false;
            if (!command("pl")) input = "null";
        }
        readyToCopy = false;
        readyToMove = false;
    }

    //-------------EXPLORING THE CANVAS WITH MOUSEWHEEL------------------------------------------------------
    public void moveC() {
        Point2D pas = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y);
        Point2D pbs = new Point2D.Double();
        atinverted.transform(pas, pbs);
        //------------------
        int x2s = (int) pbs.getX();
        int y2s = (int) pbs.getY();

        boolean dif = (x2s != x2sh || y2s != y2sh); //if canvas moved

        dx = x2s - x1s;
        dy = y1s - y2s;

        //..........dynamic drawing (while drawing).............
        if (command("l") || (command("pl"))) {
            x1 += dx;
            y1 -= dy;
        } else if (command("c")) {
            xo += dx;
            yo -= dy;
        } else if (command("dist")) {
            xd += dx;
            yd -= dy;
        } else if (command("rec")) {
            x1r += dx;
            y1r -= dy;
        } else if (moving) {
            x1m += dx;
            y1m -= dy;
        } else if (copying) {
            x1c += dx;
            y1c -= dy;
        }

        //..........static moving..........................
        if (dif) {
            if (!imageClasses.isEmpty()) for (ImageClass imageClass : imageClasses) {
                imageClass.setXimg(imageClass.getXimg() + dx);
                imageClass.setYimg(imageClass.getYimg() - dy);
            }
            for (Text t : texts) {
                t.setx(t.getx() + dx);
                t.sety(t.gety() - dy);
            }
            for (Line l : lines) {
                l.setx1(l.getx1() + dx);
                l.setx2(l.getx2() + dx);
                l.sety1(l.gety1() - dy);
                l.sety2(l.gety2() - dy);
            }
            for (Circle c : circles) {
                c.setX(c.getX() + dx);
                c.setY(c.getY() - dy);
            }
            for (Rectangle r : rectangles) {
                r.setx1(r.getx1() + dx);
                r.setx2(r.getx2() + dx);
                r.sety1(r.gety1() - dy);
                r.sety2(r.gety2() - dy);
            }
            repaint();
            x2sh = x2s;
            y2sh = y2s;
            x1s = x2s;
            y1s = y2s;
        }
    }

    //----------MOVING SELECTED DRAWN OBJECTS (command "m")----------------------------------------------
    public void move() {
        Point2D pam = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x - 8 - screenx,
                MouseInfo.getPointerInfo().getLocation().y - 54 - screeny);
        Point2D pbm = new Point2D.Double();
        atinverted.transform(pam, pbm);
        int x2m = (int) pbm.getX();
        int y2m = (int) pbm.getY();
        boolean dif = x2m != x2mh || y2m != y2mh;   //if object was moved

        int dxm = x2m - x1m;
        int dym = y1m - y2m;

        if (dif) {
            for (Line l : lines) {
                if (l.isSelected()) {
                    l.setx1(l.getx1() + dxm);
                    l.setx2(l.getx2() + dxm);
                    l.sety1(l.gety1() - dym);
                    l.sety2(l.gety2() - dym);
                }
            }
            for (Text t : texts) {
                if (t.isSelected()) {
                    t.setx(t.getx() + dxm);
                    t.sety(t.gety() - dym);
                }
            }
            for (Circle c : circles) {
                if (c.isSelected()) {
                    c.setX(c.getX() + dxm);
                    c.setY(c.getY() - dym);
                }
            }
            if (!imageClasses.isEmpty()) for (ImageClass i : imageClasses) {
                if (i.isSelected()) {
                    i.setXimg(i.getXimg() + dxm);
                    i.setYimg(i.getYimg() - dym);
                }
            }
            for (Rectangle r : rectangles) {
                if (r.isSelected()) {
                    r.setx1(r.getx1() + dxm);
                    r.setx2(r.getx2() + dxm);
                    r.sety1(r.gety1() - dym);
                    r.sety2(r.gety2() - dym);
                }
            }
            repaint();
            x2mh = x2m;
            y2mh = y2m;
            x1m = x2m;
            y1m = y2m;
        }
    }

    //-----------COPYING SELECTED DRAWN OBJECTS-----------------------------------------------------------
    public void copy() {
        Point2D pac = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x - 8 - screenx,
                MouseInfo.getPointerInfo().getLocation().y - 54 - screeny);
        Point2D pbc = new Point2D.Double();
        atinverted.transform(pac, pbc);
        int x2c = (int) pbc.getX();
        int y2c = (int) pbc.getY();
        boolean dif = x2c != x2ch || y2c != y2ch;   //if copied object was moved

        int dxc = x2c - x1c;
        int dyc = y1c - y2c;

        //........object-to-copy lists...................
        ArrayList<Line> linesToCopy = new ArrayList();
        ArrayList<Circle> circlesToCopy = new ArrayList();
        ArrayList<ImageClass> imagesToCopy = new ArrayList();
        ArrayList<Rectangle> rectanglesToCopy = new ArrayList();
        ArrayList<Text> textsToCopy = new ArrayList();

        if (dif) {
            for (Line l : lines) {
                if (l.isSelected()) {
                    Line l2 = new Line(l.getx1(), l.getx2(), l.gety1(), l.gety2(), l.getCol(), l.isPoly());
                    if (!l.isCopied()) {
                        linesToCopy.add(l2);
                        l.copiedOn();
                    }
                }
            }
            lines.addAll(linesToCopy);
            for (Line l : lines) {
                if (l.isSelected()) {
                    l.setx1(l.getx1() + dxc);
                    l.setx2(l.getx2() + dxc);
                    l.sety1(l.gety1() - dyc);
                    l.sety2(l.gety2() - dyc);
                }
            }
            for (Text t : texts) {
                if (t.isSelected()) {
                    Text t2 = new Text(t.getText(), t.getx(), t.gety(), t.getCol());
                    if (!t.isCopied()) {
                        textsToCopy.add(t2);
                        t.copiedOn();
                    }
                }
            }
            texts.addAll(textsToCopy);
            for (Text t : texts) {
                if (t.isSelected()) {
                    t.setx(t.getx() + dxc);
                    t.sety(t.gety() - dyc);
                }
            }
            for (Circle c : circles) {
                if (c.isSelected()) {
                    Circle c2 = new Circle(c.getX(), c.getY(), c.getR(), c.getCol());
                    if (!c.isCopied()) {
                        circlesToCopy.add(c2);
                        c.copiedOn();
                    }
                    c2.setX(c2.getX() + dxc);
                    c2.setY(c2.getY() - dyc);
                }
            }
            circles.addAll(circlesToCopy);
            for (Circle c : circles) {
                if (c.isSelected()) {
                    c.setX(c.getX() + dxc);
                    c.setY(c.getY() - dyc);
                }
            }
            if (!imageClasses.isEmpty()) for (ImageClass i : imageClasses) {
                if (i.isSelected()) {
                    ImageClass i2 = new ImageClass(i.getImage(), i.getImg(), i.getXimg(), i.getYimg(), i.getContour(), i.getCol());
                    if (!i.isCopied()) {
                        imagesToCopy.add(i2);
                        i.copiedOn();
                    }
                    i2.setXimg(i2.getXimg() + dxc);
                    i2.setYimg(i2.getYimg() - dyc);
                }
            }
            imageClasses.addAll(imagesToCopy);
            for (ImageClass i : imageClasses) {
                if (i.isSelected()) {
                    i.setXimg(i.getXimg() + dxc);
                    i.setYimg(i.getYimg() - dyc);
                }
            }
            for (Rectangle r : rectangles) {
                if (r.isSelected()) {
                    Rectangle r2 = new Rectangle(r.getx1(), r.getx2(), r.gety1(), r.gety2(), r.getCol());
                    if (!r.isCopied()) {
                        rectanglesToCopy.add(r2);
                        r.copiedOn();
                    }
                    r2.setx1(r2.getx1() + dxc);
                    r2.setx2(r2.getx2() + dxc);
                    r2.sety1(r2.gety1() - dyc);
                    r2.sety2(r2.gety2() - dyc);
                }
            }
            rectangles.addAll(rectanglesToCopy);
            for (Rectangle r : rectangles) {
                if (r.isSelected()) {
                    r.setx1(r.getx1() + dxc);
                    r.setx2(r.getx2() + dxc);
                    r.sety1(r.gety1() - dyc);
                    r.sety2(r.gety2() - dyc);
                }
            }
            repaint();
            x2ch = x2c;
            y2ch = y2c;
            x1c = x2c;
            y1c = y2c;
        }
    }

    //-----------CLEAN UP THE VARIABLES--------------------------------------------------------------------
    public void safelyRepaint() {
        x1 = 0;
        x2 = 0;
        y1 = 0;
        y2 = 0;
        xo = 0;
        yo = 0;
        r = 0;
        y1r = 0;
        y2r = 0;
        x1r = 0;
        x2r = 0;
        selRec = null;
        xs = 0;
        ys = 0;
        ws = 0;
        hs = 0;
        p2 = null;
        inputText = null;
    }

    //---------------CHECK WHETHER THE SELECTION REC AND DRAWN LINE INTERSECT--------------------------------------
    public boolean lineSelection(Line l) {
        int x01 = l.getx1();
        int x02 = l.getx2();
        int y01 = l.gety1();
        int y02 = l.gety2();

        if (xs != 0 && ys != 0 && x01 != 0 && y01 != 0 && x02 != 0 && y02 != 0) {
            selRec = new Rectangle2D.Double(xs, ys, ws, hs);
            Line2D.Double l2d = new Line2D.Double(x01, y01, x02, y02);
            return selRec.intersectsLine(l2d);
        }
        command("esc");
        return false;
    }

    //-------------CHECK WHETHER THE SELECTION REC AND DRAWN REC INTERSECT-------------------------------------
    public boolean recSelection(Rectangle r) {
        intersection1 = false;
        intersection2 = false;
        xrec = 0;
        yrec = 0;
        if (xs != 0 && ys != 0) {
            int w0r;
            int x0r;
            if (r.getx2() > r.getx1()) {
                x0r = r.getx1();
                w0r = r.getx2() - r.getx1();
            } else {
                x0r = r.getx2();
                w0r = r.getx1() - r.getx2();
            }
            int h0r;
            int y0r;
            if (r.gety2() > r.gety1()) {
                y0r = r.gety1();
                h0r = r.gety2() - r.gety1();
            } else {
                y0r = r.gety2();
                h0r = r.gety1() - r.gety2();
            }
            selRec = new Rectangle2D.Double(xs, ys, ws, hs);
            Rectangle2D.Double r2d = new Rectangle2D.Double(x0r, y0r, w0r, h0r);

            //..........contour's intersection condition..............
            for (xrec = r2d.getMinX(); xrec <= r2d.getMaxX(); xrec++) {
                if (selRec.contains(xrec, r2d.getMinY()) || selRec.contains(xrec, r2d.getMaxY())) intersection1 = true;
            }
            for (yrec = r2d.getMinY(); yrec <= r2d.getMaxY(); yrec++) {
                if (selRec.contains(r2d.getMinX(), yrec) || selRec.contains(r2d.getMaxX(), yrec)) intersection2 = true;
            }
        }
        if (command("esc")) return false;
        return intersection1 || intersection2;
    }

    //---------------CHECK WHETHER THE SELECTION REC AND DRAWN CIRCLE INTERSECT----------------------------------
    public boolean circleSelection(Circle c) {
        intersection1 = false;
        intersection2 = false;
        if (xs != 0 && ys != 0 && ws != 0 && hs != 0 && c.getX() != 0 && c.getY() != 0 && c.getR() != 0) {
            selRec = new Rectangle2D.Double(xs, ys, ws, hs);
            Ellipse2D.Double c2d = new Ellipse2D.Double(c.getX() - c.getR(), c.getY() - c.getR(), 2 * c.getR(), 2 * c.getR());
            int xocirc = c.getX();
            int yocirc = c.getY();
            int rcirc = c.getR();
            double xcirc;
            for (xcirc = c2d.getMinX(); xcirc <= c2d.getMaxX(); xcirc++) {
                if ((selRec.contains(xcirc, Math.sqrt(Math.pow(rcirc, 2) - Math.pow((xcirc - xocirc), 2)) + yocirc)) ||
                        (selRec.contains(xcirc, -Math.sqrt(Math.pow(rcirc, 2) - Math.pow((xcirc - xocirc), 2)) + yocirc)))
                    intersection1 = true;
            }
        }
        if (command("esc")) return false;
        return intersection1 || intersection2;
    }

    //-----------------CHECK WHETHER THE SELECTION REC AND DRAWN TEXT INTERSECT------------------------------------
    public boolean textSelection(Text t) {
        if (t != null) {
            selRec = new Rectangle2D.Double(xs, ys, ws, hs);
            Rectangle2D txtRec = new Rectangle2D.Double(t.getx(), t.gety() - 12, g2.getFontMetrics().stringWidth(t.getText()),
                    g2.getFontMetrics().getHeight());
            return selRec.intersects(txtRec);
        }
        return false;
    }

    //-----------------CHECK WHETHER THE SELECTION REC AND IMPORTED IMAGE INTERSECT-------------------------------
    public boolean imageSelection(ImageClass imageClass) {
        if (xs != 0 && ys != 0 && imageClass != null) {
            selRec = new Rectangle2D.Double(xs, ys, ws, hs);
            contourSel = new Rectangle2D.Double(xrec, yrec, imageClass.getWidth(), imageClass.getHeight());

            //.........contour's intersection condition................
            double xcont;
            for (xcont = contourSel.getMinX(); xcont <= contourSel.getMaxX(); xcont++) {
                double ycont;
                for (ycont = contourSel.getMinY(); ycont <= contourSel.getMaxY(); ycont++) {
                    if (selRec.contains(xcont + imageClass.getXimg(), ycont + imageClass.getYimg())) return true;
                }
            }
        }
        command("esc");
        return false;
    }

    //----------------CHECK WHETHER THE RIGHT MOUSECLICK IS OVER AN IMAGE---------------------------------------
    public boolean overImage(ImageClass imageClass) {
        selRec = new Rectangle2D.Double(xs, ys, ws, hs);
        contourSel = new Rectangle2D.Double(imageClass.getXimg(), imageClass.getYimg(), imageClass.getWidth(), imageClass.getHeight());
        Shape newContourSel=atinverted2.createTransformedShape(contourSel);
        if (newContourSel.contains(p.getX(), p.getY())) return true;
        command("esc");
        return false;
    }

    //----------------EXPORT AS IMAGE-----------------------------------------------------------------------------
    public void export() throws IOException, AWTException {
        if (dir != null && filename != null) {
            BufferedImage bufferedImage = new Robot().createScreenCapture(this.bounds());
            Graphics2D g2d = bufferedImage.createGraphics();
            this.print(g2d);
            if (System.getProperty("os.name").toLowerCase().contains("win"))
                ImageIO.write(bufferedImage, "jpeg", new File(dir + "\\" + filename + ".jpeg"));
            else if (System.getProperty("os.name").toLowerCase().contains("linux"))
                ImageIO.write(bufferedImage, "jpeg", new File(dir + "/" + filename + ".jpeg"));
        } else Draw.setText("Error");
    }

    //---------------IMPORT FILE-----------------------------------------------------------------------------
    public void importt() throws IOException {
        image = Toolkit.getDefaultToolkit().getImage(dir + "\\" + filename);
        if (System.getProperty("os.name").toLowerCase().contains("linux"))
            img = ImageIO.read(new File(dir + "/" + filename));
        else if (System.getProperty("os.name").toLowerCase().contains("win"))
            img = ImageIO.read(new File(dir + "\\" + filename));
        Rectangle contour = new Rectangle(ximg - 2, ximg + img.getWidth(this) + 1, yimg - 2, yimg + img.getHeight(this) + 1, dCol);
        ImageClass imageClass = new ImageClass(image, img, ximg, yimg, contour, bCol);
        contour.setImageClass(imageClass);  //drawing contour
        imageClasses.add(imageClass);
        safelyRepaint();
        repaint();
    }

    //---------------OPEN FILE------------------------------------------------------------------------------
    public void open() {
        safelyRepaint();
        repaint();
    }

    //----------------ESC KEY PRESSED------------------------------------------------------------------------
    public void esc() {
        for (Line l : lines) {
            l.markedOff();
            l.setCol(l.getCol());
            l.selectedOff();
        }
        for (Circle c : circles) {
            c.setCol(c.getCol());
            c.markedOff();
            c.selectedOff();
        }
        for (Rectangle r : rectangles) {
            r.setCol(r.getCol());
            r.markedOff();
            r.selectedOff();
        }
        moving = false;
        drawing = false;
        copying = false;
        safelyRepaint();
        repaint();
    }

    //---------------WRITE A ONE-LINE TEXT AT A SPECIFIED POINT-----------------------------------------------------
    public static void drawText(String s) {
        inputText = s;
        readyToInputText = false;
        readyToDrawText = true;
    }

    //--------------SET GRID SIZE THRU GRIDSIZEINPUT------------------------------------------------------------
    public static void setGrid(int i) {
        gridSize = i;
    }

    //--------------SNAP-TO-GRID MODE----------------------------------------------------------------------------
    public void snapToGrid() {
        p = new Point2D.Double(MouseInfo.getPointerInfo().getLocation().x - 7 - screenx,
                MouseInfo.getPointerInfo().getLocation().y - 53 - screeny);

        int offset = 0;
        if (gridSize!=0) {
            for (Line l : gridLines) {
                if (l.isHorizontal()) {
                    for (int i = 0; i < this.getWidth()/gridSize+2; i++) {
                        //..grid snapRecs geometry....................
                        if (!gridSRadded) l.addSnapRec(new Rectangle2D.Double(l.getx1()+offset-18, l.gety1()-8, 16, 16));
                        offset += gridSize;
                    }
                }
                offset=0;

                if (!l.getSnapRecs().isEmpty()) for (Rectangle2D sr : l.getSnapRecs()) {
                    if (sr.contains((int) p.getX(), (int) p.getY())) {
                        xsnap = (int) sr.getCenterX();
                        ysnap = (int) sr.getCenterY();
                        at.transform(new Point2D.Double(xsnap, ysnap), pSnap);
                        l.srContainsOn(l.getSnapRecs().indexOf(sr));
                    } else l.srContainsOff(l.getSnapRecs().indexOf(sr));
                }
            }
            gridSRadded=true;
        }
        snap.doSnapToGrid();
    }

    //............drawing grid as lines...................
    public void grid() {
        int gridX = -20;
        int gridY = -20;
        if (gridMode&&!gridAdded) {
            if (gridSize!=0) {
                for (int i=0; i<this.getWidth()/gridSize+2; i++) {
                    gridX += gridSize;
                    gridY += gridSize;
                    gridLines.add(new Line(-10, 1000, gridY, gridY, Color.GRAY, false, true));
                    gridLines.add(new Line(gridX, gridX, -10, 500, Color.GRAY, false, false));
                    gridAdded=true;
                }
            }
        } else if (!gridMode) {
            gridAdded=false;
            gridLines.removeAll(gridLines);
        }
        repaint();
    }

    //----------GETTERS AND SETTERS--------------------------------------------------------------------------
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
    public String getFilename() {
        return this.filename;
    }
    public void setDir(String s) {
        this.dir=s;
    }
    public String getDir() {
        if (this.dir==null) return "";
        else return this.dir;
    }
    public ArrayList<Line> getLines() {
        return this.lines;
    }
    public void setLines(ArrayList<Line> al) {
        this.lines=al;
    }

    public void removeLine(Line l) {
        this.lines.remove(l);
    }
    public ArrayList<Rectangle> getRectangles() {
        return this.rectangles;
    }
    public void setRectangles(ArrayList<Rectangle> r) {
        this.rectangles=r;
    }

    public void removeRec(Rectangle r) {
        this.rectangles.remove(r);
    }
    public ArrayList<Circle> getCircles() {
        return this.circles;
    }
    public void setCircles(ArrayList<Circle>  c) {
        this.circles=c;
    }
    public void setTexts(ArrayList<Text>  t) {
        this.texts=t;
    }

    public void removeCircle(Circle c) {
        this.circles.remove(c);
    }

    public void removeText(Text t) {
        this.texts.remove(t);
    }
    public ArrayList<ImageClass> getImageClasses() {
        return this.imageClasses;
    }
    public void setImageClasses(ArrayList<ImageClass> ic) {
        this.imageClasses=ic;
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
    public TextInput getTextInput() {
        return this.ti;
    }
    public static Timer getTimer() {
        return timer;
    }
    public static boolean isGridMode() {
        return gridMode;
    }
    public static boolean isSnapToGridMode() {
        return snapToGridMode;
    }
    public static void snapToGridOn() {snapToGridMode=true;}
    public static void snapToGridOff() {snapToGridMode=false;}
    public static void gridModeOn() {
        gridMode=true;
    }
    public static void gridModeOff() {
        gridMode=false;
    }
    public ArrayList<Text> getTexts() { return this.texts; }
    public static void setSRfalse(){gridSRadded=false;}
    public static boolean getSRadded() {return gridSRadded;}
    public void setPlindex(int i) {this.plindex=i;}
    public int getPlindex() {return this.plindex;}
    public void setZoomFactor(double zoomFactor) {this.zoomFactor=zoomFactor;}
    public boolean getGridMode() {return this.gridMode;}
    public void setPrevZoomFactor(double prevZoomFactor) {this.prevZoomFactor=prevZoomFactor;}
    public void setxOffset(double xOffset) {this.xOffset=xOffset;}
    public void setyOffset(double yOffset) {this.yOffset=yOffset;}
    public void setPoint(Point2D point) {this.p=point;}
    public Point2D getPoint() {return this.p;}
    public int getScreenx() {return this.screenx;}
    public int getScreeny() {return this.screeny;}
    public AffineTransform getAt() {return this.at;}
    public AffineTransform getAtinverted() {return this.atinverted;}
    public AffineTransform getAtinverted2() {return this.atinverted2;}
    public void setAtinverted(AffineTransform atinverted) {this.atinverted = atinverted;}
    public void setAtinverted2(AffineTransform atinverted2) {this.atinverted2 = atinverted2;}
    public void setAt(AffineTransform at) {this.at=at;}
    public void setXdyn(int xdyn) {this.xdyn=xdyn;}
    public int getDx() {return this.dx;}
    public void setYdyn(int ydyn) {this.ydyn=ydyn;}
    public int getDy() {return this.dy;}
    public int getX1() {return this.x1;}
    public int getY1() {return this.y1;}
    public int getX2() {return this.x2;}
    public int getY2() {return this.y2;}
    public boolean getOrtoX() {return this.ortoX;}
    public boolean getOrtoY() {return this.ortoY;}
    public void setX2(int x2) {this.x2=x2;}
    public void setY2(int y2) {this.y2=y2;}
    public int getXdyn() {return this.xdyn;}
    public int getYdyn() {return this.ydyn;}
    public int getXo() {return this.xo;}
    public int getYo() {return this.yo;}
    public void setR(int r) {this.r=r;}
    public int getXd() {return this.xd;}
    public void setD(int d) {this.d=d;}

    public double getZoomFactor() {
        return zoomFactor;
    }

    public double getPrevZoomFactor() {
        return prevZoomFactor;
    }

    public int getYd() {
        return yd;
    }

    public int getX1r() {
        return x1r;
    }

    public int getY1r() {
        return y1r;
    }

    public void setX2r(int x2r) {
        this.x2r = x2r;
    }

    public void setY2r(int y2r) {
        this.y2r = y2r;
    }

    public int getLocationX() {
        return MouseInfo.getPointerInfo().getLocation().x;
    }

    public int getLocationY() {
        return MouseInfo.getPointerInfo().getLocation().y;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public Graphics2D getG2() {
        return g2;
    }

    public void setP2(Point2D p2) {
        this.p2 = p2;
    }

    public Point2D getP2() {
        return p2;
    }

    public Point2D getP3() {
        return p3;
    }

    public void setP3(Point2D p3) {
        this.p3 = p3;
    }

    public void setPSnap(Point2D pSnap) {this.pSnap=pSnap;}
    public Point2D getpSnap() {return this.pSnap;}

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }
    public boolean snapMode() {return this.snapMode;}
    public boolean snapToGridMode() {return this.snapToGridMode;}
    public Zoom getZoom() {return this.zoom;}

    public void setXimg(int ximg) {
        this.ximg = ximg;
    }

    public void setYimg(int yimg) {
        this.yimg = yimg;
    }

    public Color getdCol() {return this.dCol;}

    public int getX2r() {
        return x2r;
    }

    public int getY2r() {
        return y2r;
    }

    public int getR() {
        return r;
    }

    public String getInputText() {return this.inputText;}
    public boolean isReadyToDrawText() {return this.readyToDrawText;}
    public void setReadyToDrawTextOff() {this.readyToDrawText=false;}
    public AffineTransform getAtSR() {return this.atSR;}
    public List<Line> getGridLines() {return this.gridLines;}
    public AffineTransform getAtGrid() {return this.atGrid;}
    public boolean isSelection() {return this.selection;}

    public Point2D getP1sel() {
        return p1sel;
    }

    public void setXs(int xs) {
        this.xs = xs;
    }

    public void setYs(int ys) {
        this.ys = ys;
    }

    public void setWs(int ws) {
        this.ws = ws;
    }

    public void setHs(int hs) {
        this.hs = hs;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
