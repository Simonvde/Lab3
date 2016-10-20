package Lab3;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Simon Van den Eynde
 */
public class TestModule {
    @Test
    public void testClustering(){
        Main main = new Main();
        AGraph<String> k6 = main.readD("data/K6.txt");
        AGraph<String> k4_and_c4 = main.readD("data/K4_and_C4.txt");
        AGraph<String> raster = main.readD("data/Raster.txt");
        AGraph<String> stars = main.readD("data/Stars.txt");
        AGraph<String> house = main.readD("data/House.txt");

        assertEquals(1, k6.meanLocalClustering(),0);
        assertEquals(0, raster.meanLocalClustering(),0);
        assertEquals((double)1/2,k4_and_c4.meanLocalClustering(),0.001);
        assertEquals(0, stars.meanLocalClustering(),0.001);
        assertEquals((double)1/3,house.meanLocalClustering(),0.0001);
    }
}
