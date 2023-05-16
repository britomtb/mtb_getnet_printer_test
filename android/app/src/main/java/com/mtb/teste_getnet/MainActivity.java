package com.mtb.teste_getnet;

import android.content.Intent;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    final String CHANNEL = "com.mtb/printer";
    final int REQUEST_TEST_PRINTER = 1000;
    MethodChannel.Result resultActivity;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler((call, result) -> {
                    resultActivity = result;
                    switch (call.method) {
                        case "TESTE_PRINTER":
                            startActivityForResult(new Intent(getContext(), PrinterGetnet.class),REQUEST_TEST_PRINTER );
                            break;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TEST_PRINTER:
                if (resultCode == RESULT_OK)
                    resultActivity.success("Impresso com sucesso");
                else
                    resultActivity.success("Atividade cancelada");
                return;
        }
    }
}
