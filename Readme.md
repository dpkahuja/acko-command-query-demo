# Acko Command Query Separation Demo

## Context and Problem:

- Traditional CRUD architectures use the same data model for both reading and updating a database.
- Complexity arises in more advanced applications where read and write representations differ significantly.
- Read and write workloads often have asymmetrical requirements in terms of performance and scalability.
- Data contention, complex queries, and security challenges may occur in traditional CRUD architectures.

## Solution

In this demo project:

- **DBContext Annotation** is created to have a dynamic data source for each repositories which makes it possible to
  change it.
- Here, We are using same db for read and write (with two different replicas). To showcase that JPARepository has been
  kept differently.
- **Commands** are used for updating data, representing task-based operations. For example "create kyc request", and not
  set kyc to INITIATED. Note: it is possible for commands to make read operations as long as it will help the operation
  to complete
- **Queries** are employed for reading data, returning Data Transfer Objects (DTOs) without domain knowledge.

## Implementation

The demo project is implemented in Java, showcasing:

- Project structure.
- Custom Datasource router with an Annotation to be used at controller level
- Query handlers for retrieving data without modifying the database.
- Use of command and query models
- Independent scaling of read and write workloads.

Note: We are not using "Event sourcing" otherwise that can be used to sync read and write dbs.

## Running the Demo

To run the demo project, follow these steps:

1. Clone the repository: `git clone <repository_url>`
2. Navigate to the project directory: `cd <project_directory>`
3. Run the application: `mvn spring-boot:run -Drun.arguments="spring.profiles.active=local"`

The application will start, and you can explore the endpoints for commands and queries.

## API Reference

#### Get all employees from master

```http
  GET localhost:8081/employee/sync
```

#### Get all employees (slim) from master

```http
  GET localhost:8081/employee/sync/slim
```

#### Get all employees from slave

```http
  GET localhost:8081/employee/async
```

#### Create employee

```http
  POST localhost:8081/employee/
```

| Parameter                                               | Type   | Description  |
|:--------------------------------------------------------|:-------|:-------------|
| ` {"employeeId": 4, "name": "Michael Johnson{master}"}` | `json` | **Required** |

## Benefits

- **Independent Scaling:** Scale read and write workloads independently to meet specific performance requirements.
- **Optimized Data Schemas:** Tailor data schemas for queries and updates, improving overall system efficiency.
- **Security:** Ensure controlled access to data by separating read and write operations.
- **Separation of Concerns:** Simplify design by isolating read and write models.

## Considerations

- **Complexity:** CQRS introduces complexity, especially when combined with the Event Sourcing pattern.
- **Messaging:** Consider using messaging for processing commands and publishing update events.
- **Eventual Consistency:** Keep in mind that eventual consistency may result from separating read and write databases.

## When to Use CQRS

Consider using CQRS in scenarios such as:

- Collaborative domains with parallel data access.
- Task-based user interfaces with complex processes.
- Systems with asymmetrical read and write workloads.
- Evolving systems where business rules change regularly.
- Integration scenarios, especially when combined with Event Sourcing.

## When Not to Use CQRS

Avoid using CQRS in scenarios with:

- Simple domains or straightforward business rules.
- CRUD-style applications where traditional read and write operations suffice.

## Conclusion

Explore the power of CQRS in Java with this demo project. Gain insights into building scalable, efficient, and
maintainable systems by separating command and query responsibilities. Happy coding!
