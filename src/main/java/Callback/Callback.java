package Callback;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Callback {
    private static HttpServer server;

    public Callback(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new Handlers.RootHandler());
            //server.createContext("/echoHeader", new Handlers.EchoHeaderHandler());
            //server.createContext("/echoGet", new Handlers.EchoGetHandler());
            server.createContext("/callback", new Handlers.EchoPostHandler());
            server.setExecutor(null);
            server.start();




//            HttpExchange h = null;
//            File file= new File("C:/Users/arthu/Desktop/success.html");
//            String webresponse= FileUtils.readFileToString(file);
//            String encoding = "UTF-8";
//            h.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
//            h.getResponseHeaders().set("Accept-Ranges", "bytes");
//            h.sendResponseHeaders(200, webresponse.length());
//            OutputStream os2 = h.getResponseBody();
//            os2.write(webresponse.getBytes("UTF-8"));
//            os2.close();
//            HttpServletResponse res = null;
//            res.sendRedirect("http://73.100.144.205:8081/success.html");


        } catch (IOException e) {
            LogManager.getLogger().error(e);
        }
    }
    public static void MyCallback(int port) {

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/", new Handlers.RootHandler());
        //server.createContext("/echoHeader", new Handlers.EchoHeaderHandler());
        //server.createContext("/echoGet", new Handlers.EchoGetHandler());
        server.createContext("/callback", new Handlers.EchoPostHandler());
        server.setExecutor(null);
        server.start();

    }
    public void Stop() {
        server.stop(0);
    }
}
