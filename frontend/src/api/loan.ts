import type { CalculateRequestDto, RepaymentPlan } from "@/types";


export async function fetchRepaymentPlans(
  dto: CalculateRequestDto,
): Promise<RepaymentPlan[]> {
    const baseUrl = import.meta.env.VITE_API_BASE;
  if (!baseUrl) {
    throw new Error("VITE_API_BASE is not defined in your environment variables");
  }

  const res = await fetch(`${baseUrl}/api/v1/loan`, {
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