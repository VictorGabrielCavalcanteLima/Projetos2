package com.meu.gamepad;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class GamepadOverlayService extends Service {
    private WindowManager windowManager;
    private Button btnA;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Define os parâmetros da janela flutuante sobreposta
        int layoutType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutType = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 200;
        params.y = 200;

        // Cria o botão virtual "A" flutuante na tela
        btnA = new Button(this);
        btnA.setText("A");
        btnA.setAlpha(0.7f); // Deixa o botão transparente

        // Ação ao tocar no botão virtual do gamepad
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui vai a comunicação com o driver uinput ou toque simulado
            }
        });

        // Adiciona o botão diretamente na tela do telemóvel
        windowManager.addView(btnA, params);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (btnA != null && windowManager != null) {
            windowManager.removeView(btnA);
        }
    }
}
