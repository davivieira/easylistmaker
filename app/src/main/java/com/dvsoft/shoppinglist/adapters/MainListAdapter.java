package com.dvsoft.shoppinglist.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.repositories.ListRepository;
import com.dvsoft.shoppinglist.util.ItemsUtil;
import com.dvsoft.shoppinglist.util.MainActivityUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davivieira on 31/03/15.
 */
public class MainListAdapter extends BaseAdapter {

    @InjectView(R.id.list_name_label)
    TextView nameLabel;

    @InjectView(R.id.item_remove_button)
    ImageButton removeButton;

    private List<ListModel> list;
    private FragmentActivity activity;
    private Bundle bundle;

    public MainListAdapter(List<ListModel> list, FragmentActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_main_list, null);
        ButterKnife.inject(this, view);

        final ListModel listModel = list.get(position);

        nameLabel.setText(listModel.getListName());

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListRepository listRepository = new ListRepository();
                listRepository.delete(listModel.getId());

                new MainActivityUtil().refreshCurrentFragment(activity);
            }
        });

        return view;
    }
}
