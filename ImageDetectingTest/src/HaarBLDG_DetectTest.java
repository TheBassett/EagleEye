import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;

//
// Detects faces in an image, draws boxes around them, and writes the results
// to "faceDetection.png".
//
class HaarTest {
  public void run() {
	  
	  Image imageToShow = null;
      Mat frame = new Mat();
      Mat frame2 = new Mat();

	  Mat image1 = Highgui.imread("/Users/joelvandepolder/EagleEye/Eagle Eye Database/COAS_3.JPG");
	  Mat image2 = Highgui.imread("/Users/joelvandepolder/EagleEye/Eagle Eye Database/COAS-77.jpeg");
	  
      FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB);
      DescriptorExtractor orbextractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

      MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
      MatOfKeyPoint keypoints_scene = new MatOfKeyPoint();
      
      Mat descriptors_object = new Mat();
      Mat descriptors_scene = new Mat();
      Mat blurredImage = new Mat();
      Mat hsvImage = new Mat();
      Mat mask = new Mat();
      Mat morphOutput = new Mat();
      Mat blurredImage2 = new Mat();
      Mat hsvImage2 = new Mat();
      Mat mask2 = new Mat();
      Mat morphOutput2 = new Mat();
      
     //Remove Noise
      Imgproc.blur(image1, blurredImage, new Size(7, 7));
      Imgproc.blur(image2, blurredImage2, new Size(7, 7));
      
      Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
      Imgproc.cvtColor(blurredImage2, hsvImage2, Imgproc.COLOR_BGR2HSV);
      
    //  Morph Operators
      Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
      Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
      
      Imgproc.erode(mask, morphOutput, erodeElement);
      Imgproc.erode(mask, morphOutput, erodeElement);

      Imgproc.dilate(mask, morphOutput, dilateElement);
      Imgproc.dilate(mask, morphOutput, dilateElement);
      
      Imgproc.erode(mask2, morphOutput2, erodeElement);
      Imgproc.erode(mask2, morphOutput2, erodeElement);

      Imgproc.dilate(mask2, morphOutput2, dilateElement);
      Imgproc.dilate(mask2, morphOutput2, dilateElement);
      
      frame = this.findAndDrawBalls(morphOutput, frame);
      frame2 = this.findAndDrawBalls(morphOutput2, frame2);
      Highgui.imwrite("Test.jpeg", frame2);
      
    //Getting the keypoints
      orbDetector.detect( image1, keypoints_object );
      orbDetector.detect( image2, keypoints_scene );
      
      
    //Compute descriptors
      orbextractor.compute( image1, keypoints_object, descriptors_object );
      orbextractor.compute( image2, keypoints_scene, descriptors_scene );
      
    //Match with Brute Force
      MatOfDMatch matches = new MatOfDMatch();
      DescriptorMatcher matcher;
      matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
      matcher.match( descriptors_object, descriptors_scene, matches );

      double max_dist = 0;
      double min_dist = 100;

      List<DMatch> matchesList = matches.toList();

      //-- Quick calculation of max and min distances between keypoints
        for( int i = 0; i < descriptors_object.rows(); i++ )
        { double dist = matchesList.get(i).distance;
          if( dist < min_dist ) min_dist = dist;
          if( dist > max_dist ) max_dist = dist;
        }

       LinkedList<DMatch> good_matches = new LinkedList<DMatch>();

       for( int i = 0; i < descriptors_object.rows(); i++ )
        { if( matchesList.get(i).distance <= 3*min_dist ) 
           { good_matches.addLast( matchesList.get(i));
          }
        }
       System.out.println(good_matches);
       System.out.println(min_dist + " and " + max_dist);
       Mat image3 = image1.clone();
       Features2d.drawMatches(image1, keypoints_object, image2, keypoints_scene, matches, image3);
       Highgui.imwrite("result_match.jpeg", frame); 
  }
  
  private Mat findAndDrawBalls(Mat maskedImage, Mat frame)
  {
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
                          Imgproc.drawContours(frame, contours, idx, new Scalar(250, 0, 0));
                  }
          }

          return frame;
  }
  
  private Image mat2Image(Mat frame)
  {
          // create a temporary buffer
          MatOfByte buffer = new MatOfByte();
          // encode the frame in the buffer, according to the PNG format
          Highgui.imencode(".png", frame, buffer);
          // build and return an Image created from the image encoded in the
          // buffer
          return new Image(new ByteArrayInputStream(buffer.toArray()));
  }
  
}

public class HaarBLDG_DetectTest {
  public static void main(String[] args) {

    // Load the native library.
    System.load("/usr/local/Cellar/opencv/2.4.12/share/OpenCV/java/libopencv_java2412.dylib");
    new DetectBuilding().run();
  }
}

