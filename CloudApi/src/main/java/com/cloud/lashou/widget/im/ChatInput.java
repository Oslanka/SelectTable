/*
package com.cloud.lashou.widget.im;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.lashou.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cloud.lashou.widget.im.ChatInput.InputMode.MORE;

*/
/**
 * 聊天界面输入控件
 *//*

public class ChatInput extends RelativeLayout implements TextWatcher, View.OnClickListener,
        ServeFragment.ServeCallBack {

    private static final String TAG = "ChatInput";


    private ImageButton btnAdd, btnSend, btnVoice, btnKeyboard, btnEmotion;
    private EditText editText;
    private boolean isSendVisible, isHoldVoiceBtn, isEmoticonReady;
    private InputMode inputMode = InputMode.NONE;
    private ChatView chatView;
    private LinearLayout morePanel, textPanel;
    private TextView voicePanel;
    private LinearLayout emoticonPanel;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private ViewPager pager;
    private static final String[] CHANNELS = new String[]{"热门"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<Fragment> fragments;
    private SetTab setTab;


    public ChatInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.chat_input, this);
        initView();
    }

    private void initView() {
        textPanel = (LinearLayout) findViewById(R.id.text_panel);
        btnAdd = (ImageButton) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        btnSend = (ImageButton) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        btnVoice = (ImageButton) findViewById(R.id.btn_voice);
        btnVoice.setOnClickListener(this);
        btnEmotion = (ImageButton) findViewById(R.id.btnEmoticon);
        btnEmotion.setOnClickListener(this);
        emoticonPanel = (LinearLayout) findViewById(R.id.emoticonPanel);
        morePanel = (LinearLayout) findViewById(R.id.morePanel);


//        TextView tt = (TextView) findViewById(R.id.tt);
//        tt.setOnClickListener(this);
//        LinearLayout BtnImage = (LinearLayout) findViewById(R.id.btn_photo);
//        BtnImage.setOnClickListener(this);
//        LinearLayout BtnPhoto = (LinearLayout) findViewById(R.id.btn_image);
//        BtnPhoto.setOnClickListener(this);
//        LinearLayout btnVideo = (LinearLayout) findViewById(R.id.btn_video);
//        btnVideo.setOnClickListener(this);
//        LinearLayout btnFile = (LinearLayout) findViewById(R.id.btn_file);
//        btnFile.setOnClickListener(this);
        setSendBtn();
        btnKeyboard = (ImageButton) findViewById(R.id.btn_keyboard);
        btnKeyboard.setOnClickListener(this);
        voicePanel = (TextView) findViewById(R.id.voice_panel);
        voicePanel.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isHoldVoiceBtn = true;
                        updateVoiceView();
                        break;
                    case MotionEvent.ACTION_UP:
                        isHoldVoiceBtn = false;
                        updateVoiceView();
                        break;
                }
                return true;
            }
        });
        editText = (EditText) findViewById(R.id.input);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    updateView(InputMode.TEXT);
                }
            }
        });
        isSendVisible = editText.getText().length() != 0;

//        final ArrayList<String> arr = new ArrayList<>();
//        arr.add("常用");
//        arr.add("生活");
//        arr.add("美食");
//        ViewPager serve_pager = (ViewPager) findViewById(R.id.serve_pager);
//        serve_pager.setOffscreenPageLimit(arr.size()-1);
//        serve_pager.setAdapter(new ServePagerAdapter2(arr,getContext()));
//
//        MagicIndicator indicator = (MagicIndicator) findViewById(R.id.indicator);
//        indicator.setBackgroundColor(Color.WHITE);
//        CommonNavigator commonNavigator = new CommonNavigator(getContext());
//        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
//            @Override
//            public int getCount() {
//                return arr.size();
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, int i) {
//                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
//                simplePagerTitleView.setNormalColor(R.color.im_title_un);
//                simplePagerTitleView.setSelectedColor(R.color.im_title);
//                simplePagerTitleView.setText(arr.get(i));
//                return null;
//            }
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
//                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
//                linePagerIndicator.setColors(R.color.im_title);
//                return linePagerIndicator;
//            }
//        });

    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
    }

    private void updateView(InputMode mode) {
        if (mode == inputMode) return;
        leavingCurrentState();
        switch (inputMode = mode) {
            case MORE:
                setTab.tabCallBack(false);
                chatView.lastMsg(MORE);
                break;
            case TEXT:
                if (editText.requestFocus()) {
                    setTab.tabCallBack(true);
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
                break;
            case VOICE:
                voicePanel.setVisibility(VISIBLE);
                textPanel.setVisibility(GONE);
                btnVoice.setVisibility(GONE);
                btnKeyboard.setVisibility(VISIBLE);
                break;
            case EMOTICON:
                if (!isEmoticonReady) {
                    prepareEmoticon();
                }
                emoticonPanel.setVisibility(VISIBLE);
                break;
        }
    }


    private void leavingCurrentState() {
        switch (inputMode) {
            case TEXT:
                try {
                    View view = ((Activity) getContext()).getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    editText.clearFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MORE:
                morePanel.setVisibility(GONE);
                break;
            case VOICE:
                voicePanel.setVisibility(GONE);
                textPanel.setVisibility(VISIBLE);
                btnVoice.setVisibility(VISIBLE);
                btnKeyboard.setVisibility(GONE);
                break;
            case EMOTICON:
                emoticonPanel.setVisibility(GONE);
        }

    }


    private void updateVoiceView() {
        if (isHoldVoiceBtn) {
            voicePanel.setText(getResources().getString(R.string.chat_release_send));
//            voicePanel.setBackground(getResources().getDrawable(R.drawable.btn_voice_pressed));
            voicePanel.setBackgroundResource(R.drawable.btn_voice_pressed);
            chatView.startSendVoice();
        } else {
            voicePanel.setText(getResources().getString(R.string.chat_press_talk));
//            voicePanel.setBackground(getResources().getDrawable(R.drawable.btn_voice_normal));
            voicePanel.setBackgroundResource(R.drawable.btn_voice_normal);
            chatView.endSendVoice();
        }
    }

    public void setTabCallBack(SetTab tabCallBack) {
        setTab = tabCallBack;
    }

    public void setTabState(boolean imState) {
        if (!imState && null != morePanel) {
            morePanel.setVisibility(VISIBLE);
        }
    }


    */
