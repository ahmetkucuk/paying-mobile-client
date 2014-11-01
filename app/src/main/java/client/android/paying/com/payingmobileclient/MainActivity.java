package client.android.paying.com.payingmobileclient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import fragment.AccountFragment;
import fragment.MainFragment;
import fragment.TableSelectionFragment;

public class MainActivity extends Activity implements WifiP2pManager.ChannelListener, WifiP2pManager.PeerListListener, ActionBar.TabListener{

    public static final String TAG = "wifidirectdemo";
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiP2pDevice device;
    ProgressDialog progressDialog;
    private BroadcastReceiver receiver = null;
    private final IntentFilter intentFilter = new IntentFilter();
    AccountFragment accountFragment = null;
    MainFragment mainFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = bar.newTab();
        tab.setText("Ana Sayfa");
        tab.setTabListener(this);
        bar.addTab(tab);

        ActionBar.Tab tab2 = bar.newTab();
        tab2.setText("Hesaplar-Kartlar");
        tab2.setTabListener(this);
        bar.addTab(tab2);

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);

        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Discovery Initiated",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reasonCode) {
                Toast.makeText(MainActivity.this, "Discovery Failed : " + reasonCode,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new WifiDirectBroadCastReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChannelDisconnected() {

    }


    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        Toast.makeText(MainActivity.this, "askdfhaskdjhf", Toast.LENGTH_SHORT).show();
        if(peers.getDeviceList().size() > 0) {
            Toast.makeText(MainActivity.this, "onPeersAvailable", Toast.LENGTH_SHORT).show();
            device = (WifiP2pDevice)peers.getDeviceList().toArray()[0];
            mainFragment.populateDeviceList(new ArrayList(peers.getDeviceList()));
        }
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        if(tab.getPosition() == 0){
            if(mainFragment == null)
                mainFragment = new MainFragment();
            ft.replace(android.R.id.content, mainFragment);
        }
        else{
            if(accountFragment == null)
                accountFragment = new AccountFragment();
            ft.replace(android.R.id.content, accountFragment);
        }
    }

    public void connectToDevice(WifiP2pDevice deviceToConnect) {

        Log.d(TAG, "connect-to-device in main activity");

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = deviceToConnect.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        manager.connect(channel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "on connect", Toast.LENGTH_SHORT).show();
                initiateTableSelectionFragment();
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Connect failed. Retry.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initiateTableSelectionFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Commit the transaction
        transaction.replace(android.R.id.content, TableSelectionFragment.newInstance("1","2"));
        transaction.addToBackStack("nice");

        transaction.commit();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
