
package mysteryinc.eagleeye;
/**
 * This class contains the event handlers and the display information to handle and display the "About" screen when the "About" button is pressed by the user.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;


public class DisplayAbout extends AppCompatActivity {
    /**
     * This method contains the actions to be performed immediately upon the creation of an instance of Displayabout
     * @param savedInstanceState is a passed value with the information of previously stored instance of DisplayAbout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String message = intent.getStringExtra(NavigationDrawerFragment.EXTRA_MESSAGE);

        //TextView textView = new TextView(this);
        TextView textView = (TextView) findViewById(R.id.about_text);
        textView.setTextSize(17);

        textView.setText(message);
        setContentView(textView);
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
}
