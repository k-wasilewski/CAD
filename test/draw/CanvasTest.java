package draw;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CanvasTest {
    CADapp cadapp;
    Canvas canvas;

    @Before
    public void constructCanvas() {
        this.cadapp = new CADapp();
        this.cadapp.run(this.cadapp);
        this.canvas = cadapp.getCanvas();
    }

    @Test
    public void exportAsImageTest() throws IOException, AWTException {
        this.canvas.setDir("/home");
        this.canvas.setFilename("test");
        this.canvas.exportAsImage();

        File test = new File("/home/test.jpeg");
        assertTrue(test.exists());
    }
}
