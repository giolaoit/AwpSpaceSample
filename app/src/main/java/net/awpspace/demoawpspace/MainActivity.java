package net.awpspace.demoawpspace;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getApplicationContext() == null) {
            Logger.e(this, "Null roi");
        }
        FacebookSdk.sdkInitialize(getApplicationContext());

        getFbHashKey();

        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();
    }

    public void loginWithFB(View v) {
        List<String> permissionNeeds = Arrays.asList("publish_actions");
        mLoginManager.logInWithPublishPermissions(this, permissionNeeds);
        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Logger.e(this, "Success....: " + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Logger.e(this, "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Logger.e(this, "Error: ");
                exception.printStackTrace();
            }
        });
    }

    private void getFbHashKey() {
        //        GET KEY
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "net.awpspace.demoawpspace",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Logger.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
