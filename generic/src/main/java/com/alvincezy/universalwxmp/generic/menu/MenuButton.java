package com.alvincezy.universalwxmp.generic.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class MenuButton extends WxMenu {

    @JSONField(name = "sub_button")
    private List<SubButton> mSubButtons;

    public MenuButton(String name) {
        this(name, new ArrayList<SubButton>());
    }

    public MenuButton(String name, List<SubButton> subButtons) {
        super(name);
        setSubButtons(subButtons);
    }

    /**
     * Add menu-item to current menu-group
     *
     * @param item {@link SubButton}
     */
    public void addItem(SubButton item) {
        if (item != null) {
            getSubButtons().add(item);
        }
    }

    public List<SubButton> getSubButtons() {
        return mSubButtons;
    }

    public void setSubButtons(List<SubButton> subButtons) {
        mSubButtons = subButtons;
    }
}
