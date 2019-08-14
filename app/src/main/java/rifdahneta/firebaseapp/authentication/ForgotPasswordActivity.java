package rifdahneta.firebaseapp.authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rifdahneta.firebaseapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.btn_back1)
    Button btnBack1;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick({R.id.btn_reset_password, R.id.btn_back1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_password:
                progressBar.setVisibility(View.VISIBLE);
                String emailLama = email.getText().toString();
                if (!emailLama.equals("")){
                    mAuth.sendPasswordResetEmail(emailLama).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "berhasil ganti email", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                finish();
                            }else {
                                Toast.makeText(ForgotPasswordActivity.this, "gagal ganti email" + task.getException(), Toast.LENGTH_SHORT).show();

                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ForgotPasswordActivity.this, "gagal " +e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    email.setError("tidak boleh kosong");
                    progressBar.setVisibility(View.GONE);
                }

                break;
            case R.id.btn_back1:
                finish();
                break;
        }
    }

}
