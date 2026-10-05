# 🎓 Campus Connect - College Management & Event Platform

A complete, production-ready **Java Full Stack** web application built with **Spring Boot 3**, **Spring Data JPA**, **Embedded H2 Database** (with MySQL support), and a **Modern Glassmorphic Single Page Frontend (HTML5 / CSS3 / JavaScript / Chart.js / QRCode.js)**.

---

## 🌟 Key Highlights & Features

1. **Role-Based Access Control (RBAC)**:
   - **👑 Administrator (Dean / Management)**: Full platform governance, publish circulars, manage departments/courses, respond to grievances, view full analytics, audit downloads.
   - **🎓 Faculty & Staff**: Department view, create & organize events, review attendees, manage academic courses.
   - **🎒 Student**: Event discovery, instant registration, digital ticket passes with scannable QR codes, join student clubs, submit queries & helpdesk tickets.
   - **⚡ 1-Click Role Switcher**: Interactive switcher on the sidebar for instant live demonstration during presentations.

2. **Campus Events Hub & Digital Pass Issuance**:
   - Filter by categories (*Hackathons, College Fests, Workshops, Sports, Seminars*).
   - Real-time seat filling progress indicators and capacity enforcement.
   - **Digital Event Passes**: Instant generation of scannable QR code tickets (`CC-EVT-XXXX-XX`) ready for mobile verification or printing.

3. **Analytics Dashboard & Live Visualizations**:
   - Real-time KPI cards (*Total Events, Registrations, Enrolled Students, Student Clubs*).
   - Interactive **Chart.js** graphs:
     - Event Category Distribution (Doughnut Chart)
     - Department Student Enrollment Strength (Bar Chart)
   - Trending events ranking with live fill rates.
   - Real-time platform activity timeline feed.

4. **Student Clubs & Societies Hub**:
   - Technical, Cultural, and Sports club directories (ByteCraft, RoboX, Tarang, Titans).
   - Instant 1-click **Join Club** integration.

5. **Academic Directory & Course Catalog**:
   - Department directory with HOD details, building locations, and strength.
   - Semester course catalog with credit breakdown and instructor mapping.

6. **Official Notice Board & Circulars**:
   - Pinned high-priority circulars (Examinations, Placement Drives, Academic Awards).
   - Filter by categories and target audience (*All, Students, Faculty*).

7. **Student Helpdesk & Grievance Redressal**:
   - Ticket submission portal for infrastructure, academic, and event requests.
   - Status tracking (*Pending, In Review, Resolved*) with administrative replies.

8. **Audit Reports & CSV Data Export**:
   - Download complete **Registrations CSV audit sheet**.
   - Download complete **Campus Events Master CSV report**.
   - Embedded **H2 Database Console** for direct SQL inspection.

---

## 🛠️ Technology Stack

| Layer | Technologies Used |
|---|---|
| **Backend** | Java 21 / 23, Spring Boot 3.3.5, Spring MVC, Spring Data JPA, Hibernate, Bean Validation |
| **Frontend** | HTML5 Semantic, Custom CSS3 Design System (Glassmorphism, Vibrant Gradients), Vanilla JavaScript (Modular ES6), Chart.js, QRCode.js, Lucide Icons |
| **Database** | Embedded H2 Persistent Database (File storage in `./data/campusconnectdb`) + MySQL Connector support |
| **Build & Tooling** | Maven Wrapper (`mvnw.cmd` / `mvnw`) — No local Maven installation required |

---

## 🚀 How to Run the Project

### Option 1: Quick Launch (Windows)
Double-click `start-app.bat` in the project root directory.

### Option 2: Command Line (Maven Wrapper)
```powershell
# Open terminal in project folder and run:
.\mvnw.cmd spring-boot:run
```

