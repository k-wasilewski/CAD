package draw;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class Zoom {
    private Canvas canvas;

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
        Point2D p3 = new Point2D.Double();
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
        canvas.getAtinverted().transform(canvas.getPoint(), p3);
        canvas.setXdyn((int) p3.getX() + canvas.getDx());
        canvas.setYdyn((int) p3.getY() + canvas.getDy());

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
        canvas.getAt().transform(canvas.getPoint(), canvas.getP2());
    }
}
