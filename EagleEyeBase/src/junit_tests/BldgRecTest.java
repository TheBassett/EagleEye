package junit_tests;

import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.junit.Assert.*;
import javaTestApp.JavaCV_PicProcessing;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BldgRecTest {

	JavaCV_PicProcessing rec;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void JoelTest() {
		rec = new JavaCV_PicProcessing();
		Mat img = imread("/Users/joelvandepolder/Desktop/coa2.jpg", CV_LOAD_IMAGE_GRAYSCALE);
		int res = rec.BLDGRecognizer(img);
		System.out.println(res);
		assertEquals(6,res);
	}

}
