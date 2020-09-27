package com.example.doubledruids;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

//import org.apache.commons.io.IOUtils;

public class MainActivity extends AppCompatActivity {

    Button cameraButton;
    Button selectButton;
    Button uploadButton;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    ImageView img;
    Uri imgUri;
    private File imageFile;
    boolean photoPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        cameraButton = (Button) findViewById(R.id.cameraButton);
        selectButton = (Button) findViewById(R.id.selectButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
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
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photoPresent==true){
                    uploadPicture();
                    Log.i("whatsgoingon", "photo present to upload");
                } else {
                    Log.i("whatsgoingon", "No photo present to upload");
                }
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
//        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);

        Intent pickPhoto = new Intent();
        pickPhoto.setType("image/jpeg/*");
        pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_PICK);

    }

    /**
     * Retrieve image picked by user and add to imageView
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("msg", "I am here!");
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            img.setImageURI(imgUri);
            photoPresent = true;
            Log.i("whatsgoingon", "req image pick done");
        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
//        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            img.setImageBitmap(photo);

            Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmapImage, "Title", null);
            imgUri = Uri.parse(path);

//            Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");
//            File tempDir= Environment.getExternalStorageDirectory();
//            tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
//            tempDir.mkdir();
//            File tempFile = null;
//            try {
//                tempFile = File.createTempFile("temptitle", ".jpg", tempDir);
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                byte[] bitmapData = bytes.toByteArray();
//                //write the bytes in file
//                FileOutputStream fos = new FileOutputStream(tempFile);
//                fos.write(bitmapData);
//                fos.flush();
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            imgUri = Uri.fromFile(tempFile);

            img.setImageURI(imgUri);
            photoPresent = true;
            Log.i("whatsgoingon", "req image capture done");
        } else {
            Log.i("whatsgoingon", String.valueOf(requestCode));
            Log.i("whatsgoingon", String.valueOf(resultCode));
            Log.i("whatsgoingon", String.valueOf(data!=null));
            Log.i("whatsgoingon", String.valueOf(data.getData() != null));
        }
    }

    private void uploadPicture() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);
        Log.i("whatsgoingon", "Attempting upload");

        riversRef.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.i("whatsgoingon", "Successful upload");
                        Toast.makeText(MainActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Log.i("whatsgoingon", "Unsuccessful upload");
                        Toast.makeText(MainActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
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