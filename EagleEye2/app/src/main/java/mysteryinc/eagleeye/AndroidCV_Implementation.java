//package mysteryinc.eagleeye;
//
//import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
//import static org.bytedeco.javacpp.opencv_imgcodecs.*;
//
//import org.bytedeco.javacpp.opencv_core.Mat;
//import org.bytedeco.javacpp.opencv_core.RectVector;
//import org.bytedeco.javacpp.opencv_core.Size;
//import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
//import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
//import org.bytedeco.javacpp.opencv_imgproc;
//
//public class AndroidCV_Implementation {
//
//    String cascade = new String("/Users/joelvandepolder/Desktop/cascade.xml");
//    private CascadeClassifier faceCascade;
//    double scale = 1.1;
//    int minNeighbors = 1;
//    double minSizeRatio = .1f;
//    double maxSizeRatio = .3f;
//
//    public void findBLDG(String ImgPath, RectVector res ){
//        Mat img = imread(ImgPath);
//        Mat tmp = new Mat();
//        faceCascade = new CascadeClassifier();
//        faceCascade.load("/Users/joelvandepolder/Desktop/cascade.xml");
//        int width = img.size().width();
//        int height = img.size().height();
//
//        Size minScaleSize = new Size((int) Math.round(minSizeRatio * width), (int) Math.round(minSizeRatio * height));
//        Size maxScaleSize = new Size((int) Math.round(maxSizeRatio * width), (int) Math.round(maxSizeRatio * height));
//
//        opencv_imgproc.cvtColor(img, tmp, opencv_imgproc.CV_BGR2GRAY);
//
//        faceCascade.detectMultiScale(tmp, res, scale, minNeighbors, 0, minScaleSize, maxScaleSize);
//    }
//
//    public String BLDGRecognizer(String path){
//        FaceRecognizer bldgRec = createLBPHFaceRecognizer();
//        bldgRec.load("test.xml");
//
//        Mat testImg = imread(path, CV_LOAD_IMAGE_GRAYSCALE);
//
//        int res = bldgRec.predict(testImg);
//
//        //This will need to change based on labels we gather
//        switch (res) {
//            case 1: return "COA";
//            case 2: return "COAS";
//            default: return "not an erau bldg";
//        }
//    }
//}
