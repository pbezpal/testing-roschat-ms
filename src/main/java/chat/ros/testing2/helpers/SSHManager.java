package chat.ros.testing2.helpers;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static chat.ros.testing2.data.LoginData.*;

public class SSHManager {

    private static JSch ssh = new JSch();
    private static String line;
    private static String HOST_SERVER = System.getProperty("host");

    public SSHManager(){}

    public static boolean isCheckQuerySSH(String command){
        try {
            Session s = ssh.getSession(LOGIN_ADMIN_SSH, HOST_SERVER, PORT_SSH);
            s.setPassword(PASSWORD_ADMIN_SSH);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            s.setConfig(config);
            s.connect();

            Channel c = s.openChannel("exec");
            ChannelExec ce = (ChannelExec) c;
            ce.setCommand(command);
            ce.connect();

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(ce.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            line = reader.readLine();

            ce.disconnect();
            s.disconnect();

            if(line != null) return true;
            return false;

        } catch (JSchException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getQuerySSH(String command){
        try {
            Session s = ssh.getSession(LOGIN_ADMIN_SSH, HOST_SERVER, PORT_SSH);
            s.setPassword(PASSWORD_ADMIN_SSH);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            s.setConfig(config);
            s.connect();

            Channel c = s.openChannel("exec");
            ChannelExec ce = (ChannelExec) c;
            ce.setCommand(command);
            ce.connect();

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(ce.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            line = reader.readLine();

            ce.disconnect();
            s.disconnect();

            return line;

        } catch (JSchException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

    }
    
}
