package com.smaran.foldablelayout.sample.activities;

import android.os.Bundle;

import com.smaran.android.commons.utils.Views;
import com.smaran.foldablelayout.FoldableListLayout;
import com.smaran.foldablelayout.sample.R;
import com.smaran.foldablelayout.sample.items.PaintingsAdapter;

public class FoldableListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foldable_list);

        FoldableListLayout foldableListLayout = Views.find(this, R.id.foldable_list);
        foldableListLayout.setAdapter(new PaintingsAdapter(this));
    }

}
