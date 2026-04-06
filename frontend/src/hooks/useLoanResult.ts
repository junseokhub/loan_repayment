import { useRef, useState } from "react";
import type { CalculateRequestDto, RepaymentPlan, RepaymentType } from "@/types";
import { fetchRepaymentPlans } from "@/api/loan";

export function useLoanResult() {
  const [plans, setPlans] = useState<RepaymentPlan[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [submitted, setSubmitted] = useState(false);
  const [currentType, setCurrentType] = useState<RepaymentType>("EQUAL_INSTALLMENT");
  const lastDto = useRef<CalculateRequestDto | null>(null);

  const submit = async (dto: CalculateRequestDto) => {
    setLoading(true);
    setError("");
    try {
      const result = await fetchRepaymentPlans(dto);
      setPlans(result);
      setCurrentType(dto.type);
      setSubmitted(true);
      lastDto.current = dto;
    } catch (err) {
      setError(err instanceof Error ? err.message : "요청에 실패했습니다.");
    } finally {
      setLoading(false);
    }
  };

  const switchType = async (type: RepaymentType) => {
    if (!lastDto.current || type === currentType) return;
    await submit({ ...lastDto.current, type });
  };

  const reset = () => {
    setSubmitted(false);
    setPlans([]);
    setError("");
  };

  return { plans, loading, error, submitted, currentType, submit, switchType, reset };
}