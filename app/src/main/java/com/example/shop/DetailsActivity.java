package com.example.shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop.databinding.ActivityDetailsBinding;
import com.example.shop.item.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;
    public static final int GALLERY_REQUEST = 333;
    Bitmap bitmap;
    private Boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolBarTB);
        getSupportActionBar().setTitle("Магазин продуктов");
        getSupportActionBar().setSubtitle("Версия 1.0");
        getSupportActionBar().setLogo(R.drawable.product);

        Item item = getIntent().getParcelableExtra("item");
        List<Item> itemList = (List<Item>) getIntent().getSerializableExtra("list");
        check = getIntent().getBooleanExtra("check", true);
        Integer position = getIntent().getIntExtra("position", 0);

        binding.imageEditIV.setImageBitmap(item.getImage());
        binding.nameEditET.setText(item.getName());
        binding.priceEditET.setText(item.getPrice());
        binding.descriptionEditET.setText(item.getDescription());

        binding.changeBTN.setOnClickListener(v -> {
            Item item1 = new Item(binding.nameEditET.getText().toString(), binding.priceEditET.getText().toString(),
                    bitmap, binding.descriptionEditET.getText().toString());

            List<Item> list = itemList;
            if (position != null) {
                list.set(position, item1);
            }

            check = false;

            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("list", (ArrayList<Item>) list);
            intent.putExtra("check", check);
            startActivity(intent);
            finish();

        });


        binding.imageEditIV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST);
        });


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

            binding.imageEditIV.setImageBitmap(bitmap);
        } else {
            binding.imageEditIV.setImageResource(R.drawable.product);
        }

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
}