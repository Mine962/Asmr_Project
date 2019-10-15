package net.dgsw.asmr.List;

import android.graphics.drawable.Drawable;

public class ListClass {
    private String iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private String hide_video;

    public void setIcon(String icon) {iconDrawable = icon ;}
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setHide_video(String hide_video) {this.hide_video = hide_video;}

    public String getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getHide_video() { return hide_video; }
    public String getDesc() {
        return this.descStr ;
    }
}