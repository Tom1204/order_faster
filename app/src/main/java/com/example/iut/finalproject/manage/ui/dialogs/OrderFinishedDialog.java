package com.example.iut.finalproject.manage.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class OrderFinishedDialog extends Dialog {
    public OrderFinishedDialog(@NonNull Context context) {
        super(context);
    }

    public OrderFinishedDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OrderFinishedDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
