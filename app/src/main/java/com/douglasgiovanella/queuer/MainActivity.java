package com.douglasgiovanella.queuer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.media.MediaPlayer;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mQueueRecycler;
    private Type selectType;
    private AlertDialog gigiDialog;
    private MediaPlayer mMediaPlayer;
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
                if (queue != null) {
                    Object dequeue = queue.dequeue();
                    mDequeuedText.setText(String.valueOf(dequeue).toLowerCase());
                    refreshRecyclerView();
                }
            }
        });

        mEnqueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (queue != null) {
                    if (TextUtils.isEmpty(mEnqueueInput.getText())) {
                        queue.enqueue(selectType.equals(Character.TYPE) ? getRandomChar() : getRandomInt());
                    } else {
                        queue.enqueue(mEnqueueInput.getText().toString().charAt(0));
                    }
                    refreshRecyclerView();
                    mEnqueueInput.setText("");

                    if (selectType.equals(Character.TYPE)) {
                        String tmp = getStringFromQueueArray().toUpperCase();
                        if (tmp.contains("GIGI")) {
                            playGigi();
                        }
                    }
                }
            }
        });

    }

    private void refreshRecyclerView() {
        mFrontText.setText(String.valueOf(queue.front()).toLowerCase());
        mSizeText.setText(String.valueOf(queue.getSize()));
        mAdapter.swap(queue.getQueueAsQueueItems());
    }

    private String getStringFromQueueArray() {
        char[] tmp = new char[queue.getArray().length];
        for (int i = 0; i < queue.getArray().length; i++) {
            if (queue.getArray()[i] != null) {
                tmp[i] = (char) queue.getArray()[i];
            }
        }
        return new String(tmp);
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

    private Object getRandomInt() {
        return new Random().nextInt(999);
    }

    private Character getRandomChar() {
        return ((char) (new Random().nextInt(26) + 'a'));
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
        mFrontText.setText("null");
        mSizeText.setText("0");
        mDequeuedText.setText("");
        if (Integer.TYPE.equals(selectType)) {
            mEnqueueInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            mEnqueueInput.setInputType(InputType.TYPE_CLASS_TEXT);
            mEnqueueInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        }
        queue = new ArrayQueue<>(listSize);
        mAdapter = new QueueListAdapter(queue.getQueueAsQueueItems());
        mQueueRecycler.setAdapter(mAdapter);
    }

    private void playGigi() {
        showDancing();
        mMediaPlayer = MediaPlayer.create(this, R.raw.gigi);
        mMediaPlayer.start();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                gigiDialog.dismiss();
            }
        });
    }

    private void showDancing() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        WebView view = new WebView(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        builder.setView(view);
        gigiDialog = builder.create();
        gigiDialog.show();
        view.loadUrl("file:///android_asset/gigi.gif");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //RODA RODA VIRA
                    if (!gigiDialog.isShowing()) {
                        mMediaPlayer.stop();
                        break;
                    }
                }

            }
        }).start();

    }
}
