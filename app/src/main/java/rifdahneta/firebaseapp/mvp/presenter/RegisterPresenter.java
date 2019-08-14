package rifdahneta.firebaseapp.mvp.presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rifdahneta.firebaseapp.mvp.view.RegisterView;

public class RegisterPresenter {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseUser user;

    RegisterView view;



    public RegisterPresenter(RegisterView view) {
        this.view = view;
    }

    public void verifikasiEmail (){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){
//                    FirebaseAuth.getInstance().signOut();
//                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                    finish();
                    view.verificationComplete();
                }else {
                    //restart activity
                    view.verificationFailed();
//                    overridePendingTransition(0,0);
//                    finish();
//                    overridePendingTransition(0,0);
                }
            }
        });
    }

    public void register(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //progressBar.setVisibility(View.GONE);
                view.setProgressVisibility(false);
                view.registerSuccess();
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                Toast.makeText(RegisterActivity.this, "berhasil register", Toast.LENGTH_SHORT).show();
//                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.setProgressVisibility(false);
                view.registerError(e);
               // progressBar.setVisibility(View.GONE);
//                Toast.makeText(RegisterActivity.this, "gagal : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
