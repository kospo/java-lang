import me.kospo.javalang.generics.CountMap;
import me.kospo.javalang.generics.CountMapImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountMapTest {
    @Test
    public void testMap() {
        CountMap<Integer> map = new CountMapImpl<>();

        map.add(10);
        map.add(10);
        map.add(5);
        map.add(6);
        map.add(5);
        map.add(10);

        assertEquals(map.getCount(5), 2);
        assertEquals(map.getCount(6), 1);
        assertEquals(map.getCount(10), 3);
    }
}
