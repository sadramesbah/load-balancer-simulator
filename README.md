# 🎛️ Load Balancer Simulator

## 📖 About

This project is a **Spring Boot** application that simulates load balancing across multiple servers with configurable CPU and RAM resources. It provides a framework for understanding and testing load balancing algorithms and strategies. The application includes a web interface to monitor server utilization and task distribution.

## ✨ Key Features

- **Server Management**: Dynamically create, add, remove, and manage servers with configurable resources.
- **Task Handling**: Efficiently assign tasks to servers based on available resources and handle task completion.
- **Resource Allocation**: Allocate high-performance and low-performance cores, as well as RAM, to tasks.
- **Monitoring**: Web interface to monitor server utilization and task distribution.
- **JUnit Testing**: Comprehensive unit tests to ensure the reliability and correctness of server/task management functionalities.

## 🛠️ Technologies

- **Java**: The primary programming language used for the development of the simulator.
- **Spring Boot**: Framework for building the application.
- **Spring MVC**: Framework for building the web interface.
- **Maven**: Build automation tool for managing project dependencies and building the project.

## 🗂️ Project Structure

- **Model**: Contains the core classes representing servers and tasks.
- **Service**: Includes the `ServerService` class responsible for managing servers and handling tasks.
- **Controller**: Contains the `ServerController` class for handling HTTP requests and responses.
- **View**: Includes the HTML, CSS, and JavaScript files for the web interface.
- **Tests**: JUnit test cases to validate the functionality of the service layer.

## 🚀 Getting Started

1. **Clone the repository**:
    ```sh
    git clone https://github.com/sadramesbah/load-balancer-simulator.git
    cd load-balancer-simulator
    ```

2. **Build the project**:
    ```sh
    mvn clean install
    ```

3. **Run the application**:
    ```sh
    mvn spring-boot:run
    ```

4. **Access the web interface**:
   Open your browser and navigate to `http://localhost:8080/server-stats` to create tasks and monitor server utilization and task distribution.

## 📜 License

This project is licensed under the **GNU General Public License**. See the `LICENSE` file for more details.