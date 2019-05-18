package com.mycroft.awesomelibrary.activity.check;

/**
 * @author mycroft
 */
public interface Interceptor {
    void intercept(Chain chain);

    interface Chain {
        void proceed(Stream stream);

        Stream stream();
    }
}
