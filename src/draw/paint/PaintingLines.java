package draw.paint;

import draw.Canvas;
import objs.Line;
import java.util.*;
import java.awt.*;
import java.awt.Color;
import java.util.List;

public class PaintingLines {
    private Canvas canvas;

    public PaintingLines(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawSnapRecs() {
        for (Line l : canvas.getLines()) {
            if (l.getContains1() && canvas.snapMode()) canvas.getG2().draw(l.getSr1());
            else if (l.getContains2() && canvas.snapMode()) canvas.getG2().draw(l.getSr2());
        }
    }

    public void drawLines(Graphics g) { 
        Graphics2D g2=canvas.getG2();
        Color dCol = canvas.getdCol();
        int x1=canvas.getX1();
        int y1 = canvas.getY1();
        int x2=canvas.getX2();
        int y2=canvas.getY2();
        List<Line> lines = canvas.getLines();
        //............drawing a line dynamically......
        g.setColor(dCol);
        if (x1 != 0 && y1 != 0) g.drawLine(x1, y1, x2, y2);
        for (Line l : lines) {
            //........line marked on.................
            if (l.getx1() != 0 && l.getx2() != 0 && canvas.lineSelection(l)) {
                l.setCol(Color.GRAY);
                l.markedOn();
            } else {
                l.markedOff();
                if (!l.getColoured()) {
                    l.setCol(dCol);
                    l.setColoured();
                }
                if (l.getCol() == Color.GRAY) l.setCol(l.getColH());
            }

            //..........drawing a line statically........
            g.setColor(l.getCol());
            if (l.getx1() != 0 && l.gety1() != 0 && l.getx2() != 0 && l.gety2() != 0 && !l.isSelected())
                g.drawLine(l.getx1(), l.gety1(), l.getx2(), l.gety2());
            else if (l.getx1() != 0 && l.gety1() != 0 && l.getx2() != 0 && l.gety2() != 0 && l.isSelected()) {
                //.......line bold.......................
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(l.getx1(), l.gety1(), l.getx2(), l.gety2());
                g2.setStroke(new BasicStroke(1));
            }
        }

    }
}
