package com.android.ex_machina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.ex_machina.Adapters.CourseAdapter;
import com.android.ex_machina.Adapters.HomeItemsAdapter;
import com.android.ex_machina.Models.ItemCourses;
import com.android.ex_machina.Models.User;
import com.android.ex_machina.SharedUi.BaseSharedActivity;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoursesActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public CardView cardToolbar;
    User actualUser;

    DatabaseReference reference;
    private CourseAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public CircleImageView avatarToolbar;

    private ArrayList<ItemCourses> cours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        toolbar = findViewById(R.id.toolbar);
        cardToolbar = findViewById(R.id.cardToolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(CoursesActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        //initToolbar();
        initView();

        /* BottomNavigation Section */
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intent1 = new Intent(CoursesActivity.this, MainActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.ic_courses:
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.ic_favoris:
                        Intent intent2 = new Intent(CoursesActivity.this, FavorisActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.ic_offline:
                        Intent intent4 = new Intent(CoursesActivity.this, OfflineActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                }


                return false;
            }
        });

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        /*for (int i = 0; i < menuView.getChildCount(); i++) {

            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 21, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 21, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }*/


        /* BottomNavigation Section */
    }

    /*private void initToolbar(){

        actualUser = new User();
        avatarToolbar = (CircleImageView) findViewById(R.id.avatar_image);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                String photoUrl = profile.getPhotoUrl().toString();

                actualUser.setImageurl(photoUrl);
                Picasso.with(CoursesActivity.this).load(actualUser.getImageurl()).into(avatarToolbar);
                actualUser.setName(name);
                actualUser.setEmail(email);


                // Setting the Title Toolbar:
                getSupportActionBar().setTitle("Tous les Cours");
                toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            }
        }

    }*/


    private void initView(){

        reference = FirebaseDatabase.getInstance().getReference().child("facultes").child("liste");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                cours = new ArrayList<>();
                int i = 0;
                int[] backgroundColors = new int[]{

                        Color.parseColor("#37464F"),
                        Color.parseColor("#546E79"),
                        Color.parseColor("#402419"),
                        Color.parseColor("#61433C"),
                        Color.parseColor("#f44336"),
                        Color.parseColor("#e53935"),
                        Color.parseColor("#4caf50"),
                        Color.parseColor("#3f51b5"),
                        Color.parseColor("#673ab7"),
                        Color.parseColor("#cddc39"),
                        Color.parseColor("#37464F"),
                };

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ItemCourses item = dataSnapshot1.getValue(ItemCourses.class);
                    item.setBackground(backgroundColors[i]);
                    Log.d("item", item.getFid());
                    cours.add(item);
                    ++i;
                }

                adapter = new CourseAdapter(cours, CoursesActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
