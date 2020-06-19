package com.example.android.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends androidx.appcompat.app.AppCompatActivity {

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
        // Figure out if the customer wants Whipped Cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the customer wants Chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Gets the name of customer
        EditText name = (EditText) findViewById(R.id.name_field_view);
        String customerName = name.getText().toString();

        // Calculates the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Displays the order summary
        String priceMessage = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject )+ customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the '+' button clicked.
     */
    public void increment(View view){
        if(quantity == 100){
            Toast.makeText(this, getString(R.string.toast_message_inc), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This  method is called when the '-' button clicked.
     */
    public void decrement (View view){
        if(quantity == 1 ) {
            Toast.makeText(this, getString(R.string.toast_message_dec), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method calculates the total price of the order.
     * @return total price
     */
    private int calculatePrice(boolean WhippedCream, boolean Chocolate){
        int basePrice = 5, Sum = 0;
        if (WhippedCream){
            basePrice += 1;
        }
        if(Chocolate){
            basePrice += 2;
        }
        Sum = quantity * basePrice;
        return Sum;
    }


    /**
     * This method creates summary of the order.
     * @param name of the customer
     * @param info all necessary information about order
     * @param whippedCream whether or not the customer chooses Whipped Cream topping
     * @param chocolate whether or not the customer chooses Chocolate topping
     * @return text summary.
     */
    @SuppressLint("StringFormatInvalid")
    private String createOrderSummary(String name, int info, boolean whippedCream, boolean chocolate){
        String messageText = getString(R.string.order_name) + name;
        messageText += "\n" + getString(R.string.order_whipped_cream) + whippedCream;
        messageText += "\n"+ getString(R.string.order_chocolate) + chocolate;
        messageText += "\n"+ getString(R.string.order_quantity) + quantity;
        messageText += "\n"+ getString(R.string.order_total_sum) + info;
        messageText += "\n" + getString(R.string.thank_you);
        return messageText;
    }
}