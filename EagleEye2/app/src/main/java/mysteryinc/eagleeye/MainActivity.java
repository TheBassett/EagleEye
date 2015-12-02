package mysteryinc.eagleeye;
/**
 * This class contains the MainActivity for the application. This class handles the main display for the application as well as all event handlers for the buttons within the application.
 *
 */
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

    /**
     * This method returns an instance of MainActivity
     * @return returns an instance of MainActivity
     */
    public static MainActivity getInstance() {
        if (_instance == null) {
            _instance = new MainActivity();
            Log.e("Startup Error", "Bad instance created");
        }
        return _instance;
    }

    /**
     * Creates a variable for the current instance of MainActivity
     */
    public MainActivity(){
        _instance = this;
    }

    /**
     * This method provides a method of logging to be implemented, so that different parts of the application can pass messages to the log
     * @param msg is a passed message to be added to the current toast
     */
    public static void toast(String msg) {
//        if (m_currentToast != null) {
//            m_currentToast.cancel();
//        }
//        m_currentToast = Toast.makeText(getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT);
//        m_currentToast.show();
        Toast.makeText(getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(String msg) {
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

    /**
     * This method contains all of the actions to initialize MainActivity as soon as it is created.
     * @param savedInstanceState is a passed value that contains a saved instance state for if the application had been minimized and then resumed
     */
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

    /**
     * This method handles the selection of items within the Navigation Drawer
     * @param position is a passed value of the location selected within the navigation drawer
     */
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

    /**
     * This method uses a passed number to set the titles to those of the proper section for each of the three sections attached.
     * @param number is a passed number containing the number of the selected section
     */
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

    /**
     * This method is used to display the ActionBar with its proper title
     */
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * This method is used to setup the options menu upon its creation
     * @param menu is a passed menu to be modified through by this method
     * @return returns super.onCreateOptionsMenu
     */
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

    /**
     * This method contains the actions for if an item on the options menu is selected
     * @param item is a passed item that contains the information of which item was selected
     * @return returns true if the id of the selected option has action items tied to it and returns super.onOptionsItemsSelected if it does not.
     */
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





//Code commented out and not used in final production of the application.
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
