# Cookbook

A modern JavaFX desktop application for managing and browsing recipes, supporting category management, recipe creation/update, image upload, and more.

## Features

- **Category Management**: Browse, add, and manage recipe categories.
- **Recipe Management**: Create, update, and view recipes with detailed instructions and ingredients.
- **Ingredient Table**: Add multiple ingredients with name, amount, and unit.
- **Image Upload & Preview**: Upload and preview recipe images, stored locally and referenced in the database.
- **Responsive UI**: Modern, adaptive layout with clear prompts and validation.
- **Form Validation**: All fields are validated with clear English error messages.
- **English-Only UI**: All labels, prompts, and messages are in English.
- **Database Integration**: Recipes, categories, and ingredients are persisted in a MySQL database.

## Tech Stack

- **Java 17+**
- **JavaFX 17+**
- **MySQL 8+**
- **Maven** (for dependency management and build)
- **FXML** (for UI layout)
- **JDBC** (for database access)

## Project Structure

```
cookbook/
├── src/
│   ├── main/
│   │   ├── java/g/
│   │   │   ├── controller/   # UI controllers (e.g., CreateViewController, UpdateViewController, etc.)
│   │   │   ├── service/      # Business logic (e.g., RecipeService, CategoryService)
│   │   │   ├── dao/          # Data access objects (e.g., RecipeDAO, CategoryDAO)
│   │   │   ├── model/        # Entity classes (e.g., Recipe, Category, Ingredient)
│   │   │   ├── dto/          # Data transfer objects (e.g., RecipeDetailResponse)
│   │   │   ├── utils/        # Utility classes (e.g., DBUtil)
│   │   │   └── App.java      # Main application entry
│   │   └── resources/g/
│   │       ├── *.fxml        # UI layout files
│   │       ├── app.css       # Stylesheet
│   │       └── Upload_Img.png# Default image resource
│   └── test/
├── DB/                       # Database schema and dump files
│   ├── *.sql
├── imgs/                     # Uploaded recipe images
├── pom.xml                   # Maven configuration
└── README.md                 # Project documentation
```

## Database Design

The application uses a MySQL database with the following main tables:

- **category**: Stores recipe categories.
- **recipe**: Stores recipes, including title, times, instructions, image path, and serve count.
- **ingredient**: Stores ingredients for each recipe (name, amount, unit).
- **category_recipe**: Many-to-many relationship between categories and recipes.

**Example Table Structure:**

```sql
CREATE TABLE category (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE recipe (
  recipe_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL UNIQUE,
  prep_time INT NOT NULL,
  cook_time INT NOT NULL,
  instruction TEXT NOT NULL,
  img_addr VARCHAR(255),
  serve INT NOT NULL
);

CREATE TABLE ingredient (
  pair_id INT AUTO_INCREMENT PRIMARY KEY,
  recipe_id INT NOT NULL,
  ingredient_name VARCHAR(100) NOT NULL,
  ingredient_amount INT NOT NULL,
  unit VARCHAR(100),
  FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) ON DELETE CASCADE
);

CREATE TABLE category_recipe (
  category_id INT NOT NULL,
  recipe_id INT NOT NULL,
  PRIMARY KEY (category_id, recipe_id),
  FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE,
  FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) ON DELETE CASCADE
);
```

> See the `DB/` folder for full SQL schema and sample data.

## How to Run

1. **Clone the repository**
2. **Configure the database**
   - Create a MySQL database (e.g., `cookbook`)
   - Import the schema from `DB/db_0621_1.sql` or the latest SQL file in `DB/`
   - Update your database connection settings in `DBUtil.java` if needed
3. **Build and run the project**
   - Using Maven:
     ```bash
     mvn clean javafx:run
     ```
   - Or run `App.java` directly from your IDE

4. **Usage**
   - On first launch, you can add categories and recipes.
   - Use the "Upload" button to add images for recipes.
   - All data is persisted in the MySQL database.

## Screenshots

- Modern, responsive UI with clear prompts and image preview.
- Ingredient table with dynamic add/remove.
- Category and recipe selection with contextual prompts.

## Customization

- **UI**: Modify FXML files in `src/main/resources/g/` and styles in `app.css`.
- **Database**: Adjust schema in `DB/` and update DAOs as needed.
- **Validation & Logic**: See controllers in `src/main/java/g/controller/`.

## License

This project is for educational and personal use.
