package com.mycroft.awesomelibrary.activity.check;

import androidx.annotation.NonNull;

import java.util.List;

public class OnCheckLInterceptor implements Interceptor {

    private List<CheckHelper.OnCheckListener> mListeners;

    public OnCheckLInterceptor(@NonNull List<CheckHelper.OnCheckListener> listeners) {
        mListeners = listeners;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void intercept(Interceptor.Chain chain) {
        RealChain realChain = (RealChain) chain;
        Stream stream = realChain.stream();
        for (CheckHelper.OnCheckListener mListener : mListeners) {
            if (mListener != null) {
                mListener.onCheck(stream.getD(), stream.getV(), stream.isToCheck());
            }
        }
        chain.proceed(stream);
    }
}
