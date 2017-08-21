package com.s.z.j.smil;

import java.util.List;

/**
 *  <smil> 第一层
 *  <head>  第二层
 * <layout> 当前第三层  下面包含RootLayout 和多个Region
 * Created by Administrator on 2017-08-21.
 */
public class Layout {
    /** 定义该smil文件的显示区域大小 */
    private   RootLayout rootLayout;//

    /** 定义一个或多个显示区域 */
    private List<Region> regionList;

    public RootLayout getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(RootLayout rootLayout) {
        this.rootLayout = rootLayout;
    }

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    @Override
    public String toString() {
        return "Layout{" +
                "rootLayout=" + rootLayout.toString() +
                ", regionList=" + regionList.toString() +
                '}';
    }
}
