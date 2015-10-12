package com.dvsoft.shoppinglist.repositories;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.dvsoft.shoppinglist.models.ListModel;
import com.dvsoft.shoppinglist.types.ListTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * Operations at ShoppingList.db / TABLE: LISTS
 *
 * Created by davivieira on 22/03/15.
 */
public class ListRepository {

    /**
     * Returns a list based on id.
     *
     * @param id
     * @return ListModel
     */
    public ListModel getById(Long id) {
        return new Select()
                .from(ListModel.class)
                .where("id = ?", id)
                .executeSingle();
    }

    /**
     * Get all lists.
     *
     * @return List<ListModel>
     */
    public List<ListModel> getAll() {
        return new Select()
                .from(ListModel.class)
                .orderBy("created_at DESC")
                .execute();
    }

    /**
     * Creates a new list on table "LISTS"
     *
     * @param listName
     * @param listType
     * @param askForPrice
     * @param canRepeatItem
     */
    public void create(String listName, ListTypeEnum listType, boolean askForPrice, boolean canRepeatItem) {
        ListModel list = new ListModel(listName, listType, askForPrice, canRepeatItem);
        list.save();
    }

    /**
     * Updates a record on table "LISTS"
     *
     * @param listName
     * @param listType
     * @param askForPrice
     * @param canRepeatItem
     * @param id
     */
    public void update(String listName, ListTypeEnum listType, boolean askForPrice, boolean canRepeatItem, Long id) {
        ListModel list = ListModel.load(ListModel.class, id);

        list.setListName(listName);
        list.setListType(listType);
        list.setAskForPrice(askForPrice);
        list.setCanRepeatItem(canRepeatItem);
        list.save();
    }

    /**
     * Remove a record on table "LISTS" bases on its id.
     *
     * @param id
     */
    public void delete(Long id) {
        new Delete().from(ListModel.class).where("id = ?", id).execute();
    }

    /**
     * Updates the date of reminder.
     *
     * @param reminder
     * @param id
     */
    public void updateReminder(Long reminder, Long id) {
        ListModel list = ListModel.load(ListModel.class, id);

        list.setReminderDate(reminder);
        list.save();
    }
}
