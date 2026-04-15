# Loan Repayment

## Language & Framework
Spring Boot 4.0.5, Java 21, Gradle (kts)
## Directory Structure
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

### Enum translations
```
EQUAL_INSTALLMENT("Equal Installment Repayment")
BULLET("Bullet Repayment")
EQUAL_PRINCIPAL("Equal Principal Repayment")
```

If you want it a bit more formal (e.g., for documentation or a resume):
Implemented the Spring Registry Strategy Pattern to support future extensibility and scalability.





