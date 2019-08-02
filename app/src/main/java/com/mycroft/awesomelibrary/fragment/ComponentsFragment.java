package com.mycroft.awesomelibrary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.dinuscxj.itemdecoration.GridDividerItemDecoration;
import com.mycroft.awesomelibrary.R;
import com.mycroft.awesomelibrary.activity.common.BaseCommonComponentActivity;
import com.mycroft.awesomelibrary.fragment.common.BaseCommonFragment;
import com.mycroft.awesomelibrary.model.ComponentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mycroft
 */
public class ComponentsFragment extends BaseCommonFragment {

    public static ComponentsFragment newInstance() {
        Bundle args = new Bundle();
        ComponentsFragment fragment = new ComponentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComponentModels.addAll(ComponentModel.getComponents());
    }

    private final List<ComponentModel> mComponentModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_components, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ComponentAdapter adapter = new ComponentAdapter(mComponentModels);
        adapter.setOnItemClickListener((a, v, position) -> {
            ComponentModel model = mComponentModels.get(position);
            if (model.isDeprecated()) {
                ToastUtils.showShort(R.string.toast_component_is_bad);
                return;
            }
            Intent intent = new Intent(getContext(), model.getKlazz());
            intent.putExtra(BaseCommonComponentActivity.EXTRA_COMPONENT_MODEL, model);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridDividerItemDecoration(getContext(), GridDividerItemDecoration.GRID_DIVIDER_VERTICAL));

        return view;
    }
}
