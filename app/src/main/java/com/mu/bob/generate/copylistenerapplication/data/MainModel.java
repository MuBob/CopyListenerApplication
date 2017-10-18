package com.mu.bob.generate.copylistenerapplication.data;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class MainModel {
    public List<CopyBean> getAllFromDb(){
        return DBManager.getInstance().queryList();
    }

    public void deleteById(int id) {
        DBManager.getInstance().delete(id);
    }
}
