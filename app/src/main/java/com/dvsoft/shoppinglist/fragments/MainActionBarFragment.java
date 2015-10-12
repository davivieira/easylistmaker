package com.dvsoft.shoppinglist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.util.MainActivityUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by davivieira on 28/03/15.
 */
public class MainActionBarFragment extends Fragment {

    @InjectView(R.id.add_list)
    ImageButton addList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.action_bar_main, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.add_list)
    public void addListEvent() {
        new MainActivityUtil().openListDialogFragment(getActivity(), null);
    }
}
