package fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import client.android.paying.com.payingmobileclient.R;
import models.CreditCard;

public class AccountFragment extends Fragment {

    private ListView creditCardListView;
    private Context activity;

    public AccountFragment() {
        // Required empty public constructor
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
        creditCardListView = (ListView) creditCardListView.findViewById(R.id.cardList);
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
        List<CreditCard> creditCardList = new ArrayList<CreditCard>();
        creditCardListView.setAdapter(new CreditCardListViewAdapter(activity, R.layout.creditcard_listview_layout, creditCardList));
    }

    class CreditCardListViewAdapter extends ArrayAdapter<CreditCard> {
        private Context context;
        private List<CreditCard> values;
        private int resourceId;

        public CreditCardListViewAdapter(Context context, int resourceId, List<CreditCard> objects) {
            super(context, resourceId, objects);
            this.resourceId = resourceId;
            // TODO Auto-generated constructor stub
            this.context = context;
            this.values = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resourceId, parent, false);

                final TextView creditCardName = (TextView) convertView
                        .findViewById(R.id.credit_card_name);

                holder = new ViewHolder(creditCardName);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return super.getView(position, convertView, parent);
        }
    }

    static class ViewHolder {

        public final TextView creditCardName;

        public ViewHolder(TextView creditCardName) {
            this.creditCardName = creditCardName;
        }
    }


}
