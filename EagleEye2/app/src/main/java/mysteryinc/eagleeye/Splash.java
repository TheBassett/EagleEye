package mysteryinc.eagleeye;
/**
 * This class contains the creation of a "Splash" page for display as the application is loading.
 * Created by Jess Updegrove
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Code from "artworkdad" on StackOverflow:
 * (http://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen)
 */
public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        // Trying to change font
        // http://stackoverflow.com/questions/2888508/how-to-change-the-font-on-the-textview
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/sansserif.ttf");
        TextView tv =(TextView)findViewById(R.id.title);
        tv.setTypeface(tf);

        // More testing to check if I'm actually accessing things
        TextView splash_info =(TextView)findViewById(R.id.splash_info);
        splash_info.setTypeface(null, Typeface.ITALIC);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}