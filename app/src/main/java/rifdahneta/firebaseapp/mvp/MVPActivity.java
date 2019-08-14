package rifdahneta.firebaseapp.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rifdahneta.firebaseapp.R;
import rifdahneta.firebaseapp.mvp.model.KalkulatorModel;
import rifdahneta.firebaseapp.mvp.presenter.KalkulatorPresenter;
import rifdahneta.firebaseapp.mvp.view.KalkulatorView;

public class MVPActivity extends AppCompatActivity implements KalkulatorView {

    @BindView(R.id.edtangka1)
    EditText edtangka1;
    @BindView(R.id.edtangka2)
    EditText edtangka2;

   private KalkulatorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);
     presenter = new KalkulatorPresenter(this, new KalkulatorModel());
    }

    public void onHitung(View view) {
        int angka1 = Integer.parseInt(edtangka1.getText().toString());
        int angka2 = Integer.parseInt(edtangka2.getText().toString());

        presenter.prosesHitung(angka1,angka2);
    }

    @Override
    public void hasil(String hasilPenjumlahan) {
        Toast.makeText(this, "hasilnya : "+hasilPenjumlahan, Toast.LENGTH_SHORT).show();
    }
}
