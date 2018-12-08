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

    final  static int IMG_GALLERY_REQUEST = 1;
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

        _imgBtn = getView().findViewById(R.id.imvPhoto_choose);
        _imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGalleryClick(_imgBtn);
            }
        });
    }

    public void onGalleryClick(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);

        File _picDirect = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String _picPath = _picDirect.getPath();

        Uri uri = Uri.parse(_picPath);

        intent.setDataAndType(uri, "image/*");

        startActivityForResult(intent, IMG_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == getActivity().RESULT_OK){

            if(requestCode == IMG_GALLERY_REQUEST){
                Uri imgUri = data.getData();

                try{
                    Bitmap _bitmap = BitmapFactory.decodeStream(
                            getActivity()
                                    .getContentResolver()
                                    .openInputStream(imgUri)
                    );

                    //scale image from device
                    Bitmap _bitmapScale = Bitmap.createScaledBitmap(_bitmap, 500, 500, true);

                    imageView = getView().findViewById(R.id.imvPhoto);
                    imageView.setImageBitmap(_bitmapScale);

                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "File not Found", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
