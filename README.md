# Sheet Cell Collaborative Application

**![Sheet_Cell_Banner](https://github.com/user-attachments/assets/9a983ab6-cd39-48dc-b16e-730f83224f74)**  

---

## Overview

Sheet Cell is a collaborative client-server desktop application built in Java using JavaFX, providing an Excel-like spreadsheet environment for real-time data manipulation, analysis, and visualization. Designed to support concurrent users, it includes real-time chat and version control, making it ideal for teams working on shared sheets.

![mainScreen](https://github.com/user-attachments/assets/2bd4c726-c7f4-4c94-ad15-869af1c06dea)

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
   - [Basic Features](#basic-features)
   - [Advanced Features](#advanced-features)
3. [Architecture](#architecture)
4. [Technologies Used](#technologies-used)
5. [Getting Started](#getting-started)
   - [Screenshots and Usage](#screenshots-and-usage)
   - [Prerequisites](#prerequisites)
6. [Running the Application](#running-the-application)
   - [Server Setup](#server-setup)
   - [Client Setup](#client-setup)
   - [Additional Commands (Optional)](#additional-commands-optional)

---

## Features

### Basic Features

- **Spreadsheet Interface**  
  Familiar grid layout for data entry and manipulation, similar to conventional spreadsheet applications.

- **Real-Time Collaboration**  
  Multiple users can edit the same sheet simultaneously, with updates reflected in real-time for all connected users.

- **Cell Operations**  
  - Update cell values and view dependencies.
  - Manage ranges for grouped operations.

- **Real-Time Chat**
- Built-in chat for seamless communication between users. 

### Advanced Features

- **Sorting and Filtering**  
  - Multi-column sorting and flexible filtering for customized data views.
    
  ![filter data](https://github.com/user-attachments/assets/6d4668ac-feb2-452d-9c33-45a4ca225ce5)

- **Graphing and Visualization**  
  - Create line, bar, and other charts from selected data ranges with real-time statistics for maximum, minimum, and average values.
    
  ![makingGraph](https://github.com/user-attachments/assets/57323d5f-e87b-4f4f-83f1-560d64712034)

- **Dynamic Analysis**  
  - Run-time analysis to experiment with data changes and observe dependencies without permanently modifying the sheet.
    
  ![RunTimeAnalysis](https://github.com/user-attachments/assets/140363a5-7b1b-4d2f-81ba-0c7b1af50f7f)

- **Auto-Complete**  
  - Auto-complete based on detected patterns and sequence predictions.
    
  ![AutoComplete](https://github.com/user-attachments/assets/eedcd836-dcdf-4591-923a-984d4c51597c)

- **Find & Replace**  
  - Find and replace values with a preview option.
    
  ![FindAndReplace](https://github.com/user-attachments/assets/6df11887-9088-417d-bb4f-55a20c391d88)

- **Permissions and User Management**  
  - Access levels (Owner, Reader, Writer) for each user, managed directly through the dashboard.
  - ![gettingIntoTheSystem](https://github.com/user-attachments/assets/ced82721-544b-4ef6-873b-70f060951bdf)

- **Version History**  
  - Built-in chat for seamless communication between users.
  - ![PreviousVersions](https://github.com/user-attachments/assets/bb69beed-9482-46be-8486-62b1ef3e6e85)

---

## Architecture

This project follows an MVC architecture and a client-server model to synchronize data across multiple users.

### Server-Side

- **Java Servlets**: Handle requests and manage session data.
- **Data Persistence**: Supports session and version tracking.

### Client-Side

- **JavaFX**: For a responsive, interactive UI.
- **FXML**: For UI component design across screens.
- **HttpRequestManager**: Manages HTTP requests to handle CRUD operations and sheet updates.

---

## Technologies Used

- Java 11+
- JavaFX for UI
- FXML for layout design
- MVC architecture
- Tomcat server
- Gson for JSON serialization

---

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- JavaFX SDK 
- Apache Tomcat server 
  
## Running the Application

After cloning the repository, navigate to the zipped folder structure containing the `client_setup` and `server_setup` folders. Follow these instructions to get the server and client running.

### Server Setup

1. **Requirements**:
   - Ensure that **Docker Desktop** is installed and running.

2. **Steps to Start the Server**:
   - Open the `server_setup` folder.
   - Double-click `run.bat` to start the server.
     - This script will build the Docker image, start the Docker container, and deploy the application on `http://localhost:8080`.
   - Verify that the server is running by opening `http://localhost:8080` in a web browser.

### Client Setup

1. **Steps to Start the Client**:
   - Open the `client_setup` folder.
   - Double-click `run.bat` to start the client application.
   - To open multiple clients, run `run.bat` again in a new window.
   - The client application will connect to the server running on `http://localhost:8080`.

### Additional Commands (Optional)

- **To stop the server**, run:
  ```bash
  docker stop my-java-app-server
