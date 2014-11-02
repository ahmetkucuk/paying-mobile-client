package client.android.paying.com.payingmobileclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import core.Constants;
import core.PayingClient;

/**
 * Created by ahmetkucuk on 01/11/14.
 */
public class TableSelectionActivity extends Activity {

    private Button button;
    private EditText tableNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_table_selection);
        button = (Button)findViewById(R.id.askBill);
        tableNumberEditText = (EditText)findViewById(R.id.tableNumber);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendRequestAsyncTask(TableSelectionActivity.this, Constants.SERVER_IP, Constants.SERVER_PORT, tableNumberEditText.getText().toString()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    class SendRequestAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private String ip;
        private int port;
        private String message;
        private Gson gson;

        public SendRequestAsyncTask(Context context, String ip, int port, String message) {

            this.context = context;
            this.port = port;
            this.ip = ip;
            this.message = message;
            gson = new GsonBuilder().create();
        }

        @Override
        protected void onPreExecute() {

            Toast.makeText(context, "Client starting", Toast.LENGTH_SHORT).show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            JsonObject object = new JsonObject();
            object.addProperty("tableId", message);
            object.addProperty("type", 1);
            return PayingClient.sendRequest(ip, port, gson.toJson(object));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("result: " + s);
            if(s == null || s.length() <= 0) {
                Toast.makeText(TableSelectionActivity.this, getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent tableDetailIntent = new Intent(TableSelectionActivity.this, TableDetailActivity.class);
            tableDetailIntent.putExtra(Constants.TABLE_DETAIL_JSON_STRING, s);
            startActivity(tableDetailIntent);
        }
    }
}
