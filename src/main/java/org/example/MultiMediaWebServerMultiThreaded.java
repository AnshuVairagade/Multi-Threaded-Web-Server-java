package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MultiMediaWebServerMultiThreaded {
        private static final int PORT = 8080;
        private static final int THREAD_POOL_SIZE = 4;
        private static final int QUEUE_LIMIT = 50;
        private static final String WEB_ROOT = "src/main/resources/public"; // Folder to serve files from

        public static void main(String[] args) {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                    THREAD_POOL_SIZE,
                    THREAD_POOL_SIZE,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(QUEUE_LIMIT)
            );

            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Server running on port: " + PORT);
                System.out.println("Serving files from: " + new File(WEB_ROOT).getAbsolutePath());

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    threadPoolExecutor.execute(() -> handleClient(clientSocket));
                }

            } catch (IOException e) {
                System.err.println("Server error: " + e.getMessage());
            }
        }

        private static void handleClient(Socket socket) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 OutputStream out = socket.getOutputStream()) {

                String requestLine = in.readLine();
                if (requestLine == null || requestLine.isEmpty()) {
                    return;
                }

                System.out.println("Request: " + requestLine);

                String[] parts = requestLine.split(" ");
                if (parts.length < 2) {
                    sendBadRequest(out);
                    return;
                }

                String method = parts[0];
                String path = parts[1];

                if (!"GET".equalsIgnoreCase(method)) {
                    sendMethodNotAllowed(out);
                    return;
                }

                serveFile(out, path);

            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }

        private static void serveFile(OutputStream out, String path) throws IOException {
            if (path.equals("/")) {
                path = "/index.html"; // Default file
            }

            File file = new File(WEB_ROOT, path);
            if (!file.exists() || file.isDirectory()) {
                sendNotFound(out);
                return;
            }

            String contentType = Files.probeContentType(file.toPath());
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            String header = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: " + (contentType != null ? contentType : "application/octet-stream") + "\r\n" +
                    "Content-Length: " + fileBytes.length + "\r\n\r\n";

            out.write(header.getBytes());
            out.write(fileBytes);
            out.flush();
        }

        private static void sendNotFound(OutputStream out) throws IOException {
            String response = "HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: text/plain\r\n\r\n" +
                    "404 - File Not Found";
            out.write(response.getBytes());
            out.flush();
        }

        private static void sendBadRequest(OutputStream out) throws IOException {
            String response = "HTTP/1.1 400 Bad Request\r\n" +
                    "Content-Type: text/plain\r\n\r\n" +
                    "400 - Bad Request";
            out.write(response.getBytes());
            out.flush();
        }

        private static void sendMethodNotAllowed(OutputStream out) throws IOException {
            String response = "HTTP/1.1 405 Method Not Allowed\r\n" +
                    "Content-Type: text/plain\r\n\r\n" +
                    "405 - Only GET method is supported";
            out.write(response.getBytes());
            out.flush();
        }
    }