/**
     * 软键盘弹起逻辑
     *//*

    public interface SetTab {
        void tabCallBack(boolean state);
    }

    */
/**
     * 关联聊天界面逻辑
     *//*

    public void setChatView(ChatView chatView) {
        this.chatView = chatView;
    }

    */
/**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     *//*

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    */
/**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     *//*

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isSendVisible = s != null && s.length() > 0;
        setSendBtn();
        if (isSendVisible) {
            if (chatView != null)
                chatView.sending();
        }
    }

    */
/**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     *//*

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void setSendBtn() {
        if (isSendVisible) {
            btnAdd.setVisibility(GONE);
            btnSend.setVisibility(VISIBLE);
        } else {
            btnAdd.setVisibility(VISIBLE);
            btnSend.setVisibility(GONE);
        }
    }

    private void prepareEmoticon() {
        if (emoticonPanel == null) return;
        for (int i = 0; i < 5; ++i) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
            for (int j = 0; j < 7; ++j) {

                try {
                    AssetManager am = getContext().getAssets();
                    final int index = 7 * i + j;
                    InputStream is = am.open(String.format("emoticon/%d.gif", index));
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Matrix matrix = new Matrix();
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    matrix.postScale(3.5f, 3.5f);
                    final Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            width, height, matrix, true);
                    ImageView image = new ImageView(getContext());
                    image.setImageBitmap(resizedBitmap);
                    image.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
                    linearLayout.addView(image);
                    image.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String content = String.valueOf(index);
                            SpannableString str = new SpannableString(String.valueOf(index));
                            ImageSpan span = new ImageSpan(getContext(), resizedBitmap, ImageSpan.ALIGN_BASELINE);
                            str.setSpan(span, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            editText.append(str);
                        }
                    });
                    is.close();
                } catch (IOException e) {

                }

            }
            emoticonPanel.addView(linearLayout);
        }
        isEmoticonReady = true;
    }

    */
