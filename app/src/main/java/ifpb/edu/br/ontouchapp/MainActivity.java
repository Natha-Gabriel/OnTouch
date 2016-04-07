package ifpb.edu.br.ontouchapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnTouchListener, SensorEventListener {

    TextView txt;
    private TextView textViewX;
    private TextView textViewY;
    private TextView textViewZ;
    private TextView textViewDetail;
    private ImageView imageView;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.textView);
        txt.setOnTouchListener(this);

        textViewX = (TextView) findViewById(R.id.text_view_x);
        textViewY = (TextView) findViewById(R.id.text_view_y);
        textViewZ = (TextView) findViewById(R.id.text_view_z);
        textViewDetail = (TextView) findViewById(R.id.text_view_detail);
        imageView = (ImageView) findViewById(R.id.imagem);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){

            //Primeiro Movimento
            case MotionEvent.ACTION_DOWN:{
                imageView.setImageResource(R.drawable.balao_azul);
                txt.setText("Ativando um evento Touch!");
                break;
            }


            //Deslizando o dedo sobre a tela
            case MotionEvent.ACTION_MOVE:{
                imageView.setImageResource(R.drawable.balao_vermelho);
                txt.setText("Deslizando!");
                break;
            }


            //Retirando o dedo da tela
            case MotionEvent.ACTION_UP:{
                imageView.setImageResource(R.drawable.balao_roxo);
                txt.setText("Retirando o dedo da tela!");
                break;
            }
        }

        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Float x = event.values[0];
        Float y = event.values[1];
        Float z = event.values[2];


        textViewX.setText("Posição X: " + x.intValue());
        textViewY.setText("Posição Y: " + y.intValue());
        textViewZ.setText("Posição Z: " + z.intValue());

        if(y < 0) { // O dispositivo esta de cabeça pra baixo
            if(x > 0)
                textViewDetail.setText("Virando para ESQUERDA ficando INVERTIDO");
            if(x < 0)
                textViewDetail.setText("Virando para DIREITA ficando INVERTIDO");
        } else {
            if(x > 0)
                textViewDetail.setText("Virando para ESQUERDA ");
            if(x < 0)
                textViewDetail.setText("Virando para DIREITA ");
        }
    }
}
