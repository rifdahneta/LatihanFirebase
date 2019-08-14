package rifdahneta.firebaseapp.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rifdahneta.firebaseapp.R;
import rifdahneta.firebaseapp.mvp.presenter.RegisterPresenter;
import rifdahneta.firebaseapp.mvp.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign_up_button)
    Button signUpButton;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.sign_in_button)
    Button signInButton;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseUser user;
    RegisterView registerView;
    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        presenter = new RegisterPresenter(this);

        mAuth = FirebaseAuth.getInstance();

        listener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    presenter.verifikasiEmail();
                }

            }
        };
    }

//    private void verifikasiEmail() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isComplete()){
//                    FirebaseAuth.getInstance().signOut();
//                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                    finish();
//                }else {
//                    //restart activity
//                    overridePendingTransition(0,0);
//                    finish();
//                    overridePendingTransition(0,0);
//                }
//            }
//        });
//    }

    @OnClick({R.id.sign_up_button, R.id.btn_reset_password, R.id.sign_in_button})
    public void onViewClicked(View view) {
        String em = email.getText().toString();
        String pw = password.getText().toString();

        switch (view.getId()) {
            case R.id.sign_up_button:
                if (TextUtils.isEmpty(em)){
                    email.setError(getText(R.string.requireedittext));
                } else if (TextUtils.isEmpty(pw)){
                    password.setError(getText(R.string.requireedittext));
                } else {
//                    registerView.setProgressVisibility(true);
                    progressBar.setVisibility(View.VISIBLE);
//                    mAuth.createUserWithEmailAndPassword(em,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            progressBar.setVisibility(View.GONE);
//                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                            Toast.makeText(RegisterActivity.this, "berhasil register", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(RegisterActivity.this, "gagal : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });

                    presenter.register(em,pw);
                }
                break;
            case R.id.btn_reset_password:
                break;
            case R.id.sign_in_button:
       startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
        }
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

    @Override
    public void registerError(Exception e) {
        Toast.makeText(RegisterActivity.this, "gagal : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccess() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        Toast.makeText(RegisterActivity.this, "berhasil register", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if (visibility)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }

    @Override
    public void verificationComplete() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();

    }

    @Override
    public void verificationFailed() {
        overridePendingTransition(0,0);
        finish();
        overridePendingTransition(0,0);

    }
}
