package com.example.shop.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop.R;
import com.example.shop.item.Item;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Item> {

    public ListAdapter(@NonNull Context context, List<Item> itemList) {
        super(context, R.layout.list_item, itemList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Item item = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            ImageView imageView = view.findViewById(R.id.imageIV);
            TextView nameTV = view.findViewById(R.id.nameTV);
            TextView priceTV = view.findViewById(R.id.priceTV);


            imageView.setImageBitmap(item.getImage());
            nameTV.setText(item.getName());
            priceTV.setText(item.getPrice());

        }

        return view;
    }
}
