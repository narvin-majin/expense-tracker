# Expense Detective

A simple terminal-based expense tracker built with **Java**, **JDBC**, and **SQLite**.

It stores expenses in a local SQLite database file, so you do not need to install or run a separate database server.

## Features

- Add an expense
- View all expenses
- Delete an expense by ID
- View total spending
- Automatically create the database table on first run
- Keep expense data between program runs

Each expense contains an ID, description, amount, category, and date.

## Requirements

### Required

- **Java Development Kit (JDK) 17 or newer**
  - The JDK includes both `java` and `javac`.
  - This project has been tested with OpenJDK 21.
- **SQLite JDBC driver**
  - File: `sqlite-jdbc-3.50.3.0.jar`
  - The driver JAR must be in the project directory, or you must update the classpath commands to point to its location.

### Optional

- SQLite command-line tool (`sqlite3`) to inspect the database from a terminal.
- `wget` or `curl` to download the JDBC driver.

### No separate database server required

SQLite is an embedded database. The program creates `expense_tracker.db` in the current working directory when it first connects. Keep this file to preserve your expenses; deleting it removes the locally stored data.

## Project files

After setup, the directory should look similar to this:

```text
expense-detective/
├── ExpenseTracker.java
├── ExpenseTracker.class       # generated when compiled
├── sqlite-jdbc-3.50.3.0.jar
└── expense_tracker.db          # generated when the program runs
```

The `.class` and `.db` files are generated files and may not exist before the first compile/run.

## Installation

### 1. Install Java

#### Debian / Ubuntu / Linux Mint

```bash
sudo apt update
sudo apt install default-jdk
```

Optional SQLite inspection tools and download utility:

```bash
sudo apt install sqlite3 wget
```

Verify:

```bash
java -version
javac -version
```

#### Fedora

```bash
sudo dnf install java-21-openjdk-devel
```

Optional:

```bash
sudo dnf install sqlite wget
```

Verify:

```bash
java -version
javac -version
```

#### Arch Linux

```bash
sudo pacman -S jdk21-openjdk
```

Optional:

```bash
sudo pacman -S sqlite wget
```

Verify:

```bash
java -version
javac -version
```

If your distribution's package names differ, install a JDK version 17 or newer using its official package manager or the vendor's official distribution.

#### macOS

Install a JDK using Homebrew:

```bash
brew install openjdk@21
```

If Homebrew says Java needs to be linked or registered, follow the instructions printed by the installer. Then verify:

```bash
java -version
javac -version
```

If `brew` is not installed, install Homebrew using the instructions at https://brew.sh/ first, or install a JDK from a trusted vendor such as Adoptium: https://adoptium.net/.

Optional SQLite command-line tool:

```bash
brew install sqlite
```

#### Windows

Install a JDK 17 or newer, for example from Eclipse Temurin: https://adoptium.net/temurin/releases/

During installation, enable options to add Java to `PATH` or set `JAVA_HOME` if the installer offers them. Open a **new** PowerShell or Command Prompt window and verify:

```powershell
java -version
javac -version
```

If either command is not recognized, finish configuring the JDK's `bin` directory in `PATH`, then open a new terminal.

Optional SQLite command-line tool: download the Windows command-line tools from https://sqlite.org/download.html and place `sqlite3.exe` in a directory on `PATH`.

### 2. Create a project directory

Choose a directory for the project.

Linux / macOS:

```bash
mkdir -p ~/projects/expense-detective
cd ~/projects/expense-detective
```

Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force "$HOME\projects\expense-detective"
Set-Location "$HOME\projects\expense-detective"
```

Place `ExpenseTracker.java` in this directory. If you already have the source code, copy it here. Otherwise, create the file and paste the project code into it.

### 3. Download the SQLite JDBC driver

Download the Xerial SQLite JDBC driver JAR from Maven Central.

Linux / macOS (with `wget`):

```bash
wget https://repo.maven.apache.org/maven2/org/xerial/sqlite-jdbc/3.50.3.0/sqlite-jdbc-3.50.3.0.jar
```

Alternatively, with `curl`:

```bash
curl -fLO https://repo.maven.apache.org/maven2/org/xerial/sqlite-jdbc/3.50.3.0/sqlite-jdbc-3.50.3.0.jar
```

Windows PowerShell:

```powershell
Invoke-WebRequest `
  -Uri "https://repo.maven.apache.org/maven2/org/xerial/sqlite-jdbc/3.50.3.0/sqlite-jdbc-3.50.3.0.jar" `
  -OutFile "sqlite-jdbc-3.50.3.0.jar"
