package com.winfo.photoselector.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.winfo.photoselector.entity.Folder;
import com.winfo.photoselector.entity.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageModel {

    /**
     * 从SDCard加载图片
     *
     * @param context  context
     * @param callback 回调
     */
    public static void loadImageForSDCard(final Context context, final DataCallback callback) {
        //由于扫描图片是耗时的操作，所以要在子线程处理。
        new Thread(() -> {
            //扫描图片
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = context.getContentResolver();

            Cursor mCursor = mContentResolver.query(mImageUri, new String[]{
                            MediaStore.Images.Media.DATA,
                            MediaStore.Images.Media.DISPLAY_NAME,
                            MediaStore.Images.Media.DATE_ADDED,
                            MediaStore.Images.Media._ID},
                    null,
                    null,
                    MediaStore.Images.Media.DATE_ADDED);

            ArrayList<Image> images = new ArrayList<>();

            //读取扫描到的图片
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(
                            mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    //获取图片名称
                    String name = mCursor.getString(
                            mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                    //获取图片时间
                    long time = mCursor.getLong(
                            mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                    if (!".downloading".equals(getExtensionName(path))) { //过滤未下载完成的文件
                        images.add(new Image(path, time, name));
                    }
                }
                mCursor.close();
            }
            Collections.reverse(images);
            callback.onSuccess(splitFolder(images));
        }).start();
    }

    /**
     * 把图片按文件夹拆分，第一个文件夹保存所有的图片
     *
     * @param images 集合
     * @return 图片集合
     */
    private static ArrayList<Folder> splitFolder(ArrayList<Image> images) {
        ArrayList<Folder> folders = new ArrayList<>();
        folders.add(new Folder("全部图片", images));

        if (images != null && !images.isEmpty()) {
            int size = images.size();
            for (int i = 0; i < size; i++) {
                String path = images.get(i).getPath();
                String name = getFolderName(path);
                if (!TextUtils.isEmpty(name)) {
                    Folder folder = getFolder(name, folders);
                    folder.addImage(images.get(i));
                }
            }
        }
        return folders;
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    private static String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf('.');
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 根据图片路径，获取图片文件夹名称
     *
     * @param path 文件路径
     * @return 文件夹名称
     */
    private static String getFolderName(String path) {
        if (!TextUtils.isEmpty(path)) {
            String[] strings = path.split(File.separator);
            if (strings.length >= 2) {
                return strings[strings.length - 2];
            }
        }
        return "";
    }

    private static Folder getFolder(String name, List<Folder> folders) {
        if (!folders.isEmpty()) {
            int size = folders.size();
            for (int i = 0; i < size; i++) {
                Folder folder = folders.get(i);
                if (name.equals(folder.getName())) {
                    return folder;
                }
            }
        }
        Folder newFolder = new Folder(name);
        folders.add(newFolder);
        return newFolder;
    }

    public interface DataCallback {
        /**
         * 成功
         *
         * @param folders
         */
        void onSuccess(ArrayList<Folder> folders);
    }
}
