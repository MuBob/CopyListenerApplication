package com.mu.bob.generate.copylistenerapplication.ui;

import com.mu.bob.generate.copylistenerapplication.data.CopyBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public interface IMainView {
    void refreshView(List<CopyBean> list);

    void refreshView();
}
