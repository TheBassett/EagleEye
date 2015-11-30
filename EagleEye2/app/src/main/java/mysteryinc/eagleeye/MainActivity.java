package mysteryinc.eagleeye;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
///////
    public static MainActivity _instance;

//    private static Toast m_currentToast;

    public static MainActivity getInstance() {
        if (_instance == null) {
            _instance = new MainActivity();
            Log.e("Startup Error", "Bad instance created");
        }
        return _instance;
    }

    public MainActivity(){
        _instance = this;
    }

    public static void toast(String msg) {
//        if (m_currentToast != null) {
//            m_currentToast.cancel();
//        }
//        m_currentToast = Toast.makeText(getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
//        m_currentToast.show();
        Toast.makeText(getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

//    public static Context getContext(){
//        return getInstance().getContext();
//    }
///////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, LiveLocatorFragment.get_instance())
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CampusDirectoryFragment.get_instance())
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CampusMapFragment.get_instance())
                        .commit();
                break;
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






/*  Code for the comparing the images of the realtime system and the database
IplImage img=cvLoadImage(" // insert image location ");
    CvSize cvSize = cvSize(img.width(), img.height());
    IplImage gry=cvCreateImage(cvSize, img.depth(), 1);
    cvCvtColor(img, gry, CV_BGR2GRAY);
    cvThreshold(gry, gry, 200, 255, CV_THRESH_BINARY);
    cvAdaptiveThreshold(gry, gry, 255, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY_INV, 11, 5);

    CvMemStorage storage = CvMemStorage.create();
    CvSeq contours = new CvContour(null);

    int noOfContors = cvFindContours(gry, storage, contours, Loader.sizeof(CvContour.class), CV_RETR_CCOMP, CV_CHAIN_APPROX_NONE, new CvPoint(0,0));

    CvSeq ptr = new CvSeq();

    int count =1;
    CvPoint p1 = new CvPoint(0,0),p2 = new CvPoint(0,0);

    for (ptr = contours; ptr != null; ptr = ptr.h_next()) {

        CvScalar color = CvScalar.BLUE;
        CvRect sq = cvBoundingRect(ptr, 0);

        p1.x(sq.x());
        p2.x(sq.x()+sq.width());
        p1.y(sq.y());
        p2.y(sq.y()+sq.height());

        cvRectangle(img, p1,p2, CV_RGB(255, 0, 0), 2, 8, 0);
        cvDrawContours(img, ptr, color, CV_RGB(0,0,0), -1, CV_FILLED, 8, cvPoint(0,0));
        count++;

    }

    cvShowImage("contures",img);
    cvWaitKey(0)
*/

}
