package com.test.assignment2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.test.assignment2.loader.TransportInfoLoader;
import com.test.assignment2.pojo.TransportInfo;
import com.test.assignment2.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Shailendra on 3/8/2016.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<TransportInfo>> {
    private RelativeLayout dataContainer;
    private TextView tvModeList;
    private ProgressBar progressBar;
    private TextView tvErrorMessage;
    private Button buttonRetry;
    private Spinner spinnerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        loadData();
    }

    /**
     * This method is used to load contents if internet is available
     */
    private void loadData() {
        if (Utility.isNetworkAvailable(this)) {
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
            progressBar.setVisibility(View.VISIBLE);
            dataContainer.setVisibility(View.GONE);
            tvErrorMessage.setVisibility(View.GONE);
            buttonRetry.setVisibility(View.GONE);
        } else {
            showMessage("Your internet connection seems to be inactive.");
        }
    }

    /**
     * Get references for views
     */
    private void initViews() {
        tvErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        buttonRetry = (Button) findViewById(R.id.btn_retry);
        dataContainer = (RelativeLayout) findViewById(R.id.data_container);

        tvModeList = (TextView) findViewById(R.id.tv_modes_list);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        spinnerName = (Spinner) findViewById(R.id.spinnerName);

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        Button buttonNavigate = (Button) findViewById(R.id.btn_navigate_on_map);
        buttonNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransportInfo transportInfo = (TransportInfo) spinnerName.getSelectedItem();
                TransportInfo.TransportLocation location = transportInfo.getTransportLocation();
                if (location != null) {
                    // Creates an Intent that will load a map
                    Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?q=%s", location.getLatitude(), location.getLongitude(), transportInfo.getName()));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });

    }


    private void showMessage(String message) {
        tvErrorMessage.setText(message);
        progressBar.setVisibility(View.GONE);
        dataContainer.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.VISIBLE);
        buttonRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<ArrayList<TransportInfo>> onCreateLoader(int id, Bundle args) {
        // set a loader so that it will fetch data from the server
        return new TransportInfoLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<TransportInfo>> loader, ArrayList<TransportInfo> data) {
        populateDataOnView(data);
    }

    /**
     * show all available on view.
     *
     * @param data list of all available transports
     */
    private void populateDataOnView(ArrayList<TransportInfo> data) {
        progressBar.setVisibility(View.GONE);
        if (data == null) {
            showMessage("No transport data found.");
        } else {
            tvErrorMessage.setVisibility(View.GONE);
            buttonRetry.setVisibility(View.GONE);
            dataContainer.setVisibility(View.VISIBLE);
            // add all transport names in spinner list
            ArrayAdapter<TransportInfo> spinnerArrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    data);
            spinnerName.setAdapter(spinnerArrayAdapter);

            // change mode according to the selection of transport name.
            spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TransportInfo transportInfo = (TransportInfo) parent.getAdapter().getItem(position);
                    tvModeList.setText(transportInfo.getFromCentral().getCar() + "\n" + transportInfo.getFromCentral().getTrain());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    tvModeList.setText("");
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<TransportInfo>> loader) {

    }
}
