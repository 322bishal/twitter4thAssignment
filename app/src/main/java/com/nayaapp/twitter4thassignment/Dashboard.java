package com.nayaapp.twitter4thassignment;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.nayaapp.twitter4thassignment.api.ApiClass;
import com.nayaapp.twitter4thassignment.model.UsersInfo;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Camera cm = new Camera();
    Login la = new Login();
    TextView tx_nmae,txt_email;
    ImageView imageView;
    public static final String base_url = "http://10.0.2.2:3000/";
    String imagePath = base_url + "uploads/" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ui );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        loadCurrentUser();
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        View hView =  navigationView.getHeaderView(0);
        tx_nmae =hView.findViewById( R.id.ppname );
        txt_email =hView.findViewById( R.id.ppemail );
        imageView =hView.findViewById( R.id.ppimageView );
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout( drawer )
                .build();
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );
        NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration );
        NavigationUI.setupWithNavController( navigationView, navController );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.dash_board, menu );
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );
        return NavigationUI.navigateUp( navController, mAppBarConfiguration )
                || super.onSupportNavigateUp();
    }
    private void loadCurrentUser() {
        String token;
        if (la.Token.isEmpty()) {
            token = cm.token;
            //Toast.makeText(getContext(), "token " +token, Toast.LENGTH_SHORT).show();

        } else {
            token = la.Token;
            // Toast.makeText(getContext(), "token " +token, Toast.LENGTH_SHORT).show();

        }

        ApiClass usersAPI = new ApiClass();

        Call<UsersInfo> userCall = usersAPI.calls().getUser( token );
        userCall.enqueue( new Callback<UsersInfo>() {
            @Override
            public void onResponse(Call<UsersInfo> call, Response<UsersInfo> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText( Dashboard.this, "Code " + response.code(), Toast.LENGTH_SHORT ).show();
                    return;
                }
                UsersInfo usersInfo = response.body();
                Toast.makeText( Dashboard.this, " "+ usersInfo.get_id(), Toast.LENGTH_SHORT ).show();
                tx_nmae.setText( usersInfo.getUsername() );
                txt_email.setText( usersInfo.getEmail() );
                String imgPath = imagePath +  usersInfo.getImage();
                try {
                    URL url = new URL(imgPath);
                    imageView.setImageBitmap( BitmapFactory.decodeStream((InputStream) url.getContent()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UsersInfo>call, Throwable t) {
                Toast.makeText( Dashboard.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();

            }
        } );


    }
}
