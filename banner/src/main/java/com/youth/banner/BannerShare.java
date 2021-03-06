package com.youth.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;
import com.youth.banner.view.BannerViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import static androidx.viewpager.widget.ViewPager.PageTransformer;

public class BannerShare extends FrameLayout implements OnPageChangeListener {
    public String tag = "banner";
    private int mIndicatorMargin = BannerConfig.PADDING_SIZE;
    private int mIndicatorWidth;
    private int mIndicatorPad;
    private int mIndicatorHeight;
    private int mIndicatorSelectedWidth;
    private int mIndicatorSelectedHeight;
    private int indicatorSize;
    private int bannerBackgroundImage;
    private int bannerStyle = BannerConfig.CIRCLE_INDICATOR;
    private int delayTime = BannerConfig.TIME;
    private int scrollTime = BannerConfig.DURATION;
    private boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;
    private boolean isScroll = BannerConfig.IS_SCROLL;
    private int mIndicatorSelectedResId = R.drawable.gray_radius;
    private int mIndicatorUnselectedResId = R.drawable.white_radius;
    private int mLayoutResId = R.layout.bannershare;
    private int titleHeight;
    private int titleBackground;
    private int titleTextColor;
    private int titleTextSize;
    private int startIndex;
    private int count = 0;
    private int currentItem;
    private int gravity = -1;
    private int lastPosition = 1;
    private int scaleType = 1;
    private List<String> titles;
    private List<String> codeList;
    private List imageUrls;
    private List<View> imageViews;
    private List<ImageView> indicatorImages;
    private Context context;
    private BannerViewPager viewPager;
    private TextView bannerTitle, numIndicatorInside, numIndicator;
    private LinearLayout indicator, indicatorInside, titleView;
    private ImageView bannerDefaultImage;
    private ImageView bannerDefaultImagee;
    private ImageView wb_qr_code;
    private ImageLoaderInterface imageLoader;
    private int type;
    private BannerPagerAdapter adapter;
    private OnPageChangeListener mOnPageChangeListener;
    private BannerScroller mScroller;
    private OnBannerClickListener bannerListener;
    private OnBannerListener listener;
    private DisplayMetrics dm;
    private String code;

    private WeakHandler handler = new WeakHandler();

    public BannerShare(Context context) {
        this(context, null);
    }

