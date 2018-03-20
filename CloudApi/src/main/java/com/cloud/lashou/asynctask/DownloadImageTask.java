package com.cloud.lashou.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载图片
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

	public DownloadImageListner mListener;

	public interface DownloadImageListner {
		void imageDownloaded(Bitmap bitmap);
	}

	public DownloadImageTask(DownloadImageListner listner) {
		this.mListener = listner;
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		Bitmap bitmap = null;
		try {
			bitmap = this.downloadImage(urls[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (null != mListener) {
			mListener.imageDownloaded(result);
		}
	}

	/**
	 * 获取图片
	 */
	private Bitmap downloadImage(String imageUrl) throws IOException {
		URL url = new URL(imageUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.connect();
		InputStream inputStream = con.getInputStream();
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		return bitmap;
	}

}
