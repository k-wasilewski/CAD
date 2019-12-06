package draw;

import java.awt.*;
import java.awt.geom.*;

//FOR DRAWING NOT STROKE-SENSITIVE GRID
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