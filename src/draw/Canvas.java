package draw;

import draw.func.*;
import draw.paint.*;
import objs.*;
import java.awt.*;
import objs.Rectangle;
import ui.TextInput;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;

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
    public static boolean snapMode;
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
    private int plindex = 0;
    private boolean popup;
    private CommandLine commandLine = new CommandLine(this);
    private Zooming zoom = new Zooming(this);
    private Snapping snap = new Snapping(this);
    private PaintingImages paintingImages = new PaintingImages(this);
    private PaintingLines paintingLines = new PaintingLines(this);
    private PaintingCircles paintingCircles = new PaintingCircles(this);
    private PaintingRectangles paintingRectangles = new PaintingRectangles(this);
    private PaintingTexts paintingTexts = new PaintingTexts(this);
    private PaintingGrid paintingGrid = new PaintingGrid(this);
    private PaintSelectionRec paintSelectionRec = new PaintSelectionRec(this);
    private boolean polyline;
    private MouseEvents mouseEvents = new MouseEvents(this);
    private Point2D pa;
    private TimerClass timerClass = new TimerClass(this);
    private SettingCoordinates settingCoordinates = new SettingCoordinates(this);
    private Moving move = new Moving(this);
    private Selecting selecting = new Selecting(this);
    private Grid grid = new Grid(this);

    public Canvas() {
        addMouseListener(this);
        addMouseWheelListener(this);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        zoom.noZoom();
        zoom.dynamicZoom();
        paintSelectionRec.setupSelRecInterval();

        if (movingC) move.moveMousewheel();
        else if (moving) move.move();
        else if (copying) move.copy();;

        if (snapToGridMode) grid.setupGridSnapRecs();
        grid.drawGrid();
        snap.snapOnIntervals();

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g.setColor(bCol);

        paintingImages.importImage();
        zoom.zoomImage();
        paintingImages.drawImageLayers();

        screenx = this.getLocationOnScreen().x - 8;
        screeny = this.getLocationOnScreen().y - 54;

        setBackground(bCol);
        g.setColor(bCol);
        g2.setBackground(bCol);

        paintingGrid.drawSnapRecs();
        paintingCircles.drawSnapRecs();
        paintingImages.drawSnapRecs();
        paintingLines.drawSnapRecs();
        paintingRectangles.drawSnapRecs();
        paintingTexts.drawSnapRecs();
        paintingLines.drawLines(g);
        paintingGrid.drawGrid(g);
        paintingRectangles.drawRectangles(g);
        paintingCircles.drawCircles(g);
        timerClass.start();

        if ((!commandLine.command("l")) && (!commandLine.command("pl")) && (!commandLine.command("c"))
                && (!commandLine.command("dist")) && (!commandLine.command("rec")))
            input = "null";

        paintingTexts.drawTexts(g);

        if (selection) {
            g.setColor(Color.GRAY);
            if (xs != 0 && ys != 0 && ws != 0 && hs != 0) g.drawRect(xs, ys, ws, hs);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseEvents.mouseWheelMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseEvents.mousePressed(e);
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
        xs = 0;
        ys = 0;
        ws = 0;
        hs = 0;
        p2 = null;
        inputText = null;
    }

    public void exportAsImage() throws IOException, AWTException {
        if (dir != null && filename != null) {
            BufferedImage bufferedImage = new Robot().createScreenCapture(this.bounds());
            Graphics2D g2d = bufferedImage.createGraphics();
            this.print(g2d);
            if (System.getProperty("os.name").toLowerCase().contains("win"))
                ImageIO.write(bufferedImage, "jpeg", new File(dir + "\\" + filename + ".jpeg"));
            else if (System.getProperty("os.name").toLowerCase().contains("linux"))
                ImageIO.write(bufferedImage, "jpeg", new File(dir + "/" + filename + ".jpeg"));
        } else CADapp.setText("Error");
    }

    public void importFile() throws IOException {
        image = Toolkit.getDefaultToolkit().getImage(dir + "\\" + filename);
        if (System.getProperty("os.name").toLowerCase().contains("linux"))
            img = ImageIO.read(new File(dir + "/" + filename));
        else if (System.getProperty("os.name").toLowerCase().contains("win"))
            img = ImageIO.read(new File(dir + "\\" + filename));
        Rectangle contour = new Rectangle(ximg - 2, ximg + img.getWidth(this) + 1, yimg - 2, yimg + img.getHeight(this) + 1, dCol);
        ImageClass imageClass = new ImageClass(image, img, ximg, yimg, contour, bCol);
        contour.setImageClass(imageClass);
        imageClasses.add(imageClass);
        safelyRepaint();
        repaint();
    }

    public void openFile() {
        safelyRepaint();
        repaint();
    }

    public void escKeyPressed() {
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

    public static void drawText(String s) {
        inputText = s;
        readyToInputText = false;
        readyToDrawText = true;
    }

    public static void setGrid(int i) {
        gridSize = i;
    }

    public void popupOff() {this.popup=false;}
    public void addImageClass(int i, ImageClass ic) {this.imageClasses.add(i, ic);}
    public void addImageClass(ImageClass ic) {this.imageClasses.add(ic);}
    public void removeImageClass(ImageClass ic) {this.imageClasses.remove(ic);}
    public void setFilename(String s) {this.filename=s;}
    public String getFilename() {return this.filename;}
    public void setDir(String s) {this.dir=s;}
    public String getDir() {
        if (this.dir==null) return "";
        else return this.dir;
    }
    public ArrayList<Line> getLines() {return this.lines;}
    public void setLines(ArrayList<Line> al) {this.lines=al;}
    public void removeLine(Line l) {this.lines.remove(l);}
    public ArrayList<Rectangle> getRectangles() {return this.rectangles;}
    public void setRectangles(ArrayList<Rectangle> r) {this.rectangles=r;}
    public void removeRec(Rectangle r) {this.rectangles.remove(r);}
    public ArrayList<Circle> getCircles() {return this.circles;}
    public void setCircles(ArrayList<Circle>  c) {this.circles=c;}
    public void setTexts(ArrayList<Text>  t) {this.texts=t;}
    public void removeCircle(Circle c) {this.circles.remove(c);}
    public void removeText(Text t) {this.texts.remove(t);}
    public ArrayList<ImageClass> getImageClasses() {return this.imageClasses;}
    public void setImageClasses(ArrayList<ImageClass> ic) {this.imageClasses=ic;}
    public void setImage(Image i) {this.image=i;}
    public static boolean isSnapMode() {return snapMode;}
    public static void snapModeOn() {snapMode=true;}
    public static void snapModeOff() {snapMode=false;}
    public TextInput getTextInput() {return this.ti;}
    public static Timer getTimer() {return timer;}
    public static boolean isGridMode() {return gridMode;}
    public static boolean isSnapToGridMode() {return snapToGridMode;}
    public static void snapToGridOn() {snapToGridMode=true;}
    public static void snapToGridOff() {snapToGridMode=false;}
    public static void gridModeOn() {gridMode=true;}
    public static void gridModeOff() {gridMode=false;}
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
    public double getZoomFactor() {return zoomFactor;}
    public double getPrevZoomFactor() {return prevZoomFactor;}
    public int getYd() {return yd;}
    public int getX1r() {return x1r;}
    public int getY1r() {return y1r;}
    public void setX2r(int x2r) {this.x2r = x2r;}
    public void setY2r(int y2r) {this.y2r = y2r;}
    public int getLocationX() {return MouseInfo.getPointerInfo().getLocation().x;}
    public int getLocationY() {return MouseInfo.getPointerInfo().getLocation().y;}
    public double getxOffset() {return xOffset;}
    public double getyOffset() {return yOffset;}
    public Graphics2D getG2() {return g2;}
    public void setP2(Point2D p2) {this.p2 = p2;}
    public Point2D getP2() { return p2;}
    public Point2D getP3() { return p3;}
    public void setP3(Point2D p3) {this.p3 = p3;}
    public void setPSnap(Point2D pSnap) {this.pSnap=pSnap;}
    public Point2D getpSnap() {return this.pSnap;}
    public Robot getRobot() {return robot;}
    public void setRobot(Robot robot) {this.robot = robot;}
    public boolean snapMode() {return this.snapMode;}
    public boolean snapToGridMode() {return this.snapToGridMode;}
    public Zooming getZoom() {return this.zoom;}
    public void setXimg(int ximg) {this.ximg = ximg;}
    public void setYimg(int yimg) {this.yimg = yimg;}
    public Color getdCol() {return this.dCol;}
    public Color getbCol() {return this.bCol;}
    public int getX2r() {return x2r;}
    public int getY2r() {return y2r;}
    public int getR() {return r;}
    public String getInputText() {return this.inputText;}
    public boolean isReadyToDrawText() {return this.readyToDrawText;}
    public void setReadyToDrawTextOff() {this.readyToDrawText=false;}
    public AffineTransform getAtSR() {return this.atSR;}
    public List<Line> getGridLines() {return this.gridLines;}
    public AffineTransform getAtGrid() {return this.atGrid;}
    public boolean isSelection() {return this.selection;}
    public Point2D getP1sel() {return p1sel;}
    public void setXs(int xs) {this.xs = xs;}
    public void setYs(int ys) {this.ys = ys;}
    public void setWs(int ws) {this.ws = ws;}
    public void setHs(int hs) {this.hs = hs;}
    public void setDx(int dx) {this.dx = dx;}
    public void setDy(int dy) {this.dy = dy;}
    public void setInput(String input) {this.input = input;}
    public String getInput() {return input;}
    public void setInputH(String inputH) {this.inputH = inputH;}
    public String getInputH() {return inputH;}
    public void selectionOff() {this.selection=false;}
    public void drawingOff() {this.drawing=false;}
    public void polylineOn() {this.polyline=true;}
    public void setbCol(Color bCol) {this.bCol = bCol;}
    public void setdCol(Color dCol) {this.dCol = dCol;}
    public boolean isOrtoX() {return ortoX;}
    public boolean isOrtoY() {return ortoY;}
    public void setOrtoX(boolean ortoX) {this.ortoX=ortoX;}
    public void setOrtoY(boolean ortoY) {this.ortoY = ortoY;}
    public void setReadyToCopy(boolean readyToCopy) {this.readyToCopy = readyToCopy;}
    public void setReadyToMove(boolean readyToMove) {this.readyToMove = readyToMove;}
    public static void setReadyToInputText(boolean readyToInputText) {Canvas.readyToInputText = readyToInputText;}
    public TextInput getTi() {return ti;}
    public void polylineOff() {this.polyline = false;}
    public boolean isDrawing() {return drawing;}
    public CommandLine getCommandLine() {return this.commandLine;}
    public boolean isReadyToMove() {return readyToMove;}
    public boolean isReadyToCopy() {return readyToCopy;}
    public void setMoving(boolean moving) {this.moving = moving;}
    public void setCopying(boolean copying) {this.copying = copying;}
    public boolean isMoving() {return moving;}
    public boolean isCopying() {return copying;}
    public static boolean isReadyToInputText() {return readyToInputText;}
    public void setP1sel(Point2D p1sel) {this.p1sel = p1sel;}
    public void setPb(Point2D pb) {this.pb = pb;}
    public Point2D getPb() {return pb;}
    public void setX1s(int x1s) {this.x1s = x1s;}
    public void setY1s(int y1s) {this.y1s = y1s;}
    public void setMovingC(boolean movingC) {this.movingC = movingC;}
    public void setNoOfOvers(int noOfOvers) {this.noOfOvers = noOfOvers;}
    public int getNoOfOvers() {return noOfOvers;}
    public void drawingOn() {this.drawing=true;}
    public void selectionOn() {this.selection=true;}
    public Point2D getPa() {return this.pa;}
    public void setPa(Point2D pa) {this.pa=pa;}
    public void setTi(TextInput ti) {this.ti = ti;}
    public void setX1(int x1) {this.x1 = x1;}
    public void setY1(int y1) {this.y1 = y1;}
    public void setXo(int xo) {this.xo = xo;}
    public void setYo(int yo) {this.yo = yo;}
    public void setXd(int xd) {this.xd = xd;}
    public void setYd(int yd) {this.yd = yd;}
    public void setX1r(int x1r) {this.x1r = x1r;}
    public void setY1r(int y1r) {this.y1r = y1r;}
    public void setX1m(int x1m) {this.x1m = x1m;}
    public void setY1m(int y1m) {this.y1m = y1m;}
    public void setX1c(int x1c) {this.x1c = x1c;}
    public void setY1c(int y1c) {this.y1c = y1c;}
    public int getD() {return d;}
    public int getX2sh() {return x2sh;}
    public int getY2sh() {return y2sh;}
    public int getX1s() {return x1s;}
    public int getY1s() {return y1s;}
    public int getX1m() {return x1m;}
    public int getY1m() {return y1m;}
    public int getX1c() {return x1c;}
    public int getY1c() {return y1c;}
    public void setX2sh(int x2sh) {this.x2sh = x2sh;}
    public void setY2sh(int y2sh) {this.y2sh = y2sh;}
    public int getX2mh() {return x2mh;}
    public int getY2mh() {return y2mh;}
    public void setX2mh(int x2mh) {this.x2mh = x2mh;}
    public void setY2mh(int y2mh) {this.y2mh = y2mh;}
    public int getX2ch() {return x2ch;}
    public int getY2ch() {return y2ch;}
    public void setX2ch(int x2ch) {this.x2ch = x2ch;}
    public void setY2ch(int y2ch) {this.y2ch = y2ch;}
    public int getXs() {return xs;}
    public int getYs() {return ys;}
    public int getWs() {return ws;}
    public int getHs() {return hs;}
    public double getXrec() {return xrec;}
    public double getYrec() {return yrec;}
    public boolean isGridAdded() {return gridAdded;}
    public static int getGridSize() {return gridSize;}
    public void setGridAdded(boolean gridAdded) {this.gridAdded = gridAdded;}
    public Snapping getSnap() {return snap;}
    public static boolean isGridSRadded() {return gridSRadded;}
    public SettingCoordinates getSettingCoordinates() {return settingCoordinates;}
    public Selecting getSelecting() {return selecting;}
    public static boolean getReadyToInputText() {return readyToInputText;}
    public static boolean getReadyToDrawText() {return readyToDrawText;}
}
