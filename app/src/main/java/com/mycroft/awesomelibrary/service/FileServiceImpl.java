package com.mycroft.awesomelibrary.service;

import com.blankj.utilcode.util.Utils;

import java.io.File;

/**
 * @author mycroft
 */
public class FileServiceImpl implements IFileService {

    private static class Holder {
        private static final FileServiceImpl INSTANCE = new FileServiceImpl();
    }

    public static FileServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    private FileServiceImpl() {
    }

    private static final String NAME_DIR_APK_PATH = "awesome";

    @Override
    public String getApkPath() {
        File cacheDir = Utils.getApp().getExternalCacheDir();
        if (cacheDir == null) {
            return null;
        }

        if (!cacheDir.exists() && cacheDir.mkdirs()) {
            return null;
        }

        return cacheDir.getAbsolutePath() + File.pathSeparator + NAME_DIR_APK_PATH;
    }
}
