package com.example.currencyconvertor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button convert;
    EditText amount;
    EditText result;
    Spinner from_currency;
    Spinner to_currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        amount = (EditText) findViewById(R.id.conversion_amount);
        result = (EditText) findViewById(R.id.conversion_result);
        from_currency = (Spinner) findViewById(R.id.convert_from);
        to_currency = (Spinner) findViewById(R.id.convert_to);
        convert = (Button) findViewById(R.id.convert);

        String[] dropDownList = { "AED","AFN","ALL","AMD","ANG","AOA","ARS","AUD","AWG","AZN","BAM","BBD","BDT","BGN","BHD","BIF","BMD","BND","BOB","BRL","BSD","BTC","BTN",
                "BWP","BYN","BZD","CAD","CDF","CHF","CLF","CLP","CNH","CNY","COP","CRC","CUC","CUP","CVE","CZK","DJF","DKK","DOP","DZD","EGP","ERN","ETB",
                "EUR","FJD","FKP","GBP","GEL","GGP","GHS","GIP","GMD","GNF","GTQ","GYD","HKD","HNL","HRK","HTG","HUF","IDR","ILS","IMP","INR","IQD",
                "IRR","ISK","JEP","JMD","JOD","JPY","KES","KGS","KHR","KMF","KPW","KRW","KWD","KYD","KZT","LAK","LBP","LKR","LRD","LSL","LYD","MAD","MDL","MGA",
                "MKD","MMK","MNT","MOP","MRO","MRU","MUR","MVR","MWK","MXN","MYR","MZN","NAD","NGN","NIO","NOK","NPR","NZD","OMR","PAB","PEN","PGK","PHP",
                "PKR","PLN","PYG","QAR","RON","RSD","RUB","RWF","SAR","SBD","SDG","SEK","SGD","SHP","SLL","SOS","SRD","SSP","STD","STN","SVC","SYP",
                "SZL","THB","TJS","TMT","TND","TOP","TRY","TTD","TWD","TZS","UAH","UGX","USD","UYU","UZS","VEF","VES","VND","VUV","WST","XAF","XAG","XAU",
                "XCD","XDR","XOF","XPD","XPF","XPT","YER","ZAR","ZMW","ZWL"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropDownList);
        from_currency.setAdapter(adapter);
        to_currency.setAdapter(adapter);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = from_currency.getSelectedItem().toString();
                String to = to_currency.getSelectedItem().toString();
                String amt = amount.getText().toString();

                ExchangeRateService service = RetrofitClientInstance.getRetrofitInstance().create(ExchangeRateService.class);
                Call<ExchangeRateResponse> call = service.getExchangeRate(from, to, amt);

                call.enqueue(new Callback<ExchangeRateResponse>() {
                    @Override
                    public void onResponse(Call<ExchangeRateResponse> call, Response<ExchangeRateResponse> response) {
                        if(response.isSuccessful()) {
                            ExchangeRateResponse exchangeRateResponse = response.body();
                            DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            result.setText(decimalFormat.format(exchangeRateResponse.getResult()));
                        } else {
                            Toast.makeText(MainActivity.this, "Conversion failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ExchangeRateResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}