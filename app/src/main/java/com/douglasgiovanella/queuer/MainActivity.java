package com.douglasgiovanella.queuer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mQueueRecycler;
    private Type selectType;
    private Button mDequeueButton, mEnqueueButton;
    private TextView mFrontText, mDequeuedText, mSizeText;
    private EditText mEnqueueInput;
    private QueueListAdapter mAdapter;
    private ArrayQueue<Object> queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startToolbar();
        startComponents();
        actionComponents();
        newQueue();
    }

    private void startToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(mToolbar);
    }


    private void startComponents() {
        mQueueRecycler = (RecyclerView) findViewById(R.id.queue_recycler);

        mDequeueButton = (Button) findViewById(R.id.dequeue_button);
        mEnqueueButton = (Button) findViewById(R.id.enqueue_button);

        mEnqueueInput = (EditText) findViewById(R.id.enqueue_input);

        mFrontText = (TextView) findViewById(R.id.front_text);
        mSizeText = (TextView) findViewById(R.id.size_text);
        mDequeuedText = (TextView) findViewById(R.id.dequeued_text);
    }

    private void actionComponents() {
        mDequeueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object dequeue = queue.dequeue();
                mDequeuedText.setText(String.valueOf(dequeue).toLowerCase());
                refreshRecyclerView();
            }
        });

        mEnqueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEnqueueInput.getText())) {
                    queue.enqueue(getRandomValue());
                } else {
                    if (selectType.equals(Integer.TYPE)) {
                        queue.enqueue(Integer.parseInt(mEnqueueInput.getText().toString()));
                    } else {
                        queue.enqueue(mEnqueueInput.getText().toString());
                    }
                }
                refreshRecyclerView();
                mEnqueueInput.setText("");
            }
        });

    }

    private void refreshRecyclerView() {
        mSizeText.setText(String.valueOf(queue.getSize()));
        mAdapter.swap(queue.getQueueAsQueueItens());
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

    private Object getRandomValue() {
        Random random = new Random();
        return selectType.equals(Integer.TYPE) ? ((char) (random.nextInt(26) + 'a')) : random.nextInt(999);
    }

    private void newQueue() {

        LayoutInflater inflater = getLayoutInflater();

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.new_queue_layout, null);

        final RadioButton charRadio = view.findViewById(R.id.char_radio);

        final EditText sizeText = view.findViewById(R.id.queue_size);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (charRadio.isChecked()) {
                    selectType = Character.TYPE;
                } else {
                    selectType = Integer.TYPE;
                }

                int size = sizeText.getText().toString().equals("") ? 5 : Integer.parseInt(sizeText.getText().toString());

                createList(size);
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.setTitle("Nova Fila");
        builder.setView(view);
        builder.setCancelable(true);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void createList(int listSize) {
        mFrontText.setText(selectType == Integer.TYPE ? "0" : "NULL");

        if (Integer.TYPE.equals(selectType)) {
            mEnqueueInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            mEnqueueInput.setInputType(InputType.TYPE_CLASS_TEXT);
            mEnqueueInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        }
        mDequeuedText.setText("");
        queue = new ArrayQueue<>(listSize);
        mAdapter = new QueueListAdapter(this, queue.getQueueAsQueueItens(), selectType);
        mQueueRecycler.setAdapter(mAdapter);
    }

}
