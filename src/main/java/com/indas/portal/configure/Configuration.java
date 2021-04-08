package com.indas.portal.configure;//package com.indas.portal.configure;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//
//public class Configuration {
//
//    private static Configuration configuration;
//
//    @Autowired
//    private Configuration(Environment env){
//        server = env.getProperty("server");
//        fileName = env.getProperty("fileName");
//        eXTENSION = env.getProperty("extension");
//        type = env.getProperty("type");
//    }
//
//    public static Configuration getInstanse(){
//        if (configuration == null) {
//            configuration = new Configuration();
//        }
//        return configuration;
//    }
//
//
//    @Value( "${server}" )
//    public String server;
//    @Value( "${fileName}" )
//    public String fileName;
//    @Value( "${extension}" )
//    public String eXTENSION;
//    @Value( "${type}" )
//    public String type;
//
//
//}
