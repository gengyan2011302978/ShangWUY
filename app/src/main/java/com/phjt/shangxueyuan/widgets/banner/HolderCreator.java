package com.phjt.shangxueyuan.widgets.banner;

import android.content.Context;
import android.view.View;

public interface HolderCreator {
    View createView(Context context, int index, Object o);
}
