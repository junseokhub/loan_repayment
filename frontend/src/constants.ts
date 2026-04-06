import type { RepaymentTypeMeta } from "@/types";

export const REPAYMENT_TYPES: RepaymentTypeMeta[] = [
  {
    value: "EQUAL_INSTALLMENT",
    label: "원리금균등",
    desc: "매월 동일한 금액을 납부",
  },
  {
    value: "EQUAL_PRINCIPAL",
    label: "원금균등",
    desc: "원금을 균등하게 분할 납부",
  },
  {
    value: "BULLET",
    label: "만기일시",
    desc: "만기에 원금을 일괄 상환",
  },
];

export const API_BASE = "http://localhost:8080";

export const formatKRW = (n: number): string =>
  new Intl.NumberFormat("ko-KR").format(n) + "원";

export const totalPay = (p: { principal: number; interest: number }): number =>
  Number(p.principal) + Number(p.interest);