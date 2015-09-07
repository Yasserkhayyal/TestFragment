package com.example.mohamedyasser.testfragment;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private final String FRAGMENT_ONE_TAG = "Fragment One";
    private final String FRAGMENT_TWO_TAG = "Fragment Two";
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null) {
            if (( getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) != Configuration.SCREENLAYOUT_SIZE_LARGE){
                Log.v("mainactivity","inside small screen size");

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new FragmentOne(), FRAGMENT_ONE_TAG).commit();
            } else {
                Log.v("mainactivity", "inside large screen size");
               initializeListView();

            }
        }else{

            if((
                    getResources().getConfiguration().screenLayout &
            Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                initializeListView();
                int id = savedInstanceState.getInt("checked item id ",-1);
                if(id !=-1){
                    lv.setSelection(id);
                }
            }

        }

    }

    private void initializeListView(){
        String[] data = new String[]{"Fragment One","Fragment Two"};
        lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext()
                , R.layout.list_item, data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv.getChildAt(position).setSelected(true);
                switch (position) {
                    case 0:
                        if (getSupportFragmentManager().
                                findFragmentById(R.id.tablet_fragment_container) == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.tablet_fragment_container
                                            , new FragmentOne(), FRAGMENT_ONE_TAG)
                                    .commit();


                        } else if (getSupportFragmentManager()
                                .findFragmentByTag(FRAGMENT_TWO_TAG) != null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.tablet_fragment_container,
                                            new FragmentOne(),
                                            FRAGMENT_ONE_TAG).commit();
                        }
                        break;

                    case 1:
                        if (getSupportFragmentManager().
                                findFragmentById(R.id.tablet_fragment_container) == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .add(R.id.tablet_fragment_container
                                            , new FragmentTwo(), FRAGMENT_TWO_TAG)
                                    .commit();

                        } else if (getSupportFragmentManager()
                                .findFragmentByTag(FRAGMENT_ONE_TAG) != null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.tablet_fragment_container,
                                            new FragmentTwo(),
                                            FRAGMENT_TWO_TAG).commit();
                        }
                        break;

                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.textView:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_ONE_TAG);
                if(fragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new FragmentTwo()).addToBackStack(null).commit();
                    getSupportFragmentManager().executePendingTransactions();
                }
                break;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getResources().getConfiguration().screenLayout==Configuration.SCREENLAYOUT_SIZE_LARGE){
            outState.putInt("checked item id",lv.getSelectedItemPosition());
        }
    }
}
