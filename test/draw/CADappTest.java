package draw;

import org.junit.Test;
import static org.junit.Assert.*;

public class CADappTest {
    CADapp cadapp;

    @Test
    public void mainTest() {
        this.cadapp = new CADapp();
        this.cadapp.run(this.cadapp);
        assertTrue(this.cadapp.isVisible());
    }

    @Test
    public void CADappTest() {
        this.cadapp = new CADapp();
        assertTrue(this.cadapp.getCanvas()!=null);
    }
}
