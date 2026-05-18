# Smart Petrol Cost Calculator with BUDI MADANI Rebate

**ICT602 — Mobile Technology | Individual Assignment (20%)**

---

## Author

| Field | Details |
|-------|---------|
| Name | Danial Farhan Bin Muhammad Faisal |
| Matric No | 2025140177 |
| Course | ICT602 — Mobile Technology |

---

## About The App

A native Android application (Java/XML) that calculates petrol costs in Malaysia and applies the **BUDI MADANI** fuel subsidy rebate for eligible users.

### Features
- **Petrol Type Selection** — RON95 (RM2.05/L), RON97 (RM3.47/L), Diesel (RM3.35/L)
- **Dynamic Pricing** — auto-fills price field on petrol type selection
- **BUDI MADANI Toggle** — enables rebate calculation (RON95 only @ RM1.99/L)
- **Instant Results** — Total Cost, BUDI Rebate, and Final Payable Amount
- **Bottom Navigation** — Home (Calculator) and About screens
- **Malaysia/MADANI Theme** — Deep Blue (#010066) and Yellow (#FFCC00)

---

## Calculation Logic

```
Step 1: Total Petrol Cost = Fuel Usage × Price per Liter
Step 2: BUDI Rebate       = Fuel Usage × RM1.99  (RON95 + BUDI eligible only)
Step 3: Total Saving      = Total Petrol Cost − BUDI Rebate
```

### Sample Calculation
| Input | Value |
|-------|-------|
| Petrol Type | RON95 |
| Price per Liter | RM 4.27 |
| Fuel Usage | 40 liters |
| BUDI MADANI Eligibility | YES |

- Total Petrol Cost = 40 × RM4.27 = **RM170.80**
- BUDI Rebate = 40 × RM1.99 = **RM79.60**
- Total Saving = RM170.80 − RM79.60 = **RM91.20**

---

## Tech Stack

- **Language:** Java
- **UI:** XML Layouts
- **Architecture:** Fragment-based with Navigation Component
- **Navigation:** Bottom Navigation Bar
- **Target SDK:** 34 (Android 14)
- **Min SDK:** 26 (Android 8.0)

---

## Project Structure

```
app/
├── src/main/
│   ├── java/com/farhan/petrolcalculator/
│   │   ├── MainActivity.java
│   │   ├── HomeFragment.java
│   │   └── AboutFragment.java
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── fragment_home.xml
│   │   │   └── fragment_about.xml
│   │   ├── menu/
│   │   │   └── bottom_nav_menu.xml
│   │   ├── navigation/
│   │   │   └── mobile_navigation.xml
│   │   ├── drawable/  (icons & shapes)
│   │   └── values/
│   │       ├── strings.xml
│   │       ├── colors.xml
│   │       ├── themes.xml
│   │       └── dimens.xml
│   └── AndroidManifest.xml
```

---

## How to Run

1. Clone this repository
2. Open in **Android Studio Hedgehog** (or newer)
3. Let Gradle sync complete
4. Run on emulator or physical device (Android 8.0+)

---

© 2025 Danial Farhan Bin Muhammad Faisal. All Rights Reserved.
