package com.example.houseprices

import android.content.res.AssetFileDescriptor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.IOException
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel

class  MainActivity : AppCompatActivity() {
    Interpreter tflite;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            tflite = new Interpreter(loadModelFile())
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescription = this.assets().openFD('model.tflite');
        FileInputStream inputstream = new FileInputStream(fileDescription.getFileDescreptor());
        FileChannel filechannel = inputstream.getChannel();
        long startOffset = fileDescription.getStartOffset;
        long declaredLength = fileDescription.getDeclaredlength();
        return fileChannel.map(