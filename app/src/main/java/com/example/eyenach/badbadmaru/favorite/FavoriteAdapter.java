package com.example.eyenach.badbadmaru.favorite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.eyenach.badbadmaru.R;

public class FavoriteAdapter extends ArrayAdapter<Favorite> {

    List<Favorite> _fav = new ArrayList();
    Context context;
    TextView _menu, _writer;

    public FavoriteAdapter(@NonNull Context context, int resource, @NonNull List<Favorite> _fav) {
        super(context, resource, _fav);
        this.context = context;
        this._fav = _fav;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View _favItem = LayoutInflater.from(context).inflate(R.layout.fragment_favorite_item, parent, false);
//        _img = _favItem.findViewById(R.id.favorite_item_img);
        _menu = _favItem.findViewById(R.id.menu_food_name);
        _writer = _favItem.findViewById(R.id.menu_food_writer_name);

        Favorite _row = _fav.get(position);
//        _img.setText(_row.getImg());
        _menu.setText(_row.getMenu());
        _writer.setText("written by "+_row.getType());

        return _favItem;
    }
}
