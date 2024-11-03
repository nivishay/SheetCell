**![Sheet_Cell_Banner](https://github.com/user-attachments/assets/9a983ab6-cd39-48dc-b16e-730f83224f74)**  

---

## Overview

This is a collaborative client-server desktop application built in Java using JavaFX. It provides an Excel-like spreadsheet environment where multiple users can interact with shared sheets in real-time. Users can manipulate data, analyze values, and visualize results with various tools, including graphing and filtering options. The application is designed to support concurrent users, enabling seamless collaboration with features like real-time chat and version control.

## Table of Contents

1. [Features](#features)
   - [Basic Features](#basic-features)
   - [Advanced Features](#advanced-features)
2. [Architecture](#architecture)
3. [Technologies Used](#technologies-used)
4. [Getting Started](#getting-started)
   - [Prerequisites](#prerequisites)
   - [Installation](#installation)
5. [Screenshots and Usage](#screenshots-and-usage)
6. [Customization and Permissions](#customization-and-permissions)
7. [Known Issues and Limitations](#known-issues-and-limitations)

---

## Features

### Basic Features

- **Spreadsheet Interface**  
  An intuitive grid layout for data entry and manipulation, emulating familiar spreadsheet functionalities.

- **Real-Time Collaboration**  
  Multiple users can work on the same sheet simultaneously, with updates synchronized across all clients.

- **Cell Operations**  
  - Update cell values and see dependent cells highlighted.
  - View and manage neighboring cells and dependent relationships within the sheet.
  - Add, edit, and delete ranges for grouped operations.

### Advanced Features

- **Sorting and Filtering**  
  - Sort data within specified ranges with multi-column support.
  - Filter data based on multiple criteria across different columns for customized views.

- **Graphing and Visualization**  
  - Generate line charts, bar charts, and other graphs from selected data ranges.
  - View real-time statistics (e.g., max, min, average) for selected data sets.

- **Dynamic Analysis**  
  - **RunTime Analysis**: Experiment with hypothetical data changes to see their impact on dependent cells without permanently modifying the sheet.

- **Auto-Complete and Find & Replace**  
  - Auto-complete patterns based on identified sequences within the data.
  - Find specific values within the grid and replace them with new ones, with an option to preview changes.

- **Permissions and User Management**  
  - Define access levels (e.g., owner, reader, writer) for each user.
  - Manage permissions directly through the dashboard.

- **Real-Time Chat**  
  - Communicate with other users directly within the application to facilitate collaborative work.

---

## Architecture

This project follows an MVC (Model-View-Controller) architecture and uses a client-server model to manage data synchronization and interactions across users.

### Server-Side

- **Java Servlets**: Handle client requests, manage business logic, and ensure data consistency.
- **Data Persistence**: Implemented for session management and version tracking of the sheets.

### Client-Side

- **JavaFX**: For a responsive and interactive UI.
- **FXML**: Used to design the UI components for different screens.
- **HttpRequestManager**: Manages HTTP requests sent to the server for CRUD operations, sheet updates, and user management.

---

## Technologies Used

- Java 11+
- JavaFX for the user interface
- FXML for UI layout design
- MVC architecture
- Tomcat server (or specify server used)
- Gson for JSON parsing and data transfer objects (DTOs)

---

## Getting Started

getting into the sheet gif explaination
![getting-into-sheet](https://github.com/user-attachments/assets/039bc885-b929-4641-bc94-1fc0b1bfc379)


### Prerequisites

- Java Development Kit (JDK) 11 or higher
- JavaFX SDK 
- Apache Tomcat server 
  
### Installation

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/yourusername/yourprojectname.git
