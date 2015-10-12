package com.dvsoft.shoppinglist.repositories;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.dvsoft.shoppinglist.models.ItemModel;
import com.dvsoft.shoppinglist.models.ListModel;

import java.util.List;

/**
 * Operations at ShoppingList.db / TABLE: ITEMS
 *
 * Created by davivieira on 23/03/15.
 */
public class ItemRepository {

    /**
     * Get all items from a list.
     *
     * @param listId
     * @return
     */
    public List<ItemModel> getItemsByList(Long listId) {
        List<ItemModel> listItems = new Select()
                .from(ItemModel.class)
                .where("list=?", listId)
                .orderBy("name ASC")
                .execute();;
        return listItems;
    }

    /**
     * Saves an item.
     *
     * @param item
     */
    public void saveItem(ItemModel item, ListModel list) {
        item.setList(list);
        item.save();
    }

    /**
     * Updates the price and the checkbox status.
     *
     * @param id
     * @param isChecked
     */
    public void afterCheckUpdate(Long id, Boolean isChecked) {
        ItemModel item = ItemModel.load(ItemModel.class, id);

        item.setChecked(isChecked);
        item.save();
    }

    /**
     * Updates the checkbox status when its unchecked.
     *
     * @param id
     */
    public void uncheckUpdate(Long id) {
        ItemModel item = ItemModel.load(ItemModel.class, id);

        item.setChecked(false);
        item.save();
    }

    /**
     * Updates the name of an item.
     *
     * @param newName
     * @param id
     */
    public void nameUpdate(String newName, Long id) {
        ItemModel item = ItemModel.load(ItemModel.class, id);

        item.setName(newName);
        item.save();
    }

    /**
     * Deletes an item.
     *
     * @param id
     */
    public void delete(Long id) {
        new Delete().from(ItemModel.class).where("id = ?", id).execute();
    }

    /**
     * Updates the price of selected item.
     *
     * @param price
     * @param id
     */
    public void updateItemPrice(Double price, Long id) {
        ItemModel item = ItemModel.load(ItemModel.class, id);

        item.setPrice(price);
        item.save();
    }
}
