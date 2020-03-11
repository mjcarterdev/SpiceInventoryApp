package SpiceRack.Application.activites;

import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import SpiceRack.databinding.AboutApplicationBinding;

public class AboutActivity extends AppCompatActivity {

    AboutApplicationBinding aboutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutLayout = AboutApplicationBinding.inflate(LayoutInflater.from(this));
        setContentView(aboutLayout.getRoot());

    }
}
