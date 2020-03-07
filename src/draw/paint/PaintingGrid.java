package draw.paint;

import draw.Canvas;
import draw.TransformedStroke;
import objs.Line;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PaintingGrid {
    private Canvas canvas;

    public PaintingGrid(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawSnapRecs() {
        Graphics2D g2 = canvas.getG2();
        AffineTransform atSR = canvas.getAtSR();
        AffineTransform atinverted2 = canvas.getAtinverted2();
        AffineTransform at = canvas.getAt();

        g2.setColor(Color.BLACK);
        if (atSR == null || atSR != atinverted2) {
            try {
                atSR = (AffineTransform) at.createInverse().clone();
            } catch (NoninvertibleTransformException ignored) {}
        }
        if (!canvas.getGridLines().isEmpty()) {
            for (Line l : canvas.getGridLines()) {
                for (Rectangle2D sr : l.getSnapRecs()) {
                    if (l.getSrContains(l.getSnapRecs().indexOf(sr)) && canvas.snapToGridMode()) { //single SR condition
                        Point2D pSR = new Point2D.Double(sr.getX(), sr.getY());
                        Point2D p2SR = new Point2D.Double();
                        atSR.transform(pSR, p2SR);
                        Shape sr2 = atSR.createTransformedShape(sr);
                        try {
                            g2.setStroke(new TransformedStroke(new BasicStroke(1), g2.getTransform()));
                        } catch (NoninvertibleTransformException ignored) {}
                        g2.draw(sr2);
                        try {
                            at.invert();
                        } catch (NoninvertibleTransformException ignored) {}
                    }
                }
            }
        }
    }

    public void drawGrid(Graphics g) {
        Graphics2D g2 = canvas.getG2();
        AffineTransform atSR = canvas.getAtSR();
        AffineTransform atinverted = canvas.getAtinverted();
        AffineTransform atGrid = canvas.getAtGrid();
        AffineTransform at = canvas.getAt();

        g.setColor(Color.GRAY);
        if (atGrid == null || atGrid != atinverted) {
            try {
                if (atSR != null) atGrid = atSR;
                else atGrid = (AffineTransform) at.createInverse().clone();
            } catch (NoninvertibleTransformException ignored) {}
        }
        if (!canvas.getGridLines().isEmpty()) {
            for (Line l : canvas.getGridLines()) {
                Point2D pGrid1 = new Point2D.Double(l.getx1(), l.gety1());
                Point2D p2Grid1 = new Point2D.Double();
                Point2D pGrid2 = new Point2D.Double(l.getx2(), l.gety2());
                Point2D p2Grid2 = new Point2D.Double();
                atGrid.transform(pGrid1, p2Grid1);
                atGrid.transform(pGrid2, p2Grid2);
                try {
                    g2.setStroke(new TransformedStroke(new BasicStroke(1), g2.getTransform()));
                } catch (NoninvertibleTransformException ignored) {}
                java.awt.geom.Line2D line = new java.awt.geom.Line2D.Double(p2Grid1.getX(), p2Grid1.getY(), p2Grid2.getX(), p2Grid2.getY());
                g2.draw(line);
                try {
                    at.invert();
                } catch (NoninvertibleTransformException ignored) {}
            }
        }
    }
}
