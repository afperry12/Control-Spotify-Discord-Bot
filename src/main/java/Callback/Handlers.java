package Callback;
import Authorization.AuthorizationCode;
import Events.Link;
import Users.Users;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import jdk.nashorn.internal.codegen.CompilerConstants;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.commons.io.FileUtils;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

public class Handlers {
    public static class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "<h1>Server start success</h1>";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static Map<String, String> usernamecode = ExpiringMap.builder().expiration(120, TimeUnit.SECONDS).build();
    public static class EchoPostHandler implements HttpHandler {


        @Override
        public void handle(HttpExchange he) throws IOException {



            System.out.println("Served by /echoPost handler...");
            System.out.println(he.getRequestURI());
            List<NameValuePair> params = new URIBuilder(he.getRequestURI()).getQueryParams();
            String code = String.valueOf(params.get(0).getValue());
            String state = String.valueOf(params.get(1).getValue());
            System.out.println(code);
            System.out.println(state);
            Map<String, String> statecode = ExpiringMap.builder().expiration(120, TimeUnit.SECONDS).build();
            statecode.put(state, code);
            if (Link.usernamestate.containsValue(state) == false) {
                File file = new File("C:/Users/arthu/Desktop/expiredfailure.html");
                String webresponse = FileUtils.readFileToString(file);
                String encoding = "UTF-8";
                he.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
                he.getResponseHeaders().set("Accept-Ranges", "bytes");
                he.sendResponseHeaders(419, webresponse.length());
                OutputStream os = he.getResponseBody();
                os.write(webresponse.getBytes());
                os.close();
            }   else if (Link.stateusername.containsKey(state) == true) {
                Connection conn = null;
                String username=Link.stateusername.get(state);
                try {
                    try {
                        conn =
                                DriverManager.getConnection("jdbc:mysql://localhost:3306/test?" +
                                        "user=root&password=&useUnicode=true&characterEncoding=UTF-8&jdbcCompliantTruncation=false");

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    ResultSet rs=null;
                    String dbName="Users";
                    Statement stmt=null;
                    Statement stmt2=null;
                    final String CREATE_TABLE_SQL="CREATE TABLE users.users ("
                            + "username VARCHAR(255) NOT NULL,"
                            + "code VARCHAR(255) NOT NULL,"
                            + "access VARCHAR(255) NOT NULL,"
                            + "refresh VARCHAR(255) NOT NULL)";


                    if (conn!=null) {
                        try {
                            rs = conn.getMetaData().getCatalogs();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        while (rs.next()) {
                            String catalogs = rs.getString(1);

                            if (dbName.equals(catalogs)) {
                                System.out.println("The Database " + dbName + " Exists!");
                            } else if (dbName.equals(catalogs)==false){
                                System.out.println("Unable to Verify Database Connection");
                                try {
                                    stmt = conn.createStatement();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }

                                String sql = "CREATE DATABASE Users";
                                try {
                                    stmt.executeUpdate(sql);
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                System.out.println("Database created successfully...");
                            }

                            try {
                                if (stmt != null)
                                    stmt.close();
                            } catch (SQLException se2) {

                            }
                        }
                    }
                    try {
                        stmt2 = conn.createStatement();

                        stmt2.executeUpdate(CREATE_TABLE_SQL);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            // Close connection
                            if (stmt != null) {
                                stmt.close();
                            } } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    String deleteQuery = "delete from users.users where username = ?";
                    PreparedStatement preparedStmt = conn.prepareStatement(deleteQuery);
                    preparedStmt.setString(1,username);
                    preparedStmt.execute();

                        String query = "INSERT INTO users.Users("+"username"+","+"code) "
                                + " values (?, ?)";

                    try {
                        PreparedStatement pstmt = conn.prepareStatement(query,
                                Statement.RETURN_GENERATED_KEYS);
                        pstmt.setString (1, username);
                        pstmt.setString (2, code);
                        pstmt.execute();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                    usernamecode.put(username, code);
                    int x=0;
                    for (Map.Entry<String,String> entry : usernamecode.entrySet()) {
                        x=x+1;
                        System.out.println("Key "+x+": "+entry.getKey());
                        System.out.println("Value "+x+": "+entry.getValue());
//                        System.out.println("Usernamecode Pair "+x+": "+usernamecode.entrySet());
                    }
                    System.out.println("Discordusername+AuthCode: " + usernamecode);
                    File file = new File("C:/Users/arthu/Desktop/success.html");
                    String webresponse = FileUtils.readFileToString(file);
                    String encoding = "UTF-8";
                    he.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
                    he.getResponseHeaders().set("Accept-Ranges", "bytes");
                    he.sendResponseHeaders(200, webresponse.length());
                    Link.stateusername.remove(state);
                    OutputStream os = he.getResponseBody();
                    os.write(webresponse.getBytes());
                    os.close();

                String[] args = new String[0];
                AuthorizationCode.authorizationCode_Async(username,code,Link.channel);


                }
            }
        }
    }

