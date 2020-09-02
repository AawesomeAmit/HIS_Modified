package com.his.android.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.his.android.R;
import com.his.android.Response.GetPatientDetailsByPidResp;
import com.his.android.Utils.ConnectivityChecker;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.RetrofitClientFile;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardPostActivity extends AppCompatActivity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int SELECT_PICTURE = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "HIS";
    public static Bitmap bitmap;
    public Uri fileUri;
    String picturePath = "", filename = "", ext = "";
    String encodedString = "";
    Uri picUri;
    String filePath = "";

    private TextView txtDrName, txtDept, img, tvSubmit;

    ImageView ivImage, ivCapture;

    EditText etPatientName, etAge, etProblems;

    Spinner spinnerAgeUnit;

    RadioButton rbMale, rbFemale;

    String[] ageUnits = {"Select", "Day", "Month", "Year"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_post);

        init();
        setListeners();

    }

    private void init() {
        txtDrName = findViewById(R.id.txtDrName);
        txtDept = findViewById(R.id.txtDept);
        img = findViewById(R.id.img);
        tvSubmit = findViewById(R.id.tvSubmit);

        ivImage = findViewById(R.id.ivImage);
        ivCapture = findViewById(R.id.ivCapture);

        etPatientName = findViewById(R.id.etPatientName);
        etAge = findViewById(R.id.etAge);
        etProblems = findViewById(R.id.etProblems);

        spinnerAgeUnit = findViewById(R.id.spinnerAgeUnit);

        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        txtDrName.setText(SharedPrefManager.getInstance(this).getUser().getDisplayName());
        txtDept.setText(SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName());

        /*img.setOnClickListener(view -> {

            PopupMenu menu = new PopupMenu(GuardPostActivity.this, img);
            menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());

            menu.getMenu().getItem(0).setVisible(false);
            menu.getMenu().getItem(1).setVisible(true);

            menu.setOnMenuItemClickListener(item -> {

                startActivity(new Intent(this,GuardPostPatientListActivity.class));
                return true;
            });
            menu.show();
        });*/

        ivCapture.setOnClickListener(view -> {
            //showCameraGalleryDialog();

            if (checkPermission()) {
                showCameraGalleryDialog();
            } else {
                requestPermission();
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(GuardPostActivity.this, R.layout.spinner_layout_new, ageUnits);
        spinnerAgeUnit.setAdapter(arrayAdapter);

        if (ConnectivityChecker.checker(this)) {
            hitGetPatientDetailsByPID();
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    private void setListeners() {

        rbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbFemale.setChecked(false);
                }
            }
        });

        rbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rbMale.setChecked(false);
                }
            }
        });

        tvSubmit.setOnClickListener(view -> {
            if (etPatientName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter Patient Name", Toast.LENGTH_SHORT).show();
            } /*else if (etAge.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter Age", Toast.LENGTH_SHORT).show();
            } else if (spinnerAgeUnit.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Select Age Unit", Toast.LENGTH_SHORT).show();
            }*/ else {
                hitSavePatientEntryByGuard();
            }

            /*else if (!rbMale.isChecked() && !rbFemale.isChecked()){
                Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
            }else if (!rbMale.isChecked() && !rbFemale.isChecked()){
                Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
            }*/
        });

    }

    private void hitGetPatientDetailsByPID() {

        Utils.showRequestDialog(GuardPostActivity.this);

        Call<List<GetPatientDetailsByPidResp>> call = RetrofitClient.getInstance().getApi().getPatientDetailsByPID(
                SharedPrefManager.getInstance(GuardPostActivity.this).getUser().getAccessToken(),
                SharedPrefManager.getInstance(GuardPostActivity.this).getUser().getUserid().toString(),
                SharedPrefManager.getInstance(GuardPostActivity.this).getPid());

        call.enqueue(new Callback<List<GetPatientDetailsByPidResp>>() {
            @Override
            public void onResponse(Call<List<GetPatientDetailsByPidResp>> call, Response<List<GetPatientDetailsByPidResp>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().size() > 0) {
                        etPatientName.setText(response.body().get(0).getPatientName());
                        etAge.setText(response.body().get(0).getAge().toString());
                        if (response.body().get(0).getAgeUnit().equalsIgnoreCase("DAY")) {
                            spinnerAgeUnit.setSelection(1);
                        } else if (response.body().get(0).getAgeUnit().equalsIgnoreCase("MONTH")) {
                            spinnerAgeUnit.setSelection(2);
                        } else if (response.body().get(0).getAgeUnit().equalsIgnoreCase("YEAR")) {
                            spinnerAgeUnit.setSelection(3);
                        }

                        if (response.body().get(0).getGender().equalsIgnoreCase("M")) {
                            rbMale.setChecked(true);
                            rbFemale.setChecked(false);
                        } else if (response.body().get(0).getGender().equalsIgnoreCase("F")) {
                            rbFemale.setChecked(true);
                            rbMale.setChecked(false);
                        }
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<List<GetPatientDetailsByPidResp>> call, Throwable t) {
                Log.e("onFailure:", t.getMessage());

                Toast.makeText(GuardPostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                Utils.hideDialog();
            }
        });
    }

    private void hitSavePatientEntryByGuard() {

        String gender = "";
        String ageUnit = "";

        if (rbMale.isChecked()) {
            gender = "M";
        } else if (rbFemale.isChecked()) {
            gender = "F";
        }

        if (spinnerAgeUnit.getSelectedItemPosition() == 1) {
            ageUnit = "DAY";
        } else if (spinnerAgeUnit.getSelectedItemPosition() == 2) {
            ageUnit = "MONTH";
        } else if (spinnerAgeUnit.getSelectedItemPosition() == 3) {
            ageUnit = "YEAR";
        }

        String pid = null;

        if (SharedPrefManager.getInstance(GuardPostActivity.this).getPid() != 0) {
            pid = String.valueOf(SharedPrefManager.getInstance(GuardPostActivity.this).getPid());
        }

        MultipartBody.Part[] fileParts = new MultipartBody.Part[1];

//         MultipartBody.Part fileParts = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        MultipartBody.Part fileParts = null;
        try {

            // for (int i = 0; i < mediaFiles.size(); i++) {

            //   String path = null;

            //  path = mediaFiles.get(i).getPath();

            Log.d("filePath", "File Path: " + filePath);

            File file = new File(filePath);

            try {
                Bitmap bitmap = BitmapFactory.decodeFile (file.getPath ());
                bitmap.compress (Bitmap.CompressFormat.JPEG, 50, new FileOutputStream(file));
            }
            catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString ());
                t.printStackTrace ();
            }

            MediaType mediaType = MediaType.parse(getMimeType(filePath));

            RequestBody fileBody = RequestBody.create(mediaType, file);

            fileParts[0] = MultipartBody.Part.createFormData("patientImage", file.getName(), fileBody);
//            fileParts = MultipartBody.Part.createFormData("patientImage", file.getName(), fileBody);

            // }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Utils.showRequestDialog(GuardPostActivity.this);

        Call<String> call = RetrofitClientFile.getInstance().getApi().savePatientEntryByGuard(
                SharedPrefManager.getInstance(GuardPostActivity.this).getUser().getAccessToken(),
                SharedPrefManager.getInstance(GuardPostActivity.this).getUser().getUserid().toString(),
                RequestBody.create(MediaType.parse("text/plain"), SharedPrefManager.getInstance(GuardPostActivity.this).getUser().getUserid().toString()),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefManager.getInstance(GuardPostActivity.this).getPid())),
