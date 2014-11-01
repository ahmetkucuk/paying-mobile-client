package fragment;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import client.android.paying.com.payingmobileclient.R;


public class MainFragment extends Fragment {

    private Activity activity;
    private ListView listView;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void populateDeviceList(ArrayList<WifiP2pDevice> devices){
        DeviceListAdapter adapter = new DeviceListAdapter(activity,devices);
        listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) view.findViewById(R.id.deviceList);
        return view;
    }

    private class DeviceListAdapter extends ArrayAdapter<WifiP2pDevice>{
        private Context context;
        private List<WifiP2pDevice> devices;

        public DeviceListAdapter(Context context, ArrayList<WifiP2pDevice> devices) {
            super(context, android.R.layout.simple_list_item_1, devices);

            this.context = context;
            this.devices = devices;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.device_listview_layout, parent, false);

            TextView textView = (TextView) convertView.findViewById(R.id.deviceName);
            textView.setText(devices.get(position).deviceName);

            return convertView;
        }
    }

}

