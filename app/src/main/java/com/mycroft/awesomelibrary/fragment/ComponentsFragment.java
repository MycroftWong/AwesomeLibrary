package com.mycroft.awesomelibrary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mycroft.awesomelibrary.R;
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
    protected void initFields(@Nullable Bundle savedInstanceState) {
        mComponentModels.addAll(ComponentModel.get());
    }

    private final List<ComponentModel> mComponentModels = new ArrayList<>();

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_components, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        ComponentAdapter adapter = new ComponentAdapter(mComponentModels);
        adapter.setOnItemClickListener((a, v, position) -> startActivity(new Intent(getContext(), mComponentModels.get(position).getKlazz())));

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return view;
    }
}
