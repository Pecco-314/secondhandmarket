package com.zerone.secondhandmarket.tools;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.exception.InvalidInfoException;

public class Validation {
    private static final String emailAddressFormat = "[A-Za-z0-9]+@([A-Za-z0-9]+\\.)+[A-Za-z0-9]+";

    public static boolean isValidEmailAddress(String emailAddress) {
        return emailAddress.matches(emailAddressFormat);
    }

    public static void checkItemInfo(Item item) throws InvalidInfoException {
        
    }

    public static void checkUserInfo(User user) throws InvalidInfoException {

    }
}
