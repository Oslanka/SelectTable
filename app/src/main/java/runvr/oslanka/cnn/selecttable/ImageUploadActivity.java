package runvr.oslanka.cnn.selecttable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloud.lashou.utils.AppUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import runvr.oslanka.cnn.selecttable.bean.InsertContentBean;
import runvr.oslanka.cnn.selecttable.bean.SelectBean;
import runvr.oslanka.cnn.selecttable.bean.UpLoadImageResponse;
import runvr.oslanka.cnn.selecttable.http.HttpFactory;
import runvr.oslanka.cnn.selecttable.util.SharedPreferencesUtil;

public class ImageUploadActivity extends BaseActivity {

    private TextView tv_ydxh;
    private TextView tv_mudiquyu;
    private TextView tv_take_photo;
    private TextView tyr;
    private LinearLayout layoutCenter;
    private SelectBean.DataBean dataBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_ydxh = findViewById(R.id.tv_ydxh);
        tv_mudiquyu = findViewById(R.id.tv_mudiquyu);
        tyr = findViewById(R.id.tyr);
        layoutCenter = findViewById(R.id.layout_center);
        tv_take_photo = findViewById(R.id.tv_take_photo);


      /*  SlideRecycleView mRecycleView = findViewById(R.id.recycle_view);
        SlideRecycleViewItemVerticalDecoration item = new SlideRecycleViewItemVerticalDecoration(this,
                SlideRecycleViewItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.driver_line12));
        mRecycleView.getRefreshableView().addItemDecoration(item);  //添加分割线
        mRecycleView.setScrollingWhileRefreshingEnabled(false); //设置刷新的时候不可滑动
        mRecycleView.getRefreshableView().setLayoutManager(new LinearLayoutManager(this)); //添加布局管理器
        mRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        selectBeans = new ArrayList<>();
        listAdapter = new ScenceListAdapter(this, selectBeans, 0);
        mRecycleView.getRefreshableView().setAdapter(listAdapter);*/
        mProgressView = findViewById(R.id.login_progress);
        findViewById(R.id.begin_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.hideSoftKeybord(ImageUploadActivity.this);
                initData();
            }
        });
        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTakePhoto(new CallBackPhoto() {
                    @Override
                    public void finish(Uri uri, Bitmap bitmap) {

                    }

                    @Override
                    public void finish(String url) {

                    }
                });
            }
        });
