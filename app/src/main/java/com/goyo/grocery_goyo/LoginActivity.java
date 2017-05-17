package com.goyo.grocery_goyo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.goyo.grocery.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edNumber)
    EditText edtUid;
    @BindView(R.id.edPassword)
    EditText edtPwd;
    @BindView(R.id.buttonSignin)
    Button btnsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    private void addListeners() {
        //btnsignin.setOnClickListener(this);

    }

    @OnClick(R.id.buttonSignin)
    public void buttonSignin(View v) {
        if (validate()) {

        }

    }

    private boolean validate() {
        String uid = edtUid.getText().toString();
        // check uid is empty
        edtUid.setError(null);
        if (edtUid.getText().toString().trim().equals("")) {
            edtUid.setError(getResources().getString(R.string.userid));
            return false;
        }
        // check password is empty
        String pwd = edtPwd.getText().toString();
        edtPwd.setError(null);
        if (edtPwd.getText().toString().trim().equals("")) {
            edtPwd.setError(getResources().getString(R.string.password));
            return false;
        }
        //Check for correct user
        if (edtUid.getText().toString().trim().equals("7600303880") && edtPwd.getText().toString().trim().equals("admin")) {
            Intent io = new Intent(this,HomeActivity.class);
            startActivity(io);
        }
        return true;
    }

}
