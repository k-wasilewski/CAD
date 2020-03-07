package draw.paint;

import draw.Canvas;
import objs.Circle;

import java.awt.*;
import java.util.List;

public class PaintingCircles {
    private Canvas canvas;

    public PaintingCircles(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawSnapRecs() {
        Graphics2D graphics2D = canvas.getG2();
        List<Circle> circleList = canvas.getCircles();

        graphics2D.setColor(canvas.getdCol());
        for (Circle c : circleList) {
            if (c.getContains()) {
                if (c.getContains() && canvas.snapMode()) {
                    graphics2D.draw(c.getSnapRec());
                }
            }
        }
    }

    public void drawCircles(Graphics g) {
        Graphics2D g2 = canvas.getG2();
        Color dCol = canvas.getdCol();
        int xo = canvas.getXo();
        int yo = canvas.getYo();
        int r = canvas.getR();
        //....................drawing a circle dynamically....
        g.setColor(dCol);
        if (xo != 0 && yo != 0 && r != 0) g.drawOval(xo - r, yo - r, 2 * r, 2 * r);
        for (Circle c : canvas.getCircles()) {
            //.................circles marked on................
            if (c.getX() != 0 && c.getY() != 0 && canvas.circleSelection(c)) {
                c.setCol(Color.GRAY);
                c.markedOn();
            } else {
                c.markedOff();
                if (!c.getColoured()) {
                    c.setCol(dCol);
                    c.setColoured();
                }
                if (c.getCol() == Color.GRAY) c.setCol(c.getColH());
            }

            //.................drawing a circle statically......
            if (c.getX() != 0 && c.getY() != 0 && !c.isSelected())
                g.drawOval(c.getX() - c.getR(), c.getY() - c.getR(), 2 * c.getR(), 2 * c.getR());
            else if (c.getX() != 0 && c.getY() != 0 && c.isSelected()) {
                //..............circle bold....................
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(c.getX() - c.getR(), c.getY() - c.getR(), 2 * c.getR(), 2 * c.getR());
                g2.setStroke(new BasicStroke(1));
            }
        }
    }
}
