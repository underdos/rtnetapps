package net.kusnadi.rtnetapps.helper;

import android.app.ProgressDialog;
import android.content.Context;

import net.kusnadi.rtnetapps.activity.LoginActivity;

/**
 * Created by root on 24/09/17.
 */

public class DialogHelper {
    private static ProgressDialog progressSpinnerDialog;

    public static void progressSpinner(Context context, String message, String title){
        progressSpinnerDialog = new ProgressDialog(context);
        progressSpinnerDialog.setMax(100);
        progressSpinnerDialog.setMessage(message);
        if (title != null || !title.equals(""))
            progressSpinnerDialog.setTitle(title);
        progressSpinnerDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressSpinnerDialog.show();

    }

    public static  void progressSpinner(boolean show){
        if (!show)
            progressSpinnerDialog.dismiss();
    }
}
