package com.goyo.grocery_goyo;

import android.content.Intent;
import android.icu.util.ULocale;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
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

import android.widget.ListView;
import android.widget.TextView;

import com.goyo.grocery.R;

import java.util.ArrayList;

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

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_profile);
        Intent io = getIntent();
        setTitle(io.getStringExtra("resturantName"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
       InitAppBar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resturant_profile, menu);
        return true;
    }
    public void InitAppBar()
    {
        View v=getLayoutInflater().inflate(R.layout.add_to_cart_xml,null);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
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
            mpage=getArguments().getInt(ARG_SECTION_NUMBER);
            fillData(rootView);
            return rootView;

        }


        private void fillData(View rootView){

            ArrayList<MenuItems> items=GetMenuList();
            ListView lv=(ListView)rootView.findViewById(R.id.list_menu_items);
            lv.setAdapter(new CustomMenuAdapter(getContext(),items));
        }

        private ArrayList<MenuItems> GetMenuList() {
            ArrayList<MenuItems> item=new ArrayList<>();
            MenuItems mi=new MenuItems();
           switch(mpage)
           {
               case 1:
                   mi = new MenuItems("Hot and Sour", 150, "");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn cjcjcjcjc cjcjcjcjcj cjcjcjcjc jjcjcjcj jcjcjc");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);
                   mi = new MenuItems("Sweet Corn Soup", 170, "It will consist baby corn");
                   item.add(mi);

                   break;

                   case 2:
                       mi = new MenuItems("Hara Bhara Kabab", 150, "It will contain pudina");
                       item.add(mi);
                       mi = new MenuItems("Spring Rolls", 170, "It will contain hakka noodles");
                       item.add(mi);
                       break;
                 case 3:
                   mi = new MenuItems("Spicy Raita", 250, "It will contain curd with some spices");
                   item.add(mi);
                   mi = new MenuItems("Vegetable Salad", 190, "Mix Vegetable salad");
                   item.add(mi);
                   mi=new MenuItems("Sweet Raita",350,"Sweet curd raita with mix fruits");
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
            return  item;
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
