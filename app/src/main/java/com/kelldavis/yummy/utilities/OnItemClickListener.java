package com.kelldavis.yummy.utilities;

import android.view.View;

public interface OnItemClickListener<MODEL> {

    void onClick(MODEL model, View view);
}
