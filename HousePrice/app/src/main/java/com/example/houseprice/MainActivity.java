package com.example.houseprice;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
    Interpreter tflite;
    EditText input1,input2,input3,input4,input5,input6,input7,input8;
    TextView output;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        input5 = findViewById(R.id.input5);
        input6 = findViewById(R.id.input6);
        input7 = findViewById(R.id.input7);
        input8 = findViewById(R.id.input8);
        output = findViewById(R.id.output);
        button = findViewById(R.id.button);

        try {
            tflite = new Interpreter(loadModelFile());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                float prediction = doinference(input8.getText().toString(),input7.getText().toString(),input6.getText().toString(),input5.getText().toString(),input4.getText().toString(),input3.getText().toString(),input2.getText().toString(),input1.getText().toString());
                output.setText(Float.toString(prediction));
            }
        });
    }
    public float doinference(String inputstring1,String inputstring2,String inputstring3,String inputstring4,String inputstring5,String inputstring6,String inputstring7,String inputstring8){
     float [][] inputvalue = new float[1][8];
     inputvalue[0][0] = Float.valueOf(inputstring1);
     inputvalue[0][1] = Float.valueOf(inputstring2);
     inputvalue[0][2] = Float.valueOf(inputstring3);
     inputvalue[0][3] = Float.valueOf(inputstring4);
     inputvalue[0][4] = Float.valueOf(inputstring5);
     inputvalue[0][5] = Float.valueOf(inputstring6);
     inputvalue[0][6] = Float.valueOf(inputstring7);
     inputvalue[0][7] = Float.valueOf(inputstring8);
     float[][] outputval = new float[1][1];
     tflite.run(inputvalue,outputval);
     float inferredvalue = outputval[0][0];
     return inferredvalue;
    }
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor=this.getAssets().openFd("tf_lite_model.tflite");
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset=fileDescriptor.getStartOffset();
        long declareLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declareLength);
    }
}