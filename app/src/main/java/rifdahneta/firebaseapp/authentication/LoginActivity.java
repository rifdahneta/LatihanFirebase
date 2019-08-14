package rifdahneta.firebaseapp.authentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rifdahneta.firebaseapp.MainActivity;
import rifdahneta.firebaseapp.R;
import rifdahneta.firebaseapp.mvp.MVPActivity;


public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.logingoogle)
    Button logingoogle;
    @BindView(R.id.loginanonymous)
    Button loginanonymous;
    @BindView(R.id.loginphone)
    Button loginphone;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
user =FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        listener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    if (user.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "selamat anda berhasil login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                } else{
                    mAuth.signOut();
                }
            }
        };
    }

    @OnClick({R.id.btn_login, R.id.logingoogle, R.id.loginanonymous, R.id.loginphone, R.id.btn_reset_password, R.id.btn_signup})
    public void onViewClicked(View view) {
        String em = email.getText().toString();
        String pw = password.getText().toString();

        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(em)){
                    email.setError(getText(R.string.requireedittext));
                } else if (TextUtils.isEmpty(pw)){
                    password.setError(getText(R.string.requireedittext));
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(em,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isComplete()){
                                progressBar.setVisibility(View.GONE);
                                if (user!=null&&user.isEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else{
                                    Toast.makeText(LoginActivity.this, "cek email untuk verifikasi", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "gagal login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "gagal login", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                break;
            case R.id.logingoogle:
                startActivity(new Intent(this, LoginGoogleActivity.class));
                break;
            case R.id.loginanonymous:
                startActivity(new Intent(this, MVPActivity.class));
                break;
            case R.id.loginphone:
                startActivity(new Intent(LoginActivity.this, LoginPhoneActivity.class));
                break;
            case R.id.btn_reset_password:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            case R.id.btn_signup:
               startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder keluar = new AlertDialog.Builder(this);
                keluar.setTitle("keluar aplikasi");
                keluar.setMessage("Anda yakin ingin keluar aplikasi?");
                keluar.setIcon(R.mipmap.ic_launcher);
                keluar.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                });

                keluar.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                keluar.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(listener!=null){
            mAuth.removeAuthStateListener(listener);
        }
    }
}
