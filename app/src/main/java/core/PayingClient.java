package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ahmetkucuk on 01/11/14.
 */public class PayingClient {
    public static String sendRequest(String host, int port, String req){
        Gson gson = new GsonBuilder().create();
        /** Define a host server */
        /** Define a port */

        StringBuffer instr = new StringBuffer();
        String TimeStamp;
        System.out.println("SocketClient initialized");
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket connection = new Socket(host, port);
            BufferedOutputStream bos = new BufferedOutputStream(
                    connection.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(bos, "UTF-8");
            JsonObject request = new JsonObject();
            System.out.println(gson.toJson(request));
            String process = req + (char) 13;


            /** Write across the socket connection and flush the buffer */
            System.out.println("Test 5");
            osw.write(process);
            osw.flush();

            System.out.println("Test 21");
            /**
             * Instantiate a BufferedInputStream object for reading /**
             * Instantiate a BufferedInputStream object for reading incoming
             * socket streams.
             */

            BufferedInputStream bis = new BufferedInputStream(
                    connection.getInputStream());
            /**
             * Instantiate an InputStreamReader with the optional character
             * encoding.
             */

            InputStreamReader isr = new InputStreamReader(bis, "UTF-8");

            /** Read the socket's InputStream and append to a StringBuffer */
            int c;
            while ((c = isr.read()) != 13)
                instr.append((char) c);

            /** Close the socket connection. */
            connection.close();
            System.out.println(instr);

        } catch (IOException f) {
            System.out.println("IOException: " + f);
        } catch (Exception g) {
            System.out.println("Exception: " + g);
        }
        return instr.toString();
    }
}

