package javaTestApp;

import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_imgproc;

public class JavaCV_PicProcessing {
	
	String cascade = new String("/Users/joelvandepolder/Desktop/cascade.xml"); 
	FaceRecognizer bldgRec = createLBPHFaceRecognizer();
	private CascadeClassifier faceCascade;
	double scale = 1.1;
	int minNeighbors = 1;
	double minSizeRatio = .1f;
    double maxSizeRatio = .3f;
    RectVector found;
    
    public void findBLDG(Mat img, RectVector res ){
		Mat tmp = new Mat();
		faceCascade = new CascadeClassifier();
		faceCascade.load(cascade);
		int width = img.size().width();
		int height = img.size().height();
		
		Size minScaleSize = new Size((int) Math.round(minSizeRatio * width), (int) Math.round(minSizeRatio * height));
		Size maxScaleSize = new Size((int) Math.round(maxSizeRatio * width), (int) Math.round(maxSizeRatio * height));
		
		opencv_imgproc.cvtColor(img, tmp, opencv_imgproc.CV_BGR2GRAY);
		
		faceCascade.detectMultiScale(tmp, res, scale, minNeighbors, 0, minScaleSize, maxScaleSize);	
	}
    
    public int BLDGRecognizer(String path){
    	FaceRecognizer bldgRec = createLBPHFaceRecognizer();
		bldgRec.load("test.xml");
		
		Mat testImg = imread(path, CV_LOAD_IMAGE_GRAYSCALE);
		
		return bldgRec.predict(testImg);
		}
   
    
    public String match(String path){
    	Mat img = imread(path);
    	findBLDG(img, found);
    	
    	for (long n = 0; n < found.size(); n++ ){
    		Rect tmpRect = found.get(n);
			Mat tmpMat = new Mat(img, tmpRect);
			
			int id = BLDGRecognizer(path);
			 System.out.print(id);
    	}
    	
    	return null;
    
    
}
 }