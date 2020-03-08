package draw.func;

import draw.Canvas;
import objs.*;
import objs.Rectangle;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Selecting {
    private Canvas canvas;
    private CommandLine commandLine;

    public Selecting(Canvas canvas) {
        this.canvas=canvas;
        this.commandLine=canvas.getCommandLine();
    }

    public boolean lineSelection(Line l) {
        int x01 = l.getx1();
        int x02 = l.getx2();
        int y01 = l.gety1();
        int y02 = l.gety2();

        if (canvas.getXs() != 0 && canvas.getYs() != 0 && x01 != 0 && y01 != 0 && x02 != 0 && y02 != 0) {
            Rectangle2D.Double selRec = (new Rectangle2D.Double(canvas.getXs(), canvas.getYs(),
                    canvas.getWs(), canvas.getHs()));
            Line2D.Double l2d = new Line2D.Double(x01, y01, x02, y02);
            return selRec.intersectsLine(l2d);
        }
        commandLine.command("esc");
        return false;
    }

    public boolean rectangleSelection(Rectangle r) {
        boolean intersection1 = false;
        boolean intersection2 = false;
        double xrec = 0;
        double yrec = 0;
        if (canvas.getXs() != 0 && canvas.getYs() != 0) {
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
            Rectangle2D.Double selRec = (new Rectangle2D.Double(canvas.getXs(), canvas.getYs(),
                    canvas.getWs(), canvas.getHs()));
            Rectangle2D.Double r2d = new Rectangle2D.Double(x0r, y0r, w0r, h0r);

            //..........contour's intersection condition..............
            for (xrec = r2d.getMinX(); xrec <= r2d.getMaxX(); xrec++) {
                if (selRec.contains(xrec, r2d.getMinY()) || selRec.contains(xrec, r2d.getMaxY())) {
                    intersection1 = true;
                }
            }
            for (yrec = r2d.getMinY(); yrec <= r2d.getMaxY(); yrec++) {
                if (selRec.contains(r2d.getMinX(), yrec) || selRec.contains(r2d.getMaxX(), yrec)) {
                    intersection2 = true;
                }
            }
        }
        if (commandLine.command("esc")) return false;
        return intersection1 || intersection2;
    }

    public boolean circleSelection(Circle c) {
        boolean intersection1 = false;
        boolean intersection2 = false;
        if (canvas.getXs() != 0 && canvas.getYs() != 0 && canvas.getWs() != 0 &&
                canvas.getHs() != 0 && c.getX() != 0 && c.getY() != 0 && c.getR() != 0) {
            Rectangle2D.Double selRec = (new Rectangle2D.Double(canvas.getXs(), canvas.getYs(),
                    canvas.getWs(), canvas.getHs()));
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
        if (commandLine.command("esc")) return false;
        return intersection1 || intersection2;
    }

    public boolean textSelection(Text t) {
        Graphics2D g2 = canvas.getG2();

        if (t != null) {
            Rectangle2D.Double selRec = (new Rectangle2D.Double(canvas.getXs(), canvas.getYs(),
                    canvas.getWs(), canvas.getHs()));
            Rectangle2D txtRec = new Rectangle2D.Double(t.getx(), t.gety() - 12, g2.getFontMetrics().stringWidth(t.getText()),
                    g2.getFontMetrics().getHeight());
            return selRec.intersects(txtRec);
        }
        return false;
    }

    public boolean imageSelection(ImageClass imageClass) {
        if (canvas.getXs() != 0 && canvas.getYs() != 0 && imageClass != null) {
            Rectangle2D.Double selRec = (new Rectangle2D.Double(canvas.getXs(), canvas.getYs(),
                    canvas.getWs(), canvas.getHs()));
            Rectangle2D.Double contourSel = new Rectangle2D.Double(canvas.getXrec(),
                    canvas.getYrec(), imageClass.getWidth(), imageClass.getHeight());

            //.........contour's intersection condition................
            double xcont;
            for (xcont = contourSel.getMinX(); xcont <= contourSel.getMaxX(); xcont++) {
                double ycont;
                for (ycont = contourSel.getMinY(); ycont <= contourSel.getMaxY(); ycont++) {
                    if (selRec.contains(xcont + imageClass.getXimg(), ycont + imageClass.getYimg())) return true;
                }
            }
        }
        commandLine.command("esc");
        return false;
    }

    public boolean isOverImage(ImageClass imageClass) {
        Rectangle2D.Double selRec = (new Rectangle2D.Double(canvas.getXs(), canvas.getYs(),
                canvas.getWs(), canvas.getHs()));
        Rectangle2D.Double contourSel = new Rectangle2D.Double(canvas.getXrec(),
                canvas.getYrec(), imageClass.getWidth(), imageClass.getHeight());
        Shape newContourSel = canvas.getAtinverted2().createTransformedShape(contourSel);
        if (newContourSel.contains(canvas.getPoint().getX(), canvas.getPoint().getY())) return true;
        commandLine.command("esc");
        return false;
    }
}
