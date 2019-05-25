package com.mycroft.awesomelibrary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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
public class HelpersFragment extends BaseCommonFragment {

    public static HelpersFragment newInstance() {

        Bundle args = new Bundle();

        HelpersFragment fragment = new HelpersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initFields(@Nullable Bundle savedInstanceState) {
        mHelperModels.addAll(ComponentModel.getHelpers());
    }

    private final List<ComponentModel> mHelperModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_components, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ComponentAdapter adapter = new ComponentAdapter(mHelperModels);
        adapter.setOnItemClickListener((a, v, position) -> {
            ComponentModel model = mHelperModels.get(position);
            Intent intent = new Intent(getContext(), model.getKlazz());
            intent.putExtra(BaseCommonComponentActivity.EXTRA_GITHUB_URL, model.getGithubUrl());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridDividerItemDecoration(getContext(), GridDividerItemDecoration.GRID_DIVIDER_VERTICAL));

        return view;
    }
}