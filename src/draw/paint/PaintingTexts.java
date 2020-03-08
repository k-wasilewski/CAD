package draw.paint;

import draw.Canvas;
import objs.Text;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class PaintingTexts extends JPanel {
    private Canvas canvas;

    public PaintingTexts(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawSnapRecs() {
        for (Text t : canvas.getTexts()) {
            if (t.getContains() && canvas.snapMode()) {
                canvas.getG2().draw(t.getSr());
            }
        }
    }

    public void drawTexts(Graphics g) {
        boolean readyToDrawText = canvas.isReadyToDrawText();
        Point2D p2 = canvas.getP2();
        String inputText = canvas.getInputText();
        Color dCol = canvas.getdCol();
        Graphics2D g2 = canvas.getG2();
        List<Text> texts = canvas.getTexts();

        if (inputText != null && p2 != null && readyToDrawText) {
            texts.add(new Text(inputText, (int) p2.getX(), (int) p2.getY(), dCol));
            canvas.setReadyToDrawTextOff();
        }
        for (Text t : texts) {
            g.setColor(t.getCol());
            if (!t.isSelected()) {
                g2.setColor(t.getCol());
                g2.drawString(t.getText(), t.getx(), t.gety());
            } else if (t.isSelected()) {
                Font font = super.getFont();
                g2.setColor(t.getCol());
                g2.setFont(new Font("default", Font.BOLD, font.getSize()));
                g2.drawString(t.getText(), t.getx(), t.gety());
                g2.setFont(font);
                repaint();
            }

            if (canvas.getSelecting().textSelection(t)) {
                t.setCol(Color.GRAY);
                t.markedOn();
            } else {
                t.setCol(dCol);
                t.markedOff();
            }
        }
    }
}
