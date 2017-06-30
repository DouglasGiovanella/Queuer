package com.douglasgiovanella.queuer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mQueueRecycler;
    private Button mQueueButton, mEnqueueButton;
    private TextView mFrontText, mDequeuedText;
    private QueueListAdapter mAdapter;
    private ArrayQueue<Object> queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startToolbar();
        startComponents();
        actionComponents();
    }

    private void startToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(mToolbar);
    }


    private void startComponents() {
        mQueueRecycler = (RecyclerView) findViewById(R.id.queue_recycler);

        mQueueButton = (Button) findViewById(R.id.queue_button);
        mEnqueueButton = (Button) findViewById(R.id.enqueue_button);

        mFrontText = (TextView) findViewById(R.id.front_text);
        mDequeuedText = (TextView) findViewById(R.id.dequeued_text);
    }

    private void actionComponents() {
        mQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mEnqueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void updateAdapter() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_queue:
                newQueue();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newQueue() {

        LayoutInflater inflater = getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.new_queue_layout, null);

        final RadioButton charRadio = view.findViewById(R.id.char_radio);
        final RadioButton intRadio = view.findViewById(R.id.int_radio);

        final EditText sizeText = view.findViewById(R.id.queue_size);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Type type;
                if (charRadio.isChecked()) {
                    type = Character.TYPE;
                } else {
                    type = Integer.TYPE;
                }

                int size = sizeText.getText().toString().equals("") ? 5 : Integer.parseInt(sizeText.getText().toString());

                createList(type, size);
            }
        });

        builder.setNegativeButton("Cancelar", null);

        builder.setView(view);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void createList(Type type, int listSize) {
        queue = new ArrayQueue<>(listSize);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        mAdapter = new QueueListAdapter(this, Arrays.asList(queue.getArray()), type);
        mQueueRecycler.setAdapter(mAdapter);
    }

}
