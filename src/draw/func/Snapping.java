package draw.func;

import draw.Canvas;
import objs.Circle;
import objs.ImageClass;
import objs.Line;
import objs.Rectangle;
import objs.Text;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Snapping {
    private draw.Canvas canvas;
    private boolean snapExecuted=false;
    private Zooming zoom;

    public Snapping(Canvas canvas) {
        this.canvas = canvas;
    }

    public void snapOnIntervals() {
        canvas.setPSnap(new Point2D.Double());
        if (zoom!=null) zoom.zoomSnap();
        snapCircles(canvas.getP2());
        snapTexts(canvas.getP2());
        snapImages(canvas.getP2());
        snapRectangles(canvas.getP2());
        doSnap();
    }

    public void doSnap() {
        try {
            canvas.setRobot(new Robot());
            if (canvas.getpSnap().getX() != 0 && canvas.getpSnap().getY() != 0
                    && canvas.snapMode() && !snapExecuted) {
                canvas.getRobot().mouseMove((int) canvas.getpSnap().getX() + 7 + canvas.getScreenx(),
                        (int) canvas.getpSnap().getY() + 53 + canvas.getScreeny());
                snapExecuted = true;
                try {
                    intervalSnapping();
                } catch (InterruptedException ignored) {}
            }
        } catch (AWTException ignored) {}
    }

    public void doSnapToGrid() {
        try {
            canvas.setRobot(new Robot());
            if (canvas.getpSnap().getX() != 0 && canvas.getpSnap().getY() != 0
                    && canvas.snapToGridMode() && !snapExecuted) {
                canvas.getRobot().mouseMove((int) canvas.getpSnap().getX() + 7 + canvas.getScreenx(),
                        (int) canvas.getpSnap().getY() + 53 + canvas.getScreeny());   //moves to actual mouse position itself !!
                snapExecuted = true;
                try {
                    intervalSnapping();
                } catch (InterruptedException ignored) {
                }
            }
        } catch (AWTException ignored) {
        }
    }

    public void intervalSnapping() throws InterruptedException {
        Thread.sleep(10);
        snapExecuted=false;
    }

    public void snapCircles(Point2D p2) {
        if (canvas.getZoom()!=null) zoom=canvas.getZoom();

        for (Circle c : canvas.getCircles()) {
            c.setSr(new Rectangle2D.Double(c.getX() - 8, c.getY() - 8, 16, 16));
            if (c.getSnapRec().contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomCircle(c);
                c.containsOn();
            } else c.containsOff();
        }
    }

    public void snapTexts(Point2D p2) {
        for (Text t : canvas.getTexts()) {
            t.setSr(new Rectangle2D.Double(t.getx() - 8, t.gety() - 8, 16, 16));
            if (t.getSr().contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomText(t);
                t.containsOn();
            } else t.containsOff();
        }
    }

    public void snapImages(Point2D p2) {
        for (ImageClass i : canvas.getImageClasses()) {
            Rectangle2D sr1;
            Rectangle2D sr2;
            Rectangle2D sr3;
            Rectangle2D sr4;

            i.updatesnapRecs();

            List<Rectangle2D> snapRecs = createImageSnapRecs(i);
            sr1=snapRecs.get(0);
            sr2=snapRecs.get(1);
            sr3=snapRecs.get(2);
            sr4=snapRecs.get(3);

            //.......conditions 'snapRec contains'.......
            if (sr1.contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomImage1(i);
                i.contains1on();
            } else i.contains1off();
            if (sr2.contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomImage2(i);
                i.contains2on();
            } else i.contains2off();
            if (sr3.contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomImage3(i);
                i.contains3on();
            } else i.contains3off();
            if (sr4.contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomImage4(i);
                i.contains4on();
            } else i.contains4off();
        }

        //...........lines SnapRec1, 2...............
        for (Line l : canvas.getLines()) {
            l.setSr1(new Rectangle2D.Double(l.getx1() - 8, l.gety1() - 8, 16, 16));
            if (l.getSr1().contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomLine1(l);
                l.contains1on();
            } else l.contains1off();
            l.setSr2(new Rectangle2D.Double(l.getx2() - 8, l.gety2() - 8, 16, 16));
            if (l.getSr2().contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomLine2(l);
                l.contains2on();
            } else l.contains2off();
        }
    }

    public List<Rectangle2D> createImageSnapRecs(ImageClass i) {
        List<Rectangle2D> list = new ArrayList<>();
        int x1rec1 = i.getSr1().getx1();
        int x2rec1 = i.getSr1().getx2();
        int y1rec1 = i.getSr1().gety1();
        int y2rec1 = i.getSr1().gety2();
        int xr1;
        int yr1;
        int wr1;
        int hr1;
        if (x2rec1 > x1rec1) {
            xr1 = x1rec1;
            wr1 = x2rec1 - x1rec1;
        } else {
            xr1 = x2rec1;
            wr1 = x1rec1 - x2rec1;
        }
        if (y2rec1 > y1rec1) {
            yr1 = y1rec1;
            hr1 = y2rec1 - y1rec1;
        } else {
            yr1 = y2rec1;
            hr1 = y1rec1 - y2rec1;
        }
        Rectangle2D sr1 = new Rectangle2D.Double(xr1, yr1, wr1, hr1);

        int x1rec2 = i.getSr2().getx1();
        int x2rec2 = i.getSr2().getx2();
        int y1rec2 = i.getSr2().gety1();
        int y2rec2 = i.getSr2().gety2();
        int xr2;
        int yr2;
        int wr2;
        int hr2;
        if (x2rec2 > x1rec2) {
            xr2 = x1rec2;
            wr2 = x2rec2 - x1rec2;
        } else {
            xr2 = x2rec2;
            wr2 = x1rec2 - x2rec2;
        }
        if (y2rec2 > y1rec2) {
            yr2 = y1rec2;
            hr2 = y2rec2 - y1rec2;
        } else {
            yr2 = y2rec2;
            hr2 = y1rec2 - y2rec2;
        }
        Rectangle2D sr2 = new Rectangle2D.Double(xr2, yr2, wr2, hr2);

        int x1rec3 = i.getSr3().getx1();
        int x2rec3 = i.getSr3().getx2();
        int y1rec3 = i.getSr3().gety1();
        int y2rec3 = i.getSr3().gety2();
        int xr3;
        int yr3;
        int wr3;
        int hr3;
        if (x2rec3 > x1rec3) {
            xr3 = x1rec3;
            wr3 = x2rec3 - x1rec3;
        } else {
            xr3 = x2rec3;
            wr3 = x1rec3 - x2rec3;
        }
        if (y2rec3 > y1rec3) {
            yr3 = y1rec3;
            hr3 = y2rec3 - y1rec3;
        } else {
            yr3 = y2rec3;
            hr3 = y1rec3 - y2rec3;
        }
        Rectangle2D sr3 = new Rectangle2D.Double(xr3, yr3, wr3, hr3);

        int x1rec4 = i.getSr4().getx1();
        int x2rec4 = i.getSr4().getx2();
        int y1rec4 = i.getSr4().gety1();
        int y2rec4 = i.getSr4().gety2();
        int xr4;
        int yr4;
        int wr4;
        int hr4;
        if (x2rec4 > x1rec4) {
            xr4 = x1rec4;
            wr4 = x2rec4 - x1rec4;
        } else {
            xr4 = x2rec4;
            wr4 = x1rec4 - x2rec4;
        }
        if (y2rec4 > y1rec4) {
            yr4 = y1rec4;
            hr4 = y2rec4 - y1rec4;
        } else {
            yr4 = y2rec4;
            hr4 = y1rec4 - y2rec4;
        }
        Rectangle2D sr4 = new Rectangle2D.Double(xr4, yr4, wr4, hr4);

        list.add(sr1);
        list.add(sr2);
        list.add(sr3);
        list.add(sr4);
        return list;
    }

    public void snapRectangles(Point2D p2) {
        for (Rectangle r : canvas.getRectangles()) {
            r.setSr1(new Rectangle2D.Double(r.getx1() - 8, r.gety1() - 8, 16, 16));
            if (r.getSr1().contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomRec1(r);
                r.contains1on();
            } else r.contains1off();
            r.setSr2(new Rectangle2D.Double(r.getx2() - 8, r.gety1() - 8, 16, 16));
            if (r.getSr2().contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomRec2(r);
                r.contains2on();
            } else r.contains2off();
            r.setSr3(new Rectangle2D.Double(r.getx1() - 8, r.gety2() - 8, 16, 16));
            if (r.getSr3().contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomRec3(r);
                r.contains3on();
            } else r.contains3off();
            r.setSr4(new Rectangle2D.Double(r.getx2() - 8, r.gety2() - 8, 16, 16));
            if (r.getSr4().contains((int) p2.getX(), (int) p2.getY())) {
                zoom.zoomRec4(r);
                r.contains4on();
            } else r.contains4off();
        }
    }
}
