# 🧩 Multi-Threaded Web Server (Java)

A **Java-based multi-threaded HTTP server** built from scratch using core networking primitives (`ServerSocket` / `Socket`) to handle concurrent TCP connections, parse **HTTP/1.1** requests, serve static files, return standard status codes, and showcase scalable concurrency using **thread-per-connection** or a **configurable thread pool**.

Packaged with **Maven** and validated under load using **ApacheBench (ab)** and **JMeter**, this project serves as a practical foundation for learning **sockets**, **synchronization**, **low-level HTTP handling**, and **performance testing in Java**.

# Apache BenchMark Testing
<img width="1920" height="1080" alt="Screenshot 2025-10-23 175105" src="https://github.com/user-attachments/assets/7d9d103d-4ec5-4554-9c4b-3ba073a82531" />

# JMeter Testing
<img width="1635" height="1079" alt="Screenshot 2025-10-31 231355" src="https://github.com/user-attachments/assets/3a68757e-cbf7-457a-bd01-ec77dc134e32" />
<img width="1694" height="1079" alt="Screenshot 2025-10-31 231559" src="https://github.com/user-attachments/assets/66f8c5d6-3a9f-4bdd-8510-2f1a886a8a37" />

---

## 🚀 Features

* ✅ Multi-threaded request handling using per-connection threads or an `ExecutorService` pool
* 📜 Basic HTTP/1.1 `GET` parsing and response generation with `200`, `404`, and `500` status codes
* 📁 Static file serving from a configurable web root
* ⚙️ Load testing workflows with **ApacheBench** and **JMeter**
* 🧱 Maven build for reproducible packaging

---

## 🧠 Tech Stack

| Category        | Tools / Frameworks                           |
| --------------- | -------------------------------------------- |
| **Language**    | Java 8+ (networking, I/O, concurrency)       |
| **Concurrency** | Threads, ExecutorService                     |
| **Networking**  | ServerSocket / Socket (TCP), HTTP/1.1 basics |
| **Build**       | Maven                                        |
| **Testing**     | ApacheBench (ab), JMeter                     |
| **IDE**         | IntelliJ IDEA metadata included              |

---

## ⚙️ Getting Started

### 🔧 Prerequisites

* **JDK 8+** – check version:

  ```bash
  java -version
  javac -version
  ```
* **Maven** – check version:

  ```bash
  mvn -v
  ```
* *(Optional)* **ApacheBench (ab)** and **JMeter** for load testing.

---

### 📦 Installation

```bash
# Clone repository
git clone https://github.com/AnshuVairagade/Multi-Threaded-Web-Server-java
cd Multi-Threaded-Web-Server-java

# Build project
mvn clean install
```

The packaged JAR will be generated under the `target/` directory.

---

### ▶️ Run the Server

```bash
# Default (uses project defaults)
java -jar target/<your-jar-file>.jar

# Example with options (if supported)
java -jar target/<your-jar-file>.jar --port 8080 --www ./public
```

Then, open [http://localhost:8080/](http://localhost:8080/) in a browser to verify the server is running.

---

### 🧪 Quick Test

```bash
# Check response headers and 200 OK
curl -i http://localhost:8080/

# Request a static file (ensure index.html exists)
curl -i http://localhost:8080/index.html
```

Expect a **404 Not Found** for non-existent paths to validate error handling.

---

## ⚡ Load Testing

### ApacheBench Quick Run

```bash
ab -n 1000 -c 50 http://127.0.0.1:8080/
```

### JMeter Scenario

* Create a **Thread Group**
* Add **HTTP Request** samplers for `/` and static files
* Configure **Ramp-Up Period** and user count
* Analyze results via **Aggregate Report** or **Graphs**

---

## ⚙️ Configuration

* **Port**, **web root**, and **thread-pool size** can be set via CLI flags or configuration file
* Tune thread pool relative to CPU cores and expected concurrency
* Use **buffered streams**, compute `Content-Length`, and consider simple caching for optimization

---

## 🗂️ Project Structure

```
Multi-Threaded-Web-Server-java/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
├── public/                # Optional static content
├── pom.xml                # Maven build file
└── target/                # Compiled artifacts
```

Includes IntelliJ IDEA project metadata for quick import.

---

## 🧩 Troubleshooting

| Issue                      | Possible Fix                                                          |
| -------------------------- | --------------------------------------------------------------------- |
| **Port already in use**    | Run on another port or free the port                                  |
| **403/404 on files**       | Verify web root and file permissions                                  |
| **Low RPS / High latency** | Use bounded ExecutorService, tune thread-pool size, minimize blocking |

---

## 🗺️ Roadmap

* [ ] Add support for `HEAD` and `POST` methods
* [ ] Directory listing, MIME type mapping, caching headers
* [ ] Graceful shutdown and connection keep-alive
* [ ] Access/error logging and metrics

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a feature branch
3. Commit changes with clear messages
4. Open a pull request describing the rationale and testing

---

## 📄 License

This project is licensed as specified by the repository owner.
Please include a **LICENSE** file for clarity.

1. Fork the repository
2. Create a feature branch
3. Commit changes with clear messages
4. Open a pull request describing the rationale and testing

---

## 📄 License

This project is licensed as specified by the repository owner.
Please include a **LICENSE** file for clarity.
