package rifdahneta.firebaseapp.mvp.view;

public interface RegisterView {
    void registerError(Exception e);
    void registerSuccess();
    void setProgressVisibility(boolean visibility);
    void verificationComplete();
    void verificationFailed();
}
