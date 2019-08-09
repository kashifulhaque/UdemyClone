package saif.nidhi.udemyclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    // An arbitrary Request Code because yeah I'm an idiot
    private static final int RC_SIGN_IN = 69;

    // Widgets
    private Button mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all the widgets in the view
        initializeWidgets();

        // Button click listeners are here
        mAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAuth();
            }
        });
    }

    // This method does all the initialization stuff
    private void initializeWidgets() {
        mAuth = findViewById(R.id.btnAuth);
    }

    // This method uses Firebase Authentication, checks the current Auth state of the user
    // Learn more @ https://firebase.google.com/docs/auth
    private void checkAuth() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Toast.makeText(this, "Redirect the user to DashboardActivity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Fire the FirebaseUI Auth", Toast.LENGTH_SHORT).show();

            // This chunk of code will start the FirebaseUI activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.PhoneBuilder().build()))
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    // To handle the sign-in response codes
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, "You pressed the back button", Toast.LENGTH_SHORT).show();
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
