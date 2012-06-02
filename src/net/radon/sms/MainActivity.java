/*
 * @author=>Thomas Esch
 */
package net.radon.sms;
import java.lang.Math;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    
	// Widgets and other global vars
	Button btnSendSMS;
    Button auto;
    String am;
    EditText et;
    EditText send_message;
    EditText num_of_times;
    
    // progress dialog initialization
    private ProgressDialog _progressDialog;
    private int _progress = 0;
    private Handler _progressHandler;
	
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnSendSMS = (Button)findViewById(R.id.btnSendSMS);
        auto = (Button)findViewById(R.id.auto);
        et = (EditText)findViewById(R.id.et1);
        et.setHint("type number");
        send_message = (EditText)findViewById(R.id.et2);
        send_message.setHint("type message to send");
        num_of_times = (EditText)findViewById(R.id.num);
        num_of_times.setHint("how many ?");
        btnSendSMS.setOnClickListener(new View.OnClickListener()
        {
           public void onClick(View v)
           {
        	   
        	   		 /* 
        	   		  * get our data from EditText, including
        	   		  * phone number, message and number of texts to send
        	   		  */
        	         Editable s = et.getText();
        	         Editable send = send_message.getText();
        	         String g = send.toString();
        	         Editable num = num_of_times.getText();
        	         
        	         am = num.toString();
        	         Integer amount = Integer.parseInt(am);
        	         
        	         // display progress dialog
        	         showDialog(1);
                     
                     _progress = 0;
                     _progressDialog.setProgress(0);
                     _progressHandler.sendEmptyMessage(0);
                     
                  // send message <amount> times   
        	         for (Integer i=0; i<amount; i++)
        	            sendSMS(s.toString(), g );
        	         
                
           }
        });
        
        auto.setOnClickListener(new View.OnClickListener()
        {
           public void onClick(View v)
           {
        	   
        	      // get data to send generic message block
        	         Editable s = et.getText();
        	         Editable send = send_message.getText();
        	         String g = send.toString();
        	         Editable num = num_of_times.getText();
        	         am = num.toString();
        	         Integer amount = Integer.parseInt(am);
        	         
        	      // display progress dialog
        	         showDialog(1);
                     
                     _progress = 0;
                     _progressDialog.setProgress(0);
                     _progressHandler.sendEmptyMessage(0);
        	         
                // send a block message of char Z
        	         for (Integer i=0; i<amount; i++)
        	            sendSMS(s.toString(),"ZZZZZZZZZZZZZZZ"
        	            		+"ZZZZZZZZZZZZZZZZZZ"
        	            		+"ZZZZZZZZZZZZZZZZZZ"
        	            		+"ZZZZZZZZZZZZZZZZZZ"
        	            		+"ZZZZZZZZZZZZZZZZZZ"
       
        	            		);
           }
        });
        
        // start out progress handler thread
        _progressHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (_progress >= 100) {
                    _progressDialog.dismiss();
                } else {
                    _progress++;
                    _progressDialog.incrementProgressBy(1);
                    _progressHandler.sendEmptyMessageDelayed(0, 100);
                }
            }
        };
  
    }
    
    private void sendSMS(String phoneNumber, String message)
    {
    	   SmsManager sms = SmsManager.getDefault();
    	   sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
    
    // build  our progress handler dialog message display 
    @Override
    protected Dialog onCreateDialog(int id) { 
        switch (id) {       	
        case 1:
            _progressDialog = new ProgressDialog(this);
            _progressDialog.setIcon(R.drawable.icon);
            _progressDialog.setTitle("Sending " + am + " message(s)...");
            _progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);            
            _progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Hide", new 
                DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, 
                    int whichButton) 
                {
                	Toast.makeText(getBaseContext(), 
                   		 "Running in background...", Toast.LENGTH_SHORT).show();
                }
            });
            _progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new 
                DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, 
                    int whichButton) 
                {
                	Toast.makeText(getBaseContext(), 
                   		 "Cancelled!", Toast.LENGTH_SHORT).show();
                	finish();
                }
            });
            return _progressDialog;
        }        
        return null;
    }
}