package draw;

import objs.*;
import objs.Rectangle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class Zoom {
    private Canvas canvas;
    private boolean circlesZoomed=false;
    int xsnap;
    int ysnap;

    public Zoom(Canvas canvas) {
        this.canvas=canvas;
    }

    public void noZoom() {
        if (canvas.getGridMode()) {
            canvas.setZoomFactor(1);
            canvas.setPrevZoomFactor(1);
            canvas.setxOffset(1);
            canvas.setyOffset(1);
        }
    }

    public void dynamicZoom() {
        if (canvas.getLocationX() != 0 && canvas.getLocationY() != 0) {
            canvas.setPoint(new Point2D.Double(Math.round((canvas.getLocationX() - 8 - canvas.getScreenx())),
                    Math.round((canvas.getLocationY() - 54 - canvas.getScreeny()))));
        }
        canvas.setP3(new Point2D.Double());
        if (canvas.getAt() != canvas.getAtinverted()) {
            try {
                canvas.setAtinverted(canvas.getAt().createInverse());
            } catch (NoninvertibleTransformException ignored) {
            }
            canvas.setAt(canvas.getAtinverted());
        }
        if (canvas.getAt() != canvas.getAtinverted2()) {
            try {
                canvas.setAtinverted2(canvas.getAt().createInverse());
            } catch (NoninvertibleTransformException ignored) {
            }
            canvas.setAt(canvas.getAtinverted2());
        }
        canvas.getAtinverted().transform(canvas.getPoint(), canvas.getP3());
        canvas.setXdyn((int) canvas.getP3().getX() + canvas.getDx());
        canvas.setYdyn((int) canvas.getP3().getY() + canvas.getDy());

        if ((canvas.command("l") || canvas.command("pl")) && canvas.getX1() != 0 && canvas.getY1() != 0) {
            if (canvas.getOrtoY()) canvas.setX2(canvas.getX1());
            else canvas.setX2(canvas.getXdyn());
            if (canvas.getOrtoX()) canvas.setY2(canvas.getY1());
            else canvas.setY2(canvas.getYdyn());
            canvas.repaint();
        } else if ((canvas.command("c")) && canvas.getXo() != 0 && canvas.getYo() != 0) {
            canvas.setR((int) Math.sqrt(Math.pow((canvas.getXdyn() - canvas.getXo()), 2)
                    + Math.pow((canvas.getYo() - canvas.getYdyn()), 2)));
            canvas.repaint();
        } else if ((canvas.command("dist")) && canvas.getXd() != 0 && canvas.getYd() != 0) {
            canvas.setD((int) Math.sqrt(Math.pow((canvas.getXdyn() - canvas.getXd()), 2)
                    + Math.pow((canvas.getYd() - canvas.getYdyn()), 2)));
            canvas.repaint();
        } else if ((canvas.command("rec")) && canvas.getX1r() != 0 && canvas.getY1r() != 0) {
            canvas.setX2r(canvas.getXdyn());
            canvas.setY2r(canvas.getYdyn());
            canvas.repaint();
        }
        if (!circlesZoomed) {
            for (Circle c : canvas.getCircles()) {
                try {
                    canvas.getAt().invert();
                } catch (NoninvertibleTransformException ignored) {
                }
                Point2D newCenter = new Point2D.Double();
                canvas.getAt().transform(new Point2D.Double(c.getX(), c.getY()), newCenter);
                c.setX((int)newCenter.getX());
                c.setY((int)newCenter.getY());
            }
            circlesZoomed=true;
        }
    }

    public void zoomImage() {
        canvas.setAt(new AffineTransform());
        double xRel = canvas.getLocationX() - canvas.getLocationOnScreen().getX();
        double yRel = canvas.getLocationY() - canvas.getLocationOnScreen().getY();
        double zoomDiv = canvas.getZoomFactor() / canvas.getPrevZoomFactor();

        canvas.setxOffset((zoomDiv) * (canvas.getxOffset()) + (1 - zoomDiv) * xRel);
        canvas.setyOffset((zoomDiv) * (canvas.getyOffset()) + (1 - zoomDiv) * yRel);
        canvas.getAt().translate(canvas.getxOffset(), canvas.getyOffset());
        canvas.getAt().scale(canvas.getZoomFactor(), canvas.getZoomFactor());
        canvas.setPrevZoomFactor(canvas.getZoomFactor());
        canvas.getG2().transform(canvas.getAt());
    }

    public void zoomSnap() {
        canvas.setPoint(new Point2D.Double(Math.round((canvas.getLocationX() - 7 - canvas.getScreenx())),
                Math.round((canvas.getLocationY() - 53 - canvas.getScreeny()))));
        canvas.setP2(new Point2D.Double());
        try {canvas.getAt().invert();} catch (Exception e) {}
        canvas.setP2(canvas.getAt().transform(canvas.getPoint(), canvas.getP2()));
    }

    public void zoomCircle(Circle c) {
        xsnap = c.getX();
        ysnap = c.getY();
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {
        }
        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap), canvas.getpSnap());
    }

    public void zoomText(Text t) {
        xsnap = t.getx();
        ysnap = t.gety();
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {
        }
        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap), canvas.getpSnap());
    }

    public void zoomImage1(ImageClass i) {
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(i.getXimg(), i.getYimg()), canvas.getpSnap());
    }

    public void zoomImage2(ImageClass i) {
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(i.getXimg() + i.getWidth(), i.getYimg()), canvas.getpSnap());
    }

    public void zoomImage3(ImageClass i) {
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(i.getXimg(), i.getYimg() + i.getHeight()), canvas.getpSnap());
    }

    public void zoomImage4(ImageClass i) {
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(i.getXimg() + i.getWidth(), i.getYimg() + i.getHeight()), canvas.getpSnap());
    }

    public void zoomLine1(Line l) {
        xsnap = l.getx1();
        ysnap = l.gety1();
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap), canvas.getpSnap());
    }

    public void zoomLine2(Line l) {
        xsnap = l.getx2();
        ysnap = l.gety2();
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap), canvas.getpSnap());
    }

    public void zoomRec1(Rectangle r) {
        xsnap = r.getx1();
        ysnap = r.gety1();
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap), canvas.getpSnap());
    }

    public void zoomRec2(Rectangle r) {
        xsnap = r.getx2();
        ysnap = r.gety1();
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap), canvas.getpSnap());
    }

    public void zoomRec3(Rectangle r) {
        xsnap = r.getx1();
        ysnap = r.gety2();
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap), canvas.getpSnap());
    }

    public void zoomRec4(Rectangle r) {
        xsnap = r.getx2();
        ysnap = r.gety2();
        try {
            canvas.getAt().invert();
        } catch (NoninvertibleTransformException ignored) {}
        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap), canvas.getpSnap());
    }
}
