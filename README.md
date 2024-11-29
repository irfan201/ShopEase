# ShopEase
![image](https://github.com/user-attachments/assets/566504ab-bcf6-42d7-b2b7-68b9f426982c)

ShopEase is an Android-based e-commerce platform designed to provide a fast, secure, and seamless online shopping experience. Developed using Kotlin and XML, the app allows users to browse product catalogs, add items to their cart, and complete transactions using various payment methods. Built with an MVVM architecture, ShopEase offers key features such as product search, wishlist management, and purchase history.

## Demo
https://github.com/user-attachments/assets/2e432718-282c-4677-85c4-42e520ef064f

## Table of Contents
- [ShopEase](#shopease)
  - [Demo](#demo)
  - [Table of Contents](#table-of-contents)
  - [Tech Stack](#tech-stack)
  - [Features](#features)
  - [Project Structure](#project-structure)
  - [Architecture Pattern](#architecture-pattern)
  - [Dependency Injection](#dependency-injection)
  - [Prerequisites to Run This Project](#prerequisites-to-run-this-project)
  - [Installation](#installation)

## Tech Stack
![image](https://github.com/user-attachments/assets/92ec4267-ea33-4be9-94ed-b07f7bb30709)

## Features
- **Firebase Google Authentication** for secure and easy login
- **Firebase Crashlytics** for real-time crash reporting and app stability monitoring
- **Product Search**  Quickly find products with a responsive and precise search functionality.
- **Shopping Cart Management** add, remove, and update items in the shopping cart to prepare for checkout.
- **Favorite Products** Save favorite products to a dedicated list for easy access and future purchases.
- **Checkout with Multiple Payment Options** Complete purchases with various payment methods
- **Order History** Track past orders with detailed information, including order status and product details.

## Project Structure
- **Data Layer**: Handles data sources such as APIs, databases, and network responses. It manages data fetching and persistence.
- **Domain Layer**: Contains the core business logic of the application, including use cases and entities. This layer acts as a bridge between the data and presentation layers.
- **Presentation Layer**: Manages the UI components, user interactions, and ViewModels that provide data to the views.
![image](https://github.com/user-attachments/assets/70ec508d-c4f8-46c0-b7ff-a117f0286622)

## Architecture Pattern
ShopEase is built using the MVVM (Model-View-ViewModel) architectural pattern. This pattern separates the user interface (UI) from the business logic, which offers several benefits:

- **Model**: Represents the data layer, including data sources, APIs, and repositories. It handles data manipulation and provides the required data to the ViewModel.
- **View**: Refers to the user interface components such as Activity, Fragment, or XML layout files. The View is responsible for displaying data to the user and sending user actions to the ViewModel.
- **ViewModel**: Acts as a mediator between the Model and View. It fetches data from the Model and provides it to the View in a format that can be easily displayed. It also manages UI-related logic and survives configuration changes, such as screen rotations, ensuring data persistence.
By adopting MVVM, ShopEase promotes a clean separation of concerns, making the codebase more maintainable, testable, and scalable.

![image](https://github.com/user-attachments/assets/1badc8cb-f936-4e78-a473-6cd35b362e83)

## Dependency Injection
ShopEase utilizes Hilt, a dependency injection framework built on top of Dagger. Dependency injection (DI) is a design pattern that allows objects to receive their dependencies from an external source rather than creating them internally.

## Prerequisites to Run This Project
Sebelum menjalankan aplikasi ini, pastikan Anda telah menginstal:
- **Android Studio**  (latest version recommended)
- **Java Development Kit (JDK)**
- An active internet connection to download project dependencies

## Installation
Follow these steps to run the project on your local machine:
1. Clone this repository:
   ```bash
   git clone https://github.com/username/ShopEase.git
   
- Open the project in Android Studio.
- Sync the project with Gradle files.
- Run the project on an emulator or a physical device.

