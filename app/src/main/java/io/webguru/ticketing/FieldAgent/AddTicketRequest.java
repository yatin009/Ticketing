package io.webguru.ticketing.FieldAgent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class AddTicketRequest extends AppCompatActivity {

    @Bind(R.id.editTextProblem)
    EditText editTextProblem;
    @Bind(R.id.seekBarPriority)
    SeekBar seekBarPriority;
    @Bind(R.id.textViewPriorityValue)
    TextView textViewPriorityValue;
    @Bind(R.id.spinnerFactory)
    Spinner spinnerFactory;
    @Bind(R.id.buttonPhoto)
    Button buttonTakePhoto;
    @Bind(R.id.imageViewPhoto)
    ImageView imageViewPhoto;
    @Bind(R.id.buttonRequestTicket)
    Button buttonRequestTicket;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    static final int REQUEST_TAKE_PHOTO = 1991;
    static final int MY_PERMISSIONS_REQUEST_CAMERA = 1992;
    private String mCurrentPhotoPath;

    private FieldAgentData fieldAgentData;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket_request);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //TODO Handle null case
        Bundle bundle = getIntent().getExtras();
        //Receving UserInfo Object from Intent
        userInfo = (UserInfo) bundle.get("UserInfo");

        fieldAgentData = new FieldAgentData();
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        buttonRequestTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTicket();
            }
        });
        seekBarPriority.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                if(value>=0 && value<30){
                    textViewPriorityValue.setText("LOW");
                    textViewPriorityValue.setTextColor(Color.BLACK);
                }
                if(value>=30 && value<70){
                    textViewPriorityValue.setText("MEDIUM");
                    textViewPriorityValue.setTextColor(Color.rgb(255,165,0)); // Orange
                }
                if(value>=70 && value<=100){
                    textViewPriorityValue.setText("HIGH");
                    textViewPriorityValue.setTextColor(Color.RED);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "io.webguru.ticketing.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
//            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =   getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("IMAGEPATH","path of image mCurrentPhotoPath >>> "+mCurrentPhotoPath);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewPhoto.setVisibility(View.VISIBLE);
            setPic();
//            imageViewPhoto.setImageBitmap(decodeFile(mCurrentPhotoPath));//setImageBitmap(imageBitmap);//
            fieldAgentData.setPhotoPath(mCurrentPhotoPath);
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = 350;//imageViewPhoto.getWidth();
        int targetH = 350;//imageViewPhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageViewPhoto.setImageBitmap(bitmap);
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(mCurrentPhotoPath)), "image/*");
                startActivity(intent);
            }
        });
    }

    private void submitTicket(){
        fieldAgentData.setProblem(editTextProblem.getText().toString());
        fieldAgentData.setPriority(textViewPriorityValue.getText().toString());
        fieldAgentData.setDateTime(GlobalFunctions.getCurrentDateTime());
        fieldAgentData.setLocation(spinnerFactory.getSelectedItem().toString());
        fieldAgentData.setApproved("pending");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("field_agent_data").child(userInfo.getUserid()).push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/field_agent_data/" + userInfo.getUserid() + "/"+key, fieldAgentData.toMap());
        mDatabase.updateChildren(childUpdates);

        Intent intent = new Intent(this, FieldAgentMainActivity.class);
        intent.putExtra("UserInfo",userInfo);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                Intent intent = new Intent(AddTicketRequest.this, FieldAgentMainActivity.class);
                intent.putExtra("UserInfo", userInfo);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
