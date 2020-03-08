package draw.func;

import draw.Canvas;
import objs.Line;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Grid {
    private Canvas canvas;

    public Grid(Canvas canvas) {
        this.canvas=canvas;
    }

    public void drawGrid() {
        int gridX = -20;
        int gridY = -20;
        if (canvas.getGridMode()&&!canvas.isGridAdded()) {
            if (Canvas.getGridSize()!=0) {
                for (int i=0; i<canvas.getWidth()/Canvas.getGridSize()+2; i++) {
                    gridX += Canvas.getGridSize();
                    gridY += Canvas.getGridSize();
                    canvas.getGridLines().add(new Line(-10, 1000, gridY, gridY,
                            Color.GRAY, false, true));
                    canvas.getGridLines().add(new Line(gridX, gridX, -10, 500,
                            Color.GRAY, false, false));
                    canvas.setGridAdded(true);
                }
            }
        } else if (!canvas.getGridMode()) {
            canvas.setGridAdded(false);
            canvas.getGridLines().removeAll(canvas.getGridLines());
        }
        canvas.repaint();
    }

    public void setupGridSnapRecs() {
        canvas.setPoint(new Point2D.Double(canvas.getLocationX() - 7 - canvas.getScreenx(),
                canvas.getLocationY() - 53 - canvas.getScreeny()));

        int offset = 0;
        if (Canvas.getGridSize()!=0) {
            for (Line l : canvas.getGridLines()) {
                if (l.isHorizontal()) {
                    for (int i = 0; i < canvas.getWidth()/Canvas.getGridSize()+2; i++) {
                        //..grid snapRecs geometry....................
                        if (!canvas.isGridSRadded()) l.addSnapRec(new Rectangle2D.Double(l.getx1()+
                                offset-18, l.gety1()-8, 16, 16));
                        offset += Canvas.getGridSize();
                    }
                }
                offset=0;

                if (!l.getSnapRecs().isEmpty()) for (Rectangle2D sr : l.getSnapRecs()) {
                    if (sr.contains((int) canvas.getPoint().getX(), (int) canvas.getPoint().getY())) {
                        int xsnap = (int) sr.getCenterX();
                        int ysnap = (int) sr.getCenterY();
                        canvas.getAt().transform(new Point2D.Double(xsnap, ysnap),
                                canvas.getpSnap());
                        l.srContainsOn(l.getSnapRecs().indexOf(sr));
                    } else l.srContainsOff(l.getSnapRecs().indexOf(sr));
                }
            }
            canvas.setGridAdded(true);
        }
        canvas.getSnap().doSnapToGrid();
    }
}
