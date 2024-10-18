# 🌱 Green Guardian - Automated Plant Care System

**Green Guardian** is an innovative IoT-based solution that automates plant care tasks like watering and environmental monitoring. Designed for ease of use, the system ensures plants receive the right amount of care at the right time, all controlled via a user-friendly Android app. Whether you're a gardening newbie or an expert, Green Guardian helps keep your plants healthy and thriving with minimal effort.

---

## 📜 Table of Contents
- [Introduction](#introduction)
- [Motivation](#motivation)
- [Problem Statement](#problem-statement)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Technologies Used](#technologies-used)
- [Challenges and Learnings](#challenges-and-learnings)
- [Future Enhancements](#future-enhancements)
- [How to Use](#how-to-use)
- [Demo](#demo)

---

## 🌟 Introduction

**Green Guardian** is a **Smart Plant Care System** that automates plant maintenance tasks, such as monitoring soil moisture and temperature, and provides real-time data through a mobile app. This innovative system ensures that plant care is simple and hassle-free for all users.

---

## 💡 Motivation

As urban living continues to grow, maintaining indoor and outdoor plants becomes a challenge for people with busy lifestyles. Green Guardian was designed to offer:
- A smart solution for easy gardening.
- Automation to enhance efficiency and water conservation.
- A seamless connection between users and their plants via technology.

---

## 🔍 Problem Statement

Home gardening requires continuous attention and effort. Many gardeners face challenges in watering plants on time and monitoring their conditions, which can lead to poor plant health. Green Guardian simplifies these tasks by:
- Monitoring soil moisture and temperature levels.
- Automating watering processes when necessary.
- Providing real-time updates and alerts through a mobile app, making it easy for users to manage their plants.

---

## 🚀 Features

- **Automated Plant Care**: Automatically waters your plants when soil moisture drops below optimal levels.
- **Real-time Monitoring**: Tracks soil moisture and temperature, providing users with up-to-date data.
- **Data Visualization**: Connects to the cloud via ThingSpeak for data storage and visualization.
- **User-friendly Mobile App**: An Android application to monitor plant health and control settings.

---

## 🛠 System Architecture

1. **Simulation**:
   - We used **Wokwi** to create virtual IoT simulations, allowing us to test and adjust sensor interactions without physical hardware.

2. **Data Storage**:
   - **ThingSpeak** was used to collect and visualize environmental data. The platform stores data and provides insights about the plants' needs.

3. **User Interface**:
   - The Android app serves as the main control hub, enabling users to view environmental data, receive alerts, and manage their plants' health easily.

---

## ⚙️ Technologies Used

- **IoT Simulations**: Wokwi (for virtual IoT sensor simulations).
- **Cloud**: ThingSpeak (for data storage and visualization).
- **Mobile App**: Android (for real-time monitoring and interaction with the system).
- **Programming**: C++, Python, Java (for app and system development).

---

## 🧠 Challenges and Learnings

- **Data Accuracy**: There were some challenges when moving from simulations to real data, requiring careful calibration of sensor values.
- **Technical Integration**: Integrating ThingSpeak with the Android app posed some technical challenges, but we managed to link the cloud data with the user interface effectively.

---

## 🌱 Future Enhancements

- **AI-based Predictions**: Future versions could integrate AI to predict plant needs based on trends in moisture and temperature data.
- **User Customization**: Introducing customizable settings in the app for different plant species with varying care requirements.

---

## 🛠 How to Use

1. **Simulate the System**: Use the provided Wokwi simulation link to test the system in action.
2. **ThingSpeak Setup**: Create a ThingSpeak account and configure a channel to store your data.
3. **App Interaction**: Download the Android app, connect it to the ThingSpeak channel, and monitor your plant’s condition in real-time.

---

## 🎥 Demo

[Insert screenshot of the app UI or a link to a demo video here]

---
