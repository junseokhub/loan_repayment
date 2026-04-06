# 대출 상환 방식
## 폴더 구조
```
project/
├─ src/main/java/com.loan.repayment/
│  ├─ controller
│  │     └─ LoanRepaymentController.java
│  ├─ domain
│  │    ├─ dto
│  │    │   └─ CalculateRequest.java     
│  │    ├─ LoanRequest.java
│  │    ├─ Repayment.java
│  │    └─ RepaymentType.
│  ├─ service
│  │    └─ LoanRepaymentService.java
│  ├─ strategy
│  │    ├─ BulletRepaymentStrategy.java
│  │    ├─ EqualInstallmentRepaymentStrategy.java
│  │    ├─ EqualPrincipalRepaymentStrategy.java
│  │    ├─ RepaymentStrategy.java
│  │    ├─ RepaymentStrategyRegistry.java
│  │    └─ UnsupportedRepaymentTypeException.java
│  ├─ util
│  │    └─ GlobalConstants.java
├─ README.md
└─ Dockerfile
```

### Strategy
- 상황방식들 간 공통 로직이 없음. 추상 클래스로 묶을것이 없음.
- 그렇다고 상속하기엔 결합도가 높음.
- 인터페이스 기반 Bean 주입이 그대로 가능한 Strategy 선택.
- 상속보다 구성 원칙과 일치

### Registry
- 다양한 상환 방식 추가 가능성
- 확장 시 변경 범위 최소화
- 작으면 switch 방식




