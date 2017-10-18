package com.mu.bob.generate.copylistenerapplication.presenter;

import com.mu.bob.generate.copylistenerapplication.data.CopyBean;
import com.mu.bob.generate.copylistenerapplication.data.MainModel;
import com.mu.bob.generate.copylistenerapplication.ui.IMainView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class MainPresenter {
    private IMainView mView;
    private MainModel mModel;
    private int longClickPosition;

    public MainPresenter(IMainView view) {
        mView=view;
        mModel=new MainModel();
    }


    public void loadList() {
        List<CopyBean> list = mModel.getAllFromDb();
        if(list!=null){
            mView.refreshView(list);
        }
    }

    public void setLongClickPosition(int i) {
        longClickPosition=i;
    }

    public int getLongClickPosition(){
        return longClickPosition;
    }


    public void clickDeleteItem(List<CopyBean> copyBeans) {
        if(longClickPosition>=0){
            mModel.deleteById(copyBeans.get(longClickPosition).getId());
            copyBeans.remove(longClickPosition);
            mView.refreshView(copyBeans);
        }

    }
}
