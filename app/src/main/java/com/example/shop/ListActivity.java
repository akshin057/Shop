package com.example.shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop.databinding.ActivityListBinding;
import com.example.shop.item.Item;
import com.example.shop.models.ListAdapter;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ActivityListBinding binding;
    Bitmap bitmap = null;
    List<Item> itemList = new ArrayList<>();
    public static final int GALLERY_REQUEST = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBarTB);
        getSupportActionBar().setTitle("Магазин продуктов");
        getSupportActionBar().setSubtitle("Версия 1.0");
        getSupportActionBar().setLogo(R.drawable.product);

        binding.circleImageIV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST);
        });

        binding.saveBTN.setOnClickListener(v -> {
            Item item = new Item(binding.nameET.getText().toString(), binding.priceET.getText().toString(),
                    bitmap, binding.descriptionET.getText().toString());

            itemList.add(item);
            ListAdapter adapter = new ListAdapter(this, itemList);
            binding.listViewLV.setAdapter(adapter);

            binding.nameET.getText().clear();
            binding.priceET.getText().clear();
            binding.descriptionET.getText().clear();
            binding.circleImageIV.setImageResource(R.drawable.product);
        });

        binding.listViewLV.setOnItemClickListener((parent, view, position, id) -> {
            Item item = itemList.get(position);

            AlertDialog.Builder builder =  new AlertDialog.Builder(this);
            View window = LayoutInflater.from(this).inflate(R.layout.activity_info, null);
            builder.setView(window);

            ImageView imageView = window.findViewById(R.id.imageIV);
            TextView nameTV = window.findViewById(R.id.nameTV);
            TextView priceTV = window.findViewById(R.id.priceTV);
            TextView descriptionTV = window.findViewById(R.id.descriptionTV);

            imageView.setImageBitmap(item.getImage());
            nameTV.setText(item.getName());
            priceTV.setText(item.getPrice());
            descriptionTV.setText(item.getDescription());

            builder.setNegativeButton("Назад", (dialog, which) -> {
                dialog.dismiss();
            });

            builder.create().show();







        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exitBTN) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            binding.circleImageIV.setImageBitmap(bitmap);
        } else {
            binding.circleImageIV.setImageResource(R.drawable.product);
        }


    }
}