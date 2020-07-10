package com.renato.paypal_mobile_payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** PaypalMobilePaymentPlugin */
public class PaypalMobilePaymentPlugin implements FlutterPlugin, MethodCallHandler,  PluginRegistry.ActivityResultListener, ActivityAware {

  static Activity activity;
  Activity context = PaypalMobilePaymentPlugin.activity;
  private static PayPalConfiguration config;
  private  PayPalPayment payPalPayment;
  private Map<String, String> paypalData;
  static  MethodChannel.Result sendBack;
  private String clientId;
  Map<String, String> items;
  MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "paypal_mobile_payment");
    channel.setMethodCallHandler(new PaypalMobilePaymentPlugin());
    Log.i("paypal","onAttachedToEngine");

  }



  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("startCheckout")){

      paypalData  = (Map<String, String>) call.arguments;
      clientId = paypalData.get("clientId");
      items = receiveMapConvertJSONMap(new JSONObject(paypalData));

      config = new PayPalConfiguration()
              .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
              .clientId("AcZJTsqYlovBu9xMRsK6cXPLMHep7z2lVuFAXIn517h5NxzElQ5DdrgBbebhlWj_6ZjmIJJ2HUB0N9t9");

      Intent intent = new Intent(context, PayPalService.class);
      intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

      if(activity != null){
        Log.i("paypal", "onMethodCall: ");
        System.out.println("onMethodCall");
        if (payPalPayment == null) startCheckout(result);

      }else{
        Log.i("paypal", "Empty");
        System.out.println("Empty");
      }
    }
  }


  public void startCheckout(Result result){
    sendBack = result;
    Log.i("paypal", "startCheckout: ");
    System.out.println("startCheckout");
    payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(items.get("price"))),items.get("currency"),
            items.get("description"),PayPalPayment.PAYMENT_INTENT_SALE);
    Intent intent = new Intent(context, PaymentActivity.class);
    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
    intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
    context.startActivityForResult(intent, 0);
  }

  Map<String, String> receiveMapConvertJSONMap(JSONObject paypalData){
    Map<String, String> data = new HashMap<>();
    try {
      JSONArray itemsArray  = new JSONArray();
      itemsArray.put(paypalData.get("items"));
      String item = itemsArray.getString(0);
      JSONObject jsonObject = new JSONObject(item);
      System.out.println(jsonObject.getString("price"));

      data.put("price", jsonObject.getString("price"));
      data.put("description", jsonObject.getString("description"));
      data.put("currency", jsonObject.getString("currency"));

    } catch (JSONException e) {
      e.printStackTrace();
    }
    return data;
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    Log.i("paypal", "onAttachedToActivity");
    System.out.println("onAttachedToActivity");
    activity = binding.getActivity();
    binding.addActivityResultListener(this);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {
    activity = null;
  }

  @Override
  public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      PaymentConfirmation confirm = data.getParcelableExtra(
              PaymentActivity.EXTRA_RESULT_CONFIRMATION);
      if (confirm != null) {
        JSONObject response =  confirm.toJSONObject();
        try{
          sendBack.success(response.toString());
        }catch (Exception e){
          System.out.println(e.getMessage());
        }
        payPalPayment = null;
      }
    } else if (resultCode == Activity.RESULT_CANCELED) {
      Log.i("sampleapp", "The user canceled.");

    } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
      Log.i("sampleapp", "Invalid payment / config set");

    }
    return true;
  }

}
