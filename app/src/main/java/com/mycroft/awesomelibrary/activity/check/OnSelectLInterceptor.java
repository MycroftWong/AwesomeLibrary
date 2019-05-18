package com.mycroft.awesomelibrary.activity.check;

import java.util.List;

public class OnSelectLInterceptor implements Interceptor {

    private final List<CheckHelper.OnSelectListener> mListeners;

    public OnSelectLInterceptor(List<CheckHelper.OnSelectListener> listeners) {
        mListeners = listeners;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void intercept(Chain chain) {
        Stream stream = chain.stream();
        for (CheckHelper.OnSelectListener listener : mListeners) {
            if (listener != null) {
                listener.onSelect(stream.getD(), stream.getV(), stream.isToCheck());
            }
        }
        chain.proceed(stream);
    }
}
