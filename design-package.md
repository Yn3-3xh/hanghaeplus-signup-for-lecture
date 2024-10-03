# 패키지 설계

```text
├── HanghaeplusSignupForLectureApplication.java
├── api
│   ├── controller
│   └── dto
│       ├── request
│       └── response
├── application
│   ├── lecture
│   │   ├── domain
│   │   │   ├── model
│   │   │   └── repository
│   │   ├── dto
│   │   │   ├── request
│   │   │   └── response
│   │   ├── facade
│   │   ├── service
│   │   └── validator
│   └── user
│       ├── domain
│       │   ├── model
│       │   └── repository
│       ├── service
│       └── validator
└── infrastructure
    ├── lecture
    │   ├── entity
    │   └── repository
    │       ├── impl
    │       └── jpa
    └── user
        ├── entity
        └── repository
            ├── impl
            └── jpa
```
