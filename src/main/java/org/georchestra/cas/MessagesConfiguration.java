package org.georchestra.cas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


@Component
@Order(1)
class MessagesConfiguration implements CommandLineRunner {

    @Autowired GeorchestraConfiguration georchestraConfiguration;
    public static final String FILE_PATH = "webapps/cas/WEB-INF/classes/messages.properties";
    public static final String EXPIRE_PASS_KEY = "screen.expiredpass.message";
    public static final String EXPIRE_PASS_VALUE = "Please <a href=\"%s\">change your password</a>.";
    public static final String WARNING_PASS_KEY = "password.expiration.warning";
    public static final String WARNING_PASS_VALUE = "Your password expires in {0} day(s). Please <a href=\"%s\">change your password</a> now.";
    public static final String CHANGE_PASSWORD_URL_NOT_CONNECTED = "../console/account/passwordRecovery";
    public static final String CHANGE_PASSWORD_URL_CONNECTED = "../console/account/changePassword";

    @Override
    public void run(String... args)  {
            boolean success = false;
            Properties props = new Properties();
            FileOutputStream output = null;
            FileInputStream configStream = null;
            try {
                File propsFile  = new File(FILE_PATH);
                configStream = new FileInputStream(propsFile);
                props.load(configStream);
                props.setProperty(EXPIRE_PASS_KEY, String.format(EXPIRE_PASS_VALUE, CHANGE_PASSWORD_URL_NOT_CONNECTED));
                props.setProperty(WARNING_PASS_KEY, String.format(WARNING_PASS_VALUE, CHANGE_PASSWORD_URL_CONNECTED));
                output = new FileOutputStream(propsFile);
                props.store(output, "message.properties file updated to set url for password change");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally {
                if (configStream != null) {
                    try {
                        configStream.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }
}