//                RequestBody.create(MediaType.parse("text/plain"), pid),
                RequestBody.create(MediaType.parse("text/plain"), etPatientName.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), etAge.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), ageUnit),
                RequestBody.create(MediaType.parse("text/plain"), gender),
                RequestBody.create(MediaType.parse("text/plain"), etProblems.getText().toString()),
                fileParts
        );


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {

                    try {

                        Toast.makeText(GuardPostActivity.this, response.body(), Toast.LENGTH_SHORT).show();

                        etPatientName.setText("");
                        etAge.setText("");
                        etProblems.setText("");
                        spinnerAgeUnit.setSelection(0);
                        rbMale.setChecked(false);
                        rbFemale.setChecked(false);
                        encodedString = "";
                        ivImage.setImageResource(R.drawable.user);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Utils.hideDialog();
                Toast.makeText(GuardPostActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;

    }

    public void showCameraGalleryDialog() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);


        dialog.setContentView(R.layout.camera_gallery);

        dialog.show();

        RelativeLayout rrCancel = dialog.findViewById(R.id.rr_cancel);
        LinearLayout llCamera = dialog.findViewById(R.id.ll_camera);
        LinearLayout llGallery = dialog.findViewById(R.id.ll_gallery);

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                dialog.dismiss();
            }
        });


        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, SELECT_PICTURE);

                dialog.dismiss();
            }
        });

        rrCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (ActivityCompat.checkSelfPermission(GuardPostActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(GuardPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(GuardPostActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permissions required!", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(GuardPostActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

            return;
        } else {

            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }

    }

    public Uri getOutputMediaFileUri(int type) {
        return FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", getOutputMediaFile(type));
    }

    //method to convert string into base64
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    public void setPictures(Bitmap b, String setPic, String base64) {

        //profile.setImageBitmap(b);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                try {

                    picturePath = fileUri.getPath().toString();
//                    filePath = fileUri.getPath();

                    // filePath = PathUtil.getPath(GuardPostActivity.this,picturePath.);

                    filePath = picturePath;

                    Log.d("path", picturePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                Log.d("filename_camera", filename);
                String selectedImagePath = picturePath;
                Uri uri = Uri.parse(picturePath);

                /*if (status == 0) {
                    ivPhoto.setVisibility(View.VISIBLE);
                    ivPhoto.setImageURI(uri);

                } else {
                    ivDocument.setVisibility(View.VISIBLE);
                    ivDocument.setImageURI(uri);
                }
*/

                ext = "jpg";
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 500;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(picturePath));
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bao);
                byte[] ba = bao.toByteArray();

                encodedString = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                ivImage.setImageBitmap(rotatedBitmap);

            }
        } else if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                try {
                    Uri contentURI = data.getData();
                    //get the Uri for the captured image
                    picUri = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getApplicationContext().getContentResolver().query(contentURI, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);

//                filePath = picUri.getPath();
                    filePath = cursor.getString(columnIndex);
//                    filePath = PathUtil.getPath(GuardPostActivity.this,contentURI);

                    filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);


                /*if (status == 0) {
                    ivPhoto.setVisibility(View.VISIBLE);
                    ivPhoto.setImageURI(picUri);

                } else {
                    ivDocument.setVisibility(View.VISIBLE);
                    ivDocument.setImageURI(picUri);
                }*/

                    Log.d("path", filePath);
                    System.out.println("Image Path : " + picturePath);


                    cursor.close();
                    filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);

                    ext = getFileType(picturePath);

                    String selectedImagePath = picturePath;

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(selectedImagePath, options);
                    final int REQUIRED_SIZE = 500;
                    int scale = 1;
                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                        scale *= 2;
                    options.inSampleSize = scale;
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(getImageOrientation(picturePath));
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bao);
                    byte[] ba = bao.toByteArray();

                    ivImage.setImageBitmap(rotatedBitmap);

                    encodedString = getEncoded64ImageStringFromBitmap(rotatedBitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static String getFileType(String path) {
        String fileType = null;
        fileType = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase();
        return fileType;
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    showCameraGalleryDialog();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }*/
                }
                break;
        }
    }


}
