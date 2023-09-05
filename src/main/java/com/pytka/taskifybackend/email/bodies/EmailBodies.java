package com.pytka.taskifybackend.email.bodies;

import java.util.Map;

public class EmailBodies {
    public final static String NOTIFICATION_BODY =
    "Hello ${username}!\n\n";

    public static final String AUTH_CODE_BODY =
            "Hello ${username}!\n\n"
            + "This is you authentication code:\n"
            + "${authCode}\n\n"
            + "Copy it and paste into text field in app!\n"
            + "Wishing You happy using of Taskify!";

}
