import { useState } from "react";
import type { CalculateRequestDto, RepaymentType } from "@/types";
import { REPAYMENT_TYPES } from "@/constants";

const stripNonNumeric = (v: string) => v.replace(/[^0-9]/g, "");

const addCommas = (v: string): string => {
  const num = stripNonNumeric(v);
  if (!num) return "";
  return Number(num).toLocaleString("ko-KR");
};

const parseNum = (v: string): number => Number(stripNonNumeric(v));

export function useLoanForm() {
  const [principal, setPrincipalRaw] = useState("");
  const [month, setMonth] = useState("");
  const [interestRate, setInterestRate] = useState("");
  const [type, setType] = useState<RepaymentType>(REPAYMENT_TYPES[0].value);

  const setPrincipal = (v: string) => setPrincipalRaw(addCommas(v));

  const isValid =
    principal !== "" &&
    month !== "" &&
    interestRate !== "" &&
    parseNum(principal) > 0 &&
    Number(month) > 0 &&
    Number(interestRate) >= 0;

  const buildDto = (): CalculateRequestDto => ({
    principal: parseNum(principal),
    month: Number(month),
    interestRate: Number(interestRate),
    type,
  });

  return {
    principal,
    month,
    interestRate,
    type,
    isValid,
    setPrincipal,
    setMonth,
    setInterestRate,
    setType,
    buildDto,
  };
}