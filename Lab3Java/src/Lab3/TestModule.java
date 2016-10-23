package Lab3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Simon Van den Eynde
 */
public class TestModule {
    @Test
    public void testClustering() {
        Main main = new Main();
        Graph<String> k6 = main.readGraph("data/K6.txt");
        Graph<String> k4_and_c4 = main.readGraph("data/K4_and_C4.txt");
        Graph<String> raster = main.readGraph("data/Raster.txt");
        Graph<String> stars = main.readGraph("data/Stars.txt");
        Graph<String> house = main.readGraph("data/House.txt");

        assertEquals(1, k6.meanLocalClustering(), 0);
        assertEquals(0, raster.meanLocalClustering(), 0);
        assertEquals((double) 1 / 2, k4_and_c4.meanLocalClustering(), 0.001);
        assertEquals(0, stars.meanLocalClustering(), 0.001);
        assertEquals((double) 1 / 3, house.meanLocalClustering(), 0.0001);
    }
}
