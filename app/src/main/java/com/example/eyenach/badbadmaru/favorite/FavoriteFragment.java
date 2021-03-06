package com.example.eyenach.badbadmaru.favorite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.eyenach.badbadmaru.R;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {

    /* get db
    FirebaseFirestore _firestore;
    FirebaseAuth _mAuth; */
    ArrayList<Favorite> _fav = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* Get db
        _firestore = FirebaseFirestore.getInstance();
        _mAuth = FirebaseAuth.getInstance(); */

        final ListView _favList = getView().findViewById(R.id.menu_list);
        final FavoriteAdapter _favAdap = new FavoriteAdapter(getActivity(), R.layout.fragment_favorite_item, _fav);

        _favAdap.clear();

        //Just test
        _fav.add(new Favorite("แกงมัสมั่น", "Eye"));
        _fav.add(new Favorite("ผัดหอยลาย", "Eye"));
        _fav.add(new Favorite("ต้มยำกุ้ง", "Eye"));
        _fav.add(new Favorite("แกงมัสมั่น", "Eye"));
        _fav.add(new Favorite("ผัดหอยลาย", "Eye"));
        _fav.add(new Favorite("ต้มยำกุ้ง", "Eye"));

        _favList.setAdapter(_favAdap);

        /* Prepare code to get data from db
        String _uid = mAuth.getCurrentUser().getUid();

        mdb.collection("myfitness").document(_uid).collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    if(doc.get("date") != null && doc.get("weight") != null && doc.get("status") != null){
                        weights.add(new Weight(doc.get("date"), doc.get("weight"), doc.get("status")));

                        ListView _weightList = getView().findViewById(R.id.weight_list);
                        WeightAdapter _weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_item, weights);
                        _weightList.setAdapter(_weightAdapter);

                        //Click item
                        _sleepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                id = _sleepList.getItemIdAtPosition(position);
                                Log.d("SLEEP", "Position = "+id+" _id = "+(id+1));

                                //get & store Bundle
                                Bundle bundle = new Bundle();
                                bundle.putInt("_id", position);

                                //set Bundle


                                getActivity().getSupportFragmentManager()
                                     .beginTransaction()
                                     .replace(R.id.main_view, )
                                     .addToBackStack(null)
                                     .commit();
                                Log.d("SLEEP", "GOTO SLEEP_FORM");
                            }
                        });
                    }
                }
            }
        }); */

    }
}
