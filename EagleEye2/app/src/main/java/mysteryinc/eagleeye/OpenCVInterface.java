/**
* This Class file contains the methods necessary to implement OpenCV using the Camera
*/
public class OpenCVInterface extends Activity implements CvCameraViewListener {

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS:
                    {
                        Log.i(TAG, "OpenCV loaded successfully");
                        mOpenCvCameraView.enableView();
                    } break;
                    default:
                    {
                        super.onManagerConnected(status);
                    } break;
                }
            }
        };

        @Override
        public void onResume()
        {
            super.onResume();
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
        }

        ...
    }
}