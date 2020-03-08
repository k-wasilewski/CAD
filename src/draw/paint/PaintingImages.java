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

                    if (canvas.getSelecting().imageSelection(imageClass)) {
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

    public void drawSnapRec1(ImageClass i) {
        Rectangle rec = i.getSr1();
        int x1rec = rec.getx1();
        int x2rec = rec.getx2();
        int y1rec = rec.gety1();
        int y2rec = rec.gety2();
        int xr;
        int yr;
        int wr;
        int hr;

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

    public void drawSnapRec2(ImageClass i) {
        Rectangle rec = i.getSr2();
        int x1rec = rec.getx1();
        int x2rec = rec.getx2();
        int y1rec = rec.gety1();
        int y2rec = rec.gety2();
        int xr;
        int yr;
        int wr;
        int hr;

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

    public void drawSnapRec3(ImageClass i) {
        Rectangle rec = i.getSr3();
        int x1rec = rec.getx1();
        int x2rec = rec.getx2();
        int y1rec = rec.gety1();
        int y2rec = rec.gety2();
        int xr;
        int yr;
        int wr;
        int hr;

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

    public void drawSnapRec4(ImageClass i) {
        Rectangle rec = i.getSr4();
        int x1rec = rec.getx1();
        int x2rec = rec.getx2();
        int y1rec = rec.gety1();
        int y2rec = rec.gety2();
        int xr;
        int yr;
        int wr;
        int hr;

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

    public void drawSnapRecs() {
        for (ImageClass i : canvas.getImageClasses()) {
            Rectangle r = i.getSr1();

            if (i.getContains1()) {
                drawSnapRec1(i);
            } else if (i.getContains2() && canvas.snapMode()) {
                drawSnapRec2(i);
            } else if (i.getContains3() && canvas.snapMode()) {
                drawSnapRec3(i);
            } else if (i.getContains4() && canvas.snapMode()) {
                drawSnapRec4(i);
            }
        }
    }
}
