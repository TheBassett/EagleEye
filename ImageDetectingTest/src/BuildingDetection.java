import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.imgproc.Imgproc;

import java.util.LinkedList;
import java.util.List;

//
// Detects faces in an image, draws boxes around them, and writes the results
// to "faceDetection.png".
//
class DetectBuilding {
  public void run() {

	  Mat image1 = Highgui.imread("/Users/JessUpde/Desktop/CS225_Workspace/ImageDetectingTest/COAS_3.JPG");
	  Mat image2 = Highgui.imread("/Users/JessUpde/Desktop/CS225_Workspace/ImageDetectingTest/COAS_2.JPG");
	  
      FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB);
      DescriptorExtractor orbextractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

      MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
      MatOfKeyPoint keypoints_scene = new MatOfKeyPoint();
      
      Mat descriptors_object = new Mat();
      Mat descriptors_scene = new Mat();
      
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
       Mat image3 = image1.clone();
       Features2d.drawMatches(image1, keypoints_object, image2, keypoints_scene, matches, image3);
       Highgui.imwrite("result_match.jpeg", image3); 
  }
}

public class BuildingDetection {
  public static void main(String[] args) {

    // Load the native library.
    System.load("/usr/local/Cellar/opencv/2.4.12/share/OpenCV/java/libopencv_java2412.dylib");
    new DetectBuilding().run();
  }
}