# Introduction
This is a recruitment task for ALA.COM which contains a product REST API.

## Requirements
- JDK17
- Docker
- Gradle

## How to run
1. Clone the repository
2. Make sure that Docker Agent is up and running
3. Make sure that ports 5432 and 8080 are free
4. Build the project using Gradle (it will also run all tests)
For Windows:
```bash
gradlew.bat build
```
   
For Linux (or macOS)
```bash
./gradlew build
```

5. Run the application
For Windows:
```bash
gradlew.bat bootRun
```

For Linux (or macOS)
```bash
./gradlew bootRun
```

It will start the application on port 8080 and PostgreSQL on port 5432.
Database will be initialized with some test data on startup (initialization script is located in `local-dev/db` directory).

## Tests
All tests are located in `src/test/kotlin` directory.
There are unit tests (`*Test.kt`) and integration tests (`*It.kt`). Integration tests are run using Testcontainers.

## API Endpoints
All available endpoints can be tested using HTTP file located in `local-dev/http` directory.

### Get product details by ID
- **URL**: `/api/v1/products/{id}`
- **Method**: `GET`
- **URL Params**: 
  - `id=[UUID]` (required) - The ID of the product to retrieve. (e.g. `550e8400-e29b-41d4-a716-446655440000`
- **Success Response**:
  - **Code**: `200 OK`
  - **Content**: 
    ```json
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "Product 1",
      "description": "Description for Product 1",
      "price": 19.99
    }
    ```

### Get discounted product price
- **URL**: `/api/v1/products/{id}/quantity/{quantity}/price`
- **Method**: `GET`
- **URL Params**:
    - `id=[UUID]` (required) - The ID of the product to retrieve. (e.g. `550e8400-e29b-41d4-a716-446655440000`)
    - `quantity=[int]` (required) - The quantity of the product. Must be greater than 0.
- **Success Response**:
  - **Code**: `200 OK`
  - **Content**:
    ```json
    {
      "productId": "550e8400-e29b-41d4-a716-446655440000",
      "quantity": 50,
      "totalPrice": 849.58
    }
    ```

## Discount policies
There are two available discount policies in the system.
They are configurable via database.
All available discount polices are in `discount` table in the database. There is a constraint that allows to configure only
PERCENTAGE and QUANTITY discount policies.

### Percentage discount
- **Description**: A fixed percentage discount applied to the total price.
- **Example**: If the product price is $100 and the discount is 10%, the total price will be $90.
- **Configuration**: 
Percentage discount is configurable via `percentage_discount` table in the database. Discount value can be changed during
application runtime. It allows to set value from 0 to 100. By default, it is set to 10.
- **Example**: 
```sql
UPDATE percentage_discount SET percentage = 20 WHERE id = 1;
```

### Quantity discount
- **Description**: A discount applied to the total price based on the quantity of products purchased.
- **Example**: If the product price is $100 and the discount is 10% for 10 or more products, the total price will be $90 for 10 or more products.
- **Configuration**:
- Quantity discount is configurable via `quantity_discount_configs` table in the database. Discount value can be changed during
application runtime. It allows to set value from 0 to 100. By default, it is set to:
```text
1-9 products: 0% discount
10-19 products: 5% discount
20-49 products: 10% discount
50+ products: 15% discount
```
There is a trigger set on this table that prevents to overlap the ranges. It means that if you set a discount for 10-19 products, you cannot set a discount for 13-22 products.
Also, maximum number of process can be infinite only in one range. To achieve that, `max_qty` must be set to NULL.
It is not possible to set multiple ranges with infinite number of products.
Maximum number of products must be grater than minimum number of products.
Both values must be grater than 0.
- **Example**:
```sql
UPDATE quantity_discount_configs SET min_qty = 50, max_qty = 100, percentage = 30 WHERE id = 4;
```

### No discount policy
If there is an invalid configuration of discount policy, the system will not apply it and will return the full price.

## Discount interactions
The system can apply discounts in two ways:
1. **Higher discount**: The system will apply the higher discount between percentage and quantity discount.
2. **Cumulative discount**: The system will apply both discounts to the total price.

### Discount interaction configuration
- **Description**: The system can be configured to apply the higher discount or cumulative discount.
- **Configuration**:
- Discount interaction is configurable via `discount_interaction` table in the database. By default, it is set to `HIGHER_DISCOUNT`.
- It is possible to activate only one discount at a time (there is a constraint in the database).
- If any of the discount interactions are inactive, no discount will be applied.
- It is possible to change the discount interaction during application runtime.

To activate the discount interaction:
1. Disable all discount interactions:
```sql
UPDATE discount_interaction SET active = false;
```
2. Activate the desired discount interaction:
* Activate HIGHER discount:
```sql
UPDATE discount_interaction SET active = true WHERE name = 'HIGHER';
```
* Activate CUMULATIVE discount:
```sql
UPDATE discount_interaction SET active = true WHERE name = 'CUMULATIVE';
```