/**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     *//*

    @Override
    public void onClick(View v) {
        Activity activity = (Activity) getContext();
        int id = v.getId();
        if (id == R.id.btn_send) {
            chatView.sendText();
        }
        if (id == R.id.btn_add) {
            updateView(inputMode == MORE ? InputMode.TEXT : MORE);
        }
//        if (id == R.id.btn_photo){
//            if(activity!=null && requestCamera(activity)){
//                chatView.sendPhoto();
//            }
//        }
//        if (id == R.id.btn_image){
//            if(activity!=null && requestStorage(activity)){
//                chatView.sendImage();
//            }
//        }
        if (id == R.id.btn_voice) {
            if (activity != null && requestAudio(activity)) {
                updateView(InputMode.VOICE);
            }
        }
        if (id == R.id.btn_keyboard) {
            updateView(InputMode.TEXT);
        }
//        if (id == R.id.btn_video){
//            if (getContext() instanceof FragmentActivity){
//                FragmentActivity fragmentActivity = (FragmentActivity) getContext();
//                if (requestVideo(fragmentActivity)){
//                    VideoInputDialog.show(fragmentActivity.getSupportFragmentManager());
//                }
//            }
//        }
        if (id == R.id.btnEmoticon) {
            updateView(inputMode == InputMode.EMOTICON ? InputMode.TEXT : InputMode.EMOTICON);
        }
//        if (id == R.id.btn_file){
//            chatView.sendFile();
////        }
//        if (id == R.id.tt){
//            chatView.sendCustom();
//            chatView.sending();
//        }
    }


    */
/**
     * 获取输入框文字
     *//*

    public Editable getText() {
        return editText.getText();
    }

    */
/**
     * 设置服务消息类容
     *//*



    */
/**
     * 设置输入框文字
     *//*

    public void setText(String text) {
        editText.setText(text);
    }

    */
/**
     *
     *//*


    */
/**
     * 设置输入模式
     *//*

    public void setInputMode(InputMode mode) {
        updateView(mode);
    }

    */
/**
     * 服务内容相关
     *
     * @param fragmentManager
     *//*

    public void initPager(FragmentManager fragmentManager) {
        pager = (ViewPager) findViewById(R.id.serve_pager);
        pager.setOffscreenPageLimit(mDataList.size() - 1);
        fragments = new ArrayList<Fragment>();
        ServeFragment serveFragment = ServeFragment.newInstance(1);
        serveFragment.setCallBack(this);
        fragments.add(serveFragment);
        ServeAdapter myPager = new ServeAdapter(fragmentManager, fragments);
//        ServeAdapter2 myPager = new ServeAdapter2(mDataList,getContext());
        pager.setAdapter(myPager);
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.indicator);
        magicIndicator.setBackgroundResource(R.color.serve_bg);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9fb3"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#8362d4"));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.parseColor("#8362d4"));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, pager);
    }

    @Override
    public void contentCallBack(String content) {
        chatView.sendServe(content);
    }


    public enum InputMode {
        TEXT,
        VOICE,
        EMOTICON,
        MORE,
        VIDEO,
        NONE,
    }

    private boolean requestVideo(Activity activity) {
        if (afterM()) {
            final List<String> permissionsList = new ArrayList<>();
            if ((activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.CAMERA);
            if ((activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.RECORD_AUDIO);
            if (permissionsList.size() != 0) {
                activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestCamera(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestAudio(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestStorage(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean afterM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


}
*/
