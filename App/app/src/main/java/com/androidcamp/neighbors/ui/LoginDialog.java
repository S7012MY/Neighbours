package com.androidcamp.neighbors.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.androidcamp.neighbors.R;

public class LoginDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        //blah blah blah
        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_sign_up, null);
        // Now use view.FindViewById() to do what you want
        b.setView(view);

        view.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignInHandler) getActivity()).resolveSignInError();
            }
        });
        return b.create();
    }

    public interface SignInHandler {
        void resolveSignInError();
    }
}
