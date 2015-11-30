package mysteryinc.eagleeye;

/**This class contains the "back end" of the application: it contains all of the methods that use OpenCV and JavaCV libraries to perform the image recognition.
 * Created by Joel and Dean for Java, created for Android application by Jonathan
 */

import android.os.Environment;
import android.util.Log;

import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_imgproc;

public class Android_JavaCV_Implementation {
    //String cascade = new String("/Users/joelvandepolder/Desktop/cascade.xml");
//    String cascade = "../../../assets/cascades/cascade.xml";
//    String cascadeTest = "../../../assets/cascades/test.xml";
    String cascade = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EagleEyeTempPics/cascade.xml";
    String cascadeTest = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EagleEyeTempPics/test.xml";

    FaceRecognizer bldgRec = createLBPHFaceRecognizer();
    private CascadeClassifier faceCascade;
    double scale = 1.1;
    int minNeighbors = 1;
    double minSizeRatio = .1f;
    double maxSizeRatio = .3f;
    RectVector found = new RectVector();

    /**
     * This method uses a passed Matrix of an image and the passed Rectangular vector along with the cascade (a pre-trained included file) to match the passed image to that of
     * one of the buildings on the ERAU-DB campus.
     * @param img is a passed image matrix that is the picture taken by the user of the app
     * @param res is a passed rectangular vector that is used to assist with the building recognition
     */
    public void findBLDG(Mat img, RectVector res ){
        MainActivity.toast("Beginning to find building...");
//        Mat tmp = new Mat();
        faceCascade = new CascadeClassifier();
        faceCascade.load(cascade);
        int width = img.size().width();
        int height = img.size().height();

        Size minScaleSize = new Size((int) Math.round(minSizeRatio * width), (int) Math.round(minSizeRatio * height));
        Size maxScaleSize = new Size((int) Math.round(maxSizeRatio * width), (int) Math.round(maxSizeRatio * height));

//        opencv_imgproc.cvtColor(img, tmp, opencv_imgproc.CV_BGRA2GRAY);//CV_BGR2GRAY);

        faceCascade.detectMultiScale(img, res, scale, minNeighbors, 0, minScaleSize, maxScaleSize);

        MainActivity.toast("Finished finding building...");
    }

    /**
     * This method uses a passed path to create a matrix of an image located at the path given and pass it to the bldgRec method to begin the process of finding the matching
     * building.
     * @param path is a path that contains the location of the image to be used for comparison
     * @return calls bldgRec.predict and passes it the grayscale matrix form of the user-taken image
     */
    public int BLDGRecognizer(String path){
        MainActivity.toast("Beginning to recognize building...");
        FaceRecognizer bldgRec = createLBPHFaceRecognizer();
        //bldgRec.load("test.xml");
        bldgRec.load(cascadeTest);

        Mat testImg = imread(path, CV_LOAD_IMAGE_GRAYSCALE);

        MainActivity.toast("Finishing recognize building...");
        return bldgRec.predict(testImg);
    }

    /**
     * This method uses the passed path of a user-taken image and goes through a series of loops to compare the image to the project database several times to find the closest
     * possible match.
     * @param path is a passed value that contains the location of the image to be compared
     * @return calls callBuilding and passes it the number value id of the building in the image with the closest match to those in the database
     */
    public String match(String path){
        MainActivity.toast("Beginning to match...");
        Log.e("image proc", "***********file path: "+path);
//        if (path.startsWith("/"))
//        path = path.substring(1);
        Mat img = imread(path, 0);
        if (img.empty()) {
            Log.e("image proc", "***********img null***********");
        }
        findBLDG(img, found);

        int[] results = new int[(int) found.size()];

        // the most frequene int naming building
        int buildingName;

        for (long n = 0; n < found.size(); n++ ){
//            Rect tmpRect = found.get(n);
//            Mat tmpMat = new Mat(img, tmpRect);

            int id = BLDGRecognizer(path);
            results[(int) n] = id;
            System.out.print(id);


        }


        int[] testx = new int[90];

        for (int i = 0; i < results.length; i++) {
            testx[results[i]]++;

        }
        int largest = 0, index = 0;
        for (int i = 0; i < testx.length; i++) {

            if ( testx[i] > largest ) {
                largest = testx[i];
                index = i;


            }
        }

        MainActivity.toast("Finishing match (fake)...");
        return callBuilding(index);
    }

    /**
     * This method uses a passed value to return a string with the full name of the building.
     * @param cB is the passed ID value of the building that has been matched
     * @return returns the name of the matched building
     */
    public String callBuilding(int cB){
        MainActivity.toast("Beginning to call building...");
        String name = "";
        switch (cB) {
            case 1:
                name ="Lehman";
                break;
            case 2:
                name ="M-Bldg";
                break;
            case 6:
                name ="COA";
                break;
            case 7:
                name ="Mail Center";
                break;
            case 8:
                name ="COAS";
                break;
            case 10:
                name ="Sim Bldg";
                break;
            case 11:
                name ="IC";
                break;
            case 12:
                name ="UC";
                break;
            case 21:
                name ="Mod 21 Bookstore";
                break;
            case 26:
                name ="FLOPS";
                break;
            case 27:
                name ="AMS";
                break;
            case 29:
                name ="COB";
                break;
            default:
                name = "unknown building";
                break;
        }

        MainActivity.toast("Finishing call building...");
        return name;
    }

}
