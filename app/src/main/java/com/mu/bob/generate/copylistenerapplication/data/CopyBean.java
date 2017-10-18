package com.mu.bob.generate.copylistenerapplication.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/18.
 */

public class CopyBean implements Serializable {
    private int id;
    private String text;
    private String timestample;

    public static final String TABLE_NAME="texttable";
    public static final String ID="_id";
    public static final String TEXT="text";
    public static final String TIME_STAMPLE="time";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestample() {
        return timestample;
    }

    public void setTimestample(String timestample) {
        this.timestample = timestample;
    }
}
