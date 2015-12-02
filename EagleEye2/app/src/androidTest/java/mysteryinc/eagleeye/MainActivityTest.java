package mysteryinc.eagleeye;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import mysteryinc.eagleeye.LiveLocatorFragment;
import mysteryinc.eagleeye.MainActivity;

/**
 * Created by Alex Bassett on 11/30/2015.
 */
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

    Intent mLaunchIntent;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), MainActivity.class);
        startActivity(mLaunchIntent, null, null);
    }

    @MediumTest
    public void testCamera(){
        assertFalse(LiveLocatorFragment.get_instance().deviceMissingCamera());
    }
}
