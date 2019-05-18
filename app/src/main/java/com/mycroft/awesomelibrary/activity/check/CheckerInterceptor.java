package com.mycroft.awesomelibrary.activity.check;

public class CheckerInterceptor implements Interceptor {

    private final CheckHelper.Checker mChecker;

    public CheckerInterceptor(CheckHelper.Checker checker) {
        mChecker = checker;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void intercept(Chain chain) {
        Stream stream = chain.stream();
        if (mChecker != null) {
            if (stream.isToCheck()) {
                mChecker.check(stream.getD(), stream.getV());
            } else {
                mChecker.unCheck(stream.getD(), stream.getV());
            }
        }
        chain.proceed(stream);
    }
}
