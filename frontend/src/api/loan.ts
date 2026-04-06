import { API_BASE } from "../constants";
import type { CalculateRequestDto, RepaymentPlan } from "../types";

export async function fetchRepaymentPlans(
  dto: CalculateRequestDto,
): Promise<RepaymentPlan[]> {
  const res = await fetch(`${API_BASE}/api/v1/loan`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dto),
  });

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || `서버 오류 (${res.status})`);
  }

  return res.json();
}