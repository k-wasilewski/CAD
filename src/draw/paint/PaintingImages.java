package draw.paint;

import draw.*;
import draw.Canvas;
import objs.*;
import objs.Rectangle;

import java.awt.*;

public class PaintingImages {
    private Canvas canvas;

    public PaintingImages(Canvas canvas) {
        this.canvas = canvas;
    }

    public void importImage() {
        if (!canvas.getImageClasses().isEmpty()) {
            for (ImageClass imageClass : canvas.getImageClasses()) {
                if (imageClass != null) {
                    imageClass.setWidth(imageClass.getWidth());
                    imageClass.setHeight(imageClass.getHeight());

                    //.....images marked on..................
                    if (canvas.imageSelection(imageClass)) {
                        imageClass.markedOn();
                        imageClass.getContour().setImageClass(imageClass);
                        imageClass.getGraphics().setColor(Color.GRAY);
                        imageClass.paint();
                    } else {
                        imageClass.getContour().markedOff();
                        imageClass.markedOff();
                        imageClass.paint();
                    }
                } else {
                    canvas.setXimg(0);
                    canvas.setYimg(0);
                }
            }
        }
    }

    public void drawImageLayers() {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvas.getG2().setComposite(ac);
        for (ImageClass i : canvas.getImageClasses()) {
            canvas.getG2().drawImage(i.getImgCont(), i.getXimg(), i.getYimg(), null);
        }
    }

    public void drawSnapRecs() {
        for (ImageClass i : canvas.getImageClasses()) {
            Rectangle r = i.getSr1();
            int x1rec;
            int x2rec;
            int y1rec;
            int y2rec;
            int xr;
            int yr;
            int wr;
            int hr;

            if (i.getContains1()) {
                Rectangle rec = i.getSr1();
                x1rec = rec.getx1();
                x2rec = rec.getx2();
                y1rec = rec.gety1();
                y2rec = rec.gety2();
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
                canvas.getG2().drawRect(xr, yr, wr, hr);
            } else if (i.getContains2() && canvas.snapMode()) {
                Rectangle rec = i.getSr2();
                x1rec = rec.getx1();
                x2rec = rec.getx2();
                y1rec = rec.gety1();
                y2rec = rec.gety2();
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
                canvas.getG2().drawRect(xr, yr, wr, hr);
            } else if (i.getContains3() && canvas.snapMode()) {
                Rectangle rec = i.getSr3();
                x1rec = rec.getx1();
                x2rec = rec.getx2();
                y1rec = rec.gety1();
                y2rec = rec.gety2();
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
                canvas.getG2().drawRect(xr, yr, wr, hr);
            } else if (i.getContains4() && canvas.snapMode()) {
                Rectangle rec = i.getSr4();
                x1rec = rec.getx1();
                x2rec = rec.getx2();
                y1rec = rec.gety1();
                y2rec = rec.gety2();
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
                canvas.getG2().drawRect(xr, yr, wr, hr);
            }
        }
    }
}
