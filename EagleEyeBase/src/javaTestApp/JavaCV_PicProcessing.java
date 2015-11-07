package javaTestApp;

import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_imgproc;

public class JavaCV_PicProcessing {
	
	static String cascade = new String("/Users/joelvandepolder/Desktop/cascade.xml"); 
	private  static CascadeClassifier faceCascade;
	static double scale = 1.1;
	static int minNeighbors = 1;
	static double minSizeRatio = .1f;
    static double maxSizeRatio = .3f;
    
    public static void findBLDG(String ImgPath, RectVector res ){
		Mat img = imread(ImgPath);
		Mat tmp = new Mat();
		faceCascade = new CascadeClassifier();
		faceCascade.load("/Users/joelvandepolder/Desktop/cascade.xml");
		int width = img.size().width();
		int height = img.size().height();
		
		Size minScaleSize = new Size((int) Math.round(minSizeRatio * width), (int) Math.round(minSizeRatio * height));
		Size maxScaleSize = new Size((int) Math.round(maxSizeRatio * width), (int) Math.round(maxSizeRatio * height));
		
		opencv_imgproc.cvtColor(img, tmp, opencv_imgproc.CV_BGR2GRAY);
		
		faceCascade.detectMultiScale(tmp, res, scale, minNeighbors, 0, minScaleSize, maxScaleSize);	
	}
	
}
