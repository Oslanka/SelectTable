package com.cloud.lashou.widget.banner.loader;

import android.content.Context;
import android.view.View;

import java.io.Serializable;


public interface ImageLoaderInterface<T extends View> extends Serializable {

    void displayImage(Context context, Object path, T imageView, int positon);

    T createImageView(Context context);
}
