package com.bingo.king.app.utils;

import android.content.Intent;
import android.net.Uri;

import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class OkHttpDownloader
{
    private static final DateFormat FORMAT = new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());

    /**
     * @Description 下载专用(比如下载图片)
     */
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
//            .addInterceptor(createHttpLoggingInterceptor())
            .build();

//    private static HttpLoggingInterceptor createHttpLoggingInterceptor()
//    {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
//        return loggingInterceptor;
//    }


    public static void downloadImage(String url,String dirName,DownloadListener listener){
        final Request request = new Request.Builder().url(url).build();
        OkHttpDownloader.client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (listener != null)
                {
                    listener.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String url = response.request().url().toString();
                int index = url.lastIndexOf("/");
                String pictureName = url.substring(index+1);
                File file = FileUtil.creatDownloadImageFile(pictureName);
                FileOutputStream fos = new FileOutputStream(file);
                InputStream in = response.body().byteStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = in.read(buf))!=-1){
                    fos.write(buf,0,len);
                }
                fos.flush();
                in.close();
                fos.close();

                //文件存储已经完毕，保存的图片没有加入到系统图库中
                //，此时需要发送广播，刷新图库，很简单几行代码搞定
                Intent intent =
                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                Utils.getApp().sendBroadcast(intent);

                if (listener != null)
                {
                    listener.onSuccess();
                }
            }
        });

    }

    /**
     * @Description 图片下载
     */
    public static void downloadADImage(String url){
        final Request request = new Request.Builder().url(url).build();
        OkHttpDownloader.client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Timber.d(e);
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException
            {
                String url = response.request().url().toString();
                int index = url.lastIndexOf("/");
                String pictureName = url.substring(index+1);
                if(FileUtil.isADimageExist(pictureName)){
                    return;
                }
                Timber.i("pictureName="+pictureName);
                FileOutputStream fos = new FileOutputStream(FileUtil.createADImageFile(pictureName));
                InputStream in = response.body().byteStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = in.read(buf))!=-1){
                    fos.write(buf,0,len);
                }
                fos.flush();
                in.close();
                fos.close();
            }
        });
    }

    public interface DownloadListener{
        void onSuccess();
        void onFailure(IOException e);
    }


}
