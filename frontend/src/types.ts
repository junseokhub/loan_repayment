export type RepaymentType =
  | "EQUAL_INSTALLMENT"
  | "EQUAL_PRINCIPAL"
  | "BULLET";

export interface CalculateRequestDto {
  principal: number;
  month: number;
  interestRate: number;
  type: RepaymentType;
}

export interface RepaymentPlan {
  installmentNo: number;
  principal: number;
  interest: number;
}

export interface RepaymentTypeMeta {
  value: RepaymentType;
  label: string;
  desc: string;
}