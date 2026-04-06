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

/** 응답 아이템 */
export interface RepaymentPlan {
  installmentNo: number;
  principal: number;
  interest: number;
}

/** UI 메타 (상환방식 추가 시 여기만 확장) */
export interface RepaymentTypeMeta {
  value: RepaymentType;
  label: string;
  desc: string;
}