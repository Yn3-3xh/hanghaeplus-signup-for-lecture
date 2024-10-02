# 패키지 설계

```text
└── src   
   └── main   
      └── java   
         └── hanghaeplus   
             └── signupforlecture   
                 └── lecture   
                     ├── application   
                     │   ├── dto   
                     │   └── service   
                     ├── controller   
                     ├── domain   
                     │   ├── entity   
                     │   └── repository   
                     ├── facade   
                     └── infrastructure
```

* application   
    애플리케이션의 비즈니스 로직 처리 담당

  * dto   
    클라이언트와 서버 간의 데이터 송수신 객체 정의
  
  * service   
    비즈니스 로직 구현

* controller   
  클라이언트 요청 처리 및 응답 반환

* domain   
  도메인 모델 및 핵심 비즈니스 로직 담당

  * entity   
    DB와 매핑되는 도메인 객체 정의

  * repository   
    DB 작업 수행

* facade   
  서비스 및 컴포넌트 통합

* infrastructure   
  외부 시스템과 통신
