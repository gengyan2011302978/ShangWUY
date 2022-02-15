package com.phjt.shangxueyuan.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phjt.base.integration.AppManager;
import com.phjt.shangxueyuan.R;

import java.io.Serializable;

/**
 * @author: Roy
 * date:   2020/4/1
 * company: 普华集团
 * description:
 */
public class LoadingViewUtil {
    private static final String LOADING_VIEW = "LOADING_VIEW";
    private static class InnerInstance {
        private static final LoadingViewUtil INSTANCE = new LoadingViewUtil();
    }

    private LoadingViewUtil() {
    }

    public static LoadingViewUtil getInstance() {
        return InnerInstance.INSTANCE;
    }

    private class ViewObj implements Serializable {
        private View view;

        public ViewObj(View view) {
            this.view = view;
        }

        public View getView() {
            return view;
        }
    }

    private synchronized void setLoadingView(boolean isShow) {
        Activity activity = AppManager.getAppManager().getTopActivity();
        if (activity != null) {
            View loadingView = null;
            Serializable extra = activity.getIntent().getSerializableExtra(LOADING_VIEW);
            if (extra != null) {
                ViewObj viewObj = (ViewObj) extra;
                loadingView = viewObj.getView();
            } else {
                ViewGroup parentLayout = activity.getWindow().getDecorView().findViewById(android.R.id.content);

                loadingView = LayoutInflater.from(activity).inflate(R.layout.loading_layout, null);
                parentLayout.addView(loadingView);
                loadingView.setClickable(true);
                activity.getIntent().putExtra(LOADING_VIEW, new ViewObj(loadingView));

            }
            loadingView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }


    public void hide() {
        setLoadingView(false);
    }

    public void show() {
        setLoadingView(true);
    }
}
