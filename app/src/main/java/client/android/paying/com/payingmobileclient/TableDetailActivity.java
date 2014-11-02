package client.android.paying.com.payingmobileclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import adapters.CreditCardListViewAdapter;
import adapters.ItemListViewAdapter;
import core.Constants;
import core.CreditCardHolder;
import core.HelperFunctions;
import core.PayingClient;
import models.CreditCard;
import models.Item;
import models.PaymentRequest;
import models.Table;


public class TableDetailActivity extends Activity {

    private String tableDetailJson;
    private EditText amountToPayEditText;

    private TextView amountTextView;
    private TextView paidTextView;
    private TextView tobePaidTextView;
    private double amountToPay;
    private CreditCard creditCard;
    private ListView creditCardListView;
    private ListView itemListView;
    private String tableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);

        amountToPayEditText = (EditText) findViewById(R.id.amount_to_pay);

        amountTextView = (TextView)findViewById(R.id.amount_textview);
        paidTextView = (TextView) findViewById(R.id.amount_paid_textview);
        tobePaidTextView = (TextView)findViewById(R.id.amount_will_be_paid_textview);
        creditCardListView = (ListView)findViewById(R.id.credit_cards_listview);
        itemListView = (ListView)findViewById(R.id.items_list_view);

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.TABLE_DETAIL_JSON_STRING)) {

            tableDetailJson = getIntent().getExtras().getString(Constants.TABLE_DETAIL_JSON_STRING);
            Table table = new Gson().fromJson(tableDetailJson, Table.class);
            System.out.println("TAble Detail: " + tableDetailJson);

            Type collectionType = new TypeToken<List<Item>>(){}.getType();
            JsonObject element = (JsonObject)new JsonParser().parse(tableDetailJson);
            List<Item> items = new Gson().fromJson(element.getAsJsonArray("nameQuantityPriceList"), collectionType);
            table.setItemList(items);

            tableId = table.getId();
            amountTextView.setText(table.getTotalAmount() + "");
            paidTextView.setText(table.getPaidAmount() + "");

            System.out.println(table.toString());
            getActionBar().setTitle("Masa - " + table.getId());

            itemListView.setAdapter(new ItemListViewAdapter(TableDetailActivity.this, R.layout.item_list_view, table.getItemList()));

        }

        creditCardListView.setAdapter(new CreditCardListViewAdapter(TableDetailActivity.this, R.layout.creditcard_listview_layout, CreditCardHolder.getInstance().getCreditCardList()));

        creditCardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                amountToPay = Double.valueOf(amountToPayEditText.getText().toString());
                CreditCard selectedCreditCard = (CreditCard)parent.getAdapter().getItem(position);

                PaymentRequest paymentRequest = new PaymentRequest(selectedCreditCard, tableId, amountToPay, 2);
                String requestJson = new Gson().toJson(paymentRequest);
                System.out.println(requestJson);
                showAlertDialogForPassword(amountToPay, requestJson);


            }
        });

    }

    public void showAlertDialogForPassword(double amount, final String requestJson) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(getResources().getString(R.string.pin));
        String s = getResources().getString(R.string.enter_pin_alert);
        alert.setMessage(String.format(s, amount));

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Toast.makeText(TableDetailActivity.this, value, Toast.LENGTH_SHORT).show();
                new SendAmountRequestAsyncTask(TableDetailActivity.this, Constants.SERVER_IP, Constants.SERVER_PORT,requestJson).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.SELECT_CREDIT_CARD_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                if(data.hasExtra(Constants.CREDIT_CARD)) {
                    creditCard = (CreditCard) data.getExtras().getSerializable(Constants.CREDIT_CARD);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.table_selection, menu);
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


    class SendAmountRequestAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private String ip;
        private int port;
        private String message;

        public SendAmountRequestAsyncTask(Context context, String ip, int port, String message) {

            this.context = context;
            this.port = port;
            this.ip = ip;
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Client starting", Toast.LENGTH_SHORT).show();
            HelperFunctions.showProgressBar(TableDetailActivity.this, "sending request");

        }

        @Override
        protected String doInBackground(Void... params) {

            return PayingClient.sendRequest(ip, port, message);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase(Constants.SERVER_RESPONSE_SUCCESS)) {
                Toast.makeText(TableDetailActivity.this, "Basarili", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(TableDetailActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            HelperFunctions.hideProgressDialog();
        }
    }
}
