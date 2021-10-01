package com.molchanov.myapplication;


import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CustomDialog extends AppCompatDialogFragment {
    private String name;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String name = getArguments().getString("exName");
        String surname = getArguments().getString("exSurname");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Приветствие")
                .setMessage("Доброе утро, "+name+" "+surname)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}