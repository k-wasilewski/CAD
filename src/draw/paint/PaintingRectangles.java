package draw.paint;

import draw.Canvas;
import objs.ImageClass;
import objs.Rectangle;
import java.awt.*;
import java.util.List;

public class PaintingRectangles {
    private Canvas canvas;

    public PaintingRectangles(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawSnapRecs() {
        for (Rectangle r : canvas.getRectangles()) {
            if (r.getContains1() && canvas.snapMode()) {
                canvas.getG2().draw(r.getSr1());
            }
            else if (r.getContains2() && canvas.snapMode()) {
                canvas.getG2().draw(r.getSr2());
            }
            else if (r.getContains3() && canvas.snapMode()) {
                canvas.getG2().draw(r.getSr3());
            }
            else if (r.getContains4() && canvas.snapMode()) {
                canvas.getG2().draw(r.getSr4());
            }
        }
    }

    public void drawRectangles(Graphics g) {
        Graphics2D g2=canvas.getG2();
        Color dCol = canvas.getdCol();
        int x1r=canvas.getX1r();
        int y1r = canvas.getY1r();
        int x2r=canvas.getX2r();
        int y2r=canvas.getY2r();
        List<Rectangle> rectangles = canvas.getRectangles();
        int wr;
        int hr;
        int xr;
        int yr;

        if (x2r > x1r) {
            xr = x1r;
            wr = x2r - x1r;
        } else {
            xr = x2r;
            wr = x1r - x2r;
        }
        if (y2r > y1r) {
            yr = y1r;
            hr = y2r - y1r;
        } else {
            yr = y2r;
            hr = y1r - y2r;
        }
        g.setColor(dCol);
        if (xr != 0 && yr != 0) g.drawRect(xr, yr, wr, hr);
        if (!rectangles.isEmpty()) for (Rectangle r : rectangles) {
            if (r.getx1() != 0 && r.getx2() != 0 && canvas.getSelecting().rectangleSelection(r)) {
                r.setCol(Color.GRAY);
                r.markedOn();
            } else {
                r.markedOff();
                if (!r.getColoured()) {
                    r.setCol(dCol);
                    r.setColoured();
                }
                if (r.getCol() == Color.GRAY) r.setCol(r.getColH());
            }
            if (!canvas.getImageClasses().isEmpty()) {
                for (ImageClass imageClass : canvas.getImageClasses()) {
                    if (r.getImageClass() != null) {
                        if (canvas.getSelecting().imageSelection(r.getImageClass())) {
                            r.setCol(Color.GRAY);
                            r.markedOn();
                        } else {
                            r.markedOff();
                            r.setCol(dCol);
                        }
                    }
                }
            }

            g.setColor(r.getCol());
            int x1rec = r.getx1();
            int x2rec = r.getx2();
            int y1rec = r.gety1();
            int y2rec = r.gety2();

            if (x2rec > x1rec) {
                xr = x1rec;
                wr = x2rec - x1rec;
            } else {
                xr = x2rec;
                wr = x1rec - x2rec;
            }
            if (y2rec > y1rec) {
                yr = y1rec;
                hr = y2rec - y1rec;
            } else {
                yr = y2rec;
                hr = y1rec - y2rec;
            }
            if (r.getx1() != 0 && r.gety1() != 0 && r.getx2() != 0 && r.gety2() != 0 && !r.isSelected())
                g.drawRect(xr, yr, wr, hr);
            else if (r.getx1() != 0 && r.gety1() != 0 && r.getx2() != 0 && r.gety2() != 0 && r.isSelected()) {
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(xr, yr, wr, hr);
                g2.setStroke(new BasicStroke(1));
            }
        }
    }
}
