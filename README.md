# Ultimate Life Simulator

A text-based Android life simulator where players can experience royalty, political careers, criminal enterprises, business tycoonery, and professional careers—all within one interconnected world engine.

## Features

- **5 Life Paths**: Royalty, Politics, Crime, Business, Career
- **Comprehensive Stats System**: 10 primary stats, 8 secondary stats
- **Skills System**: 28+ skills across 8 categories
- **30+ Traits**: Positive, negative, and neutral traits
- **Relationships**: Deep relationship system with trust, loyalty, fear
- **Health System**: Physical and mental health with injuries/illnesses
- **Inventory & Economy**: Items, assets, debts
- **Random Events**: Procedural events with meaningful choices
- **Time System**: Day/night cycles, seasons, aging

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Database**: Room
- **Navigation**: Jetpack Navigation
- **Async**: Coroutines + Flow

## Building

```bash
# Clone the repository
git clone https://github.com/yourusername/UltimateLifeSimulator.git
cd UltimateLifeSimulator

# Build debug APK
./gradlew assembleDebug

# APK location: app/build/outputs/apk/debug/app-debug.apk
```

## GitHub Actions

The project includes CI/CD via GitHub Actions. On push to main, the APK is automatically built.

## Screenshots

The app features a bottom navigation with 5 main screens:
- Character (stats, skills, traits)
- World (locations, factions)
- Relationships (family, friends, rivals)
- Health (physical, mental)
- Inventory (items, assets)

## License

MIT License
