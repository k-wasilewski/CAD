package draw.paint;

import draw.Canvas;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class PaintSelectionRec {
    private Canvas canvas;

    public PaintSelectionRec(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setupSelRecInterval() {
        int screenx=canvas.getScreenx();
        int screeny=canvas.getScreeny();
        AffineTransform atinverted = canvas.getAtinverted();
        Point2D p1sel = canvas.getP1sel();
        int dx = canvas.getDx();
        int dy = canvas.getDy();

        if (canvas.isSelection()) {
            canvas.safelyRepaint();
            if (canvas.getLocationX() != 0 && canvas.getLocationY() != 0)
                canvas.setPoint(new Point2D.Double(canvas.getLocationX() - 8 - screenx,
                        canvas.getLocationY() - 54 - screeny));
            Point2D p2sel = new Point2D.Double();
            atinverted.transform(canvas.getPoint(), p2sel);
            if (p1sel.getX() != 0 && p2sel.getY() != 0) {
                p1sel.setLocation(p1sel.getX() + dx, p1sel.getY() - dy);
                if (p2sel.getX() > p1sel.getX()) {
                    canvas.setXs((int) p1sel.getX());
                    canvas.setWs((int) p2sel.getX() - (int) p1sel.getX());
                } else {
                    canvas.setXs((int) p2sel.getX());
                    canvas.setWs((int) p1sel.getX() - (int) p2sel.getX());
                }
                if (p2sel.getY() > p1sel.getY()) {
                    canvas.setYs((int) p1sel.getY());
                    canvas.setHs((int) p2sel.getY() - (int) p1sel.getY());
                } else {
                    canvas.setYs((int) p2sel.getY());
                    canvas.setHs((int) p1sel.getY() - (int) p2sel.getY());
                }
            }
            canvas.repaint();
            canvas.setDx(0);
            canvas.setDy(0);
        }
    }
}
