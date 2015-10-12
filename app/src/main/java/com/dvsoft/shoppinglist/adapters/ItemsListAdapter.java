package com.dvsoft.shoppinglist.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.models.ItemModel;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.repositories.ItemRepository;
import com.dvsoft.shoppinglist.repositories.ListRepository;
import com.dvsoft.shoppinglist.util.ItemsUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by davivieira on 04/04/15.
 */
public class ItemsListAdapter extends BaseAdapter {

    @InjectView(R.id.item_name_label)
    TextView itemName;

    @InjectView(R.id.delete_item)
    ImageButton deleteIcon;

    @InjectView(R.id.item_checkBox)
    CheckBox itemCheckbox;

    @InjectView(R.id.price_label)
    TextView priceLabel;

    private List<ItemModel> items;
    private FragmentActivity activity;
    private ListModel listModel;
    Bundle bundle;

    public ItemsListAdapter(List<ItemModel> items, FragmentActivity activity) {
        this.items = items;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_items_list, null);
        ButterKnife.inject(this, view);

        final ItemModel itemModel = items.get(position);
        ListRepository listRepository = new ListRepository();
        listModel = listRepository.getById(itemModel.getList().getId());

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemRepository itemRepository = new ItemRepository();
                itemRepository.delete(itemModel.getId());

                bundle = new Bundle();
                bundle.putSerializable("listModel", listModel);
                new ItemsUtil().refreshItemsListFragmentAfterDelete(activity, bundle);
            }
        });

        itemCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemModel.isChecked()) {
                    itemModel.setChecked(true);
                } else {
                    itemModel.setChecked(false);
                }

                if (listModel.getAskForPrice() && itemModel.isChecked()) {
                    bundle = new Bundle();
                    bundle.putSerializable("itemModel", itemModel);
                    bundle.putSerializable("listModel", listModel);
                    new ItemsUtil().openAskForPriceDialog(activity, bundle);
                }

                new ItemsUtil().checkOrUncheckItem(itemModel);
            }
        });

        if (itemModel.getPrice() != null) {
            String price = String.format("%.2f", itemModel.getPrice());
            priceLabel.setText("$" + price);
        }
        itemCheckbox.setChecked(itemModel.isChecked());
        itemName.setText(itemModel.getName());

        return view;
    }
}
