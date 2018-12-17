package com.example.iut.finalproject.models;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ErrorHandler {
    static public String getErrors(String errorBody) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(errorBody);
        JsonObject jsonObject = element.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        StringBuilder builder = new StringBuilder();
        Gson gson = new Gson();
        int j = 0;
        for (Map.Entry<String, JsonElement> entry : entries) {
            TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {
            };
            List<String> errors = gson.fromJson(entry.getValue(), typeToken.getType());
            for (int i = 0; i < errors.size(); i++) {
                builder.append(errors.get(i));
                if (i != errors.size() - 1) {
                    builder.append("\n");
                }
            }
            if (j != entries.size() - 1) {
                builder.append("\n");
            }
            j++;
        }
        return builder.toString();
    }

    static public Snackbar getSnackbarError(View view, String error, int length) {
        return Snackbar.make(view, error, length);
    }

    static public Snackbar getSnackbarError(View view, String error) {
        return Snackbar.make(view, error, Snackbar.LENGTH_SHORT);
    }
}
