package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultiThreadedServer {
    public static void main(String[] args) {
        final int port = 8080;

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                4, 4, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50)
        );

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Running the server at port "+ port);
            while(true){
                Socket socket = serverSocket.accept();
                threadPoolExecutor.execute(()->requestHandler(socket));
            }
        }
        catch(IOException e){
            System.out.println("Error handling error");
        }

    }

    private static void requestHandler(Socket socket) {
        try(
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream()){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            String parts[] = line.split(" ");
            String request = parts[0];
            String path = parts[1];
            Thread.sleep(1000);
            if("GET".equalsIgnoreCase(request) && "/messages".equalsIgnoreCase(path)){
                responseSender(outputStream);
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket");
            }
        }

    }

    private static void responseSender(OutputStream outputStream) throws  IOException{
        String message = "This is multi-threaded web server";
        String httpResponse = """
                HTTP/1.1 200 OK
                Content-type: text/plain
                Content-length:"""+message.length()+"\n\n"+
                message;
        outputStream.write(httpResponse.getBytes());
        outputStream.flush();
        outputStream.close();

    }
}
