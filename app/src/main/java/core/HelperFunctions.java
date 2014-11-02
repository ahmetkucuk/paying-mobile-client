package core;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by ahmetkucuk on 01/11/14.
 */
public class HelperFunctions {


    private static ProgressDialog progressDialog;

    public static void showProgressBar(Context context, String message) {

        if(progressDialog == null) {
            progressDialog = ProgressDialog.show(context, "Bekleyin", message, false);
        }
    }

    public static void hideProgressDialog() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