    public BannerShare(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerShare(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        titles = new ArrayList<>();
        codeList = new ArrayList<>();
        imageUrls = new ArrayList<>();
        imageViews = new ArrayList<>();
        indicatorImages = new ArrayList<>();
        dm = context.getResources().getDisplayMetrics();
        indicatorSize = dm.widthPixels / 80;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        imageViews.clear();
        handleTypedArray(context, attrs);
        View view = LayoutInflater.from(context).inflate(mLayoutResId, this, true);
        bannerDefaultImage = (ImageView) view.findViewById(R.id.bannerDefaultImage);
        bannerDefaultImagee = (ImageView) view.findViewById(R.id.bannerDefaultImagee);
        viewPager = (BannerViewPager) view.findViewById(R.id.bannerViewPager);
        titleView = (LinearLayout) view.findViewById(R.id.titleView);
        indicator = (LinearLayout) view.findViewById(R.id.circleIndicator);
        indicatorInside = (LinearLayout) view.findViewById(R.id.indicatorInside);
        bannerTitle = (TextView) view.findViewById(R.id.bannerTitle);
        numIndicator = (TextView) view.findViewById(R.id.numIndicator);
        numIndicatorInside = (TextView) view.findViewById(R.id.numIndicatorInside);
        bannerDefaultImage.setImageResource(bannerBackgroundImage);
        bannerDefaultImagee.setImageResource(bannerBackgroundImage);
        initViewPagerScroll();
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_width, indicatorSize);
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_height, indicatorSize);
        mIndicatorSelectedWidth = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_selected_width, indicatorSize);
        mIndicatorSelectedHeight = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_selected_height, indicatorSize);
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_margin, BannerConfig.PADDING_SIZE);
        mIndicatorPad = typedArray.getDimensionPixelSize(R.styleable.Banner_indicator_padding, BannerConfig.PADDING_SIZE);
        mIndicatorSelectedResId = typedArray.getResourceId(R.styleable.Banner_indicator_drawable_selected, R.drawable.gray_radius);
        mIndicatorUnselectedResId = typedArray.getResourceId(R.styleable.Banner_indicator_drawable_unselected, R.drawable.white_radius);
        scaleType = typedArray.getInt(R.styleable.Banner_image_scale_type, scaleType);
        delayTime = typedArray.getInt(R.styleable.Banner_delay_time, BannerConfig.TIME);
        scrollTime = typedArray.getInt(R.styleable.Banner_scroll_time, BannerConfig.DURATION);
        isAutoPlay = typedArray.getBoolean(R.styleable.Banner_is_auto_play, BannerConfig.IS_AUTO_PLAY);
        titleBackground = typedArray.getColor(R.styleable.Banner_title_background, BannerConfig.TITLE_BACKGROUND);
        titleHeight = typedArray.getDimensionPixelSize(R.styleable.Banner_title_height, BannerConfig.TITLE_HEIGHT);
        titleTextColor = typedArray.getColor(R.styleable.Banner_title_textcolor, BannerConfig.TITLE_TEXT_COLOR);
        titleTextSize = typedArray.getDimensionPixelSize(R.styleable.Banner_title_textsize, BannerConfig.TITLE_TEXT_SIZE);
        mLayoutResId = typedArray.getResourceId(R.styleable.Banner_banner_layout, mLayoutResId);
        bannerBackgroundImage = typedArray.getResourceId(R.styleable.Banner_banner_default_image, R.drawable.share_by_bg);
        typedArray.recycle();
    }

    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new BannerScroller(viewPager.getContext());
            mScroller.setDuration(scrollTime);
            mField.set(viewPager, mScroller);
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
        }
    }


    public BannerShare isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    public BannerShare setImageLoader(ImageLoaderInterface imageLoader, int type) {
        this.imageLoader = imageLoader;
        this.type = type;
        return this;
    }

    public BannerShare setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    public BannerShare setIndicatorGravity(int type) {
        switch (type) {
            case BannerConfig.LEFT:
                this.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case BannerConfig.CENTER:
                this.gravity = Gravity.CENTER;
                break;
            case BannerConfig.RIGHT:
                this.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        return this;
    }

    public BannerShare setBannerAnimation(Class<? extends PageTransformer> transformer) {
        try {
            setPageTransformer(true, transformer.newInstance());
        } catch (Exception e) {
            Log.e(tag, "Please set the PageTransformer class");
        }
        return this;
    }

    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the adapter when needed.
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     * @return Banner
     */
    public BannerShare setOffscreenPageLimit(int limit) {
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    /**
     * Set a {@link PageTransformer} that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding look and feel.
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     * @return Banner
     */
    public BannerShare setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    public BannerShare setBannerTitles(List<String> titles) {
        this.titles = titles;
        return this;
    }

    public BannerShare setBannerStyle(int bannerStyle) {
        this.bannerStyle = bannerStyle;
        return this;
    }

    public BannerShare setViewPagerIsScroll(boolean isScroll) {
        this.isScroll = isScroll;
        return this;
    }

    public BannerShare setImages(List<?> imageUrls, String code) {
        this.imageUrls.addAll(imageUrls);
        this.code = code;
        this.count = imageUrls.size();
        return this;
    }

    public BannerShare setCodeList(List<String> codeLists) {
        codeList.clear();
        this.codeList.addAll(codeLists);
        return this;
    }


    public BannerShare updateCode(String code) {
        this.code = code;
        return this;
    }

    public void update(List<?> imageUrls, List<String> titles) {
        this.titles.clear();
        this.titles.addAll(titles);
        update(imageUrls);
    }

    public void update(List<?> imageUrls) {
        this.imageUrls.clear();
        this.imageViews.clear();
        this.indicatorImages.clear();
        this.imageUrls.addAll(imageUrls);
        this.count = this.imageUrls.size();
        start();
    }

    public void updateBannerStyle(int bannerStyle) {
        indicator.setVisibility(GONE);
        numIndicator.setVisibility(GONE);
        numIndicatorInside.setVisibility(GONE);
        indicatorInside.setVisibility(GONE);
        bannerTitle.setVisibility(View.GONE);
        titleView.setVisibility(View.GONE);
        this.bannerStyle = bannerStyle;
        start();
    }

    public BannerShare start() {
        setBannerStyleUI();
        if (type == 3) {
            setImageTitleList(imageUrls, titles,codeList);
        } else {
            setImageList(imageUrls);
        }
        setData();
        return this;
    }

    private void setTitleStyleUI() {
        if (titles.size() != imageUrls.size()) {
            throw new RuntimeException("[Banner] --> The number of titles and images is different");
        }
        if (titleBackground != -1) {
            titleView.setBackgroundColor(titleBackground);
        }
        if (titleHeight != -1) {
            titleView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleHeight));
        }
        if (titleTextColor != -1) {
            bannerTitle.setTextColor(titleTextColor);
        }
        if (titleTextSize != -1) {
            bannerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        }
        if (titles != null && titles.size() > 0) {
            bannerTitle.setText(titles.get(0));
            bannerTitle.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.VISIBLE);
        }
    }

    private void setBannerStyleUI() {
        int visibility = count > 1 ? View.VISIBLE : View.GONE;
        switch (bannerStyle) {
            case BannerConfig.CIRCLE_INDICATOR:
                indicator.setVisibility(visibility);
                break;
            case BannerConfig.NUM_INDICATOR:
                numIndicator.setVisibility(visibility);
                break;
            case BannerConfig.NUM_INDICATOR_TITLE:
                numIndicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE:
                indicator.setVisibility(visibility);
                setTitleStyleUI();
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                indicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                break;
        }
    }

    private void initImages() {
        imageViews.clear();
        if (bannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE ||
                bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
            createIndicator();
        } else if (bannerStyle == BannerConfig.NUM_INDICATOR_TITLE) {
            numIndicatorInside.setText("1/" + count);
        } else if (bannerStyle == BannerConfig.NUM_INDICATOR) {
            numIndicator.setText("1/" + count);
        }
    }

    ImageView imageView = null;
    ImageView imageView1 = null;
    View relativeLayout = null;

    /**
     * ?????????
     *
     * @param imagesUrl
     * @param titles
     */
    private void setImageTitleList(List<?> imagesUrl, List<String> titles,List<String>  codeList) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            bannerDefaultImage.setVisibility(VISIBLE);
            Log.e(tag, "The image data set is empty.");
            return;
        }
        bannerDefaultImage.setVisibility(GONE);
        initImages();
        for (int i = 0; i <= count + 1; i++) {
            Object url = null;
            String tiele = "";
            String mCode = "";
            if (i == 0) {
                url = imagesUrl.get(count - 1);
                tiele = titles.get(count - 1);
                mCode = codeList.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
                tiele = titles.get(0);
                mCode = codeList.get(0);
            } else {
                url = imagesUrl.get(i - 1);
                tiele = titles.get(i - 1);
                mCode = codeList.get(i - 1);
            }

            if (imageLoader != null) {
                if (type == 3) {
                    relativeLayout = View.inflate(context, R.layout.bannercode, null);
                    imageView = relativeLayout.findViewById(R.id.bannerDefaultImage);
                    wb_qr_code = relativeLayout.findViewById(R.id.wb_qr_code);
                    imageView1 = (ImageView) imageLoader.createImageView(context, url);
                } else {
                    imageView = (ImageView) imageLoader.createImageView(context, url);
                }
            }
            if (imageView == null) {
                if (type == 3) {
                    imageView = relativeLayout.findViewById(R.id.bannerDefaultImage);
                    wb_qr_code = relativeLayout.findViewById(R.id.wb_qr_code);
                } else {
                    imageView = new ImageView(context);
                }

            }
            if (imageLoader != null) {
                if (type == 3) {
                    imageLoader.displayImage(context, url, mCode, tiele, imageView, i);
                } else {
                    imageLoader.displayImage(context, url, imageView);
                }
            } else {
                Log.e(tag, "Please set images loader.");
            }

            if (type == 3) {
                setScaleType(imageView1);
                imageView1.setBackgroundResource(R.drawable.black_background);
                imageViews.add(imageView);
            } else {
                setScaleType(imageView);
                imageViews.add(imageView);
            }
        }
    }

    private void setImageList(List<?> imagesUrl) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            bannerDefaultImage.setVisibility(VISIBLE);
            Log.e(tag, "The image data set is empty.");
            return;
        }
        bannerDefaultImage.setVisibility(GONE);
        initImages();
