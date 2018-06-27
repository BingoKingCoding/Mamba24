package com.bingo.king.app.imageloader;

/**
 * <实现接口进行操作>
 * Created by WangWB on 2018/06/27  10:56.
 */

public interface ILoaderStrategy {

    void loadImage(LoaderOptions options);

    /**
     * Desc:清理磁盘缓存
     *
     * @author wwb
     */
    void clearMemoryCache();

    /**
     * Desc:清理磁盘缓存
     *
     * @author wwb
     */
    void clearDiskCache();
}
