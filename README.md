# 🏫 ExamMaster - An examination management system (EMS) 

A role-based Spring Boot application for managing university-level exams with secure JWT authentication, dynamic seating arrangements, and a user-friendly UI using Thymeleaf.

---

## 🚀 Features

### ✅ Authentication & Authorization
- **User Registration**: Register new users (Admin or Student).
- **JWT Login**: Issues a token only if the user is not already logged in.
- **Secure Logout**: Removes or expires the token from in-memory storage.
- **Role-Based Access**:
  - `ADMIN`: Can create exams, manage students/subjects, and allocate seating.
  - `STUDENT`: Can view hall allocations and download hall tickets.

---

## 🎓 User Roles
- `ADMIN`: Full system control.
- `STUDENT`: Restricted to viewing only permitted information.

---

## 🪑 Seating Arrangement
- Auto-assign students to classrooms in round-robin fashion.
- Rebuild seating allocations if needed.
- Generate hall allocation summary for each exam.

---

## 🎫 Hall Ticket System
- View assigned classroom per exam.
- Accessible by students for each scheduled exam.

---

## 🧰 Tech Stack

| Layer              | Technology                            |
|-------------------|----------------------------------------|
| Backend            | Spring Boot, Spring Security, JWT     |
| Frontend (UI)      | Thymeleaf (dynamic server-side HTML)  |
| Authentication     | JWT (stateless security)              |
| Database           | H2 (in-memory for development)        |
| ORM & Persistence  | JPA / Hibernate                       |
| API Testing        | Postman                               |
| Build Tool         | Maven                                 |

---

## 🔐 Authentication Flow

1. **Register**: `POST /ems/auth/register`
2. **Login**: `POST /ems/auth/login`  
   - Returns JWT if not already logged in.
3. **Use Token**:  
   - Add token in Postman using:
     ```
     Authorization: Bearer <token>
     ```
4. **Logout**: `POST /ems/auth/logout`  
   - Pass token in `Authorization` header to invalidate.

---

---

## 🔄 Workflow

### 👤 User Management
1. **Register**  
   - Admin or Student registers using `/ems/auth/register`.
   - Duplicate usernames are rejected with a proper message.

2. **Login**  
   - Authenticates using `/ems/auth/login`.
   - Returns a JWT token only if the user is not already logged in.

3. **Logout**  
   - Uses `/ems/auth/logout` with the JWT in the `Authorization` header.
   - Removes token from in-memory store to log out user.

---

### 🏫 Exam & Seating Flow (Admin Only)

1. **Create Entities**  
   - Add students, subjects, classrooms using `/ems/student`, `/ems/subject`, `/ems/classroom`.

2. **Create Exam**  
   - Add a new exam via `/ems/exam`.
   - Automatically triggers seat allocation using round-robin distribution.

3. **Rebuild Allocation** *(if needed)*  
   - Endpoint to regenerate seat assignments: `/ems/hallAllocation/rebuild`.

4. **View Allocations**  
   - Admin can view room allocations per exam.
   - Students can view their assigned classroom using `/ems/hallAllocation/student/{username}`.

---

### 🧾 Hall Ticket (Student)

1. **Login as Student**
2. **Access Hall Ticket Info**  
   - Authenticated students can view classroom allocation for each exam.

---

### 🔐 Role-Based Access Summary

| Endpoint                          | Access Role   | Auth Required |
|----------------------------------|---------------|---------------|
| `/ems/auth/register`             | Public        | ❌            |
| `/ems/auth/login`                | Public        | ❌            |
| `/ems/auth/logout`               | ADMIN/STUDENT | ✅            |
| `/ems/student/**`, `/exam/**`    | ADMIN only    | ✅            |
| `/ems/hallAllocation/ticket/**` | STUDENT only  | ✅            |
| `/ems/hallAllocation/rebuild`    | ADMIN only    | ✅            |

---

## 📦 Modules & Packages

- `com.naher_farhsa.ems.Controller`: All controllers (auth, seating, etc.)
- `com.naher_farhsa.ems.Service`: Business logic
- `com.naher_farhsa.ems.JWT`: token utility and in-memory token store
- `com.naher_farhsa.ems.Entity`: Entities like User, Student, Exam, etc.
- `com.naher_farhsa.ems.DTO`: DTOs for Login and JWT responses
- `com.naher_farhsa.ems.Config`: Spring Security configuration

---

## 📄 How to Run

```bash
git clone <https://github.com/naher-farhsa/ExamMaster-EMS.git>
cd ems
mvn spring-boot:run
