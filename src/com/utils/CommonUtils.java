package com.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

public class CommonUtils {

	public interface SpinnerCallBack {
		void onItemSelected(int pos);
	}

	public void SingleChoice(String title, final Context act,
			final String[] array, final TextView txtView,
			final SpinnerCallBack mCallBack) {
		int position = -1;
		try {
			position = (Integer) txtView.getTag();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Builder builder = new AlertDialog.Builder(act);
		builder.setTitle(title);
		builder.setSingleChoiceItems(array, position,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						txtView.setTag(which);
						mCallBack.onItemSelected(which);
						dialog.dismiss();
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
