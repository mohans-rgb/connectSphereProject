# ConnectSphere — Professional Networking Backend
 
A LinkedIn-inspired social networking backend built as a **microservices architecture** with 6 independently deployable Spring Boot services, deployed on Kubernetes. ConnectSphere covers the full social platform lifecycle — user auth, graph-based connections, post creation with media uploads, likes, and real-time event-driven notifications.

## Key Features
 
### Graph-Based Connections (Neo4j)
- Users are stored as `Person` nodes in a Neo4j graph database
- Relationships modeled as `REQUESTED_TO` and `CONNECTED_TO` edges
- Cypher queries handle send, accept, and reject connection requests with duplicate and self-connection guards
- First-degree connection lookups done in a single native graph traversal query
- When a user signs up, `user_created_topic` triggers automatic Person node creation in Neo4j
### Event-Driven Notifications (Apache Kafka)
- **`user_created_topic`** → Connections Service auto-creates a graph node for new users
- **`post_created_topic`** (3 partitions) → fans out to all first-degree connections of the post author
- **`post_liked_topic`** (3 partitions) → notifies the post owner when someone likes their post
- Notification Service consumes all events asynchronously — zero direct coupling between services
### API Gateway — Centralised Auth
- Custom `AuthenticationFilter` validates JWT on every protected route
- Extracts `userId` from token and injects it as `X-User-Id` header for downstream services
- Inner services never touch JWT — they read user identity from the forwarded header
- Routes: `/api/v1/users/**`, `/api/v1/posts/**`, `/api/v1/connections/**`
### Pluggable Media Uploads (Strategy Pattern)
- Uploader Service abstracts media storage behind a strategy interface
- Supports Cloudinary and Google Cloud Storage as interchangeable backends
- Posts Service calls Uploader via OpenFeign — switching storage providers requires zero changes in calling code
### Service Discovery (Eureka)
- All services register with a central Eureka Discovery Server
- API Gateway routes use `lb://SERVICE-NAME` for client-side load balancing
- K8s profiles override URLs with in-cluster service names via `application-k8s.properties`
---
 
## Tech Stack
 
| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot 3.3 |
| API Gateway | Spring Cloud Gateway |
| Service Discovery | Netflix Eureka |
| Inter-service calls | OpenFeign |
| Message Broker | Apache Kafka |
| Graph Database | Neo4j |
| Relational Database | PostgreSQL |
| Security | JWT (stateless) |
| Containerization | Docker (Jib Maven Plugin) |
| Orchestration | Kubernetes |
| Ingress | GCE Nginx Ingress Controller |
| Mapping | ModelMapper |
| Boilerplate | Lombok |
 
