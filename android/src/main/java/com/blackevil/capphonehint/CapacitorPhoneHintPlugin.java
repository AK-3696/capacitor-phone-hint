package com.blackevil.capphonehint;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts; // Ensure this is imported
import androidx.annotation.NonNull;

import com.getcapacitor.*;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;

@CapacitorPlugin(name = "CapacitorPhoneHint")
public class CapacitorPhoneHintPlugin extends Plugin {

  // Change the type argument for the launcher
  private ActivityResultLauncher<IntentSenderRequest> phoneNumberHintLauncher;
  private PluginCall savedCall;

  @Override
  public void load() {
    super.load();
    // Adjust the contract to StartIntentSenderForResult
    phoneNumberHintLauncher = getActivity().registerForActivityResult(
      new ActivityResultContracts.StartIntentSenderForResult(),
      activityResult -> { // The result here is an ActivityResult
        if (savedCall == null) {
          return;
        }
        try {
          // The data is directly in activityResult.getData()
          if (activityResult.getResultCode() == Activity.RESULT_OK && activityResult.getData() != null) {
            String phoneNumber = Identity.getSignInClient(getActivity()).getPhoneNumberFromIntent(activityResult.getData());
            JSObject ret = new JSObject();
            ret.put("phoneNumber", phoneNumber);
            savedCall.resolve(ret);
          } else {
            savedCall.reject("Failed to get phone number or user canceled.");
          }
        } catch (Exception e) {
          savedCall.reject("Error processing phone number hint result: " + e.getMessage());
        }
        savedCall = null;
      });
  }

  @PluginMethod
  public void getPhoneNumber(PluginCall call) {
    savedCall = call;

    GetPhoneNumberHintIntentRequest request = GetPhoneNumberHintIntentRequest.builder().build();

    Identity.getSignInClient(getContext())
      .getPhoneNumberHintIntent(request)
      .addOnSuccessListener(pendingIntent -> { // result is a PendingIntent
        try {
          if (phoneNumberHintLauncher != null) {
            IntentSenderRequest intentSenderRequest =
              new IntentSenderRequest.Builder(pendingIntent.getIntentSender())
                .build();
            phoneNumberHintLauncher.launch(intentSenderRequest);
          } else {
            call.reject("Phone number launcher not initialized.");
            Log.e("CapacitorPhoneHint", "phoneNumberHintLauncher is null!");
          }
        } catch (Exception e) {
          // It's good practice to catch specific exceptions if possible,
          // e.g., IntentSender.SendIntentException if the pendingIntent is canceled
          call.reject("Error launching phone number hint: " + e.getMessage());
        }
      })
      .addOnFailureListener(e -> {
        call.reject("Failed to get phone number hint intent: " + e.getMessage());
      });
  }
}
