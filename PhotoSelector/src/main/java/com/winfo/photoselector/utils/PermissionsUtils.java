package com.winfo.photoselector.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * 权限检查类
 *
 * @author wangqiang
 */
public class PermissionsUtils {

    public static boolean checkReadStoragePermission(Activity activity) {
        int readStoragePermissionState =
                ContextCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE);

        boolean readStoragePermissionGranted = readStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!readStoragePermissionGranted) {
            ActivityCompat.requestPermissions(activity,
                    PermissionsConstant.PERMISSIONS_EXTERNAL_READ,
                    PermissionsConstant.REQUEST_EXTERNAL_READ);
        }
        return readStoragePermissionGranted;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkWriteStoragePermission(Activity activity) {

        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!writeStoragePermissionGranted) {
            activity.requestPermissions(PermissionsConstant.PERMISSIONS_EXTERNAL_WRITE,
                    PermissionsConstant.REQUEST_EXTERNAL_WRITE);
        }
        return writeStoragePermissionGranted;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkCameraPermission(Activity activity) {
        int cameraPermissionState = ContextCompat.checkSelfPermission(activity, CAMERA);

        boolean cameraPermissionGranted = cameraPermissionState == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermissionGranted) {
            activity.requestPermissions(PermissionsConstant.PERMISSIONS_CAMERA,
                    PermissionsConstant.REQUEST_CAMERA);
        }
        return cameraPermissionGranted;
    }

}
