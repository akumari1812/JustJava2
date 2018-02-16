
package com.example.hp.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        CheckBox whippedCream= (CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean hasChecked= whippedCream.isChecked();
        CheckBox Chocolate= (CheckBox) findViewById(R.id.me_chocolate);
        boolean isChocolate= Chocolate.isChecked();
        EditText name=(EditText)findViewById(R.id.hg_view);
        int price=calculatePrice(hasChecked,isChocolate);
        String value= name.getText().toString();
        String priceMessage=createOrderSummmary(price,hasChecked,isChocolate,value);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        // intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + value);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    public void increment(View view) {
        if(quantity == 100){
            Toast.makeText(this,"You can't have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return ;
        }
        quantity = quantity + 1;
        display(quantity);
    }
    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this,"You can't have more than 100 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
    private int calculatePrice(boolean w,boolean c){
        int basePrice=5;
        if(w){
            basePrice+=1;
        }
        if(c){
            basePrice+=2;
        }
        return (quantity*basePrice);
    }
    private String createOrderSummmary(int number,boolean b,boolean e,String value){
        String priceMessage=getString(R.string.order_summary_name,value)+'\n';
        priceMessage+=getString(R.string.order_summary_whipped_cream,b)+'\n';
        priceMessage+=getString(R.string.order_summary_chocolate,e)+'\n';
        priceMessage+=getString(R.string.order_summary_quantity,quantity)+'\n';
        priceMessage+=getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(number))+'\n';

        priceMessage+=getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     *
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}