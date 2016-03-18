package course.examples.cmsc434doodle;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;


/*
* Color picker plugin taken from source: https://github.com/chiralcode/Android-Color-Picker
* */
public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    DoodleView doodleView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doodleView = (DoodleView) findViewById(R.id.doodleView);

        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.clear();
            }
        });

        Button brushSizeButton = (Button) findViewById(R.id.brushSizeButton);
        brushSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu brushSizePopupMenu = new PopupMenu(MainActivity.this, v);
                brushSizePopupMenu.setOnMenuItemClickListener(MainActivity.this);
                brushSizePopupMenu.inflate(R.menu.brushsize_popup_menu);
                brushSizePopupMenu.show();
            }
        });

        Button colorButton = (Button) findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SOURCE: https://github.com/chiralcode/Android-Color-Picker
                ColorPickerDialog colorPickerDialog = new ColorPickerDialog(MainActivity.this, 0, new ColorPickerDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        doodleView.setColor(color);
                    }
                });
                colorPickerDialog.show();
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                doodleView.fillBackground();
            }
        });
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.brushSize1:
                doodleView.setBrushSize(1.0f);
                return true;
            case R.id.brushSize4:
                doodleView.setBrushSize(4.0f);
                return true;
            case R.id.brushSize16:
                doodleView.setBrushSize(16.0f);
                return true;
            case R.id.brushSize32:
                doodleView.setBrushSize(32.0f);
                return true;
            case R.id.brushSize64:
                doodleView.setBrushSize(64.0f);
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
