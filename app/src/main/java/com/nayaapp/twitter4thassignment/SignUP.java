package com.nayaapp.twitter4thassignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nayaapp.twitter4thassignment.api.ApiClass;
import com.nayaapp.twitter4thassignment.model.Check;
import com.nayaapp.twitter4thassignment.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUP extends AppCompatActivity {
    String method = "email";
    String Email = "";
    String Username = "";
    EditText semail, susername;
    ImageView sn_Us, sn_Em, back;
    Button btnnext;
    int countUsername = 0;
    int initialbtn = 0;
    boolean chekU=false;
    boolean chekE=false;
    TextView tvChange, sn_emailerror, sn_usererror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
        semail = findViewById( R.id.SUemail );
        susername = findViewById( R.id.SUsernmae );
        back=findViewById( R.id.SBack );
        sn_emailerror = findViewById( R.id.SPassError );
        sn_usererror = findViewById( R.id.SUserError );
        sn_Us = findViewById( R.id.SN_userP );
        sn_Em = findViewById( R.id.SN_emailP );
        btnnext = findViewById( R.id.btnSignup );
        tvChange = findViewById( R.id.textView9 );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Email = bundle.getString( "email" );
            Username = bundle.getString( "username" );
            semail.setText( bundle.getString( "email" ) );
            susername.setText( bundle.getString( "username" ) );
        }
        back.setOnClickListener( new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent back=new Intent( SignUP.this,MainActivity.class );
                                         startActivity( back );
                                     }
                                 }
        );
        btnnext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chekE==true&&chekU==true){

                        User user = new User( Email );
                    Checkuser( user );

                }else{
                    Toast.makeText( SignUP.this, "fill require field with valid information", Toast.LENGTH_SHORT ).show();
                    return;
                }

            }
        } );
        susername.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int countL = susername.length();
                if (count > 0) {
                    if (countUsername >= 0) {
                        countUsername = 50 - countL;
                        sn_usererror.setTextColor( Color.BLACK );
                        sn_usererror.setText( "" + countUsername );
                        sn_Us.setImageResource( R.drawable.ic_checked );
                        chekU=true;
                        Username = susername.getText().toString();
                        return;
                    } else if (countUsername < 0) {
                        countUsername = 50 - countL;
                        sn_usererror.setTextColor( Color.RED );
                        sn_usererror.setText( "Must be 50 characters or fewer." );
                        sn_usererror.append( "      " + countUsername );
                        chekU=false;
                        sn_Us.setImageResource( R.drawable.ic_clear );
                        return;


                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        tvChange.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initialbtn == 0) {
                    method = "phone";
                    semail.setText( "" );
                    initialbtn++;
                    semail.setHint( "used Phone number" );
                    semail.setInputType( InputType.TYPE_CLASS_PHONE );
                    semail.setMaxLines( 13 );
                    tvChange.setText( "use email instead" );
                    return;
                } else {
                    method = "email";
                    semail.setText( "" );
                    initialbtn = 0;
                    semail.setInputType( InputType.TYPE_CLASS_TEXT );
                    semail.setHint( "used Email" );
                    tvChange.setText( "use phone instead" );
                    return;
                }

            }
        } );
        semail.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                switch (method) {
                    case "email":
                        sn_emailerror.setText( "" );
                        if ((semail.getText().toString().toLowerCase().contains( "@" )) && (semail.getText().toString().toLowerCase().contains( ".com" ))) {
                            sn_Em.setImageResource( R.drawable.ic_checked );
                            Email = semail.getText().toString();
                            chekE=true;
                        } else {
                            sn_emailerror.setText( "check your email" );
                            sn_Em.setImageResource( R.drawable.ic_clear );
                            chekE=false;

                        }
                        break;
                    case "phone":
                        sn_emailerror.setText( "" );
                        if ((semail.length() != 10)) {
                            sn_emailerror.setText( "check your number" );
                            sn_Em.setImageResource( R.drawable.ic_clear );
                            chekE=false;
                            return;

                        } else {
                            sn_Em.setImageResource( R.drawable.ic_checked );
                            Email = semail.getText().toString();
                            chekE=true;
                            return;

                        }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

    }

    void Checkuser(User us) {
        ApiClass apiClass = new ApiClass();
        Call<Check> checkCall = apiClass.calls().check( us );
        checkCall.enqueue( new Callback<Check>() {
            @Override
            public void onResponse(Call<Check> call, Response<Check> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText( SignUP.this, "error" + response.code(), Toast.LENGTH_SHORT ).show();
                    Log.d( "error", "error" + response.code() );
                    return;
                }
                Check check = response.body();
                //Toast.makeText( SignUP.this, "user " + check.getStatus(), Toast.LENGTH_SHORT ).show();
                if (check.getStatus().equals( "good to go" )) {
                    Intent next = new Intent( SignUP.this, Costumization.class );
                    next.putExtra( "email", Email );
                    next.putExtra( "username", Username );
                    startActivity( next );
                    return;
                } else {
                    //Toast.makeText( SignUP.this, "user " + check.getStatus(), Toast.LENGTH_SHORT ).show();
                    sn_emailerror.setText( "exited" );
                    sn_emailerror.setTextColor( Color.RED );
                }
            }

            @Override
            public void onFailure(Call<Check> call, Throwable t) {
                Toast.makeText( SignUP.this, "error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
                Log.d( "error", "error   " + t.getLocalizedMessage() );

            }
        } );
    }
}
