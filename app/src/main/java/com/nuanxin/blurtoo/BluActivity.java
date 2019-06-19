package com.nuanxin.blurtoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

/**
 * @author Staff
 * @description
 * @date 2019/4/25
 */
public class BluActivity extends Activity {

    private TextView tvScan;
    private TextView tvShe;
    private TextView tvConnect;
    private String MAC="DA:F9:A6:8D:9A:E8";//马桶板子
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        tvScan = findViewById(R.id.tvScan);
        tvShe = findViewById(R.id.tvShe);
        tvConnect = findViewById(R.id.tvConnect);
        //扫描
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  initData();
                Intent intent=new Intent(BluActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //连接
        tvConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BleConnectOptions options = new BleConnectOptions.Builder()
                        .setConnectRetry(3)   // 连接如果失败重试3次
                        .setConnectTimeout(30000)   // 连接超时30s
                        .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                        .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                        .build();
                MyApplication.getBluetoothClient().connect(MAC,options, new BleConnectResponse() {
                    @Override
                    public void onResponse(int code, BleGattProfile data) {
                        if (code == 0) {
                            Toast.makeText(BluActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    /**
     * 蓝牙扫描
     */
    private void initData() {

        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();

        MyApplication.getBluetoothClient().search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {

            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                Beacon beacon = new Beacon(device.scanRecord);
               // if (device.getName().contains("NanochapEM")){
                    tvShe.append("\n" + device.getName() + "==>" + device.getAddress() + "\n");
              //  }
                // BluetoothLog.v(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));
            }

            @Override
            public void onSearchStopped() {

            }

            @Override
            public void onSearchCanceled() {

            }
        });
    }

    /**
     * 蓝牙连接
     */
    private void initConnectData() {

    }
}
