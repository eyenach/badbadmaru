package com.example.eyenach.badbadmaru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eyenach.badbadmaru.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;

public class UploadImage extends Fragment {

    FirebaseStorage storage;
    StorageReference storageRef, imgRef;
    private static final int select = 100;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    Uri uriImage;

    EditText _nameImage;
    ImageView _imageView;
    Button _pickImage, _nextStep;
    TextView _yourUrlImage;

    String _nameImageStr;

    Image image = new Image();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_imgview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        _pickImage = getView().findViewById(R.id.imvPhoto_choose);
        _nextStep = getView().findViewById(R.id.imvPhoto_next);
        _yourUrlImage = getView().findViewById(R.id.imvPhoto_url);

        _pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    public void chooseImage(){
        Intent photoIntent = new Intent(Intent.ACTION_PICK);
        File _picDirect = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String _picPath = _picDirect.getPath();
        Log.d("Upload", _picPath);

        photoIntent.setType("image/*");
        startActivityForResult(photoIntent, select);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case select:
                if(resultCode == RESULT_OK){
                    uriImage = data.getData();

                    image.setUriImage(uriImage);

                    try{

                        Bitmap _bitmap = BitmapFactory.decodeStream(
                                getActivity()
                                        .getContentResolver()
                                        .openInputStream(uriImage)
                        );

//                        _imageView = getView().findViewById(R.id.imvPhoto);
//                        _imageView.setImageBitmap(_bitmap);

                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "File not Found", Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        e.printStackTrace();
                        Log.d("Upload", "File too large");
                    }

                    _nextStep.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            _nameImage = getActivity().findViewById(R.id.imvPhoto_name);
                            _nameImageStr = _nameImage.getText().toString();

                            //create Bundle
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("uriImage", image);

                            NextStepFragment obj = new NextStepFragment();
                            obj.setArguments(bundle);

                            Log.d("Upload", "GOTO NEXT");

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, obj)
                                    .commit();
                        }
                    });
                }
        }
    }

    public void uploadFoto(){
        Log.d("Upload","Name Image : "+ _nameImageStr);

/*        imgRef = storageRef.child("myphotos/");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);

        uploadTask = imgRef.putFile(uriImage);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.incrementProgressBy((int) progress);
            }
        });

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                String urlImage = downloadUrl.toString();
                progressDialog.dismiss();

                _yourUrlImage.setText("Your Download URL : "+urlImage);
            }
        }); */
    }
}
