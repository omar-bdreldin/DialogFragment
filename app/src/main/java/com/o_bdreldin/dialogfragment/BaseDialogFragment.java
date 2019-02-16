package com.o_bdreldin.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialogFragment extends DialogFragment {

    private static final String TAG = BaseDialogFragment.class.getSimpleName();

    private int width = WindowManager.LayoutParams.MATCH_PARENT, height = WindowManager.LayoutParams.MATCH_PARENT;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, 0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setViews(view);
        setListeners();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(isCancelable());
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setLayout(width, height);
    }

    protected abstract void setViews(View view);

    protected abstract void setListeners();

}
