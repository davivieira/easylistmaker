package com.dvsoft.shoppinglist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.adapters.ItemsListAdapter;
import com.dvsoft.shoppinglist.models.ItemModel;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.repositories.ItemRepository;
import com.dvsoft.shoppinglist.util.ItemsUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davivieira on 02/04/15.
 */
public class ItemsListFragment extends Fragment implements Serializable{

    @InjectView(R.id.items_list)
    ListView itemsList;

    private ListModel listModel;
    private ItemsListAdapter adapter;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        ButterKnife.inject(this, view);

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                fragmentTransaction.remove(ItemsListFragment.this);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        registerForContextMenu(itemsList);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            listModel = (ListModel) bundle.getSerializable("listModel");
        }

        ItemRepository itemRepository = new ItemRepository();
        final List<ItemModel> items = itemRepository.getItemsByList(listModel.getId());

        if (items != null) {
            adapter = new ItemsListAdapter(items, getActivity());
            itemsList.setAdapter(adapter);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        MenuItem editName = menu.add(R.string.edit_item_name);
        editName.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ItemModel itemModel = (ItemModel) itemsList.getItemAtPosition(info.position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemModel", itemModel);
                bundle.putSerializable("listModel", listModel);

                new ItemsUtil().addItemOnList(getActivity(), bundle);
                return false;
            }
        });

        MenuItem editPrice = menu.add(R.string.edit_item_price);
        editPrice.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ItemModel itemModel = (ItemModel) itemsList.getItemAtPosition(info.position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemModel", itemModel);
                bundle.putSerializable("listModel", listModel);

                new ItemsUtil().openAskForPriceDialog(getActivity(), bundle);
                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
