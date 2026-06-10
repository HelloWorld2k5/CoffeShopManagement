package com.coffeeshop.config;

public class AppConfig {
    private static AppConfig instance;

    // Thông tin ứng dụng mặc định
    private String appName;
    private String appVersion;
    private String databaseHost;
    private int databasePort;
    private String databaseName;
    private String databaseUser;
    private String databasePassword;

    private AppConfig() {
        this.appName = "Coffee Shop Management";
        this.appVersion = "1.0.0";
        this.databaseHost = "localhost";
        this.databasePort = 3306;
        this.databaseName = "quanLyCaPheDb";
        this.databaseUser = "root";
        // Nhập mk mysql vào đây
        this.databasePassword = "";
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public void setDatabasePort(int databasePort) {
        this.databasePort = databasePort;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getJdbcUrl() {
        return "jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseName
                + "?useSSL=false&serverTimezone=Asia/Bangkok&allowPublicKeyRetrieval=true";
    }
}