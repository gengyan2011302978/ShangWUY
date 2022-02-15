package com.phjt.shangxueyuan.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * @author: gengyan
 * date:    2019/11/1 16:18
 * company: 普华集团
 * description: TabLayout实体
 */
public class TabEntity implements CustomTabEntity {

    private String title;
    private int selectedIcon;
    private int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
