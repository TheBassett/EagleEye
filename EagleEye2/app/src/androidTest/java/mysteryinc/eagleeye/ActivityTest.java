package mysteryinc.eagleeye;

import android.app.Application;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

/**
 * Created by Alex Bassett on 11/30/2015.
 */
public class ActivityTest extends ActivityUnitTestCase<MainActivity> {

    Intent mLaunchIntent;

    public ActivityTest() {
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
    public void cameraTest(){
        assertFalse(LiveLocatorFragment.get_instance().deviceMissingCamera());
    }
}
