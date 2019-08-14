package rifdahneta.firebaseapp.mvp.presenter;

import rifdahneta.firebaseapp.mvp.MVPActivity;
import rifdahneta.firebaseapp.mvp.model.KalkulatorModel;
import rifdahneta.firebaseapp.mvp.view.KalkulatorView;


public class KalkulatorPresenter {
    KalkulatorView view;
    KalkulatorModel model;

    public KalkulatorPresenter(MVPActivity mvpActivity, KalkulatorModel kalkulatorModel) {
        view = mvpActivity;
        model = kalkulatorModel;
    }

    public void prosesHitung(int angka1, int angka2) {
        int hasil = angka1+angka2;
        view.hasil(String.valueOf(hasil));
    }
}
