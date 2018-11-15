package com.example.eyenach.badbadmaru.add_recipe;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.eyenach.badbadmaru.Model.Menu;
import com.example.eyenach.badbadmaru.Model.Mymenu;
import com.example.eyenach.badbadmaru.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class AddStepFragment extends Fragment {

    StorageReference mStorageRef;

    FirebaseAuth mAuth;
    FirebaseFirestore myDB;

    SQLiteDatabase mySQL;

    String _imgStr, _nameStr, _descStr, _typeStr, _timeStr, _ingStr, uidUser, stepStr, linkStr, _writer;
    Button _submitBtn;

    Menu menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addstep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get UID
        mAuth = FirebaseAuth.getInstance();
        uidUser = mAuth.getUid();

        //open firebase
        myDB = FirebaseFirestore.getInstance();

        //open firebaseStorage
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //query data
        mySQL = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        Cursor myCursor = mySQL.rawQuery("SELECT * FROM menu", null);

        while (myCursor.moveToNext()) {

            _nameStr = myCursor.getString(1);
            _descStr = myCursor.getString(2);
            _typeStr = myCursor.getString(3);
            _timeStr = myCursor.getString(4);
            _ingStr = myCursor.getString(5);
            _imgStr = myCursor.getString(6);

            //Writer's name
            getWriter(uidUser);

            initSubmitBtn();
        }
    }

    void initSubmitBtn() {
        _submitBtn = getView().findViewById(R.id.step_submit);
        _submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText step = getView().findViewById(R.id.step);
                EditText link = getView().findViewById(R.id.step_link);

                //Step info
                stepStr = step.getText().toString();
                linkStr = link.getText().toString();

                //Set data to Mymenu
//                setMyMenu();

                Log.d("ADD RECIPE", _nameStr + _descStr
                        + _typeStr + _timeStr + _ingStr + _imgStr + _writer);

                Uri file = Uri.fromFile(new File(_imgStr));
/*                StorageReference riversRef = storageRef.child("images/rivers.jpg");

                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                String  downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                                Toast.makeText(getActivity(), downloadUrl, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                            }
                        }); */
            }
        });
    }

    ////Writer's name
    void getWriter(final String uidUser) {
        myDB.collection("User")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.get("Uid").equals(uidUser)) {
                                _writer = doc.get("displayName").toString();
                                Log.d("ADD STEP", _writer);
                            }
                        }
                    }
                });
    }

    //Set data to Mymenu
    void setMyMenu(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss");
        String date = sdf.format(Calendar.getInstance().getTime());

        Mymenu myMenu = new Mymenu(_nameStr, _typeStr, date.split("_")[0], _writer);

        myDB.collection("Mymenu")
                .document(uidUser)
                .collection("menu")
                .document(date)
                .set(myMenu)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD STEP", "insert MyMenu");
                        //Set data to Menu
                        setMenu();
                        Log.d("ADD STEP", "GOTO insert Menu");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ADD STEP", "FAIL");
            }
        });
    }

    //Set data to Menu
    void setMenu(){

        menu = new Menu(_nameStr, _descStr, _typeStr, _timeStr, _ingStr, _imgStr, _writer);

        myDB.collection("Menu")
                .document(_typeStr)
                .collection(_nameStr)
                .document(_nameStr)
                .set(menu)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "SAVE", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new AddMenuFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
