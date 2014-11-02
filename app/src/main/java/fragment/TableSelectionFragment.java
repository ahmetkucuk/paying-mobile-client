package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import client.android.paying.com.payingmobileclient.R;
import client.android.paying.com.payingmobileclient.TableDetailActivity;
import core.Constants;
import core.PayingClient;


public class TableSelectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Context activity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button button;
    private EditText tableNumberEditText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TableSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TableSelectionFragment newInstance(String param1, String param2) {
        TableSelectionFragment fragment = new TableSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public TableSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table_selection, container, false);
        button = (Button)view.findViewById(R.id.askBill);
        tableNumberEditText = (EditText)view.findViewById(R.id.tableNumber);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendRequestAsyncTask(activity, Constants.SERVER_IP, Constants.SERVER_PORT, tableNumberEditText.getText().toString()).execute();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;

    }

    class SendRequestAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private String ip;
        private int port;
        private String message;

        public SendRequestAsyncTask(Context context, String ip, int port, String message) {

            this.context = context;
            this.port = port;
            this.ip = ip;
            this.message = message;
        }

        @Override
        protected void onPreExecute() {

            Toast.makeText(context, "Client starting", Toast.LENGTH_SHORT).show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            return PayingClient.sendRequest(ip, port, message);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent tableDetailIntent = new Intent(activity, TableDetailActivity.class);
            tableDetailIntent.putExtra(Constants.TABLE_DETAIL_JSON_STRING, s);
            startActivity(tableDetailIntent);
            Toast.makeText(context, "Response is: " + s, Toast.LENGTH_SHORT).show();
        }
    }


}
