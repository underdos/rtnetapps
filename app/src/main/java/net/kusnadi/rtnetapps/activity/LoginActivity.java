package net.kusnadi.rtnetapps.activity;

import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import net.kusnadi.rtnetapps.R;
import net.kusnadi.rtnetapps.entity.LoginRequest;
import net.kusnadi.rtnetapps.entity.LoginResponse;
import net.kusnadi.rtnetapps.entity.User;
import net.kusnadi.rtnetapps.helper.DialogHelper;
import net.kusnadi.rtnetapps.helper.SessionManagerHelper;
import net.kusnadi.rtnetapps.helper.RetrofitClientHelper;
import net.kusnadi.rtnetapps.service.UserService;

import java.io.IOException;

import retrofit2.Response;


public class LoginActivity extends BaseActivity {

    private UserLoginTask mAuthTask = null;
    private SessionManagerHelper sessionManagerHelper;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private View mProgressView;
    private View mLoginFormView;
    private UserService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManagerHelper = new SessionManagerHelper(LoginActivity.this);

        // Set a service api class
        mService = RetrofitClientHelper.getApiService();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            DialogHelper.progressSpinner(LoginActivity.this, "Authenticating....","");
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Context context;
        private Boolean loginStatus;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            context = getApplicationContext();
            loginStatus = false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                Response<LoginResponse> response = mService.login(new LoginRequest(mEmail,mPassword)).execute();
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful() && loginResponse.getStatus() == 0){
                    User user = loginResponse.getResponse();
                    Log.d("RESPONSEHEADER", loginResponse.toString());
                    Log.d("RESPONSEBODY", user.toString());
                    if (mEmail.equals(user.getUsername())) {
                        sessionManagerHelper.createSession(user.getEmail(), user);
                        loginStatus = true;
                    }

                } else {
                   loginStatus = false;
                }

            } catch (IOException ex){
                ex.printStackTrace();
            }

            return loginStatus;

        }

        @Override
        protected void onPostExecute(Boolean success) {
            mAuthTask = null;
            DialogHelper.progressSpinner(false);
            Log.d("TEST1", ""+success);
            if (success && success != null) {
                context.startActivity(new Intent(context, HomeActivity.class));
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            DialogHelper.progressSpinner(false);
        }
    }
}

