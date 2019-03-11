package com.mezzsy.copyzhihunews.view;

import com.mezzsy.copyzhihunews.bean.ContentBean;
import com.mezzsy.copyzhihunews.bean.ExtraBean;

public interface ContentView {
    void load(ContentBean bean);

    void extra(ExtraBean bean);
}
