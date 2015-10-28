package javaTestApp;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;


//Much of this Code was constructed with assistance from examples found at: opencv-java-tutorials.redthedoc.org/en/latest/#
/**
 * @author Team Scooby Doo
 * This class contains the methods that OpenCV uses to calculate and return image types and image matches. 
 */
public class PicProcessing {

	private  static CascadeClassifier faceCascade;
	private static int absoluteFaceSize;
	String result;
	
	/**Method used to detect objects within an inputted image to compare to facial recognition software
	 * @param loc passes in the file system location of the image to be checked
	 */
	public void DetectObject(String loc){
		//Load Library
		System.load("/usr/local/Cellar/opencv/2.4.12/share/OpenCV/java/libopencv_java2412.dylib");
		
		//Instantiate types for use within method.  Mat is the data type used by opencv
		Mat mat1 = new Mat();
		MatOfRect bldg = new MatOfRect();
		Mat grayFrame = new Mat();
		
		//Instantiate a Cascade classifier and read in one that has been trained
		faceCascade = new CascadeClassifier(); //Create 
		faceCascade.load("/Users/joelvandepolder/Desktop/cascade.xml");
		
		//Grab selected image and convert to Mat
		mat1 = Highgui.imread(loc); //Uses code for Highgui which is not in OpenCV ver 3.0 ****REVIEW FOR SPRINT 2****
		
		//Convert to Grayscale image
		Imgproc.cvtColor(mat1, grayFrame, Imgproc.COLOR_BGR2GRAY);
		
		//Equalizes the histogram of grayscale image
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		//Set minimum face sizes
		if (absoluteFaceSize == 0){
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0)
			{
				absoluteFaceSize = Math.round(height * 0.2f);
			}
		}
		
		//Use Cascade to find objects that it is trained for
		faceCascade.detectMultiScale(grayFrame, bldg, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(
				absoluteFaceSize, absoluteFaceSize), new Size());
		
		//Draw rectangles around faces
		Rect[] facesArray = bldg.toArray();
		for (int i = 0; i < facesArray.length; i++)
			Core.rectangle(mat1, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
		
		
		//Save String for building result
		result = "The image is a square";
		
		//Save result Image
		Highgui.imwrite("result.jpeg", mat1);
	}
	
	/**Loads in image and finds the contours within the image to determine general building shape.
	 * @param loc passes in the location of the image of which contours wish to be found
	 */
	public void DectectObject2(String loc){
		//Load Library
		System.load("/usr/local/Cellar/opencv/2.4.12/share/OpenCV/java/libopencv_java2412.dylib");
		
		//Instantiate types for use within method.  Mat is the data type used by opencv
		Mat frame = new Mat();
		Mat blurredImage = new Mat();
		Mat hsvImage = new Mat();
		Mat mask = new Mat();
		Mat morphOutput = new Mat();
		
		//Grab selected image and convert to Mat
		frame = Highgui.imread(loc);
		
		//Blurs an image using the normalized box filter.
		Imgproc.blur(frame, blurredImage, new Size(7, 7));
		
		//BGR to Hue, Saturation, and value image
		Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
		
		//Set minimum values for hue, saturation, and value
		Scalar minValues = new Scalar(10, 60, 50);
		
		//Set Maximum values for hue, saturation, and value
		Scalar maxValues = new Scalar(200, 200, 255);
		
		//Checks if array elements lie between the elements of two other arrays.
		Core.inRange(hsvImage, minValues, maxValues, mask);
		
		//Create Dilate and erode image
		Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
		Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
		
		Imgproc.erode(mask, morphOutput, erodeElement);
		Imgproc.erode(mask, morphOutput, erodeElement);
		
		Imgproc.dilate(mask, morphOutput, dilateElement);
		Imgproc.dilate(mask, morphOutput, dilateElement);
		
		//Find Contours and objects
		frame = findAndDrawBalls(morphOutput, frame);
		
		//Save result Image
		Highgui.imwrite("result.jpeg", frame);
		
		//Save String for building result
		result = "The image is a square";
	}
	
	//Class to convert Java BufferedImage to OpenCV Mat
	/**Method used to load a Java Buffered image and convert it to the Matrix form required by OpenCV
	 * @param in passes in a Java Buffered image to be converted to Mat form
	 * @return an image in Mat form for use by OpenCV Library
	 */
	public Mat img2Mat(BufferedImage in){
		Mat out;
        byte[] data;
        int r, g, b;
        
        if(in.getType() == BufferedImage.TYPE_INT_RGB)
        {
            out = new Mat(240, 320, CvType.CV_8UC3);
            data = new byte[320 * 240 * (int)out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
            for(int i = 0; i < dataBuff.length; i++)
            {
                data[i*3] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                data[i*3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                data[i*3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
            }
        }
        else
        {
            out = new Mat(240, 320, CvType.CV_8UC1);
            data = new byte[320 * 240 * (int)out.elemSize()];
            int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
            for(int i = 0; i < dataBuff.length; i++)
            {
              r = (byte) ((dataBuff[i] >> 16) & 0xFF);
              g = (byte) ((dataBuff[i] >> 8) & 0xFF);
              b = (byte) ((dataBuff[i] >> 0) & 0xFF);
              data[i] = (byte)((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
            }
         }
         out.put(0, 0, data);
         return out;
	}

	//Class to convert OpenCV Mat to Java BufferedImage
	/**Method used to convert an OpenCV Matrix form to a Java Buffered image for output by system
	 * @param in passes in the Matrix to be converted to Java Buffered image
	 * @return an image in the Java Buffered image format
	 */
	public BufferedImage mat2Img(Mat in){

		
        BufferedImage out;
        byte[] data = new byte[320 * 240 * (int)in.elemSize()];
        int type;
        in.get(0, 0, data);

        if(in.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(320, 240, type);

        out.getRaster().setDataElements(0, 0, 320, 240, data);
        return out;
    } 
	
	/**Method to find the contours of a passed building and display them back onto the photo
	 * @param maskedImage passes in a masked image of which the contours will be found
	 * @param frame passes in a frame of which the contours will be drawn on
	 * @return the frame with the contours drawn on it
	 */
	private Mat findAndDrawBalls(Mat maskedImage, Mat frame){
		// init
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		
		// find contours
		Imgproc.findContours(maskedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
		
		// if any contour exist...
		if (hierarchy.size().height > 0 && hierarchy.size().width > 0)
		{
			// for each contour, display it in blue
			for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0])
			{
				Imgproc.drawContours(frame, contours, idx, new Scalar(250, 0, 0), 3);
			}
		}
		
		return frame;
	}
}
