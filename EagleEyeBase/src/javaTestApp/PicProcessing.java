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

public class PicProcessing {

	private  static CascadeClassifier faceCascade;
	private static int absoluteFaceSize;
	String result;
	
	public void DetectObject(String loc){
		System.load("/usr/local/Cellar/opencv/2.4.12/share/OpenCV/java/libopencv_java2412.dylib");
		
		Mat mat1 = new Mat();
		MatOfRect bldg = new MatOfRect();
		Mat grayFrame = new Mat();
		faceCascade = new CascadeClassifier();
		faceCascade.load("/Users/joelvandepolder/Desktop/cascade.xml");
		
		
		mat1 = Highgui.imread(loc);
		Imgproc.cvtColor(mat1, grayFrame, Imgproc.COLOR_BGR2GRAY);
		
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		if (absoluteFaceSize == 0){
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0)
			{
				absoluteFaceSize = Math.round(height * 0.2f);
			}
		}
		
		faceCascade.detectMultiScale(grayFrame, bldg, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(
				absoluteFaceSize, absoluteFaceSize), new Size());
		Rect[] facesArray = bldg.toArray();
		for (int i = 0; i < facesArray.length; i++)
			Core.rectangle(mat1, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
		
		result = "The image is a square";
		Highgui.imwrite("result.jpeg", mat1);
		//return mat2Img(mat1);
		
	}
	
	public void DectectObject2(String loc){
System.load("/usr/local/Cellar/opencv/2.4.12/share/OpenCV/java/libopencv_java2412.dylib");
		
		Mat frame = new Mat();
		Mat blurredImage = new Mat();
		Mat hsvImage = new Mat();
		Mat mask = new Mat();
		Mat morphOutput = new Mat();
		
		frame = Highgui.imread(loc);
		
		Imgproc.blur(frame, blurredImage, new Size(7, 7));
		
		Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
		
		Scalar minValues = new Scalar(10, 60, 50);
		Scalar maxValues = new Scalar(150, 200, 300);
		
		Core.inRange(hsvImage, minValues, maxValues, mask);
		
		Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
		Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
		
		Imgproc.erode(mask, morphOutput, erodeElement);
		Imgproc.erode(mask, morphOutput, erodeElement);
		
		Imgproc.dilate(mask, morphOutput, dilateElement);
		Imgproc.dilate(mask, morphOutput, dilateElement);
		
		frame = findAndDrawBalls(morphOutput, frame);
		
		Highgui.imwrite("result.jpeg", frame);
		result = "The image is a square";
	}
	
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
