package com.bingo.king.app.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * <SD卡工具类>
 */

public class FileUtil
{
    public static final String HTTP_CACHE_DIR = "http";
    public static final String IMAGE_CACHE_DIR = "image";
    public static final String AD_IMAGE_CACHE_DIR = "adimages";

    /**
     * sd卡是否存在
     *
     * @return
     */
    public static boolean isSdcardExist()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 判断路径下的文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path)
    {
        boolean result = false;
        if (path != null)
        {
            result = new File(path).exists();
        }
        return result;
    }


    /**
     * 获取SD卡缓存目录
     *
     * @param dirName 文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                {@link Environment#DIRECTORY_MUSIC},
     *                {@link Environment#DIRECTORY_PODCASTS},
     *                {@link Environment#DIRECTORY_RINGTONES},
     *                {@link Environment#DIRECTORY_ALARMS},
     *                {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link Environment#DIRECTORY_PICTURES}, or
     *                {@link Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    private static File getExternalCacheDirectory(String dirName)
    {
        File appCacheDir = null;
        if (!TextUtils.isEmpty(dirName))
        {
            appCacheDir = new File(Utils.getApp().getExternalCacheDir(), dirName);
        }
        if (appCacheDir == null)
        {// 有些手机需要通过自定义目录
            appCacheDir = new File(getExternalStorageDirectory(), "Android/data/" + Utils.getApp().getPackageName() + "/cache/" + dirName);
        }
        return appCacheDir;
    }

    private static File getExternalDirectory(String dirName)
    {
        File appDir = null;
        if (!TextUtils.isEmpty(dirName))
        {
            if (isSdcardExist())
                appDir = new File(getExternalStorageDirectory(), dirName);
        }
        return appDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param dirName 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    private static File getInternalCacheDirectory(String dirName)
    {
        File appCacheDir = null;
        if (TextUtils.isEmpty(dirName))
        {
            appCacheDir = Utils.getApp().getCacheDir();// /data/data/app_package_name/cache
        } else
        {
            appCacheDir = new File(Utils.getApp().getFilesDir(), dirName);// /data/data/app_package_name/files/type
        }
        if (!appCacheDir.exists() && !appCacheDir.mkdirs())
        {
            Log.e("getInternalDirectory", "getInternalDirectory fail ,the reason is make directory fail !");
        }
        return appCacheDir;
    }

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param dirName 文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static File getCacheDirectory(String dirName)
    {
        File appCacheDir = null;
        if (isSdcardExist())
        {
            appCacheDir = getExternalCacheDirectory(dirName);
        } else
        {
            appCacheDir = getInternalCacheDirectory(dirName);
        }
        return appCacheDir;
    }

    public static File getHttpCacheDir()
    {
        return getCacheDirectory(HTTP_CACHE_DIR);
    }


    /**
     * 获得图片下载的文件夹(下载位置建议放在sd卡 这样其他应用在选择图片的时候就可以获取到)
     */
    public static File getDownloadImageDir()
    {
        return getExternalDirectory(Utils.getApp().getPackageName());
    }

    /**
     * @return
     */
    public static boolean isADimageExist(String paramString)
    {
        return isFileExist(getADImageCacheDir().getAbsolutePath() + File.separator + paramString);
    }

    /**
     * 获得图片缓存的文件夹
     */
    public static File getImageCacheDirectory()
    {
        return getCacheDirectory(IMAGE_CACHE_DIR);
    }

    /**
     * 创建image文件缓存
     */
    public static File creatImageCache(String fileName)
    {

        File dir = new File(getImageCacheDirectory().getAbsolutePath());
        if (!dir.exists())
        {
            boolean create = dir.mkdirs();
            Timber.d("create = " + create);
        }
        return new File(getImageCacheDirectory(), fileName);
    }

    /**
     * 下载图片
     */
    public static File creatDownloadImageFile(String fileName)
    {
        File dir = new File(getDownloadImageDir().getAbsolutePath());
        if (!dir.exists())
        {
            boolean create = dir.mkdirs();
            Timber.d("create = " + create);
        }
        return new File(getDownloadImageDir(), fileName);
    }

    // 广告图片缓存文件夹
    public static File getADImageCacheDir()
    {
        return getCacheDirectory(AD_IMAGE_CACHE_DIR);
    }

    public static File createADImageFile(String fileName) throws IOException
    {
        File dir = new File(getADImageCacheDir().getAbsolutePath());
        if (!dir.exists())
        {
            boolean create = dir.mkdirs();
            Timber.d("create = " + create);
        }
        return new File(getADImageCacheDir().getAbsolutePath(), fileName);
    }

    /**
     * 得到 adimages 文件夹下的所有文件
     */
    public static List<String> getAllAD()
    {
        File file = new File(getADImageCacheDir().getAbsolutePath());
        File[] fileList = file.listFiles();
        List<String> list = new ArrayList<>();
        if (null != fileList)
        {
            for (File f : fileList)
            {
                list.add(f.getAbsolutePath());
            }
        }
        return list;
    }

    public static File getPicturesDir()
    {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        return dir;
    }


    public static boolean deleteFileOrDir(File path)
    {
        if (path != null && path.exists())
        {
            if (path.isFile())
            {
                return path.delete();
            } else
            {
                File[] files = path.listFiles();
                if (files != null)
                {
                    File[] var2 = files;
                    int var3 = files.length;

                    for (int var4 = 0; var4 < var3; ++var4)
                    {
                        File file = var2[var4];
                        deleteFileOrDir(file);
                    }
                }

                return path.delete();
            }
        } else
        {
            return true;
        }
    }
}
