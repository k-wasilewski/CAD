package draw.func;

import draw.Canvas;
import draw.CADapp;
import objs.*;
import objs.Rectangle;

import java.awt.*;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLine {
    private draw.Canvas canvas;

    public CommandLine(draw.Canvas canvas) {
        this.canvas=canvas;
    }

    public void input(String text) {
        canvas.safelyRepaint();
        canvas.setInput(text);


        if (canvas.getInput().contains("\n")) {
            canvas.setInput(canvas.getInput().replace("\n", ""));
        }
        canvas.selectionOff();
        canvas.drawingOff();
        canvas.safelyRepaint();
        canvas.repaint();
        if (command("")) {
            canvas.setInput(canvas.getInputH());
        }
        if (command("regen")) {
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("pl")) {
            canvas.polylineOn();
        } else if (command("cl")) {
            canvas.getLines().clear();
            canvas.getCircles().clear();
            canvas.getRectangles().clear();
            canvas.setInput("null");
            canvas.setImage(null);
            canvas.getImageClasses().removeAll(canvas.getImageClasses());
            canvas.setXimg(0);
            canvas.setYimg(0);
            canvas.setPrevZoomFactor(1);
            canvas.setZoomFactor(1);
            canvas.setxOffset(1);
            canvas.setyOffset(1);
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("bcol:bla")) {
            canvas.setbCol(Color.BLACK);
            canvas.setInput("null");
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("bcol:blu")) {
            canvas.setbCol(Color.BLUE);
            canvas.setInput("null");
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("bcol:w")) {
            canvas.setbCol(Color.WHITE);
            canvas.setInput("null");
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("bcol:g")) {
            canvas.setbCol(Color.GREEN);
            canvas.setInput("null");
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("bcol:y")) {
            canvas.setbCol(Color.YELLOW);
            canvas.setInput("null");
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("bcol:r")) {
            canvas.setbCol(Color.RED);
            canvas.setInput("null");
            canvas.repaint();
        } else if (command("dcol:bla")) {
            canvas.setdCol(Color.BLACK);
            canvas.setInput("null");
        } else if (command("dcol:blu")) {
            canvas.setdCol(Color.BLUE);
            canvas.setInput("null");
        } else if (command("dcol:w")) {
            canvas.setdCol(Color.WHITE);
            canvas.setInput("null");
        } else if (command("dcol:g")) {
            canvas.setdCol(Color.GREEN);
            canvas.setInput("null");
        } else if (command("dcol:y")) {
            canvas.setdCol(Color.YELLOW);
            canvas.setInput("null");
        } else if (command("dcol:r")) {
            canvas.setdCol(Color.RED);
            canvas.setInput("null");
        } else if (command("ortoX")) {
            if (canvas.isOrtoX()) canvas.setOrtoX(false);
            else if (!canvas.isOrtoX()) canvas.setOrtoX(true);
            canvas.setInput("null");
        } else if (command("ortoY")) {
            if (canvas.isOrtoY()) canvas.setOrtoY(false);
            else if (!canvas.isOrtoY()) canvas.setOrtoY(true);
            canvas.setInput("null");
        } else if (command("co")) {
            canvas.setReadyToCopy(true);
            canvas.setInput("null");
        } else if (command("m")) {
            canvas.setReadyToMove(true);
            canvas.setInput("null");
        } else if (command("t")) {
            Canvas.setReadyToInputText(true);
            canvas.setInput("null");
        } else if (command("esc")) {
            ui.TextInput ti=canvas.getTi();
            if (ti != null && ti.isVisible()) ti.setVisible(false);
            canvas.setInput("null");
            for (Line l : canvas.getLines()) {
                if (l.isSelected()) l.selectedOff();
            }
            Iterator<Line> linesIt = canvas.getLines().iterator();
            while (linesIt.hasNext()) {
                Line l = linesIt.next();
                if (l.isPolyline()) linesIt.remove();
            }
            for (Text t : canvas.getTexts()) if (t.isSelected()) t.selectedOff();
            for (Rectangle r : canvas.getRectangles()) if (r.isSelected()) r.selectedOff();
            for (Circle c : canvas.getCircles()) if (c.isSelected()) c.selectedOff();
            for (ImageClass i : canvas.getImageClasses()) if (i.isSelected()) i.selectedOff();
            canvas.esc();
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("null")) {
            canvas.setInput("null");
            canvas.setReadyToCopy(false);
            canvas.setReadyToMove(false);
            canvas.safelyRepaint();
            canvas.repaint();
        } else if (command("l") || command("c") || command("dist") || command("rec") || command("pl")) {
            if (command("pl")) {
                canvas.polylineOff();
                canvas.setPlindex(canvas.getPlindex()+1);
            }
        } else {
            CADapp.unknownOn();
            CADapp.setText("unknown command");
        }
        canvas.setInputH(canvas.getInput());
        if (!canvas.isDrawing()) canvas.safelyRepaint();
    }

    public boolean command(String s) {
        boolean active = false;
        Pattern p1 = Pattern.compile("\n" + s);
        Pattern p2 = Pattern.compile(s);
        Matcher m1 = p1.matcher(canvas.getInput());
        Matcher m2 = p2.matcher(canvas.getInput());
        if (m1.matches() || m2.matches()) active = true;
        return active;
    }
}
