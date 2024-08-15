package com.sampleadsmaster.newkonsep;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.adsmedia.mastermodul.MasterAdsHelper;

public class MainActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MasterAdsHelper.initializeAds(this,BuildConfig.APPLICATION_ID);
        if (BuildConfig.DEBUG){
            MasterAdsHelper.debugMode(true);
        }
        MasterAdsHelper.showBanner(this, findViewById(R.id.layAds));
        MasterAdsHelper.loadInterstitial(this);
        this.findViewById(R.id.tbShow).setOnClickListener(v -> {
            MasterAdsHelper.showInterstitial(MainActivity.this);
        });

    }
}