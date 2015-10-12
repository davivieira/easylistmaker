package com.dvsoft.shoppinglist.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.adapters.MainListAdapter;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.repositories.ListRepository;
import com.dvsoft.shoppinglist.util.ItemsUtil;
import com.dvsoft.shoppinglist.util.MainActivityUtil;
import com.dvsoft.shoppinglist.util.ShareUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/**
 * Created by davivieira on 26/03/15.
 */
public class MainFragment extends Fragment {

    @InjectView(R.id.lists_list)
    ListView listsList;

    @InjectView(R.id.hint_container)
    TextView hintContainer;

    MainListAdapter adapter;
    List<ListModel> allLists;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerForContextMenu(listsList);


        ListRepository listRepository = new ListRepository();
        allLists = listRepository.getAll();

        if (allLists != null) {
            adapter = new MainListAdapter(allLists, getActivity());
            listsList.setAdapter(adapter);
        }

        if (allLists.size() == 0) {
            hintContainer.setText(getResources().getString(R.string.no_lists_created));
        }

        listsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("listModel", allLists.get(position));
                new ItemsUtil().openItemsListFragment(getActivity(), bundle);
            }
        });
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        MenuItem share = menu.add(R.string.share);
        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ListModel listModel = allLists.get(info.position);
                new ShareUtil().shareList(listModel, getActivity());
                return false;
            }
        });

        MenuItem edit = menu.add(R.string.edit);
        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle = new Bundle();
                ListModel listModel = allLists.get(info.position);
                bundle.putSerializable("listModel", listModel);

                new MainActivityUtil().openListDialogFragment(getActivity(), bundle);
                return false;
            }
        });

        MenuItem reminder = menu.add(R.string.reminder);
        reminder.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle = new Bundle();
                ListModel listModel = allLists.get(info.position);
                bundle.putSerializable("listModel", listModel);

                new MainActivityUtil().openReminderDialogFragment(getActivity(), bundle);
                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
