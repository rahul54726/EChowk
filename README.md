# 🛠 EChowk - Peer-to-Peer Skill Exchange Platform

Welcome to **EChowk**, an open-source full-stack **Peer-to-Peer Skill Exchange Platform** where users can offer skills they are proficient in and learn new skills by exchanging sessions with others.  

The backend is built with **Spring Boot**, ensuring scalability, performance, and security.

---

## 🚀 Features

- **🧑‍🎓 User Registration & Authentication**  
  Secure sign-up and login using JWT (JSON Web Tokens).  

- **🗂️ Role-Based Authorization**  
  Distinct roles for `User` and `Admin` with different levels of access.  

- **✍️ Skill Offer Management**  
  Create, update, and delete skill offerings.  

- **📩 Skill Request System**  
  Users can request to learn a skill with an accept/reject flow for providers.  

- **⭐ Review & Rating System**  
  Comprehensive feedback system for completed sessions.  

- **🖼️ Profile Picture Uploads**  
  Integrated with **Cloudinary** for image storage.  

- **📥 Email Notifications**  
  Automated email notifications for requests and password resets.  

- **⚡ Redis Caching**  
  Faster access to frequently used skill offers.  

- **🔒 Global Exception Handling**  
  Centralized error handling for consistency.  

- **📝 API Documentation**  
  Interactive API docs with **Swagger**.  

---

## 🧑‍💻 Tech Stack

| Category      | Technology / Service |
|---------------|----------------------|
| **Backend**   | `Spring Boot`, `Spring Security`, `Spring Data MongoDB` |
| **Database**  | `MongoDB` |
| **Cache**     | `Redis` |
| **Services**  | `Cloudinary` (Image Storage), `JavaMail Sender` (SMTP) |
| **API & Docs**| `REST APIs`, `JWT` (Auth), `Swagger` |
| **Frontend**  | *(Backend-ready for React, Next.js, or any frontend framework)* |

---

## 📦 Installation & Run (Backend)

Follow these steps to set up the project locally:

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/rahul54726/EChowk.git
cd EChowk
