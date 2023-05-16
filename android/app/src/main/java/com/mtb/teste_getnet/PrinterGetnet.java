package com.mtb.teste_getnet;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;

import com.getnet.posdigital.PosDigital;
import com.getnet.posdigital.printer.AlignMode;
import com.getnet.posdigital.printer.FontFormat;
import com.getnet.posdigital.printer.IPrinterCallback;

public class PrinterGetnet extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectPosDigitalService();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyPosDigitalService();
    }

    private void destroyPosDigitalService() {
        PosDigital.unregister(getApplicationContext());
    }

    private void connectPosDigitalService() {
        PosDigital.register(getApplicationContext(), callBackBind);
    }

    PosDigital.BindCallback callBackBind = new PosDigital.BindCallback() {
        @Override
        public void onError(Exception e) {
            try {
                PosDigital.unregister(getApplicationContext());
            } catch (Exception err) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }

        @Override
        public void onDisconnected() {
        }

        @Override
        public void onConnected() {
            try {
                handleIntent();
            } catch (Exception e) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    };

    private void handleIntent() throws RemoteException {
        PosDigital.getInstance().getPrinter().init();
        PosDigital.getInstance().getPrinter().defineFontFormat(FontFormat.SMALL);
        for(int i = 0; i < 10; i++)
            PosDigital.getInstance().getPrinter().addText(AlignMode.CENTER, "TESTE LINHA " + (i + 1)+ "\n");

        PosDigital.getInstance().getPrinter().print(new IPrinterCallback.Stub(){
            @Override
            public void onSuccess() throws RemoteException {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(int i) throws RemoteException {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}
