package com.example.foodutan;

public class DatabaseSchema {
    public static class LoginTable {
        public static final String NAME = "login";
        public static class Cols {
            public static final String ID = "id";
            public static final String USERNAME = "username";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
        }
    }

    public static class CartTable {
        public static final String NAME = "cart";
        public static class Cols {
            public static final String EMAIL = "email";
            public static final String FOODIMAGE = "foodimage";
            public static final String RESTAURANT = "restaurant";
            public static final String FOODNAME = "foodname";
            public static final String FOODPRICE = "foodprice";
            public static final String AMOUNT = "amount";
        }
    }

    public static class RestaurantTable {
        public static final String NAME = "restaurant";
        public static class Cols {
            public static final String
        }
    }

    //For the order history, not implemented yet
    public static class HistoryTable {
        public static final String NAME = "history";
        public static class Cols {
            public static final String EMAIL = "email";
            public static final String TOTALAMOUNT = "totalamount";
            public static final String ORDERTIME = "ordertime";
        }
    }
}
