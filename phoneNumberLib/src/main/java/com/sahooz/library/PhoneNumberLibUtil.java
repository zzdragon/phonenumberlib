package com.sahooz.library;

import android.content.Context;

import io.michaelrocks.libphonenumber.android.Phonenumber;

public class PhoneNumberLibUtil {

    public static boolean checkPhoneNumber(Context context, int code, Long PhoneNumber) {
        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(code);
        pn.setNationalNumber(PhoneNumber);

        return io.michaelrocks.libphonenumber.android.PhoneNumberUtil.createInstance(context).isValidNumber(pn);
    }
}
