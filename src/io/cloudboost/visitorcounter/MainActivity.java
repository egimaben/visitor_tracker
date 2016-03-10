package io.cloudboost.visitorcounter;

import io.cloudboost.CloudApp;
import io.cloudboost.CloudException;
import io.cloudboost.CloudObject;
import io.cloudboost.CloudObjectCallback;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// inflate the main layout
		setContentView(R.layout.main_layout);

		final TextView count = (TextView) findViewById(R.id.count);
		/*
		 * initialize you cloudboost app, 
		 * the first parameter is app id and the
		 * second is the client key.
		 * please replace accordingly
		 */
		CloudApp.init("bengi", "ailFnQf+xxxxxxxxxx==");
		try {
			/*
			 * start listening to "created" 
			 * events on visitors table. the other
			 * 2 types of events are 
			 * "updated" and "deleted"
			 */

			CloudObject.on("visitors", "created", new CloudObjectCallback() {

				@Override
				public void done(final CloudObject x, CloudException t)
						throws CloudException {
					/*
					 * remember this callback 
					 * runs on a background thread not 
					 * the ui thread, so to update our adapter 
					 * with the received
					 * cloudobject, we run the code on ui thread 
					 */
					Log.d("REALTIME", "RECEIVED A NOTIFICATION");

					runOnUiThread(new Runnable() {
						public void run() {
							// get the previous count in the view
							int prevCount = Integer.parseInt((String) count
									.getText());
							// increment it by 1 and set new value
							count.setText("" + (++prevCount));
						}
					});

				}
			});
		} catch (CloudException e) {
			e.printStackTrace();
		}

	}

}