```

If you already have `sqlite-jdbc-3.50.3.0.jar`, copy it into the project directory rather than downloading it again.

### 4. Compile the program

Run the command from the project directory.

Linux / macOS:

```bash
javac -cp ".:sqlite-jdbc-3.50.3.0.jar" ExpenseTracker.java
```

Windows PowerShell or Command Prompt:

```powershell
javac -cp ".;sqlite-jdbc-3.50.3.0.jar" ExpenseTracker.java
```

If compilation succeeds, Java usually prints nothing and creates `ExpenseTracker.class`.

**Classpath separator:** Linux and macOS use a colon (`:`); Windows uses a semicolon (`;`).

### 5. Run the program

Linux / macOS:

```bash
java -cp ".:sqlite-jdbc-3.50.3.0.jar" ExpenseTracker
```

Windows PowerShell or Command Prompt:

```powershell
java -cp ".;sqlite-jdbc-3.50.3.0.jar" ExpenseTracker
```

The application should display the Expense Detective menu. Choose an option by entering its number.

## How to use it

1. Choose `1` to add an expense.
2. Enter its description, amount, category, and date in `YYYY-MM-DD` format.
3. Choose `2` to view saved expenses.
4. Choose `3` to delete an expense using its ID.
5. Choose `4` to display total spending.
6. Choose `0` to exit normally.

Example test expense:

- Description: `Lunch`
- Amount: `120`
- Category: `Food`
- Date: `2026-10-18`

## Where is the data stored?

The program uses this JDBC URL:

```java
jdbc:sqlite:expense_tracker.db
```

This is a **relative path**, so SQLite creates or opens `expense_tracker.db` in the program's current working directory. Run the application from the project directory to keep the database in the expected place.

The database persists when you exit and restart the application. Back up `expense_tracker.db` to back up your expenses. Do not delete it unless you intend to remove the stored data.

To inspect the database with the optional SQLite CLI, run this from the project directory:

```bash
sqlite3 expense_tracker.db "SELECT * FROM expenses;"
```

## Everyday workflow

After editing `ExpenseTracker.java`, compile it again and then run it.

Linux / macOS:

```bash
javac -cp ".:sqlite-jdbc-3.50.3.0.jar" ExpenseTracker.java
java -cp ".:sqlite-jdbc-3.50.3.0.jar" ExpenseTracker
```

Windows:

```powershell
javac -cp ".;sqlite-jdbc-3.50.3.0.jar" ExpenseTracker.java
java -cp ".;sqlite-jdbc-3.50.3.0.jar" ExpenseTracker
```

You do not need to recreate the database every time. The existing `expense_tracker.db` file is reused.

## Troubleshooting

### `javac` is not recognized / command not found

The JDK is missing or its `bin` directory is not on `PATH`. Install a JDK (not just a Java runtime), configure `PATH` if necessary, and open a new terminal.

### `package org.sqlite does not exist` or SQLite driver errors

Check that `sqlite-jdbc-3.50.3.0.jar` is in the current directory and that the classpath command uses the correct separator for your operating system.

### `Could not find or load main class ExpenseTracker`

Make sure you are in the directory containing `ExpenseTracker.class`, compile `ExpenseTracker.java`, and use `ExpenseTracker` (without `.java` or `.class`) as the main class name.

### The program creates a database in an unexpected place

The database path is relative to the current working directory. Change into the project directory before running the program.

### The database file is missing

Run the application successfully at least once. SQLite creates the database file when the application connects.

## Current scope and limitations

This is a small learning project intended to demonstrate Java, JDBC, SQL, and SQLite. It is a local terminal application, not yet a web service or mobile app. It currently has basic input handling and does not include user accounts, cloud synchronization, or remote access.

## License

No license has been specified yet. Add a `LICENSE` file before distributing the project if you want to define reuse permissions.
