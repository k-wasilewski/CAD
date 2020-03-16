package draw;

import org.junit.Assert;
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
    public void runApp() {
        cadapp = new CADapp();
        cadapp.run(cadapp);
        canvas = cadapp.getCanvas();
    }

    @Test
    public void paintComponentTest() {
        assertEquals(canvas.getBackground(), canvas.getbCol());
        assertEquals("null", canvas.getInput());
    }

    @Test
    public void exportAsImageTest() throws IOException, AWTException {
        canvas.setDir("/home");
        canvas.setFilename("test");
        canvas.exportAsImage();

        File test = new File("/home/test.jpeg");
        assertTrue(test.exists());
    }

    @Test
    public void importFileTest() throws IOException {
        canvas.setDir("/home");
        canvas.setFilename("test.jpeg");
        canvas.importFile();

        assertEquals(1, canvas.getImageClasses().size());
    }

    @Test
    public void drawTextTest() {
        Canvas.drawText("test");

        assertEquals("test", canvas.getInputText());
        assertFalse(Canvas.getReadyToInputText());
        assertTrue(Canvas.getReadyToDrawText());
    }

    @Test
    public void setGridTest() {
        Canvas.setGrid(100);
        assertEquals(100, Canvas.getGridSize());
    }
}
