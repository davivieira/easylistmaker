package com.dvsoft.shoppinglist.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Item model.
 *
 * Created by davivieira on 22/03/15.
 */
@Table(name = "ITEMS", id = "id")
public class ItemModel extends Model implements Serializable {

    @Column(name = "name",
            notNull = true)
    private String name;

    @Column(name = "checked")
    private boolean checked = false;

    @Column(name = "price")
    private Double price;

    @Column(name = "list",
            onDelete = Column.ForeignKeyAction.CASCADE)
    private ListModel list;

    public ItemModel() {
        super();
    }

    public ListModel getList() {
        return list;
    }

    public void setList(ListModel list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        ItemModel itemObj = (ItemModel) obj;
        if (this.name == itemObj.getName()) {
            return true;
        } else {
            return false;
        }
    }

}
