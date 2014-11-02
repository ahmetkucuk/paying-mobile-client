package fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.CreditCardListViewAdapter;
import client.android.paying.com.payingmobileclient.R;
import core.CreditCardHolder;
import models.CreditCard;

public class AccountFragment extends Fragment {

    private ListView creditCardListView;
    private Context activity;
    private Button addNewCard;

    List<CreditCard> creditCardList;

    public AccountFragment() {
        // Required empty public constructor
    }

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static AccountFragment newInstance(int sectionNumber) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        creditCardListView = (ListView) view.findViewById(R.id.cardList);
        addNewCard = (Button)view.findViewById(R.id.addNewAccount);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        creditCardList = CreditCardHolder.getInstance().getCreditCardList();
        creditCardListView.setAdapter(new CreditCardListViewAdapter(activity, R.layout.creditcard_listview_layout, creditCardList));
        addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCardDialog();
            }
        });
//
//        creditCardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }

    public void showAddCardDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_new_card);
        dialog.setTitle(getResources().getString(R.string.add_new_card_dialog_title));
        //colorAlertDialogTitle(dialog, Color.RED);

        final EditText cardNumberEditText = (EditText)dialog.findViewById(R.id.card_number_edittext);
        final EditText cardOwnerEditText = (EditText)dialog.findViewById(R.id.card_owner_edittext);
        final EditText cardExpireDate1 = (EditText)dialog.findViewById(R.id.expire_date_edittext_1);
        final EditText cardExpireDate2 = (EditText)dialog.findViewById(R.id.expire_date_edittext_2);

        Button addButton = (Button)dialog.findViewById(R.id.add_card_dialog_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber = cardNumberEditText.getText().toString();
                String cardOwnerName = cardOwnerEditText.getText().toString();
                String expireDate = cardExpireDate1.getText().toString() + cardExpireDate2.getText().toString();
                CreditCardHolder.getInstance().addCreditCard(new CreditCard(cardNumber,cardOwnerName,expireDate,"none", "ING"));
                creditCardList = CreditCardHolder.getInstance().getCreditCardList();
                creditCardListView.setAdapter(new CreditCardListViewAdapter(activity, R.layout.creditcard_listview_layout, creditCardList));
                dialog.dismiss();
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

}
