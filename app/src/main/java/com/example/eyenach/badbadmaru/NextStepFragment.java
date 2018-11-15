package com.example.eyenach.badbadmaru;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eyenach.badbadmaru.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NextStepFragment extends Fragment{

    FirebaseStorage storage;
    StorageReference storageRef, imgRef;
    private static final int select = 100;
    ProgressDialog progressDialog;
    UploadTask uploadTask;

    TextView _yourUrlImage;
    Button _submitBtn;

    Image image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addstep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        _yourUrlImage = getView().findViewById(R.id.step_link_image);
        _submitBtn = getView().findViewById(R.id.step_submit);

        Bundle bundle = getArguments();
        if(bundle != null){
            image = (Image) bundle.getSerializable("uriImage");
        } else {
            Toast.makeText(getActivity(), "Bundle Null", Toast.LENGTH_SHORT).show();
        }

        _submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFoto(image.getUriImage());
            }
        });

    }



    public void uploadFoto(Uri uriImage){
        Log.d("Upload","Name Image");

        imgRef = storageRef.child("myphotos/"); //+ typeFood + nameFood

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
        });
    }
}
