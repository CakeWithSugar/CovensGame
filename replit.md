# CovensGame - Minecraft Paper Plugin

## Overview
This is a Minecraft Paper server plugin called CovensGame. It's a magic wand/casting system plugin written in Java 17 for Minecraft 1.19.4.

## Project Structure
- `src/main/java/org/cws/covensGame/` - Main plugin source code
  - `CovensGame.java` - Main plugin class
  - `BaseValues.java` - Base configuration values
  - `listener/` - Event listeners (inventory clicks, player interactions)
  - `manager/` - Game managers (wand building, casting, particles, projectiles)
- `src/main/resources/plugin.yml` - Plugin metadata
- `pom.xml` - Maven build configuration

## Build System
- **Language**: Java 17 (GraalVM 22.3)
- **Build Tool**: Maven
- **Packaging**: JAR (shaded)
- **Dependencies**: Paper API 1.19.4

## Building
Run `mvn clean package` to build the plugin. The output JAR will be in `target/covensgame-1.0.jar`.

## Usage
This plugin is designed to be installed on a Minecraft Paper server (version 1.19.4). After building, copy the JAR file to your server's `plugins/` directory.

## Note
This project was originally targeting Java 21 / Paper API 1.21, but has been downgraded to Java 17 / Paper API 1.19.4 for compatibility with the Replit environment. Some newer features (like Material.BREEZE_ROD) have been removed.

## Recent Changes
- Downgraded from Java 21 to Java 17 for Replit compatibility (January 2026)
- Updated Paper API from 1.21.11 to 1.19.4
- Fixed deprecated API usage (Particle.FIREWORK -> FIREWORKS_SPARK, ItemStack.of() -> new ItemStack())
- Removed BREEZE_ROD material reference (1.21+ only)
