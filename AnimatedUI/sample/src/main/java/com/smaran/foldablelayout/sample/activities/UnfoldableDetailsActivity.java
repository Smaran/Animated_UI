package com.smaran.foldablelayout.sample.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.texts.SpannableBuilder;
import com.alexvasilkov.android.commons.utils.Views;
import com.smaran.foldablelayout.UnfoldableView;
import com.smaran.foldablelayout.sample.items.Painting;
import com.smaran.foldablelayout.sample.items.PaintingsAdapter;
import com.smaran.foldablelayout.shading.GlanceFoldShading;
import com.squareup.picasso.Picasso;

public class UnfoldableDetailsActivity extends BaseActivity {

    private ListView mListView;
    private View mListTouchInterceptor;
    private View mDetailsLayout;
    private UnfoldableView mUnfoldableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smaran.foldablelayout.sample.R.layout.activity_unfoldable_details);

        mListView = Views.find(this, com.smaran.foldablelayout.sample.R.id.list_view);
        mListView.setAdapter(new PaintingsAdapter(this));

        mListTouchInterceptor = Views.find(this, com.smaran.foldablelayout.sample.R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);

        mDetailsLayout = Views.find(this, com.smaran.foldablelayout.sample.R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);

        mUnfoldableView = Views.find(this, com.smaran.foldablelayout.sample.R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), com.smaran.foldablelayout.sample.R.drawable.unfold_glance);
        mUnfoldableView.setFoldShading(new GlanceFoldShading(this, glance));

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Painting painting) {
        ImageView image = Views.find(mDetailsLayout, com.smaran.foldablelayout.sample.R.id.details_image);
        TextView title = Views.find(mDetailsLayout, com.smaran.foldablelayout.sample.R.id.details_title);
        TextView description = Views.find(mDetailsLayout, com.smaran.foldablelayout.sample.R.id.details_text);

        Picasso.with(this).load(painting.getImageId()).into(image);
        title.setText(painting.getTitle());

        SpannableBuilder builder = new SpannableBuilder(this);
        builder
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(com.smaran.foldablelayout.sample.R.string.year).append(": ")
                .clearStyle()
                .append(painting.getYear()).append("\n")
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(com.smaran.foldablelayout.sample.R.string.location).append(": ")
                .clearStyle()
                .append(painting.getLocation());
        description.setText(builder.build());

        mUnfoldableView.unfold(coverView, mDetailsLayout);
    }

}
