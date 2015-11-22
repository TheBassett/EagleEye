package mysteryinc.eagleeye.Tests;

import android.test.InstrumentationTestCase;

/**
 * Created by JessUpde on 11/22/15.
 */
public class Tests extends InstrumentationTestCase {
    public void testJess() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}
