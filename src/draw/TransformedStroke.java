/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import java.awt.*;
import java.awt.geom.*;


/**
 * A implementation of {@link Stroke} which transforms another Stroke
 * with an {@link AffineTransform} before stroking with it.
 *
 * This class is immutable as long as the underlying stroke is
 * immutable.
 */
public class TransformedStroke implements Stroke {
    private AffineTransform at;
    private AffineTransform atinverted;
    private Stroke stroke;

    public TransformedStroke(Stroke base, AffineTransform at) throws NoninvertibleTransformException {
        this.at = new AffineTransform(at);
        this.atinverted = at.createInverse();
        this.stroke = base;
    }
    public Shape createStrokedShape(Shape s) {
        Shape sTrans = at.createTransformedShape(s);
        Shape sTransStroked = stroke.createStrokedShape(sTrans);
        Shape sStroked = atinverted.createTransformedShape(sTransStroked);
        return sStroked;
    }
}
/*
My paint-method using it then looks like this:

public void paintComponent(Graphics context) {
    super.paintComponent(context);
    Graphics2D g = (Graphics2D)context.create();

    int height = getHeight();
    int width = getWidth();

    g.scale(width/4.0, height/7.0);

    try {
        g.setStroke(new TransformedStroke(new BasicStroke(2f),
                                          g.getTransform()));
    }
    catch(NoninvertibleTransformException ex) {
        // should not occur if width and height > 0
        ex.printStackTrace();
    }

    g.setColor(Color.BLACK);
    g.draw(new Rectangle( 1, 2, 2, 4));
}*/