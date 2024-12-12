# LearnIT - Study Management Application

**LearnIT** is a study management application designed to help students organize their study goals, track tasks, and monitor progress. With features like subject management, task tracking, and goal setting, LearnIT provides an intuitive and efficient way to enhance academic productivity.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Overview

LearnIT enables students to manage their study hours, track subjects, set study goals, and keep track of their tasks. This application allows:

- **Subject Management**: Create, update, and delete subjects, each with a specific goal and color for easy visual identification.
- **Task Tracking**: Mark tasks as completed and monitor progress toward goals.
- **Real-Time Progress Monitoring**: Track total study hours across subjects and visualize progress.
- **State Management**: Utilizes the ViewModel for handling UI-related data and ensures seamless real-time updates.

## Features

- **Subject Management**: 
  - Create, edit, and delete subjects.
  - Set specific study goals and target study hours for each subject.
  - Assign unique colors to each subject for easy visual distinction.

- **Task Tracking**: 
  - Add tasks related to each subject.
  - Mark tasks as complete or incomplete to track progress.

- **Progress Tracking**: 
  - Monitor study hours spent on each subject and compare it to the target study hours.
  - Visual progress bar to show completion percentage for each subject.

- **UI Notifications**: 
  - Snackbar notifications for success or failure when actions are completed (e.g., task completion, subject update).

- **Interactive UI**: 
  - Dynamic card colors to differentiate subjects.
  - Real-time input for subject names and study hours, reflecting changes immediately.

## Technologies Used

LearnIT uses the following technologies:

- **Kotlin**: Primary programming language used for the app.
- **Jetpack Compose**: UI toolkit used for building the application’s user interface with a declarative approach.
- **Hilt**: Dependency injection library used for managing app dependencies.
- **Coroutines & Flow**: Used for managing asynchronous tasks, background operations, and state updates.
- **Room Database**: Local database for storing and retrieving study-related data.
- **Material Design 3**: Provides modern, responsive UI components like buttons, snackbars, and cards.

## Setup Instructions

To set up and run LearnIT locally, follow these steps:

### Prerequisites

Make sure you have the following installed:

- [Android Studio](https://developer.android.com/studio)
- Kotlin
- Gradle

### Steps to Run

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/Lashman-singh/learnit-app.git
