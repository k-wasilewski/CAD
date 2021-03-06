package draw.func;

import draw.Canvas;
import objs.*;
import objs.Rectangle;
import ui.ImgPopup;
import ui.Popup;
import ui.TextInput;

import java.awt.geom.AffineTransform;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class MouseEvents {
    private Canvas canvas;
    private CommandLine commandLine;

    public MouseEvents(Canvas canvas) {
        this.canvas=canvas;
        commandLine=canvas.getCommandLine();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            canvas.setZoomFactor(canvas.getZoomFactor()*1.1);
            canvas.repaint();
        }

        if (e.getWheelRotation() > 0) {
            canvas.setZoomFactor(canvas.getZoomFactor()/1.1);
            canvas.repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
        AffineTransform at = canvas.getAt();

        if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
            leftMouseButton();
        }
        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            scrollMouseButton(at);
        }
        if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
            rightMouseButton(e);
        }
        canvas.repaint();
    }

    public void leftMouseButton() {
        moving();
        drawing();
        showTextInput();
    }

    public void handleImgPopup(ImgPopup menu, MouseEvent e) {
        for (ImageClass i : canvas.getImageClasses()) {
            if (canvas.getSelecting().isOverImage(i)) {
                canvas.setNoOfOvers(canvas.getNoOfOvers()+1);
                i.overImageOn();
                menu.setImageClass(i);
                menu.show(e.getComponent(), e.getX(), e.getY());
            } else i.overImageOff();
        }
    }

    public void handlePopup(Popup menu1, MouseEvent e) {
        for (ImageClass i : canvas.getImageClasses()) {
            if (canvas.getNoOfOvers() == 0) menu1.show(e.getComponent(), e.getX(), e.getY());
        }
        if (canvas.getImageClasses().isEmpty()) menu1.show(e.getComponent(), e.getX(), e.getY());
        canvas.setNoOfOvers(0);
    }

    public void rightMouseButton(MouseEvent e) {
        ImgPopup menu = new ImgPopup(canvas);
        ui.Popup menu1 = new Popup();

        handleImgPopup(menu, e);
        handlePopup(menu1, e);
    }

    public void scrollMouseButton(AffineTransform at) {
        if (canvas.getLocationX() != 0 && canvas.getLocationY() != 0)
            canvas.setPoint(new Point2D.Double(canvas.getLocationX(), canvas.getLocationY()));
        Point2D p1s = new Point2D.Double();
        try {
            at.invert();
        } catch (Exception ignored) {
        }
        at.transform(canvas.getPoint(), p1s);
        canvas.setX1s((int) p1s.getX());
        canvas.setY1s((int) p1s.getY());
        canvas.setMovingC(true);
        canvas.repaint();
    }

    public void showTextInput() {
        if (Canvas.isReadyToInputText()) {
            canvas.setPa(new Point2D.Double(canvas.getLocationX() - canvas.getScreenx(),
                    canvas.getLocationY() - canvas.getScreeny()));
            canvas.setP2(new Point2D.Double());
            try {
                canvas.getAt().invert();
            } catch (NoninvertibleTransformException ignored) {}
            canvas.getAt().transform(canvas.getPa(), canvas.getP2());
            if (canvas.getTi() != null) canvas.getTi().dispose();
            canvas.setTi(new TextInput());
            canvas.getTi().setLocation((int) canvas.getP2().getX(), (int) canvas.getP2().getY());
            canvas.getTi().dispose();
            canvas.getTi().setUndecorated(true);
            canvas.getTi().setVisible(true);
        }
    }

    public void moving() {
        for (Line l : canvas.getLines()) {
            if (l.isSelected() && (canvas.isReadyToMove())) canvas.setMoving(true);
        }
        for (Text t : canvas.getTexts()) {
            if (t.isSelected() && (canvas.isReadyToMove())) canvas.setMoving(true);
        }
        for (ImageClass i : canvas.getImageClasses()) {
            if (i.isSelected() && (canvas.isReadyToMove())) canvas.setMoving(true);
        }
        for (Rectangle r : canvas.getRectangles()) {
            if (r.isSelected() && (canvas.isReadyToMove())) canvas.setMoving(true);
        }
        for (Circle c : canvas.getCircles()) {
            if (c.isSelected() && (canvas.isReadyToMove())) canvas.setMoving(true);
        }
        for (Line l : canvas.getLines()) {
            if (l.isSelected() && (canvas.isReadyToCopy())) canvas.setCopying(true);
        }
        for (Text t : canvas.getTexts()) {
            if (t.isSelected() && (canvas.isReadyToCopy())) canvas.setCopying(true);
        }
        for (ImageClass i : canvas.getImageClasses()) {
            if (i.isSelected() && (canvas.isReadyToCopy())) canvas.setCopying(true);
        }
        for (Rectangle r : canvas.getRectangles()) {
            if (r.isSelected() && (canvas.isReadyToCopy())) canvas.setCopying(true);
        }
        for (Circle c : canvas.getCircles()) {
            if (c.isSelected() && (canvas.isReadyToCopy())) canvas.setCopying(true);
        }
    }

    public void drawFirstPoint() {
        if (!canvas.isSelection() || (canvas.getX1() != 0 && canvas.getY1() != 0 && (commandLine.command("l") || commandLine.command("pl"))) || (canvas.getXo() != 0 && canvas.getYo() != 0 && commandLine.command("c")) ||
                (canvas.getXd() != 0 && canvas.getYd() != 0 && commandLine.command("dist")) || (canvas.getX1r() != 0 && canvas.getY1r() != 0 && commandLine.command("rec") || canvas.isMoving() || canvas.isCopying())) {
            canvas.drawingOn();
        }

        if (canvas.getLocationX() != 0 && canvas.getLocationY() != 0) {
            canvas.setPa(new Point2D.Double(canvas.getLocationX() - 8 - canvas.getScreenx(),
                    canvas.getLocationY() - 54 - canvas.getScreeny()));
            canvas.setP2(new Point2D.Double());
            try {
                canvas.getAt().invert();
            } catch (NoninvertibleTransformException ignored) {}
            canvas.getAt().transform(canvas.getPa(), canvas.getP2());
            canvas.getSettingCoordinates().setFirstPointCoords((int) canvas.getP2().getX(), (int) canvas.getP2().getY());    //method point A
        }
    }

    public void drawSelectionRec() {
        if (canvas.getLocationX() != 0 && canvas.getLocationY() != 0) {
            canvas.setPoint(new Point2D.Double(canvas.getLocationX() - 8
                    - canvas.getScreenx(), canvas.getLocationY() - 54 - canvas.getScreeny()));
        }
        canvas.setP1sel(new Point2D.Double());
        try {
            canvas.getAt().invert();
        } catch (Exception ignored) {
        }
        canvas.getAt().transform(canvas.getPoint(), canvas.getP1sel());
        canvas.selectionOn();
        canvas.repaint();
        canvas.safelyRepaint();
    }

    public void drawSecondPoint() {
        if (!commandLine.command("pl")) canvas.drawingOff();
        if (canvas.getLocationX() != 0 && canvas.getLocationY() != 0)
            canvas.setPb(new Point2D.Double(canvas.getLocationX(), canvas.getLocationY()));
        canvas.setP3(new Point2D.Double());
        try {
            canvas.getAt().invert();
        } catch (Exception ignored) {}
        canvas.getAt().transform(canvas.getPb(), canvas.getP3());
        canvas.getSettingCoordinates().setSecondPointCoords((int) canvas.getPb().getX(),
                (int) canvas.getPb().getY());
    }

    public void objSelection() {
        canvas.selectionOff();
        for (ImageClass i : canvas.getImageClasses()) {
            if (i.isMarked()) i.selectedOn();
            i.paint();
        }
        for (Text t : canvas.getTexts()) {
            if (t.isMarked()) t.selectedOn();
        }
        for (Line l : canvas.getLines()) {
            if (l.isMarked()) l.selectedOn();
        }
        for (Rectangle r : canvas.getRectangles()) {
            if (r.isMarked()) r.selectedOn();
            if (!canvas.getImageClasses().isEmpty()) for (ImageClass imageClass : canvas.getImageClasses())
                if (r.getImageClass() == imageClass && canvas.getSelecting().imageSelection(imageClass)) r.selectedOn();
        }
        for (Circle c : canvas.getCircles()) {
            if (c.isMarked()) c.selectedOn();
        }
        canvas.repaint();
        canvas.setXs(0);
        canvas.setYs(0);
        canvas.setWs(0);
        canvas.setHs(0);
    }

    public void drawing() {
        if ((commandLine.command("l") || commandLine.command("pl") ||
                commandLine.command("c") || commandLine.command("dist") ||
                commandLine.command("rec") || canvas.isMoving() || canvas.isCopying())
                && !canvas.isDrawing()) {
            drawFirstPoint();
        } else if (commandLine.command("null") && !canvas.isSelection() && !canvas.isMoving()
                && !canvas.isCopying() && !Canvas.isReadyToInputText() && !canvas.isDrawing()) {
            drawSelectionRec();
            return;
        } else if (canvas.isDrawing()) {
            drawSecondPoint();
        }
        if (commandLine.command("null") && canvas.isSelection()) {
            objSelection();
        }
    }
}
