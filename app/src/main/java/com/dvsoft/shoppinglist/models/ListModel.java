package com.dvsoft.shoppinglist.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dvsoft.shoppinglist.types.ListTypeEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * List model.
 *
 * Created by davivieira on 22/03/15.
 */
@Table(name = "LISTS", id = "id")
public class ListModel extends Model implements Serializable {

    @Column(name = "name",
            notNull = true)
    private String listName;

    @Column(name = "list_type")
    private ListTypeEnum listType = ListTypeEnum.LIST;

    @Column(name = "created_at",
            index = true)
    private Date createdAt = new Date();

    @Column(name = "ask_for_price")
    private boolean askForPrice;

    @Column(name = "can_repeat_item")
    private boolean canRepeatItem;

    @Column(name = "reminder_date")
    private Long reminderDate;

    public List<ItemModel> getItemModelList () {
        return getMany(ItemModel.class, "list");
    }

    public ListModel() {
        super();
    }

    public ListModel(String listName, ListTypeEnum listType, boolean askForPrice, boolean canRepeatItem) {
        super();

        this.listName = listName;
        this.listType = listType;
        this.askForPrice = askForPrice;
        this.canRepeatItem = canRepeatItem;
    }

    public Long getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Long reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ListTypeEnum getListType() {
        return listType;
    }

    public void setListType(ListTypeEnum listType) {
        this.listType = listType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean getAskForPrice() {
        return askForPrice;
    }

    public void setAskForPrice(boolean askForPrice) {
        this.askForPrice = askForPrice;
    }

    public boolean isCanRepeatItem() {
        return canRepeatItem;
    }

    public void setCanRepeatItem(boolean canRepeatItem) {
        this.canRepeatItem = canRepeatItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        ListModel objListModel = (ListModel) obj;
        if (this.listName == objListModel.getListName()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return listName;
    }
}
