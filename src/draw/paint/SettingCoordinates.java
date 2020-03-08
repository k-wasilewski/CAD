package draw.paint;

import draw.Canvas;
import draw.func.CommandLine;
import draw.Draw;
import objs.*;

public class SettingCoordinates {
    private Canvas canvas;

    public SettingCoordinates(Canvas canvas) {
        this.canvas=canvas;
    }

    public void setFirstPointCoords(int x, int y) {
        CommandLine commandLine = canvas.getCommandLine();
        if (x != 0 && y != 0) {
            if (commandLine.command("l") || commandLine.command("pl")) {
                canvas.setX1(x);
                canvas.setY1(y);
            } else if (commandLine.command("c")) {
                canvas.setXo(x);
                canvas.setYo(y);
            } else if (commandLine.command("dist")) {
                canvas.setXd(x);
                canvas.setYd(y);
            } else if (commandLine.command("rec")) {
                canvas.setX1r(x);
                canvas.setY1r(y);
            } else if (canvas.isMoving()) {
                canvas.setX1m(x);
                canvas.setY1m(y);
            } else if (canvas.isCopying()) {
                canvas.setX1c(x);
                canvas.setY1c(y);
            }
            canvas.repaint();
        }
    }

    public void setSecondPointCoords(int x, int y) {
        CommandLine commandLine = canvas.getCommandLine();
        //............adding drawn objects to lists......
        if (canvas.getX1() != 0 && canvas.getY1() != 0 && !commandLine.command("pl")) {
            canvas.getLines().add(new Line(canvas.getX1(), canvas.getX2(), canvas.getY1(),
                    canvas.getY2(), canvas.getdCol(), false));
        }
        else if (canvas.getX1() != 0 && canvas.getY1() != 0 && commandLine.command("pl")) {
            canvas.getLines().add(new Line(canvas.getX1(), canvas.getX2(), canvas.getY1(),
                    canvas.getY2(), canvas.getdCol(), true, canvas.getPlindex()));
        }
        if (canvas.getX1r() != 0 && canvas.getY1r() != 0) {
            canvas.getRectangles().add(new Rectangle(canvas.getX1r(), canvas.getX2r(),
                    canvas.getY1r(), canvas.getY2r(), canvas.getdCol()));
        }
        if (canvas.getXo() != 0 && canvas.getYo() != 0) {
            canvas.getCircles().add(new Circle(canvas.getXo(), canvas.getYo(),
                    canvas.getR(), canvas.getdCol()));
        }

        if (x != 0 && y != 0) {
            if (commandLine.command("l") || commandLine.command("pl")) {
                if (canvas.isOrtoY()) canvas.setX2(canvas.getX1());
                else canvas.setX2(x);
                if (canvas.isOrtoX()) canvas.setY2(canvas.getY1());
                else canvas.setY2(y);
            } else if (commandLine.command("c")) {
                canvas.setR((int) Math.sqrt(Math.pow((x - canvas.getXo()), 2)
                        + Math.pow((canvas.getYo() - y), 2)));
            } else if (commandLine.command("dist")) {
                canvas.setD((int) Math.sqrt(Math.pow((x - canvas.getXd()), 2) +
                        Math.pow((canvas.getYd() - y), 2)));
                Draw.setText("dist: " + canvas.getD() + "p");
            } else if (commandLine.command("rec")) {
                canvas.setX2r(x);
                canvas.setY2r(y);
            } else if (canvas.isMoving() || canvas.isCopying()) {
                canvas.setMoving(false);
                canvas.setCopying(false);
                for (ImageClass i : canvas.getImageClasses()) if (i.isSelected()) i.selectedOff();
                for (Text t : canvas.getTexts()) if (t.isSelected()) t.selectedOff();
                for (Line l : canvas.getLines())
                    if (l.isSelected()) {
                        l.selectedOff();
                        l.copiedOff();
                    }
                for (Rectangle r : canvas.getRectangles()) if (r.isSelected()) r.selectedOff();
                for (Circle c : canvas.getCircles()) if (c.isSelected()) c.selectedOff();
            }
            canvas.repaint();
            if (!commandLine.command("pl")) {
                canvas.safelyRepaint();
            }
            else setFirstPointCoords(canvas.getXdyn(), canvas.getYdyn());    //polyline
            canvas.selectionOff();
            if (!commandLine.command("pl")) canvas.setInput("null");
        }
        canvas.setReadyToCopy(false);
        canvas.setReadyToMove(false);
    }
}
