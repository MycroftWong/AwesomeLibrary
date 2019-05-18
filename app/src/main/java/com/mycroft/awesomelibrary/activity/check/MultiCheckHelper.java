package com.mycroft.awesomelibrary.activity.check;

import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.RecyclerView;

import com.luwei.checkhelper.CheckHelper;
import com.luwei.checkhelper.Stream;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author mycroft
 */
public class MultiCheckHelper extends CheckHelper {

    private Map<Class, Set<?>> mMap;

    public MultiCheckHelper() {
        mMap = new ArrayMap<>();
        addDownSteamInterceptor(chain -> {
            synchronized (MultiCheckHelper.this) {
                Stream stream = chain.stream();
                update(stream.getD(), stream.isToCheck());
                chain.proceed(stream);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void add(Object d) {
        Set<Object> set = (Set<Object>) mMap.get(d.getClass());
        if (set == null) {
            set = new HashSet<>();
            mMap.put(d.getClass(), set);
        }
        set.add(d);
    }

    @Override
    public void remove(Object d) {
        Set set = mMap.get(d.getClass());
        if (set != null) {
            set.remove(d);
            if (set.size() == 0) {
                mMap.remove(d.getClass());
            }
        }
    }

    private void update(Object d, boolean toCheck) {
        if (toCheck) {
            add(d);
        } else {
            remove(d);
        }
    }

    /**
     * 取消全部选中
     */
    public void unCheckAll(RecyclerView.Adapter adapter) {
        if (mMap.size() == 0) {
            return;
        }
        mMap.clear();
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    public <T> Set<T> getChecked(Class<T> type) {
        return (Set<T>) mMap.get(type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Object> getChecked() {
        Set<Object> set = new HashSet<>();
        for (Set set1 : mMap.values()) {
            set.addAll(set1);
        }
        return set;
    }

    @Override
    public boolean isChecked(Object d, RecyclerView.ViewHolder v) {
        Set set = mMap.get(d.getClass());
        return set != null && set.contains(d);
    }

    @Override
    public boolean hasChecked() {
        return mMap.size() != 0;
    }
}
