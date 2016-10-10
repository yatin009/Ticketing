package io.webguru.ticketing.FieldAgent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.gcacace.signaturepad.views.SignaturePad;
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
import butterknife.OnClick;
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class AddTicketRequest extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.text_site)
    TextInputEditText editSite;
    @Bind(R.id.text_description)
    TextInputEditText editDescription;
    @Bind(R.id.text_location)
    TextInputEditText editLocation;
    @Bind(R.id.text_priority)
    TextInputEditText editPriority;
    @Bind(R.id.text_scope)
    TextInputEditText editScope;
    @Bind(R.id.text_shop)
    TextInputEditText editShop;
    @Bind(R.id.text_ssrtype)
    TextInputEditText editSSRType;
    @Bind(R.id.signature_pad)
    SignaturePad signaturePad;
    @Bind(R.id.viewFlipper)
    ViewFlipper viewFlipper;
    @Bind(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    private String TAG = "ADDTICKETREQUEST";
    static final int REQUEST_TAKE_PHOTO = 1991;
    static final int MY_PERMISSIONS_REQUEST_CAMERA = 1992;
    private String mCurrentPhotoPath;
    private boolean isSignatureTaken = false;

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
        progressBar.setProgress(20);
//        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takePhoto();
//            }
//        });
//        buttonRequestTicket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                submitTicket();
//            }
//        });
//        seekBarPriority.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
//                if(value>=0 && value<30){
//                    textViewPriorityValue.setText("LOW");
//                    textViewPriorityValue.setTextColor(Color.BLACK);
//                }
//                if(value>=30 && value<70){
//                    textViewPriorityValue.setText("MEDIUM");
//                    textViewPriorityValue.setTextColor(Color.rgb(255,165,0)); // Orange
//                }
//                if(value>=70 && value<=100){
//                    textViewPriorityValue.setText("HIGH");
//                    textViewPriorityValue.setTextColor(Color.RED);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Log.d(TAG, "STARTED SIGNATURE");
            }

            @Override
            public void onSigned() {
                Log.d(TAG, "DONE WITH SIGNATURE");
                isSignatureTaken = true;
            }

            @Override
            public void onClear() {
                Log.d(TAG, "CLEAR SIGNATURE");
                isSignatureTaken = false;
            }
        });
    }

    @OnClick(R.id.clear_signature)
    public void clearSignature(){
        signaturePad.clear();
    }

    @OnClick(R.id.prioriy_next)
    public void priorityNext(){
        progressBar.setProgress(40);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        viewFlipper.showNext();
    }

    @OnClick(R.id.location_previous)
    public void locationPrevious(){
        progressBar.setProgress(20);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        viewFlipper.showPrevious();
    }

    @OnClick(R.id.location_next)
    public void locationNext(){
        progressBar.setProgress(60);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        viewFlipper.showNext();
    }

    @OnClick(R.id.description_previous)
    public void descriptionPrevious(){
        progressBar.setProgress(40);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        viewFlipper.showPrevious();
    }

    @OnClick(R.id.description_next)
    public void descriptionNext(){
        progressBar.setProgress(80);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        viewFlipper.showNext();
    }

    @OnClick(R.id.scope_previous)
    public void scopePrevious(){
        progressBar.setProgress(60);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        viewFlipper.showPrevious();
    }

    @OnClick(R.id.scope_next)
    public void scopeNext(){
        progressBar.setProgress(100);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        viewFlipper.showNext();
    }

    @OnClick(R.id.sign_previous)
    public void signPrevious(){
        progressBar.setProgress(80);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        viewFlipper.showPrevious();
    }

    @OnClick(R.id.request_ticket)
    public void requestTicket(){
        String priority = editPriority.getText().toString();
        if("".equals(priority)){
            editPriority.setError("Invalid Priority");
            return;
        }
        String site = editSite.getText().toString();
        if("".equals(site)){
            editSite.setError("Invalid Site");
            return;
        }
        String shop = editShop.getText().toString();
        if("".equals(shop)){
            editShop.setError("Invalid Shop");
            return;
        }
        String location = editLocation.getText().toString();
        if("".equals(location)){
            editLocation.setError("Invalid Location");
            return;
        }
        String description = editDescription.getText().toString();
        if("".equals(description)){
            editDescription.setError("Invalid Description");
            return;
        }
        String scope = editScope.getText().toString();
        if("".equals(scope)){
            editScope.setError("Invalid Scope");
            return;
        }
        String ssrType = editSSRType.getText().toString();
        if("".equals(ssrType)){
            editSSRType.setError("Invalid SSR Type");
            return;
        }
        fieldAgentData = new FieldAgentData("Pending", GlobalFunctions.getCurrentDateTime(), "", description, priority, location, shop, site, scope, ssrType);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("field_agent_data").child(userInfo.getUserid()).push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/field_agent_data/" + userInfo.getUserid() + "/"+key, fieldAgentData.toMap());
        mDatabase.updateChildren(childUpdates);

        DatabaseReference mDatabaseManager = FirebaseDatabase.getInstance().getReference();
        //TODO replace 1 with call center agent ID.
        String managerKey = mDatabaseManager.child("manager_data").child("1").child("pending").push().getKey();
        ManagerData managerData = new ManagerData(fieldAgentData, userInfo, key);
        Map<String, Object> managerChildUpdates = new HashMap<>();
        //TODO replace 1 with call center agent ID.
        managerChildUpdates.put("/manager_data/" + "1" + "/pending" + "/"+managerKey,managerData.toMap());
        mDatabaseManager.updateChildren(managerChildUpdates);

        Intent intent = new Intent(this, FieldAgentMainActivity.class);
        intent.putExtra("UserInfo",userInfo);
        startActivity(intent);
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
//            imageViewPhoto.setVisibility(View.VISIBLE);
            setPic();
//            imageViewPhoto.setImageBitmap(decodeFile(mCurrentPhotoPath));//setImageBitmap(imageBitmap);//
//            fieldAgentData.setPhotoPath(mCurrentPhotoPath);
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
//        imageViewPhoto.setImageBitmap(bitmap);
//        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(new File(mCurrentPhotoPath)), "image/*");
//                startActivity(intent);
//            }
//        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
