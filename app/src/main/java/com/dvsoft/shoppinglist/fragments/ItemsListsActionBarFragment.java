package com.dvsoft.shoppinglist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.util.ItemsUtil;
import com.dvsoft.shoppinglist.util.MainActivityUtil;
import com.dvsoft.shoppinglist.util.ShareUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by davivieira on 03/04/15.
 */
public class ItemsListsActionBarFragment extends Fragment {

    @InjectView(R.id.current_list)
    TextView currentList;

    private ListModel listModel;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.action_bar_items_list, container, false);
        ButterKnife.inject(this, view);
        bundle = this.getArguments();

        if (bundle != null) {
            listModel = (ListModel) bundle.getSerializable("listModel");
        }

        String currentListName = "";

        if (listModel.getListName().length() >= 15) {
            currentListName = listModel.getListName().substring(0, 14);
        } else {
            currentListName = listModel.getListName();
        }

        currentList.setText(currentListName);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.config_list_button)
    public void onClickConfigListButton() {
        bundle.putSerializable("listModel", listModel);
        bundle.putSerializable("itemListFragment", new ItemsListFragment());
        new MainActivityUtil().openListDialogFragment(getActivity(), bundle);
    }

    @OnClick(R.id.add_item)
    public void onClickAddItem() {
        new ItemsUtil().addItemOnList(getActivity(), bundle);
    }

    @OnClick(R.id.share_list_button)
    public void onClickShareButton(View view) {
        registerForContextMenu(view);
        view.showContextMenu();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem preferences = menu.add(R.string.config_share);
        preferences.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("listModel", listModel);

                new ShareUtil().openShareConfigDialog(getActivity(), bundle);
                return false;
            }
        });

        MenuItem share = menu.add(R.string.share_option);
        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new ShareUtil().shareList(listModel, getActivity());
                return false;
            }
        });
    }
}
