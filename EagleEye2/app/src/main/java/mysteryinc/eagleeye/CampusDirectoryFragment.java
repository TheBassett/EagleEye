package mysteryinc.eagleeye;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CampusDirectoryFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "test";
    private static final int SECTION_NUMBER = 1;
    private static CampusDirectoryFragment _instance;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CampusDirectoryFragment get_instance() {
        if (_instance == null) {
            _instance = new CampusDirectoryFragment();
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
        View rootView = inflater.inflate(R.layout.campus_directory_fragment, container, false);
        return rootView;
    }
}