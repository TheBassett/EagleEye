package mysteryinc.eagleeye;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LiveLocatorFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "test";
    private static final int SECTION_NUMBER = 0;
    private static LiveLocatorFragment _instance;
    private String photopath = "";
    private String buildingname = "";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LiveLocatorFragment get_instance() {
        if (_instance == null) {
            _instance = new LiveLocatorFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, SECTION_NUMBER);
            _instance.setArguments(args);
            _instance.setHasOptionsMenu(true);
        }
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.live_locator_fragment, container, false);
        return rootView;
    }

    public void setOverlayText(String string) {
        // Method to set the text in the overlay
    }

    public String takePicture() {
        // Method to open the camera and allow the user to take a picture.
        // Returns a string to the location of the photograph
        return photopath;
    }

    public String buildingCompare (String photopath) {
        //Method to call database comparison methods from OpenCV
        //Has input of the photo path of photo to be compared to database
        //returns the String name of the building name to be inserted into the overlay
        return buildingname;
    }
}
