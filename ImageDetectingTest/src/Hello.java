import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Hello
{
   public static void main( String[] args )
   {
	  System.load("/usr/local/Cellar/opencv/2.4.12/share/OpenCV/java/libopencv_java2412.dylib");
      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
      System.out.println( "mat = " + mat.dump() );
   }
}