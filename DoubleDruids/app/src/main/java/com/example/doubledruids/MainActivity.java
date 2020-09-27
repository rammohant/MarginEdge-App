package com.example.doubledruids;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

//import org.apache.commons.io.IOUtils;

public class MainActivity extends AppCompatActivity {

    Button cameraButton;
    Button selectButton;
    ImageView img;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        selectButton = (Button) findViewById(R.id.selectButton);
        img = (ImageView) findViewById(R.id.imageView);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchChoosePictureIntent();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_PICK = 2;

    private void dispatchTakePictureIntent() {
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePhoto, REQUEST_IMAGE_CAPTURE);
    }
    private void dispatchChoosePictureIntent() {
        // from library
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);
    }

    /**
     * Retrieve image picked by user and add to imageView
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("msg", "I am here!");
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        img.setImageBitmap(photo);
        super.onActivityResult(requestCode, resultCode, data);

//        Log.i("msg", "I am here1");
//        if (resultCode != RESULT_CANCELED) {
//            Log.i("msg", "I am here2");
//            switch (requestCode) {
//                case 1:
//                    Log.i("msg", "I am here3");
//                    if (resultCode == RESULT_OK && data != null) {
//                        Log.i("msg", "I am here4");
//                        Uri selectedImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (selectedImage != null) {
//                            Log.i("msg", "I am here5");
//                            Cursor cursor = getContentResolver().query(selectedImage,
//                                    filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                Log.i("msg", "I am here6");
//                                cursor.moveToFirst();
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                imageFile = new File(getApplicationContext().getFilesDir(),picturePath.substring(picturePath.lastIndexOf("/")+1));
//
//
//
////                                try (InputStream stream = getApplicationContext().getContentResolver().openInputStream(selectedImage)) {
////                                    OutputStream outputStream = new FileOutputStream(imageFile);
//////                                    IOUtils.copy(stream,outputStream);
////                                }
////                                catch(IOException e){
////                                    e.printStackTrace();
////                                }
//                                img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                cursor.close();
//                                Log.i("msg", "I am here7");
//                            }
//                        }
//                        Log.i("msg", "I am here8");
//                        img.setImageURI(selectedImage);
//                    }
//                    break;
//            }
//        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            img.setImageBitmap(imageBitmap);
//        }
//    }

//    String currentPhotoPath;
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }


}