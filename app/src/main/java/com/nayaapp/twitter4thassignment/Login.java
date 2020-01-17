package com.nayaapp.twitter4thassignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nayaapp.twitter4thassignment.api.LoginClass;
import com.nayaapp.twitter4thassignment.model.User;
import com.nayaapp.twitter4thassignment.strictMode.StrictModeClass;

public class Login extends AppCompatActivity {
    EditText et_email, et_password;
    ImageButton ib_show_P;
    TextView Tx_sp;
    Button btn_login;
    public static String Token="";
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_activity );
        Tx_sp=findViewById( R.id.Tx_sp );
        et_email = findViewById( R.id.login_email );
        et_password = findViewById( R.id.login_password );
        ib_show_P = findViewById( R.id.btn_SP );
        btn_login = findViewById( R.id.btn_login );
        Tx_sp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent( Login.this,MainActivity.class );
                startActivity( back );
            }
        } );
        ib_show_P.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    et_password.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    i++;
                } else {
                    et_password.setInputType( InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD );
                    i = 0;
                }

            }
        } );
        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty( et_email.getText().toString() )) {
                    if (!TextUtils.isEmpty( et_password.getText().toString() )) {
                        User u = new User( et_email.getText().toString(),
                                et_password.getText().toString() );
login( u );
                    } else {
                        et_password.setError( "empty" );
                    }
                } else {
                    et_email.setError( "empty" );
                }
            }
        } );
    }
    private void login(User u) {
        LoginClass loginClass = new LoginClass();
        StrictModeClass.StrictMode();
        if (loginClass.checkUser(u.getEmail(), u.getPassword())) {
            Intent intent = new Intent( Login.this,Dashboard.class );
          Token= loginClass.Token;
            startActivity( intent );
            //Toast.makeText( this, "welcome "+loginClass.Token,Toast.LENGTH_SHORT ).show();

        } else {
            Toast.makeText(this, "Either username or password is incorrect", Toast.LENGTH_SHORT).show();
           // etUsername.requestFocus();
        }
    }
}
