package com.dvsoft.shoppinglist.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dvsoft.shoppinglist.R;
import com.dvsoft.shoppinglist.models.ItemModel;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.repositories.ItemRepository;
import com.dvsoft.shoppinglist.util.ItemsUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by davivieira on 14/04/15.
 */
public class ItemNameDialog extends DialogFragment {

    @InjectView(R.id.item_name_edit)
    EditText itemName;

    private ListModel listModel;
    private ItemModel itemModel;
    private Bundle bundle;
    private boolean isEditing = false;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_item, container, false);
        ButterKnife.inject(this, view);
        itemName.requestFocus();

        getArgumentsFromItemsList();

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.cancel_item_dialog)
    public void onClickCancelItemDialog() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        closeDialog();
    }

    @OnClick(R.id.confirm_item_name)
    public void confirmItemName() {
        ItemRepository itemRepository = new ItemRepository();
        List<ItemModel> itemsList = itemRepository.getItemsByList(listModel.getId());
        prepareSaveItem(itemRepository, itemsList);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        closeDialog();
    }

    private void closeDialog() {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        this.dismiss();
        fragmentTransaction.remove(this);
    }

    private void getArgumentsFromItemsList() {
        bundle = this.getArguments();
        if (bundle != null) {
            listModel = (ListModel) bundle.getSerializable("listModel");
            itemModel = (ItemModel) bundle.getSerializable("itemModel");
        }

        if (itemModel != null) {
            itemName.setText(itemModel.getName().toString());
            isEditing = true;
        }
    }

    private void prepareSaveItem(ItemRepository itemRepository, List<ItemModel> itemsList) {
        if (isEditing) {
            itemModel.setName(itemName.getText().toString());

            if (!listModel.isCanRepeatItem()) {
                boolean canSave = true;
                for (ItemModel item : itemsList) {
                    if (item.getName().toUpperCase().trim().equals(itemModel.getName().toUpperCase().trim())
                            && !item.getId().equals(itemModel.getId())) {
                        Toast.makeText(getActivity(), R.string.itemAlert, Toast.LENGTH_LONG).show();
                        canSave = false;
                        break;
                    }
                }
                if (canSave) {
                    itemRepository.nameUpdate(itemModel.getName(), itemModel.getId());
                    new ItemsUtil().refreshItemsListFragment(getActivity(), bundle);
                } else {
                    new ItemsUtil().refreshItemsListFragment(getActivity(), bundle);
                }

            } else {
                itemModel.setName(itemName.getText().toString());
                itemRepository.nameUpdate(itemModel.getName(), itemModel.getId());
                new ItemsUtil().refreshItemsListFragment(getActivity(), bundle);
            }

        } else {
            itemModel = new ItemModel();
            itemModel.setName(itemName.getText().toString());

            if (!listModel.isCanRepeatItem()) {
                boolean canSave = true;
                for (ItemModel item : itemsList) {
                    if (item.getName().toUpperCase().trim().equals(itemModel.getName().toUpperCase().trim())) {
                        Toast.makeText(getActivity(), R.string.itemAlert, Toast.LENGTH_LONG).show();
                        canSave = false;
                        break;
                    }
                }
                if (canSave) {
                    itemRepository.saveItem(itemModel, listModel);
                    new ItemsUtil().refreshItemsListFragment(getActivity(), bundle);
                }

            } else {
                itemModel.setName(itemName.getText().toString());
                itemRepository.saveItem(itemModel, listModel);
                new ItemsUtil().refreshItemsListFragment(getActivity(), bundle);
            }
        }
    }
}
