package draw.func;

import draw.Canvas;
import objs.*;
import objs.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Moving {
    private draw.Canvas canvas;

    public Moving(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawingWhileMoving() {
        CommandLine commandLine = canvas.getCommandLine();
        int dx = canvas.getDx();
        int dy = canvas.getDy();

        if (commandLine.command("l") || (commandLine.command("pl"))) {
            canvas.setX1(canvas.getX1() + dx);
            canvas.setY1(canvas.getY1() - dy);
        } else if (commandLine.command("c")) {
            canvas.setXo(canvas.getXo() + dx);
            canvas.setYo(canvas.getYo() - dy);
        } else if (commandLine.command("dist")) {
            canvas.setXd(canvas.getXd() + dx);
            canvas.setYd(canvas.getYd() - dy);
        } else if (commandLine.command("rec")) {
            canvas.setX1r(canvas.getX1r() + dx);
            canvas.setY1r(canvas.getY1r() - dy);
        } else if (canvas.isMoving()) {
            canvas.setX1m(canvas.getX1m() + dx);
            canvas.setY1m(canvas.getY1m() - dy);
        } else if (canvas.isCopying()) {
            canvas.setX1c(canvas.getX1c() + dx);
            canvas.setY1c(canvas.getY1c() - dy);
        }
    }

    public void staticMoving() {
        Point2D pbs = new Point2D.Double();
        int x2s = (int) pbs.getX();
        int y2s = (int) pbs.getY();
        boolean dif = (x2s != canvas.getX2sh() || y2s != canvas.getY2sh());
        int dx = canvas.getDx();
        int dy = canvas.getDy();

        if (dif) {
            if (!canvas.getImageClasses().isEmpty()) {
                for (ImageClass imageClass : canvas.getImageClasses()) {
                    imageClass.setXimg(imageClass.getXimg() + dx);
                    imageClass.setYimg(imageClass.getYimg() - dy);
                }
            }
            for (Text t : canvas.getTexts()) {
                t.setx(t.getx() + dx);
                t.sety(t.gety() - dy);
            }
            for (Line l : canvas.getLines()) {
                l.setx1(l.getx1() + dx);
                l.setx2(l.getx2() + dx);
                l.sety1(l.gety1() - dy);
                l.sety2(l.gety2() - dy);
            }
            for (Circle c : canvas.getCircles()) {
                c.setX(c.getX() + dx);
                c.setY(c.getY() - dy);
            }
            for (Rectangle r : canvas.getRectangles()) {
                r.setx1(r.getx1() + dx);
                r.setx2(r.getx2() + dx);
                r.sety1(r.gety1() - dy);
                r.sety2(r.gety2() - dy);
            }
            canvas.repaint();
            canvas.setX2sh(x2s);
            canvas.setY2sh(y2s);
            canvas.setX1s(x2s);
            canvas.setY1s(y2s);
        }
    }

    public void moveMousewheel() {
        Point2D pas = new Point2D.Double(canvas.getLocationX(), canvas.getLocationY());
        Point2D pbs = new Point2D.Double();
        int x2s = (int) pbs.getX();
        int y2s = (int) pbs.getY();

        canvas.setDx(x2s - canvas.getX1s());
        canvas.setDy(canvas.getY1s() - y2s);
        canvas.getAtinverted().transform(pas, pbs);
        drawingWhileMoving();
        staticMoving();
    }

    public void moveLines(int dxm, int dym) {
        for (Line l : canvas.getLines()) {
            if (l.isSelected()) {
                l.setx1(l.getx1() + dxm);
                l.setx2(l.getx2() + dxm);
                l.sety1(l.gety1() - dym);
                l.sety2(l.gety2() - dym);
            }
        }
    }

    public void moveTexts(int dxm, int dym) {
        for (Text t : canvas.getTexts()) {
            if (t.isSelected()) {
                t.setx(t.getx() + dxm);
                t.sety(t.gety() - dym);
            }
        }
    }

    public void moveCircles(int dxm, int dym) {
        for (Circle c : canvas.getCircles()) {
            if (c.isSelected()) {
                c.setX(c.getX() + dxm);
                c.setY(c.getY() - dym);
            }
        }
    }

    public void moveImages(int dxm, int dym) {
        if (!canvas.getImageClasses().isEmpty()) {
            for (ImageClass i : canvas.getImageClasses()) {
                if (i.isSelected()) {
                    i.setXimg(i.getXimg() + dxm);
                    i.setYimg(i.getYimg() - dym);
                }
            }
        }
    }

    public void moveRectangles(int dxm, int dym) {
        for (Rectangle r : canvas.getRectangles()) {
            if (r.isSelected()) {
                r.setx1(r.getx1() + dxm);
                r.setx2(r.getx2() + dxm);
                r.sety1(r.gety1() - dym);
                r.sety2(r.gety2() - dym);
            }
        }
    }

    public void move() {
        System.out.println("in");
        Point2D pam = new Point2D.Double(canvas.getLocationX() - 8 -
                canvas.getScreenx(),canvas.getLocationY() - 54 - canvas.getScreeny());
        Point2D pbm = new Point2D.Double();
        canvas.getAtinverted().transform(pam, pbm);
        int x2m = (int) pbm.getX();
        int y2m = (int) pbm.getY();
        boolean dif = x2m != canvas.getX2mh() || y2m != canvas.getY2mh();

        int dxm = x2m - canvas.getX1m();
        int dym = canvas.getY1m() - y2m;

        if (dif) {
            moveLines(dxm, dym);
            moveTexts(dxm, dym);
            moveCircles(dxm, dym);
            moveImages(dxm, dym);
            moveRectangles(dxm, dym);

            canvas.repaint();
            canvas.setX2mh(x2m);
            canvas.setY2mh(y2m);
            canvas.setX1m(x2m);
            canvas.setY1m(y2m);
        }
    }

    public void copyLines(int dxc, int dyc) {
        ArrayList<Line> linesToCopy = new ArrayList();

        for (Line l : canvas.getLines()) {
            if (l.isSelected()) {
                Line l2 = new Line(l.getx1(), l.getx2(), l.gety1(), l.gety2(), l.getCol(), l.isPoly());
                if (!l.isCopied()) {
                    linesToCopy.add(l2);
                    l.copiedOn();
                }
            }
        }
        canvas.getLines().addAll(linesToCopy);
        for (Line l : canvas.getLines()) {
            if (l.isSelected()) {
                l.setx1(l.getx1() + dxc);
                l.setx2(l.getx2() + dxc);
                l.sety1(l.gety1() - dyc);
                l.sety2(l.gety2() - dyc);
            }
        }
    }

    public void copyCircles(int dxc, int dyc) {
        ArrayList<Circle> circlesToCopy = new ArrayList();

        for (Circle c : canvas.getCircles()) {
            if (c.isSelected()) {
                Circle c2 = new Circle(c.getX(), c.getY(), c.getR(), c.getCol());
                if (!c.isCopied()) {
                    circlesToCopy.add(c2);
                    c.copiedOn();
                }
                c2.setX(c2.getX() + dxc);
                c2.setY(c2.getY() - dyc);
            }
        }
        canvas.getCircles().addAll(circlesToCopy);
        for (Circle c : canvas.getCircles()) {
            if (c.isSelected()) {
                c.setX(c.getX() + dxc);
                c.setY(c.getY() - dyc);
            }
        }
    }

    public void copyTexts(int dxc, int dyc) {
        ArrayList<Text> textsToCopy = new ArrayList();

        for (Text t : canvas.getTexts()) {
            if (t.isSelected()) {
                Text t2 = new Text(t.getText(), t.getx(), t.gety(), t.getCol());
                if (!t.isCopied()) {
                    textsToCopy.add(t2);
                    t.copiedOn();
                }
            }
        }
        canvas.getTexts().addAll(textsToCopy);
        for (Text t : canvas.getTexts()) {
            if (t.isSelected()) {
                t.setx(t.getx() + dxc);
                t.sety(t.gety() - dyc);
            }
        }
    }

    public void copyImages(int dxc, int dyc) {
        ArrayList<ImageClass> imagesToCopy = new ArrayList();

        if (!canvas.getImageClasses().isEmpty()) {
            for (ImageClass i : canvas.getImageClasses()) {
                if (i.isSelected()) {
                    ImageClass i2 = new ImageClass(i.getImage(), i.getImg(), i.getXimg(), i.getYimg(), i.getContour(), i.getCol());
                    if (!i.isCopied()) {
                        imagesToCopy.add(i2);
                        i.copiedOn();
                    }
                    i2.setXimg(i2.getXimg() + dxc);
                    i2.setYimg(i2.getYimg() - dyc);
                }
            }
        }
        canvas.getImageClasses().addAll(imagesToCopy);
        for (ImageClass i : canvas.getImageClasses()) {
            if (i.isSelected()) {
                i.setXimg(i.getXimg() + dxc);
                i.setYimg(i.getYimg() - dyc);
            }
        }
    }

    public void copyRectangles(int dxc, int dyc) {
        ArrayList<Rectangle> rectanglesToCopy = new ArrayList();

        for (Rectangle r : canvas.getRectangles()) {
            if (r.isSelected()) {
                Rectangle r2 = new Rectangle(r.getx1(), r.getx2(), r.gety1(), r.gety2(), r.getCol());
                if (!r.isCopied()) {
                    rectanglesToCopy.add(r2);
                    r.copiedOn();
                }
                r2.setx1(r2.getx1() + dxc);
                r2.setx2(r2.getx2() + dxc);
                r2.sety1(r2.gety1() - dyc);
                r2.sety2(r2.gety2() - dyc);
            }
        }
        canvas.getRectangles().addAll(rectanglesToCopy);
        for (Rectangle r : canvas.getRectangles()) {
            if (r.isSelected()) {
                r.setx1(r.getx1() + dxc);
                r.setx2(r.getx2() + dxc);
                r.sety1(r.gety1() - dyc);
                r.sety2(r.gety2() - dyc);
            }
        }
    }

    public void copy() {
        Point2D pac = new Point2D.Double(canvas.getLocationX() - 8 - canvas.getScreenx(),
                canvas.getLocationY() - 54 - canvas.getScreeny());
        Point2D pbc = new Point2D.Double();
        canvas.getAtinverted().transform(pac, pbc);
        int x2c = (int) pbc.getX();
        int y2c = (int) pbc.getY();
        boolean dif = x2c != canvas.getX2ch() || y2c != canvas.getY2ch();

        int dxc = x2c - canvas.getX1c();
        int dyc = canvas.getY1c() - y2c;

        if (dif) {
            copyLines(dxc, dyc);
            copyCircles(dxc, dyc);
            copyTexts(dxc, dyc);
            copyImages(dxc, dyc);
            copyRectangles(dxc, dyc);

            canvas.repaint();
            canvas.setX2ch(x2c);
            canvas.setY2ch(y2c);
            canvas.setX1c(x2c);
            canvas.setY1c(y2c);
        }
    }
}
