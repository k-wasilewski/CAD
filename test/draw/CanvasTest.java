package draw;

import draw.func.Zooming;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.spy;

//@RunWith(PowerMockRunner.class)
@PrepareForTest({ Canvas.class , Zooming.class})
public class CanvasTest {
    @Before
    public void init() {
        /*
        CADapp cadapp = new CADapp();
        cadapp.run(cadapp);
        Zooming zoomMock = spy(new Zooming(cadapp.getCanvas()));//Mockito.mock(Zooming.class);
        try {
            PowerMockito.whenNew(Zooming.class)
                    .withNoArguments()
                    .thenReturn(zoomMock);
        } catch (Exception ignored) {}
     */
    }

    @Test
    public void actionPerformedTest() throws Exception {
        /*
        // prepare
        CADapp cadapp = new CADapp();

        // execute
        cadapp.run(cadapp);

        // checks if the constructor has been called once and with the expected argument values:
        PowerMockito.verifyNew(Zooming.class).withNoArguments();
         */
    }
}
