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
        if (MouseInfo.getPointerInfo().getLocation().x != 0 && MouseInfo.getPointerInfo().getLocation().y != 0) {
            canvas.setPoint(new Point2D.Double(Math.round((MouseInfo.getPointerInfo().getLocation().x - 8 - canvas.getScreenx())),
                    Math.round((MouseInfo.getPointerInfo().getLocation().y - 54 - canvas.getScreeny()))));
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
                    + Math.pow((canvas.getYo() - canvas.getXdyn()), 2)));
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
}
