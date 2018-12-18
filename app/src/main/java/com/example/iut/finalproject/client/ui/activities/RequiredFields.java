package com.example.iut.finalproject.client.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

public class RequiredFields extends AppCompatActivity {
    protected EditText[] requiredFields;

    protected boolean checkRequiredFields() {
        boolean has_errors = false;
        if (requiredFields.length == 0)
            return has_errors;

        for (int i = 0; i < requiredFields.length; i++) {
            if (TextUtils.isEmpty(requiredFields[i].getText())) {
                has_errors = true;
                requiredFields[i].setError("This field is required");
            }
        }

        return has_errors;
    }
}
