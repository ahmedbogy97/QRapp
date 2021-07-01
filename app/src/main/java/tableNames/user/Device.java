package tableNames.user;

public class Device {

    private static final String
            table_name = "users"
            , device_token="device_token"
            , is_active="is_active"
            , OS="OS";

//   device
//      Y6nq3ddpIVbvVLVHankExfL6OkE3                // user id
//          -LPWZmzr7kjBs8Gbe_ZF                    // device id
//              os:"android"                        // device os
//              is_active:"active"
//              device_token:"Y6nq3ddpIVbvVLVHankExfL6OkE3Y6nq3ddpIVbvVLVHankExfL6OkE3"   // device token


    public static String getTable_name() {
        return table_name;
    }

    public static String getDevice_token() {
        return device_token;
    }

    public static String getIs_active() {
        return is_active;
    }

    public static String getOS() {
        return OS;
    }
}
