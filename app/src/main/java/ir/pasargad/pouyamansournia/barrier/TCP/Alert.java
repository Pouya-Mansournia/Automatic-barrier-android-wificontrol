package ir.pasargad.pouyamansournia.barrier.TCP;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Pouya_Mn on 25-Jan-17.
 */

public class Alert {

    private static Context context=null;

    /**
     * Init void.
     *
     * @param context the context
     */
    public static void init(Context context){
        Alert.context =context;
    }

    /**
     * Info void.
     *
     * @param msg the msg
     * @param callback the callback
     */
    public static void info(String Title, String msg, DialogInterface.OnClickListener callback){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setTitle(Title)
                .setMessage(msg)
                .setPositiveButton("Ok",null);

        alertDialogBuilder.show();
    }

    /**
     * Show progress.
     *
     * @param msg the msg
     * @return the alert dialog
     */
    public static ProgressDialog showProgress(String msg){
        return ProgressDialog.show(context,
                msg, null, true, true);
    }
}
