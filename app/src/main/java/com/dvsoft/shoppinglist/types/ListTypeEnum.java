package com.dvsoft.shoppinglist.types;

/**
 * Enum with ListView visualization options.
 *
 * Created by davivieira on 22/03/15.
 */
public enum ListTypeEnum {

    GRID("view.type.grid"),
    LIST("view.type.list");

    private String listType;

    ListTypeEnum(String listType) {
        this.listType = listType;
    }

    public String getListType() {
        return listType;
    }
}
