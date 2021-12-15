package com.example.hw3_v3;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    //store and calculate the value inputted
    private EditText display;
    private TextView previousAnswer;
    boolean error=false;
    boolean errorSignChange=true;
    int checkOverflow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gets the instance of it
        previousAnswer = findViewById(R.id.previousAnswer);
        display = findViewById(R.id.calculationText);
    }

    private void updateText(String strUpdate){
        //what is being currently displayed
        String currLine = display.getText().toString();

        //selector means cursor
        int selectorPos = display.getSelectionStart();

        //prev position
        String leftStr = currLine.substring(0, selectorPos);
        //next position
        String rightStr = currLine.substring(selectorPos);

        //final display
        display.setText(String.format("%s%s%s", leftStr, strUpdate, rightStr));

        //update selector position
        display.setSelection(selectorPos + strUpdate.length());
    }

    /*ALL STR BELOW DEFINED BY STRINGS.XML*/
    public void pushXSquaredButton(View view){
        updateText("^2");
        checkOverflow();
    }
    public void pushXPowerYButton(View view){
        updateText("^");
        checkOverflow();
    }

    public void pushSqrtButton(View view){
        updateText("2√");
        checkOverflow();
    }

    public void pushNaturalLogButton(View view){
        updateText("LN");
        checkOverflow();
    }

    public void pushSignChangeButton(View view){
        if (errorSignChange){
            updateText("-");
            errorSignChange=false;
            checkOverflow();
        }
        else {
            display.setText("");
            errorSignChange=true;
        }
    }

    public void pushPercentageButton(View view){
        updateText(getResources().getString(R.string.percentageText));
        checkOverflow();
    }

    private void checkOverflow() {
        checkOverflow++;
        if (checkOverflow>9){
            display.setText("OVERFLOW");
        }
    }

    public void pushParenthesesLButton(View view){
        updateText(getResources().getString(R.string.parenthesesLText));
        checkOverflow();
    }
    public void pushParenthesesRButton(View view){
        updateText(getResources().getString(R.string.parenthesesRText));
        checkOverflow();
    }

    public void pushDecimalButton(View view){
        updateText(getResources().getString(R.string.decimalText));
        checkOverflow();
    }

    public void pushMultiplyButton(View view){
        updateText(getResources().getString(R.string.multiplyText));
        checkOverflow();
    }
    public void pushDivideButton(View view){
        updateText(getResources().getString(R.string.divisionText));
        checkOverflow();
        //follow divide by zero logic
        error=true;
    }
    public void pushSubtractionButton(View view){
        updateText(getResources().getString(R.string.subtractionText));
        checkOverflow();
    }
    public void pushAdditionButton(View view){
        updateText(getResources().getString(R.string.additionText));
        checkOverflow();
    }

    public void pushZeroButton(View view){
        if (error){
            display.setText("ERROR");
        }else {
            updateText(getResources().getString(R.string.zeroText));
            checkOverflow();
        }
        error=false;
    }
    public void pushOneButton(View view){
        error=false;
        updateText(getResources().getString(R.string.oneText));
        checkOverflow();
    }
    public void pushTwoButton(View view){
        error=false;
        updateText(getResources().getString(R.string.twoText));
        checkOverflow();
    }
    public void pushThreeButton(View view){
        error=false;
        updateText(getResources().getString(R.string.threeText));
        checkOverflow();
    }
    public void pushFourButton(View view){
        error=false;
        updateText(getResources().getString(R.string.fourText));
        checkOverflow();
    }
    public void pushFiveButton(View view){
        error=false;
        updateText(getResources().getString(R.string.fiveText));
        checkOverflow();
    }
    public void pushSixButton(View view){
        error=false;
        updateText(getResources().getString(R.string.sixText));
        checkOverflow();
    }
    public void pushSevenButton(View view){
        error=false;
        updateText(getResources().getString(R.string.sevenText));
        checkOverflow();
    }
    public void pushEightButton(View view){
        error=false;
        updateText(getResources().getString(R.string.eightText));
        checkOverflow();
    }
    public void pushNineButton(View view){
        error=false;
        updateText(getResources().getString(R.string.nineText));
        checkOverflow();
    }

    public void pushACButton(View view){
        display.setText("");
        previousAnswer.setText("");
        checkOverflow = 0;
    }

    public void pushDELButton(View view){

        String currLine = display.getText().toString();
        //want to know length to find bounds
        int displayLength = currLine.length();
        checkOverflow--;
        //we can not have them press DEL on the below line bc it will cause error
        if (displayLength>0){
            currLine=currLine.substring(0, displayLength-1);
            display.setText(currLine);
        }

    }

    public void pushEqualButton(View view){
        //convert to String
        String currLine = display.getText().toString();
        DecimalFormat df = new DecimalFormat("0.00");
        //turn ln to fake char variable so its easier to work with
        currLine = currLine.replaceAll("LN", "10y");

        //make class where operation happens
        Calculator calc = new Calculator();

        String result = String.valueOf(Double.parseDouble(df.format(calc.evaluate(currLine))));

        display.setText(result);
        display.setSelection(result.length());
    }
}

//        userExp = userExp.replaceAll(getResources().getString(R.string.multiplyText), "*");
//
//        Expression exp = new Expression(userExp);
//        String result = String.valueOf(exp.calculate());