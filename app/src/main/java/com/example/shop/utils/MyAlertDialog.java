package com.example.shop.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shop.item.Item;

public class MyAlertDialog extends DialogFragment {
    Removable removable;
    Updatable updatable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Removable) {
            removable = (Removable) context;
        }

        if (context instanceof Updatable) {
            updatable = (Updatable) context;
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Item item = (Item) requireArguments().getParcelable("item");
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
        return dialog.setTitle("Внимание!!!")
                .setMessage("Действия!!!")
                .setPositiveButton("Удалить", (dialog1, which) -> {
                    if (removable != null) {
                        removable.remove(item);
                    }
                })
                .setNeutralButton("Редактировать", (dialog12, which) -> {
                    if (updatable != null){
                        updatable.update(item);
                    }
                })
                .setNegativeButton("Отмена", null).create();
    }
}
