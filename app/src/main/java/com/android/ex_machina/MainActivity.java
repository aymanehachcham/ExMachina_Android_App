package com.android.ex_machina;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ex_machina.Adapters.HomeItemsAdapter;
import com.android.ex_machina.Helpers.HidingScrollListener;
import com.android.ex_machina.Models.ItemHome;
import com.android.ex_machina.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient mGoogleSignInClient;

    public TextView nameDisplayed, emailDisplayed, actualUserConnected;
    public CircleImageView image, avatarToolbar;
    public Toolbar toolbar;
    public CardView cardToolbar;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ItemHome> articles = new ArrayList<>();
    private HomeItemsAdapter adapter;
    private BottomNavigationView bottomNavigationView;


    User actualUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //testt
        toolbar = findViewById(R.id.toolbar);
        cardToolbar = findViewById(R.id.cardToolbar);
        setSupportActionBar(toolbar);


        // Populating the Articles:
        articles.add(new ItemHome("Mes Cours", "Trouvez vos cours personnalisés", R.drawable.ic_books_white, Color.parseColor("#FF9400")));
        articles.add(new ItemHome("Blog", "Dernières actualités du club", R.drawable.ic_courses, Color.parseColor("#007AFF")));
        articles.add(new ItemHome("Scolarité", "Procédures et Contacts", R.drawable.ic_scolar, Color.parseColor("#25AF5F")));
        articles.add(new ItemHome("Informations Utiles", "Cherchez de l'aide", R.drawable.ic_info, Color.parseColor("#FF3A30")));
        articles.add(new ItemHome("Transport", "Tout vos circuits", R.drawable.ic_i, Color.parseColor("#5755D5")));
        articles.add(new ItemHome("Bibliothèques", "Trouvez vos livres", R.drawable.ic_book, Color.parseColor("#795548")));

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        adapter = new HomeItemsAdapter(articles, MainActivity.this);
        recyclerView.setAdapter(adapter);

        initClickListnerOnItemCards();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        /* BottomNavigation Section */
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.ic_courses:
                        Intent intent1 = new Intent(MainActivity.this, CoursesActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.ic_favoris:
                        Intent intent2 = new Intent(MainActivity.this, FavorisActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.ic_offline:
                        Intent intent4 = new Intent(MainActivity.this, OfflineActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                }


                return false;
            }
        });

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        for (int i = 0; i < menuView.getChildCount(); i++) {

            final View iconView = menuView.getChildAt(i).findViewById(com.google.android.material.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 23, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 23, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

    }

    // Methods for Hiding and Showing Toolbar:
    private void hideViews() {

        // For the Card Toolbar to Hide
        cardToolbar.animate().translationY(-cardToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        // For the BottomNavigationView to Hide
        bottomNavigationView.animate().translationY(bottomNavigationView.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();

    }


    // Seeting the OnclickItemListener for each click on the Main Cards :
    private void initClickListnerOnItemCards(){

        adapter.setOnItemClickListener(new HomeItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, CoursesActivity.class);

                ItemHome article = articles.get(position);
                intent.putExtra("title", article.getTitle());
                intent.putExtra("Description", article.getDescription());
                intent.putExtra("Img",  article.getProfile());
                intent.putExtra("Background",  article.getBackground());


                startActivity(intent);
            }
        });

    }

    private void showViews() {

        // For the Card Toolbar to Show
        cardToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

        // For the BottomNavigationView to Show
        bottomNavigationView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        nameDisplayed = (TextView) findViewById(R.id.nameUser);//Affichage au niveau du drawer
        image = (CircleImageView) findViewById(R.id.imageUser);//Affichage de la photo de profile
        avatarToolbar = (CircleImageView) findViewById(R.id.avatar_image);
        emailDisplayed = (TextView) findViewById(R.id.emailUser);

        actualUser = new User();

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
                Picasso.with(MainActivity.this).load(actualUser.getImageurl()).into(image);
                Picasso.with(MainActivity.this).load(actualUser.getImageurl()).into(avatarToolbar);
                actualUser.setName(name);
                actualUser.setEmail(email);

                nameDisplayed.setText(actualUser.getName());
                emailDisplayed.setText(actualUser.getEmail());


                // Setting the Title Toolbar:
                getSupportActionBar().setTitle("Hi " + name);
                toolbar.setTitleTextColor(getResources().getColor(R.color.black));
            }
        }


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.actual_user) {
            // Handle the camera action
        } else if (id == R.id.nav_filiere) {

        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_info) {


        } else if(id == R.id.nav_feedback) {

        } else if (id == R.id.nav_deconnect) {
            // Firebase sign out
            signOut();

            // Google sign out
            mGoogleSignInClient.signOut();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Facebook Sign out
        //LoginManager.getInstance().logOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //updateUI(null);
                    }
                });
    }

}
