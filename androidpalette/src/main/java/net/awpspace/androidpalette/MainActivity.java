package net.awpspace.androidpalette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.senab.photoview.PhotoView;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private TextView mVibrantTextView;
    private TextView mLightVibrantTextView;
    private TextView mDarkVibrantTextView;
    private TextView mMutedTextView;
    private TextView mLightMutedTextView;
    private TextView mDarkMutedTextView;

    private static final int[] sDrawables = {R.drawable.a1, R.drawable.a2,
            R.drawable.a3};
    private ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();

        mVibrantTextView = (TextView) findViewById( R.id.vibrant );
        mLightVibrantTextView = (TextView) findViewById( R.id.light_vibrant );
        mDarkVibrantTextView = (TextView) findViewById( R.id.dark_vibrant );
        mMutedTextView = (TextView) findViewById( R.id.muted );
        mLightMutedTextView = (TextView) findViewById( R.id.light_muted );
        mDarkMutedTextView = (TextView) findViewById( R.id.dark_muted );

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeImage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeImage(int position) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), sDrawables[position]);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mActionBar.setBackgroundDrawable(new ColorDrawable(palette.getVibrantSwatch().getRgb()));

                setViewSwatch(mVibrantTextView, palette.getVibrantSwatch());
                setViewSwatch(mLightVibrantTextView, palette.getLightVibrantSwatch());
                setViewSwatch(mDarkVibrantTextView, palette.getDarkVibrantSwatch());
                setViewSwatch(mMutedTextView, palette.getMutedSwatch());
                setViewSwatch(mLightMutedTextView, palette.getLightMutedSwatch());
                setViewSwatch(mDarkMutedTextView, palette.getDarkMutedSwatch());

                for(Palette.Swatch swatch : palette.getSwatches()){
                    //do sth with swatch
                }
            }
        });
    }

    private void setViewSwatch(TextView view, Palette.Swatch swatch) {
        if( swatch != null ) {
            view.setTextColor( swatch.getTitleTextColor() );
            view.setBackgroundColor( swatch.getRgb() );
            view.setVisibility( View.VISIBLE );
        } else {
            view.setVisibility( View.GONE );
        }
    }

    static class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setImageResource(sDrawables[position]);

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}