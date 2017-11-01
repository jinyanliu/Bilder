package se.sugarest.jane.bilder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import se.sugarest.jane.bilder.GetPhotosTask;
import se.sugarest.jane.bilder.PhotoAdapter;
import se.sugarest.jane.bilder.R;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.PhotoAdapterOnClickHandler {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private EditText mEditText;

    private Button mButton;

    private PhotoAdapter mPhotoAdapter;

    public PhotoAdapter getmPhotoAdapter() {
        return mPhotoAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerViewWithAdapter();

        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String editTextString = mEditText.getText().toString();
                if (editTextString.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please tell me what to search! :)", Toast.LENGTH_SHORT).show();
                } else {
                    new GetPhotosTask(MainActivity.this).execute(editTextString);
                }
            }
        });
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setUpRecyclerViewWithAdapter() {
        /**
         * Using findViewById, get a reference to the RecyclerView from xml.
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_photos);

        /**
         * this: Current context, will be used to access resources.
         * 4: The number of columns in the grid
         * GridLayoutManager.VERTICAL: Layout orientation.
         * false: When set to true, layouts from end to start.
         */
        final GridLayoutManager layoutManager
                = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        /**
         * Use this setting to improve performance that changes in content do not change the child
         * layout size in the RecyclerView.
         */
        mRecyclerView.setHasFixedSize(true);

        /**
         * The PhotoAdapter is responsible for linking the photo data with the Views that
         * will end up displaying the photo data.
         */
        if (mPhotoAdapter == null) {
            mPhotoAdapter = new PhotoAdapter(this, this);
        }

        /**
         * Setting the adapter attaches it to the RecyclerView in the layout.
         */
        mRecyclerView.setAdapter(mPhotoAdapter);
    }

    @Override
    public void onClick(String photoUrl) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("photoUrl", photoUrl);
        startActivity(intentToStartDetailActivity);
    }
}
