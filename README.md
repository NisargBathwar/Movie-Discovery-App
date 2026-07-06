# 🎬 MovieApi

A modern Android Movie & TV Series application built using **Jetpack Compose**, **MVVM Architecture**, **Hilt**, **Retrofit**, **Room**, and the **OMDb API**.

The application allows users to browse curated movie collections, search movies and TV series, view detailed information, and save their favorites locally using Room Database.

---
## 📸 App Preview

<p align="center">
  <img src="screenshot/android%20photo/Screenshot_20260706_102521.png" width="185"/>
  <img src="screenshot/android%20photo/Screenshot_20260706_102610.png" width="185"/>
  <img src="screenshot/android%20photo/Screenshot_20260706_102638.png" width="185"/>
</p>

<p align="center">
  <b>🏠 Home</b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>📂 Categories</b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>🎬 Movie Details</b>
</p>

<p align="center">
  <img src="screenshot/android%20photo/Screenshot_20260706_104421.png" width="185"/>
  <img src="screenshot/android%20photo/Screenshot_20260706_105202.png" width="185"/>
  <img src="screenshot/android%20photo/Screenshot_20260706_102834.png" width="185"/>
</p>

<p align="center">
  <b>🔍 Search</b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>🎯 Search Results</b>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b>❤️ Watchlist</b>
</p>

# ✨ Features

- 🎬 Browse curated movie collections
- 📺 Browse curated TV series collections
- 🔍 Search Movies & TV Series using the OMDb API
- 🎞 View detailed movie information
- ❤️ Add movies to Watchlist
- 🗑 Remove movies from Watchlist
- 💾 Offline Watchlist using Room Database
- 🖼 High-quality movie posters
- 📱 Modern Material 3 UI
- 🌙 Dark Theme
- ⚡ Smooth card animations
- 🧭 Bottom Navigation

---

# 🏗 Architecture

```
Presentation
│
├── Compose UI
├── Navigation
├── ViewModels
│
Domain
│
├── Repository
├── Models
│
Data
│
├── Retrofit
├── OMDb API
├── Room Database
├── Repository Implementation
│
Dependency Injection
│
└── Hilt
```

### Architecture Pattern

- MVVM
- Repository Pattern
- Dependency Injection (Hilt)
- Unidirectional Data Flow

---

# 🛠 Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Hilt
- Retrofit
- Room Database
- Kotlin Coroutines
- StateFlow
- Coil
- Navigation Compose
- OMDb API

---

# 📂 Project Structure

```
app
│
├── data
│   ├── api
│   ├── database
│   ├── model
│   └── repository
│
├── di
│
├── navigation
│
├── presentation
│   ├── home
│   ├── search
│   ├── details
│   └── watchlist
│
├── ui
│
└── MainActivity
```

---

# 🌐 API

This application uses the **OMDb API** to fetch:

- Movie Information
- TV Series Information
- Posters
- Ratings
- Release Year
- Genre
- Runtime

---

# 🚀 Getting Started

Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/MovieApi.git
```

Open the project in Android Studio.

Add your **OMDb API Key**.

Build and run the application.

---

# 🌟 Highlights

- 100% Jetpack Compose UI
- MVVM Architecture
- Repository Pattern
- Hilt Dependency Injection
- Offline Watchlist with Room
- Material 3 Design
- Responsive UI
- Smooth Card Animations

---

# 📄 License

This project was created for learning and portfolio purposes.
