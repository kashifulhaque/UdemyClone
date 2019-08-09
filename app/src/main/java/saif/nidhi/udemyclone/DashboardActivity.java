package saif.nidhi.udemyclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

@SuppressWarnings("FieldCanBeLocal")
public class DashboardActivity extends AppCompatActivity {

    // Widgets
    private TextView mUsername;
    private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeWidgets();
        checkAuth();

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initializeWidgets() {
        mUsername = findViewById(R.id.tvUsername);
        mLogout = findViewById(R.id.btnLogout);
    }

    private void checkAuth() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mUsername.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
    }
}