//        initData();

    }

    @Override
    public String getMyTitle() {
        return "签收单上传";
    }

    @Override
    public int getResId() {
        return R.layout.activity_iamge_upload;
    }

    private void initData() {
        showProgress(true);
        HttpFactory.getInstance().getSelect(tv_ydxh.getText().toString(), tv_mudiquyu.getText().toString(), tyr.getText().toString()).enqueue(new Callback<SelectBean>() {
            @Override
            public void onResponse(Call<SelectBean> call, Response<SelectBean> response) {
                showProgress(false);
                tv_take_photo.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    SelectBean body = response.body();
                    if (body.getCode() == 0) {
                        if (body.getSize() > 1) {
                            Toast.makeText(ImageUploadActivity.this, "查询数据不唯一，请精确查询", Toast.LENGTH_SHORT).show();
                        } else if (body.getSize() == 1) {
                            Toast.makeText(ImageUploadActivity.this, "查询到数据", Toast.LENGTH_SHORT).show();
                            dataBean = body.getData().get(0);
                            refreshView();
                        } else {
                            Toast.makeText(ImageUploadActivity.this, "没有查询到数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SelectBean> call, Throwable t) {
                showProgress(false);
                tv_take_photo.setVisibility(View.GONE);

            }
        });
    }

    private void refreshView() {
        try {
            layoutCenter.removeAllViews();
            LayoutInflater from = LayoutInflater.from(this);
            CardView layout = (CardView) from.inflate(R.layout.activity_iamge_upload_layout, null);

            ((TextView) layout.findViewById(R.id.ydh)).setText("运单号 : " + dataBean.getYdxh());
            ((TextView) layout.findViewById(R.id.time)).setText("托运日期 : " + dataBean.getTyrq());
            ((TextView) layout.findViewById(R.id.from)).setText(dataBean.getJbren());
            ((TextView) layout.findViewById(R.id.name)).setText(dataBean.getTyr());
            ((TextView) layout.findViewById(R.id.to)).setText(dataBean.getYhzh());
            ((TextView) layout.findViewById(R.id.who)).setText(dataBean.getShdw());
            layoutCenter.addView(layout);
//        layoutCenter.addView(getTextView("运单号 : ", dataBean.getYdxh(), from));
//        layoutCenter.addView(getTextView("托运日期 : ", dataBean.getTyrq(), from));
//        layoutCenter.addView(getTextView("始发站 : ", dataBean.getJbren(), from));
//        layoutCenter.addView(getTextView("目的区域 : ", dataBean.getYhzh(), from));
//        layoutCenter.addView(getTextView("收货人 : ", dataBean.getShdw(), from));
//        layoutCenter.addView(getTextView("拍照上传", dataBean.getShdw(), from));
            tv_take_photo.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextView getTextView(String name, String text, LayoutInflater from) {
        TextView textView = (TextView) from.inflate(R.layout.activity_iamge_upload_item, null);
        textView.setText(name + text);
        return textView;
    }

    public static final int TAKE_PHOTO = 1000;


    public void openTakePhoto(CallBackPhoto callBackPhoto) {
        /**
         * 在启动拍照之前最好先判断一下sdcard是否可用
         */
        try {
//            String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
//            if (state.equals(Environment.MEDIA_MOUNTED)) {   //如果可用


//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(captureIntent, TAKE_PHOTO);
            getImageFromCamera();
            this.callBackPhoto = callBackPhoto;
//            } else {
//                Toast.makeText(ImageUploadActivity.this, "sdcard不可用", Toast.LENGTH_SHORT).show();
//            }
        } catch (SecurityException e) {
            Toast.makeText(this, "请设置中打开应用的相机权限", Toast.LENGTH_SHORT).show();
        }
    }

    private String mCurrentPhotoPath;

    public void getImageFromCamera() {

        String fileName = "Temp_camera" + ".jpg";
        File cropFile = null;
        try {
            cropFile = new File(getExternalCacheDir().getAbsolutePath(), fileName);
        }catch (Exception e){

        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getApplicationContext() == null || cropFile == null) {
            throw new NullPointerException();
        }

        Uri fileUri = Uri.fromFile(cropFile);
        mCurrentPhotoPath = fileUri.getPath();

        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(getApplicationContext(), "com.lashou.cloud.fileprovider", cropFile);
        } else {
            uri = Uri.fromFile(cropFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO) {
            if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
                Bitmap bitmap = BitmapFactory.decodeFile((mCurrentPhotoPath));
                File file = compressImage(bitmap);
                upload(file);
                callBackPhoto.finish(mCurrentPhotoPath);
            } else {
                if (data != null && (data.getData() != null || data.getExtras() != null)) {
                    Uri uri = data.getData();
                    Bitmap photo = null;
                    if (uri != null) {
                        photo = BitmapFactory.decodeFile(uri.getPath()); //拿到图片
                    }
                    if (photo == null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            photo = (Bitmap) bundle.get("data");
                        } else {
                            Toast.makeText(getApplicationContext(), "找不到图片", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (callBackPhoto != null) {
//                    Bitmap comp = BitmapUtil.comp(photo);
                        callBackPhoto.finish(uri, photo);
                        try {
                            if (photo != null) {
                                File file = compressImage(photo);
                                upload(file);
                            } else if (uri != null) {
                                upload(new File(new URI(uri.toString())));
                            }
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private CallBackPhoto callBackPhoto;

    public interface CallBackPhoto {
        void finish(Uri uri, Bitmap bitmap);

        void finish(String url);
    }


    public void upload(final File file) {
        showProgress(true);
        String user = SharedPreferencesUtil.getString("user", "");
        String nickName = SharedPreferencesUtil.getString("nickName", "");
        String value = TextUtils.isEmpty(nickName) ? user : nickName;
        HttpFactory.getInstance().uploadContent(dataBean == null ? "888" : dataBean.getYdh(), value).enqueue(new Callback<InsertContentBean>() {
            @Override
            public void onResponse(Call<InsertContentBean> call, Response<InsertContentBean> response) {
                showProgress(false);
                if (response.isSuccessful()) {
                    InsertContentBean body = response.body();
                    List<InsertContentBean.DataBean> data = body.getData();
                    if (data != null && data.size() == 1) {
                        updateImage(data.get(0).getCode(), file);
                    }
                }
            }

            @Override
            public void onFailure(Call<InsertContentBean> call, Throwable t) {
                showProgress(false);
                Toast.makeText(ImageUploadActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateImage(String code, File file) {
        showProgress(true);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", code, requestFile);
        HttpFactory.getInstance().postImage(filePart).enqueue(new Callback<UpLoadImageResponse>() {
            @Override
            public void onResponse(Call<UpLoadImageResponse> call, Response<UpLoadImageResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getCode() == 0) {
                    Toast.makeText(ImageUploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
//                    initData();
                } else {
                    Toast.makeText(ImageUploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }

                showProgress(false);
            }

            @Override
            public void onFailure(Call<UpLoadImageResponse> call, Throwable t) {
                showProgress(false);
                Toast.makeText(ImageUploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private View mProgressView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 压缩图片（质量压缩）
     *
     * @param bitmap
     */
    public File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date(System.currentTimeMillis());
        String filename = "compressCache";//format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }
}
