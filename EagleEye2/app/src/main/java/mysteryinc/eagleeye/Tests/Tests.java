package mysteryinc.eagleeye.Tests;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import mysteryinc.eagleeye.DisplayAbout;
import mysteryinc.eagleeye.R;

/**
 * Created by JessUpde on 11/22/15.
 */
public class Tests extends ActivityUnitTestCase<DisplayAbout> {


    private Intent mDisplayIntent;
    private TextView textView;
    private DisplayAbout mDisplayAboutActivity;

    public Tests() {
        super(DisplayAbout.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Create an intent to launch target Activity
        mDisplayIntent = new Intent(getInstrumentation().getTargetContext(),
                DisplayAbout.class);
        mDisplayAboutActivity = getActivity();
        TextView textView = (TextView) mDisplayAboutActivity.findViewById(R.id.about_text);
    }

    public void testJess() throws Exception {
        startActivity(mDisplayIntent, null, null);
        assertNotNull("mDisplayAbout is null", getActivity());
        assertEquals("This app was designed for the Fall 2015 SE 300 class with Dr. Jafer by the following developers:\n\n" +
                "Team Lead: Jessica Updegrove\n" +
                "Android Lead: Alex Bassett\n" +
                "Android Team: Alex Bassett, Jonathon Rach, Jessica Updegrove (assistance)\n" +
                "Image Processing Team: Dean Laga, Joel Vande Polder, Jessica Updegrove (assistance)\n" +
                "\nThis app is meant to be used on the ERAU DB campus to assist visitors, alumni, and new students as they" +
                " explore the ever-changing campus. To use, take a picture of a building on campus and the app will process " +
                "the image and return the name of the building. Additional features, such as a campus map and building directory " +
                "will be added in future versions.\n\nHave fun and we hope you enjoy the app.", textView.getText());
    }
}
