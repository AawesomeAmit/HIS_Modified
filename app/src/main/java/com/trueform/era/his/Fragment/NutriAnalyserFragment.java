package com.trueform.era.his.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.trueform.era.his.Activity.AchievedNutrientGraphActivity;
import com.trueform.era.his.Activity.AverageNutrientReportActivity;
import com.trueform.era.his.Activity.IdealNutrientIntakeActivity;
import com.trueform.era.his.R;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NutriAnalyserFragment extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;

    private int[] tabIcons = {
            R.drawable.rda_achivement_icon,
            R.drawable.todaysrecommendation_icon_white,
            R.drawable.foodlist_icon_white
    };

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SectionsPagerAdapter2 mSectionsPagerAdapter2;

    int mDay = 0, mMonth = 0, mYear = 0;
    Bundle bundle;

    ImageView ivCalender;
    TextView tvDate;

    Calendar c;

    private ViewPager mViewPager;

    public static String NUTRI_TOKEN = "AGTRIOPLKJRTYHNMJHF458GDETIOHHKA456978ADFHJHW";

    String date = "";

    private SharedPrefManager sharedPrefManager;

    public static int check = 0;

   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_nutri_analyser2, container, false);

        init(view);

        setListeners();

        return view;
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nutri_analyser2);

        init();

        setListeners();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        Menu menu = navigationView.getMenu();

        for (int i = 0; i <= 3; i++) {
            if (i == 0) {
                menu.add("Achieved Nutrient Graph");
                menu.getItem(i).setIcon(getResources().getDrawable(R.drawable.rda_achivement_icon));
            }
            else if (i == 1) {
                menu.add("Average Nutrient Report");
                menu.getItem(i).setIcon(getResources().getDrawable(R.drawable.rda_achivement_icon));
            }
            else if (i == 2) {
                menu.add("Ideal Nutrient Intake");
                menu.getItem(i).setIcon(getResources().getDrawable(R.drawable.rda_achivement_icon));
            }
        }
    }

    private void init(){

        bundle = new Bundle();

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        sharedPrefManager = SharedPrefManager.getInstance(mActivity);

//        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        mSectionsPagerAdapter2 = new SectionsPagerAdapter2(getSupportFragmentManager());

        tvDate = findViewById(R.id.tvDate);

        ivCalender = findViewById(R.id.ivCalender);

        mViewPager = findViewById(R.id.container);

        mSectionsPagerAdapter2.addFragment(new AchievedRda(),"Tab1",0);
        mSectionsPagerAdapter2.addFragment(new TodayRecomndDiet(),"Tab2",1);
        mSectionsPagerAdapter2.addFragment(new FoodList(),"Tab3",2);

//        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setAdapter(mSectionsPagerAdapter2);

        date = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH);

     //   sharedPrefManager.putString("intakeDate",date);

        SharedPrefManager.getInstance(mActivity).setIntakeDate(date);

        tvDate.setText(Utils.formatDateNew(date));

        bundle.putString("cal", mYear + "-" + mMonth + "-" + mDay);

        tabLayout = findViewById(R.id.tabs);
        //tabLayout.setSelectedTabIndicatorColor(Integer.parseInt("#FA9600"));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2){
                    check = 4;
                }

                /*if (tab.getPosition() == 1){
                    check = 4;
                }*/

                mViewPager.setAdapter(mSectionsPagerAdapter2);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setupTabIcons();
    }

    private void setListeners(){
        ivCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;

                                date = year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth;

                                tvDate.setText(Utils.formatDateNew(date));

                                SharedPrefManager.getInstance(mActivity).setIntakeDate(date);
                                //sharedPrefManager.putString("intakeDate", date);

                                if (check == 1){
                                    if (ConnectivityChecker.checker(mActivity)) {

                                        AchievedRda.hitGetAllNutrient(mActivity);
                                        AchievedRda.getNutrientValues();


                                    } else {
                                        Toast.makeText(mActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if (check == 2){
                                    if (ConnectivityChecker.checker(mActivity)) {

                                        NutriAnalyserIntakeDiet.hitGetRequiredSupplement(mActivity);

                                    } else {
                                        Toast.makeText(mActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                else if (check == 3){
                                    if (ConnectivityChecker.checker(mActivity)) {

                                        HealthPrediction.hitGetNutrientLevel(mActivity);
                                    } else {
                                        Toast.makeText(mActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                    }
                                }else if (check == 4){
                                    if (ConnectivityChecker.checker(mActivity)) {

                                        FoodList.hitFoodListApi(mActivity);
                                    } else {
                                        Toast.makeText(mActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }
                        }, mYear, mMonth, mDay);
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            }
        });

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

        //to remove tab
      //  mSectionsPagerAdapter2.removeFragment(1);


        ///////////////////// not working ////////////
//        View view = tabLayout.getTabAt(1).getCustomView();
//        view.setVisibility(View.GONE);
//        tabLayout.removeTabAt(1);
//        tab_layout.addTab(tab_layout.newTab().setCustomView(view));
    }



    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }*/

    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Nutri Analyzer");
    }*/

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            bundle.putString("cal", mYear + "-" + (mMonth + 1) + "-" + mDay);
            switch (position) {
                case 0:

                    AchievedRda tab3 = new AchievedRda();
                    tab3.setArguments(bundle);

                    //((DashBoard)getApplicationContext()).displayView(7);

                    return tab3;

                case 1:
                    TodayRecomndDiet tab4 = new TodayRecomndDiet();
                    tab4.setArguments(bundle);
                    return tab4;
                case 2:
                    FoodList tab5 = new FoodList();
                    tab5.setArguments(bundle);

                 //   FoodList.hitFoodListApi();
                    return tab5;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
//            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tab1";
                case 1:
                    return "Tab2";
                case 2:
                    return "Tab3";
            }
            return null;
        }

    }

    public class SectionsPagerAdapter2 extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter2(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title, int position) {

            bundle.putString("cal", mYear + "-" + (mMonth + 1) + "-" + mDay);
            fragment.setArguments(bundle);

            mFragmentList.add(position, fragment);
            mFragmentTitleList.add(position, title);

            notifyDataSetChanged();
        }

        public void removeFragment(int position) {
            mFragmentList.remove(position);
            mFragmentTitleList.remove(position);
            tabLayout.removeTabAt(position);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (item.getTitle().toString().contains("Achieved Nutrient Graph")){
            startActivity(new Intent(NutriAnalyserFragment.this, AchievedNutrientGraphActivity.class));
        }
        else if (item.getTitle().toString().contains("Average Nutrient Report")){
            startActivity(new Intent(NutriAnalyserFragment.this, AverageNutrientReportActivity.class));
        }

        else if (item.getTitle().toString().contains("Ideal Nutrient Intake")){
            startActivity(new Intent(NutriAnalyserFragment.this, IdealNutrientIntakeActivity.class));
        }

       /* if (id == R.id.nav_patient_list) {
            if (SharedPrefManager.getInstance(Dashboard.this).getHeadID() != 1) {
                Intent intent = new Intent(this, PatientList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }*/

        return true;
    }
}
