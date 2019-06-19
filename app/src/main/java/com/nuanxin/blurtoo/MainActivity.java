package com.nuanxin.blurtoo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //定义
    private BluetoothAdapter mBluetoothAdapter;
    private TextView text, text2, text3;
    private Button botton;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = this.findViewById(R.id.textView);  //已配对
        text2 = this.findViewById(R.id.textView2); //状态信息
        text3 = this.findViewById(R.id.textView3); //未配对
        botton = this.findViewById(R.id.button); 

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter2);

        botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }
                mBluetoothAdapter.startDiscovery();
                text2.setText("正在搜索...");
            }
        });
        
    }

 
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    

    //定义广播接收
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {    //显示已配对设备
                    text.append("\n" + device.getName() + "==>" + device.getAddress() + "\n");
                } else if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    text3.append("\n" + device.getName() + "==>" + device.getAddress() + "\n");
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {

                text2.setText("搜索完成...");
            }
        }
    };
    
}
