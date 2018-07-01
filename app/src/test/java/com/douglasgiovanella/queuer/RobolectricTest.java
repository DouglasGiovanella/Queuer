package com.douglasgiovanella.queuer;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.douglasgiovanella.queuer.view.MainActivity;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RobolectricTest {

    @Test
    public void MainActivityTest() {

        //=========
        // Inicia activity
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        //=========


        //=========
        // Bind dos botões
        Button mDequeueButton = activity.findViewById(R.id.dequeue_button);
        Button mEnqueueButton = activity.findViewById(R.id.enqueue_button);

        EditText mEnqueueInput = activity.findViewById(R.id.enqueue_input);

        TextView mFrontText = activity.findViewById(R.id.front_text);
        TextView mSizeText = activity.findViewById(R.id.size_text);
        TextView mDequeuedText = activity.findViewById(R.id.dequeued_text);
        //=========

        //=========
        // Inicia uma nova fila de characteres
        activity.selectType = Character.TYPE;
        activity.initList(5);
        //=========

        // Valida se inicializou a lista na tela certo
        Assert.assertEquals(5, activity.mAdapter.getList().size());

        // Insere um "T" na fila
        mEnqueueInput.setText("g");
        mEnqueueButton.performClick();

        // Verifica se depois do click limpou o campo
        Assert.assertEquals("", mEnqueueInput.getText().toString());

        // Valida os campos em tela
        Assert.assertEquals("g", activity.mAdapter.getItem(0).getValue().toString());
        Assert.assertEquals("g", mFrontText.getText().toString());
        Assert.assertEquals("1", mSizeText.getText().toString());

        // Insere um "H" na fila
        mEnqueueInput.setText("i");
        mEnqueueButton.performClick();

        // Valida os campos em tela
        Assert.assertEquals("g", activity.mAdapter.getItem(0).getValue().toString());
        Assert.assertEquals("i", activity.mAdapter.getItem(1).getValue().toString());
        Assert.assertEquals("g", mFrontText.getText().toString());
        Assert.assertEquals("2", mSizeText.getText().toString());

        // Adiciona mais 3 itens na fila aleatoriamente
        mEnqueueButton.performClick();
        mEnqueueButton.performClick();
        mEnqueueButton.performClick();

        // Valida o campo do tamanho
        Assert.assertEquals("5", mSizeText.getText().toString());

        mEnqueueButton.performClick();

        // Valida se dobrou o tamanho da lista
        Assert.assertEquals(10, activity.mAdapter.getList().size());


        Assert.assertEquals("6", mSizeText.getText().toString());


        // Remove o primeiro da fila
        mDequeueButton.performClick();

        Assert.assertEquals("5", mSizeText.getText().toString());
        Assert.assertEquals("i", mFrontText.getText().toString());
        Assert.assertEquals("g", mDequeuedText.getText().toString());


        // ======================

        activity.selectType = Character.TYPE;
        activity.initList(4);

        mEnqueueInput.setText("g");
        mEnqueueButton.performClick();

        mEnqueueInput.setText("i");
        mEnqueueButton.performClick();

        mEnqueueInput.setText("g");
        mEnqueueButton.performClick();

        mEnqueueInput.setText("i");
        mEnqueueButton.performClick();

        Assert.assertTrue(activity.gigiDialog.isShowing());
    }

}
