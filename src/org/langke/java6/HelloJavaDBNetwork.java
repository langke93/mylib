package org.langke.java6;

public class HelloJavaDBNetwork {
	public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String protocol = "jdbc:derby:";
    public static String framework =  "derbyclient";

    public static void main(String[] args) {
        // same as before
    }
    private static void parseArguments(String[] args) {
        if (args.length == 0 || args.length > 1) {
            return;
        }
        if (args[0].equalsIgnoreCase("derbyclient")) {
            framework = "derbyclient";
            driver = "org.apache.derby.jdbc.ClientDriver";
            protocol = "jdbc:derby://localhost:1527/";
        }
    }

}
