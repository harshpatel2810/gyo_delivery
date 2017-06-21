package com.goyo.grocery_goyo.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.goyo.grocery.R;
import com.goyo.grocery_goyo.Adapters.CustomMenuAdapter;
import com.goyo.grocery_goyo.model.MenuItems;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
@RequiresApi(api = Build.VERSION_CODES.N)
public class ResturantProfile extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    protected String ResturantName;
    protected static Integer resturant_id;
    static public TextView QTY;
    static public TextView totalAmount;
    static public ImageView checkout;
    static Intent io;
    private int CART;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static   TextView txtRestauranName,txtRestaurantStatus;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_profile);
        //Qty initialization for setting total number of cart in it
        //total Amount initialization
        QTY = (TextView) findViewById(R.id.textAddToCart);
        totalAmount = (TextView) findViewById(R.id.txtTotalAmount);
        io = getIntent();
        //initializing checkout button to proceed for bill
        checkout = (ImageView) findViewById(R.id.CheckOut);
        //Setting Name of the Resturant by going into the next screen
        InitToolBar();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resturant_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    {

    }
    //Method to initialize tool bar and set the name of appropriate restuarant
    private void InitToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View view = getLayoutInflater().inflate(R.layout.restaurant_detail_layout, null);
        txtRestauranName = (TextView) view.findViewById(R.id.txtRestaurantProfileName);
        txtRestaurantStatus = (TextView)view.findViewById(R.id.txtRestaurantStatus);
        txtRestauranName.setText(io.getStringExtra("resturantName"));
        txtRestauranName.setTypeface(EasyFonts.caviarDreamsItalic(this));
        toolbar.addView(view);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        TextView txtDisplayCart;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int mpage;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_resturant_profile, container, false);
            mpage = getArguments().getInt(ARG_SECTION_NUMBER);
            txtDisplayCart = (TextView) rootView.findViewById(R.id.textAddToCart);
            fillData(rootView);
            return rootView;

        }


        private void fillData(View rootView) {

            ArrayList<MenuItems> items = GetMenuList();
            ListView lv = (ListView) rootView.findViewById(R.id.list_menu_items);
            lv.setAdapter(new CustomMenuAdapter(getContext(), items, io.getStringExtra("resturantName")));
        }

        private ArrayList<MenuItems> GetMenuList() {
            ArrayList<MenuItems> item = new ArrayList<>();
            MenuItems mi = new MenuItems();
            switch (mpage) {
                case 1:
                    mi = new MenuItems(1, "Hot and Sour", 150, "");
                    item.add(mi);
                    mi = new MenuItems(2, "Sweet Corn Soup 2", 170, "It will consist baby corn cjcjcjcjc cjcjcjcjcj cjcjcjcjc jjcjcjcj jcjcjc");
                    item.add(mi);
                    mi = new MenuItems(4, "Sweet Corn Soup 4", 170, "It will consist baby corn");
                    item.add(mi);
                    mi = new MenuItems(5, "Sweet Corn Soup 5", 170, "It will consist baby corn");
                    item.add(mi);
                    mi = new MenuItems(6, "Sweet Corn Soup 6", 170, "It will consist baby corn");
                    item.add(mi);
                    mi = new MenuItems(7, "Sweet Corn Soup 7", 170, "It will consist baby corn");
                    item.add(mi);
                    mi = new MenuItems(8, "Sweet Corn Soup 8", 170, "It will consist baby corn");
                    item.add(mi);
                    mi = new MenuItems(9, "Sweet Corn Soup 9", 170, "It will consist baby corn");
                    item.add(mi);
                    mi = new MenuItems(10, "Sweet Corn Soup 10", 170, "It will consist baby corn");
                    item.add(mi);
                    mi = new MenuItems(11, "Sweet Corn Soup 11", 170, "It will consist baby corn");
                    item.add(mi);
                    mi = new MenuItems(12, "Sweet Corn Soup 12", 170, "It will consist baby corn");
                    item.add(mi);
                    break;
                case 2:
                    mi = new MenuItems(13, "Hara Bhara Kabab 13", 150, "It will contain pudina");
                    item.add(mi);
                    mi = new MenuItems(14, "Spring Rolls 14", 170, "It will contain hakka noodles");
                    item.add(mi);
                    break;
                case 3:
                    mi = new MenuItems(15, "Spicy Raita 15", 250, "It will contain curd with some spices");
                    item.add(mi);
                    mi = new MenuItems(16, "Vegetable Salad 16", 190, "Mix Vegetable salad");
                    item.add(mi);
                    mi = new MenuItems(17, "Sweet Raita 17", 350, "Sweet curd raita with mix fruits");
                    item.add(mi);
                    break;

            }
            /*if(mpage==1) {
               mi = new MenuItems("Hot cjjcjcjc and Sour", 150, "It will gggg gggg ggggg ggggg gggggg gggggggggggg");
                item.add(mi);
                mi = new MenuItems("Sweet  cjjcjc Corn Soup", 170, "It will consist baby corn");
                item.add(mi);
            }
            if(mpage==2)
            {
                mi = new MenuItems("Hara Bhara Kabab", 150, "It will contain pudina");
                item.add(mi);
                mi = new MenuItems("Spring Rolls", 170, "It will contain hakka noodles");
                item.add(mi);
            }*/
            return item;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return Category.category.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Category.category[position];
        }
    }
}
