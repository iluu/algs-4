import org.junit.Test;

public class PointSETTest {

    @Test(expected = NullPointerException.class)
    public void throwsNPEWhenInsertingNullPoint() {
        PointSET set = new PointSET();
        set.insert(null);
    }
}