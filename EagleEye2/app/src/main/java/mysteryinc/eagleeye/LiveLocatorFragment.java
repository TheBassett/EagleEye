package mysteryinc.eagleeye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LiveLocatorFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String EXTRA_MSG = "mysteryinc.eagleeye";
    private static final String ARG_SECTION_NUMBER = "test";
    private static final int SECTION_NUMBER = 0;
    private static LiveLocatorFragment _instance;
    //    private String photopath = "";
    private String buildingname = "";

    private static String ExternalStorageDirectoryPath;
    private static String PictureDirPath;
    private static File pictureFilePath;
    private static boolean fileDirValid;

    private static final int REQUEST_PICTURE = 1;
    private static final int REQUEST_CROP_PICTURE = 2;
    private static final int RESULT_OK = -1;
    private static final int MEDIA_TYPE_IMAGE = 1;
    /**
     * ************************* START COPYRIGHT MATERIAL *************************
     */

    /*
     * Copyright (c) 2014 Rex St. John on behalf of AirPair.com
     *
     * Permission is hereby granted, free of charge, to any person obtaining a copy
     * of this software and associated documentation files (the "Software"), to deal
     * in the Software without restriction, including without limitation the rights
     * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     * copies of the Software, and to permit persons to whom the Software is
     * furnished to do so, subject to the following conditions:
     *
     * The above copyright notice and this permission notice shall be included in
     * all copies or substantial portions of the Software.
     *
     * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
     * THE SOFTWARE.
     */

    // Activity result key for camera
    static final int REQUEST_TAKE_PHOTO = 11111;

    /**
     * Start the camera by dispatching a camera intent. Returns a string location to the photo.
     */
    private void takePicture() {
        // Check if there is a camera.
        if (deviceMissingCamera()) {
            MainActivity.toast("This device does not have a camera.");
            return;
        }

        //use standard intent to capture an image
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE)); // create a file to save the image
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        //we will handle the returned data in onActivityResult
        startActivityForResult(captureIntent, REQUEST_PICTURE);//CAMERA_CAPTURE

//        // Ensure that there's a camera activity to handle the intent
//        Activity activity = getActivity();
//        File photoFile = null;
//        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
//            // Create the File where the photo should go.
//            // If you don't do this, you may get a crash in some devices.
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                ex.printStackTrace();
//                MainActivity.toast("There was a problem saving the photo...");
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri fileUri = Uri.fromFile(photoFile);
////                activity.setCapturedImageURI(fileUri);
////                activity.setCurrentPhotoPath(fileUri.getPath());
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
////                        activity.getCapturedImageURI());
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//        return photoFile.getAbsolutePath();
    }

    public boolean deviceMissingCamera() {
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();
        return !packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "EagleEyeTempPics");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("EagleEyeTempPics", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "tempPicture.jpg");
        }
        /*else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } */
        else {
            return null;
        }

        return mediaFile;
    }

    /**
     * Creates the image file to which the image must be saved.
     *
     * @return
     * @throws IOException
     */
    protected File createImageFile() throws IOException {
        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
        String imageFileName = "tempPicture.jpg";
        File image = new File(pictureFilePath + imageFileName);
//        File image = new File(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                pictureFilePath      /* directory */ //storageDir
//        );

        // Save a file: path for use with ACTION_VIEW intents
//        CameraActivity activity = (CameraActivity)getActivity();
//        activity.setCurrentPhotoPath("file:" + image.getAbsolutePath());
        return image;
    }

    /**
     * The activity returns with the photo.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
//            addPhotoToGallery();
//            CameraActivity activity = (CameraActivity)getActivity();
//
//            // Show the full sized image.
//            setFullImageFromFilePath(activity.getCurrentPhotoPath(), mImageView);
//            setFullImageFromFilePath(activity.getCurrentPhotoPath(), mThumbnailImageView);

            String path_temp = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EagleEyeTempPics/tempPicture.jpg";
            testPicture(path_temp);

        } else {
            MainActivity.toast("Image Capture Failed");
        }
    }

    private void testPicture(String filename) {
        File file = new File(filename);
        String result = "";
        if (file.exists()) {
            Log.e("image proc", "***********img exists***********");
            result = buildingCompare(filename);
        } else {
            Log.e("image proc", "***********img DOESN'T exist***********");
        }
        //"../../../assets/COA_2.JPG");


//            Intent intent = new Intent(this.getActivity(),DisplayBuildingNameActivity.class);
//            intent.putExtra(EXTRA_MSG, result);
//            startActivity(intent);

        MainActivity.toastLong("Result: " + result);
    }

    private void testPicture() {
        String path_temp = Environment.getExternalStorageDirectory().getAbsolutePath() + "/EagleEyeTempPics/coa_2.jpg";
        testPicture(path_temp);
    }

    /**
     * ************************* END COPYRIGHT MATERIAL *************************
     */


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LiveLocatorFragment get_instance() {
        if (_instance == null) {
            _instance = new LiveLocatorFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, SECTION_NUMBER);
            _instance.setArguments(args);
            _instance.setHasOptionsMenu(true);
        }
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.live_locator_fragment, container, false);

        verifyPictureDirectory();

        Button picture = (Button) rootView.findViewById(R.id.picture_button);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileDirValid) {
                    takePicture();
//                    testPicture();
                } else {
                    MainActivity.toast("Invalid picture directory - bad developer");
                }
            }
        });
        return rootView;
    }

    private void verifyPictureDirectory() {
        ExternalStorageDirectoryPath =
                Environment.getExternalStorageDirectory().getAbsolutePath();
        PictureDirPath = ExternalStorageDirectoryPath + "/EagleEyeTempPics/";
        pictureFilePath = new File(PictureDirPath);
        pictureFilePath.mkdirs();
        if (!pictureFilePath.isDirectory()) {
//            Toast.makeText(MainActivity.getContext(), "Picture Directory Failed", Toast.LENGTH_LONG).show();
            Log.e("Startup Error", "Bad file directory");
            MainActivity.toast("Picture directory failed");
            fileDirValid = false;
        } else {
            fileDirValid = true;
        }
    }

    public String buildingCompare(String photopath) {
        //Method to call database comparison methods from OpenCV
        //Has input of the photo path of photo to be compared to database
        //returns the String name of the building name to be inserted into the overlay
        //ORB Image Matching Algorithm found online will be implemented

        Android_JavaCV_Implementation pictureGetter = new Android_JavaCV_Implementation();
        buildingname = pictureGetter.match(photopath);
        Log.e("image proc", "***********Building: " + buildingname);
        return buildingname;
    }
}