//        if (type == 2) {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            params.setMargins(0, 0, 0, 100);
//            viewPager.setLayoutParams(params);
//        } else {
//
//        }

        for (int i = 0; i <= count + 1; i++) {
            Object url = null;
            if (i == 0) {
                url = imagesUrl.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }

            if (imageLoader != null) {
                if (type == 2) {
                    relativeLayout = View.inflate(context, R.layout.bannercode, null);
                    imageView = relativeLayout.findViewById(R.id.bannerDefaultImage);
                    wb_qr_code = relativeLayout.findViewById(R.id.wb_qr_code);
                    imageView1 = (ImageView) imageLoader.createImageView(context, url);
                } else {
                    imageView = (ImageView) imageLoader.createImageView(context, url);
                }
            }
            if (imageView == null) {
                if (type == 2) {
                    imageView = relativeLayout.findViewById(R.id.bannerDefaultImage);
                    wb_qr_code = relativeLayout.findViewById(R.id.wb_qr_code);
                } else {
                    imageView = new ImageView(context);
                }

            }
            if (imageLoader != null) {
                if (type == 2) {
                    imageLoader.displayImage(context, url, code, imageView, i);
                } else {
                    imageLoader.displayImage(context, url, imageView);
                }

            } else {
                Log.e(tag, "Please set images loader.");
            }

            if (type == 2) {
                setScaleType(imageView1);
                imageView1.setBackgroundResource(R.drawable.black_background);
                imageViews.add(imageView);
            } else {
                setScaleType(imageView);
                imageViews.add(imageView);
            }

        }
    }

    private void setScaleType(View imageView) {
        if (imageView instanceof ImageView) {
            ImageView view = ((ImageView) imageView);
            switch (scaleType) {
                case 0:
                    view.setScaleType(ScaleType.CENTER);
                    break;
                case 1:
                    view.setScaleType(ScaleType.CENTER_CROP);
                    break;
                case 2:
                    view.setScaleType(ScaleType.CENTER_INSIDE);
                    break;
                case 3:
                    view.setScaleType(ScaleType.FIT_CENTER);
                    break;
                case 4:
                    view.setScaleType(ScaleType.FIT_END);
                    break;
                case 5:
                    view.setScaleType(ScaleType.FIT_START);
                    break;
                case 6:
                    view.setScaleType(ScaleType.FIT_XY);
                    break;
                case 7:
                    view.setScaleType(ScaleType.MATRIX);
                    break;
            }

        }
    }

    private void createIndicator() {
        indicatorImages.clear();
        indicator.removeAllViews();
        indicatorInside.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, mIndicatorPad);
            LinearLayout.LayoutParams params;
            if (i == 0) {
                params = new LinearLayout.LayoutParams(mIndicatorSelectedWidth, mIndicatorSelectedHeight);
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            indicatorImages.add(imageView);
            if (bannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                    bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE) {
                indicator.addView(imageView, params);
            } else if (bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {
                indicatorInside.addView(imageView, params);
            }
        }
    }


    private void setData() {
        if (startIndex != 0) {
            currentItem = startIndex;
        } else {
            currentItem = 1;
        }
        if (adapter == null) {
            adapter = new BannerPagerAdapter();
            viewPager.addOnPageChangeListener(this);
            viewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(currentItem);
        if (gravity != -1) {
            indicator.setGravity(gravity);
        }
        if (isScroll && count > 1) {
            viewPager.setScrollable(true);
        } else {
            viewPager.setScrollable(false);
        }
        if (isAutoPlay) {
            startAutoPlay();
        }
    }


    public void startAutoPlay() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, delayTime);
    }

    public void stopAutoPlay() {
        handler.removeCallbacks(task);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (count > 1 && isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
//                Log.i(tag, "curr:" + currentItem + " count:" + count);
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, delayTime);
                }
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * ?????????????????????
     *
     * @param position
     * @return ?????????0??????
     */
    public int toRealPosition(int position) {
        int realPosition = 0;
        if (count != 0) {
            realPosition = (position - 1) % count;
        }
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            if (bannerListener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(tag, "??????????????????????????????????????????????????????1?????????" +
                                "????????????????????????setOnBannerListener????????????0????????????");
                        bannerListener.OnBannerClick(position);
                    }
                });
            }
            if (listener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnBannerClick(toRealPosition(position));
                    }
                });
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
//        Log.i(tag,"currentItem: "+currentItem);
        switch (state) {
            case 0://No operation
                if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case 1://start Sliding
                if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                }
                break;
            case 2://end Sliding
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(toRealPosition(position), positionOffset, positionOffsetPixels);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(toRealPosition(position));
        }
        if (bannerStyle == BannerConfig.CIRCLE_INDICATOR ||
                bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE ||
                bannerStyle == BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE) {

            LinearLayout.LayoutParams
                    Selectedparams = new LinearLayout.LayoutParams(mIndicatorSelectedWidth, mIndicatorSelectedHeight);

            Selectedparams.leftMargin = mIndicatorMargin;
            Selectedparams.rightMargin = mIndicatorMargin;
            LinearLayout.LayoutParams
                    Unselectedparams = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            Unselectedparams.leftMargin = mIndicatorMargin;
            Unselectedparams.rightMargin = mIndicatorMargin;
            indicatorImages.get((lastPosition - 1 + count) % count).setImageResource(mIndicatorUnselectedResId);
            indicatorImages.get((lastPosition - 1 + count) % count).setLayoutParams(Unselectedparams);
            indicatorImages.get((position - 1 + count) % count).setImageResource(mIndicatorSelectedResId);
            indicatorImages.get((position - 1 + count) % count).setLayoutParams(Selectedparams);
            lastPosition = position;

            if (type == 2 || type == 3) {
                if (imageUrls.size() == 1) {
                    imageViews.get(position).setPadding(100, 0, 100, 0);
                } else {
                    imageViews.get(position).setPadding(100, 0, 100, 0);
                    if (position == imageViews.size() - 1) {
                        imageViews.get(0).setPadding(-100, 0, 100, 0);
                    } else if (position > 0) {
                        imageViews.get(position - 1).setPadding(100, 0, -100, 0);
                        if (position + 1 < imageViews.size()) {
                            imageViews.get(position + 1).setPadding(-100, 0, 100, 0);
                        }
                    }
                }

            }
            System.out.println(position + "_________" + adapter.getItemPosition(0) + "_______" + imageView.canScrollVertically(position) + "_____" + imageView.canScrollVertically(1));
        }
        if (position == 0) {
            position = count;
        }
        if (position > count) {
            position = 1;
        }
        switch (bannerStyle) {
            case BannerConfig.CIRCLE_INDICATOR:
                break;
            case BannerConfig.NUM_INDICATOR:
                numIndicator.setText(position + "/" + count);
                break;
            case BannerConfig.NUM_INDICATOR_TITLE:
                numIndicatorInside.setText(position + "/" + count);
                bannerTitle.setText(titles.get(position - 1));
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE:
                bannerTitle.setText(titles.get(position - 1));
                break;
            case BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE:
                bannerTitle.setText(titles.get(position - 1));
                break;
            default:
                break;
        }

    }

    @Deprecated
    public BannerShare setOnBannerClickListener(OnBannerClickListener listener) {
        this.bannerListener = listener;
        return this;
    }

    /**
     * ???????????????????????????????????????????????????1???????????????????????????????????????
     *
     * @param listener
     * @return
     */
    public BannerShare setOnBannerListener(OnBannerListener listener) {
        this.listener = listener;
        return this;
    }

    public BannerShare setStartIndex(int index) {
        this.startIndex = index;
        return this;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public void releaseBanner() {
        handler.removeCallbacksAndMessages(null);
    }
}