Once started, open your web browser and navigate to:
👉 **[http://localhost:8080](http://localhost:8080)**

---

## 🔑 Pre-Configured Demo Accounts (Zero-Config)

| Role | Name | Email | Password | Details |
|---|---|---|---|---|
| **👑 Admin** | Dr. Rajesh Raman | `admin@campusconnect.edu` | `admin123` | Campus Dean & Chief Administrator |
| **🎓 Faculty** | Prof. Ananya Sharma | `faculty@campusconnect.edu` | `faculty123` | Head of Technical Events & Associate Prof |
| **🎒 Student** | Rahul Verma | `student@campusconnect.edu` | `student123` | 3rd Year B.Tech CSE (Roll: `22CS045`) |
| **🎒 Student** | Priya Patel | `priya@campusconnect.edu` | `student123` | 3rd Year B.Tech ECE (Roll: `22EC012`) |

*(Use the **Active Role** dropdown in the top-left sidebar to switch between any account instantly during your demo!)*

---

## 🗄️ H2 Database Console Access
- **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:file:./data/campusconnectdb`
- **User Name**: `sa`
- **Password**: `password`

---

## 📡 REST API Endpoints Summary

- **Auth & Users**:
  - `POST /api/auth/login` - User login
  - `POST /api/auth/register` - Student registration
  - `GET /api/auth/users` - List all users
- **Events & Ticketing**:
  - `GET /api/events` - List events (search & filter)
  - `POST /api/events` - Create new event
  - `POST /api/events/{id}/register` - Student registration & pass generation
  - `GET /api/events/student/{id}/registrations` - Student's registered passes
- **Departments & Courses**:
  - `GET /api/departments` - List departments
  - `GET /api/courses` - List courses (filter by dept)
- **Clubs**:
  - `GET /api/clubs` - List student societies
  - `POST /api/clubs/join` - Join a club
- **Notice Board**:
  - `GET /api/notices` - List circulars
  - `POST /api/notices` - Publish circular
- **Helpdesk**:
  - `GET /api/feedback` - List queries
  - `POST /api/feedback` - Submit grievance
  - `POST /api/feedback/{id}/respond` - Admin resolution
- **Analytics & Exports**:
  - `GET /api/analytics/dashboard` - KPI counts & graphs
  - `GET /api/analytics/export/registrations/csv` - Download Registrations CSV
  - `GET /api/analytics/export/events/csv` - Download Events CSV

---

## 📁 Project Directory Structure

```
JFS 2/
├── pom.xml                                      # Maven dependencies & build config
├── mvnw.cmd / mvnw                              # Embedded Maven wrapper
├── start-app.bat                                # 1-Click Windows execution script
├── README.md                                    # Documentation & Submission guide
├── src/
│   ├── main/
│   │   ├── java/com/campusconnect/
│   │   │   ├── CampusConnectApplication.java   # Spring Boot entry point
│   │   │   ├── config/
│   │   │   │   ├── WebConfig.java               # CORS & Static resource mappings
│   │   │   │   └── DataInitializer.java         # Auto seed database on startup
│   │   │   ├── controller/                      # REST Controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── EventController.java
│   │   │   │   ├── DepartmentController.java
│   │   │   │   ├── CourseController.java
│   │   │   │   ├── ClubController.java
│   │   │   │   ├── NoticeController.java
│   │   │   │   ├── FeedbackController.java
│   │   │   │   └── AnalyticsController.java
│   │   │   ├── dto/                             # Data Transfer Objects
│   │   │   ├── model/                           # JPA Entities (User, Event, Club, etc.)
│   │   │   ├── repository/                      # Spring Data JPA Repositories
│   │   │   └── service/                         # Business Logic & CSV Generators
│   │   └── resources/
│   │       ├── application.properties           # Database & server port configs
│   │       └── static/                          # Modern Frontend UI
│   │           ├── index.html                   # Single Page Web App structure
│   │           ├── css/
│   │           │   └── styles.css               # Modern glassmorphism dark theme
│   │           └── js/
│   │               └── app.js                   # Interactive client logic & Chart.js
```
