package com.o_bdreldin.dialogfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.o_bdreldin.dialogfragment.helper.Setter;

import java.util.HashMap;
import java.util.Map;

public final class MutableDialogFragment extends BaseDialogFragment {

    public static final String TAG = MutableDialogFragment.class.getSimpleName();

    public static final int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;

    private int layoutRes = 0;
    private Map<Integer, Setter> viewsIdsAndSetters;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return layoutRes == 0
                ? super.onCreateView(inflater, container, savedInstanceState)
                : inflater.inflate(layoutRes, container, false);
    }

    @Override
    protected void setViews(View view) {
        if (viewsIdsAndSetters != null) {
            for (Map.Entry<Integer, Setter> entry : viewsIdsAndSetters.entrySet()) {
                if (entry.getKey() != 0) {
                    if (entry.getValue() != null) {
                        View v = view.findViewById(entry.getKey());
                        entry.getValue().set(v);
                    }
                } else {
                    Log.e(TAG, "id value of 0 is not allowed.");
                }
            }
        }
    }

    @Override
    protected void setListeners() {
    }

    public static class Builder {

        private int layoutRes, width = MATCH_PARENT, height = MATCH_PARENT;
        private Map<Integer, Setter> viewsIdsAndSetters;
        private Boolean cancelable = true;

        public Builder() {
        }

        public Builder layoutRes(int layoutRes) {
            this.layoutRes = layoutRes;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public <T extends View> Builder set(int id, Setter<T> setter) {
            if (id != 0) {
                if (setter != null) {
                    if (viewsIdsAndSetters == null)
                        viewsIdsAndSetters = new HashMap<>();
                    viewsIdsAndSetters.put(id, setter);
                }
            } else {
                Log.e(TAG, "id value of 0 is not allowed.");
            }
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public MutableDialogFragment create() {
            MutableDialogFragment fragment = new MutableDialogFragment();
            fragment.layoutRes = layoutRes;
            fragment.setWidth(width);
            fragment.setHeight(height);
            fragment.setCancelable(cancelable);
            return fragment;
        }

        public void show(FragmentManager fragmentManager, String tag) {
            MutableDialogFragment fragment = new MutableDialogFragment();
            fragment.layoutRes = layoutRes;
            fragment.setWidth(width);
            fragment.setHeight(height);
            fragment.viewsIdsAndSetters = viewsIdsAndSetters;
            fragment.setCancelable(cancelable);
            fragment.show(fragmentManager, tag);
        }
    }
}
