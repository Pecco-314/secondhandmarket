package com.zerone.secondhandmarket.connectssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class SSHConnection {

// 需要了可以打开
//private final static String S_PATH_FILE_PRIVATE_KEY = "/Users/xxx/.ssh/id_rsa";
// 需要了可以打开
//private final static String S_PATH_FILE_KNOWN_HOSTS = "/Users/xxx/.ssh/id_rsa/.ssh/known_hosts";

    // 自定义的中转接口，需要和数据源接口设置一样
    private final static int LOCAl_PORT = 3307;
    // 服务端的数据库端口
    private final static int REMOTE_PORT = 3306;

    // 服务器端SSH端口 默认是22
    private final static int SSH_REMOTE_PORT = 22;
    // SSH用户名
    private final static String SSH_USER = "ubuntu";
    // SSH使用密码
    private final static String SSH_PASSWORD = "Zerone_0101";
    // 连接到哪个服务端的SSH
    private final static String SSH_REMOTE_SERVER = "1.15.220.157";
    // 服务端的本地mysql服务
    private final static String MYSQL_REMOTE_SERVER = "127.0.0.1";

    private Session session; //represents each ssh session

    // 测试连接
    public static void main(String[] args) throws Throwable {
        System.out.println(new SSHConnection());
    }

    public SSHConnection() throws Throwable {

        JSch jsch = new JSch();
        // 需要用到了开启
        // jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
        //jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY);

        session = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
        session.setPassword(SSH_PASSWORD);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        // 去连接
        session.connect(); //ssh connection established!
        // 设置转发
        session.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);

        System.out.println("SSHConnection--运行OK");
    }

    public void closeSSH() {
        session.disconnect();
    }
}
