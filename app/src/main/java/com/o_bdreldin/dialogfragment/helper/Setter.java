package com.o_bdreldin.dialogfragment.helper;

import android.support.v4.app.DialogFragment;
import android.view.View;

public interface Setter<T extends View> {
    void set(T view, DialogFragment dialogFragment);
}
