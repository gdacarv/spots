package com.dcc.matc89.spots;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// Para que a ActionBar funcione em todas as versões é necessário estender ActionBarActivity ao invés de Activity
public class MainActivity extends ActionBarActivity {


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button button = (Button)findViewById(R.id.btn_gonextscreen);
        button.setOnClickListener(onBtnClickListener);
    }

    private OnClickListener onBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, SpotEditActivity.class);
			startActivity(i);
		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.action_add_spot:
			Intent i = new Intent(this, SpotEditActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }
}
