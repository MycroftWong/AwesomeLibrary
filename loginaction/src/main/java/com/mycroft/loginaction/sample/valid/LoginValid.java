package com.mycroft.loginaction.sample.valid;

import android.app.Activity;
import android.content.Context;

import com.mycroft.loginaction.action.Valid;
import com.mycroft.loginaction.sample.LoginActivity;
import com.mycroft.loginaction.sample.UserConfigCache;

/**
 * @author mycroft
 */
public class LoginValid implements Valid {
    private Context context;

    public LoginValid(Context context) {
        this.context = context;
    }

    /**
     * check whether it login in or not
     *
     * @return
     */
    @Override
    public boolean check() {
        return UserConfigCache.isLogin(context);
    }

    /**
     * if check() return false. then doValid was called
     */
    @Override
    public void doValid() {
        LoginActivity.start((Activity) context);
    }
}
