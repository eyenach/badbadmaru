package com.example.eyenach.badbadmaru;

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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class ImgViewFragment extends Fragment{

    ImageView imageView;
    Button _imgBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_imgview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        photoController();
    }

    public void photoController(){
        _imgBtn = getView().findViewById(R.id.imvPhoto_choose);
        _imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                //Image's Path
                File _picDirect = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String _picPath = _picDirect.getPath();
                Log.d("IMAGE", _picPath);

                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "CHOOSE APP"), 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK){
            try {
                Uri uri = data.getData();

                Bitmap bitmap = BitmapFactory.decodeStream(
                        getActivity()
                                .getContentResolver()
                                .openInputStream(uri)
                );
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 800,600, true);

                imageView = getView().findViewById(R.id.imvPhoto);
                imageView.setImageBitmap(bitmap1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
