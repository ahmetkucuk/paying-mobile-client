package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import client.android.paying.com.payingmobileclient.R;
import models.CreditCard;

/**
 * Created by ahmetkucuk on 01/11/14.
 */
public class CreditCardListViewAdapter extends ArrayAdapter<CreditCard> {
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

            final TextView creditCardNumber = (TextView) convertView
                    .findViewById(R.id.credit_card_number);

            holder = new ViewHolder(creditCardName, creditCardNumber);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.creditCardName.setText(values.get(position).getUserName());
        holder.creditCardNumber.setText(values.get(position).getCardNumber());

        return convertView;
    }
    static class ViewHolder {

        public final TextView creditCardName;
        public final TextView creditCardNumber;

        public ViewHolder(TextView creditCardName, TextView creditCardNumber) {
            this.creditCardName = creditCardName;
            this.creditCardNumber = creditCardNumber;
        }
    }
}
