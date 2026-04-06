import type { RepaymentPlan, RepaymentType } from "@/types";
import { REPAYMENT_TYPES, formatKRW, totalPay } from "@/constants";
import SummaryCard from "@/components/SummaryCard";

interface RepaymentTableProps {
  plans: RepaymentPlan[];
  type: RepaymentType;
  loading: boolean;
  onSwitchType: (type: RepaymentType) => void;
  onReset: () => void;
}

export default function RepaymentTable({ plans, type, loading, onSwitchType, onReset }: RepaymentTableProps) {
  const totalInterest = plans.reduce((s, p) => s + Number(p.interest), 0);
  const totalPrincipal = plans.reduce((s, p) => s + Number(p.principal), 0);
  const grandTotal = totalInterest + totalPrincipal;
  const typeLabel = REPAYMENT_TYPES.find((t) => t.value === type)?.label ?? type;
  const maxPayment = Math.max(...plans.map((p) => totalPay(p)));

  const interestRatio = ((totalInterest / totalPrincipal) * 100).toFixed(1);
  const firstPayment = plans.length > 0 ? totalPay(plans[0]) : 0;
  const lastPayment = plans.length > 0 ? totalPay(plans[plans.length - 1]) : 0;

  return (
    <div style={{ animation: "fade-up 0.8s cubic-bezier(.22,1,.36,1) both" }}>
      <div className="flex flex-col md:flex-row md:items-end justify-between gap-4 border-b border-white/[0.05] pb-8 mb-10">
        <div>
          <div
            className="inline-block px-3.5 py-[5px] rounded-full text-[10px] font-extrabold tracking-[0.15em] uppercase text-[#34d399]"
            style={{
              background: "linear-gradient(135deg, rgba(52,211,153,0.15), rgba(96,165,250,0.1))",
              border: "1px solid rgba(52,211,153,0.2)",
            }}
          >
            상환 분석 완료
          </div>
          <h2 className="text-4xl font-black text-[#f8fafc] mt-3 tracking-[-0.03em] leading-[1.1]">
            {typeLabel}
            <span className="text-[#475569]"> 상환 계획</span>
          </h2>
        </div>
        <button
          onClick={onReset}
          className="px-6 py-2.5 rounded-[14px] bg-white/[0.04] border border-white/[0.08] text-[13px] font-semibold text-[#94a3b8] cursor-pointer transition-all duration-200 hover:bg-white/[0.08] hover:text-[#e2e8f0]"
        >
          ← 조건 변경
        </button>
      </div>

      <div className="flex gap-2 mb-8">
        {REPAYMENT_TYPES.map((rt) => {
          const active = type === rt.value;
          return (
            <button
              key={rt.value}
              onClick={() => onSwitchType(rt.value)}
              disabled={loading}
              className={[
                "px-4 py-2 rounded-xl text-[13px] font-bold cursor-pointer",
                "border transition-all duration-300 ease-[cubic-bezier(.22,1,.36,1)]",
                active
                  ? "bg-[rgba(52,211,153,0.12)] border-[rgba(52,211,153,0.3)] text-[#34d399]"
                  : "bg-white/[0.02] border-white/[0.06] text-[#64748b] hover:border-white/[0.15] hover:text-[#94a3b8]",
                loading ? "opacity-50 cursor-wait" : "",
              ].join(" ")}
            >
              {rt.label}
            </button>
          );
        })}
      </div>

      <div className="grid grid-cols-[repeat(auto-fit,minmax(180px,1fr))] gap-3.5 mb-10">
        <SummaryCard label="대출 원금" value={formatKRW(totalPrincipal)} icon="💰" variant="neutral" />
        <SummaryCard label="총 이자" value={formatKRW(totalInterest)} icon="📊" variant="amber" />
        <SummaryCard label="상환 합계" value={formatKRW(grandTotal)} icon="🏦" variant="mint" />
        <SummaryCard label="월 평균" value={formatKRW(Math.round(grandTotal / plans.length))} icon="📅" variant="blue" />
      </div>

      <div className="flex flex-wrap gap-2 mb-7">
        <span className="inline-flex items-center gap-1.5 px-3.5 py-1.5 rounded-full text-xs font-semibold bg-white/[0.03] border border-white/[0.06] text-[#94a3b8]">
          이자 비율 <em className="not-italic font-extrabold text-[#fbbf24]">{interestRatio}%</em>
        </span>
        <span className="inline-flex items-center gap-1.5 px-3.5 py-1.5 rounded-full text-xs font-semibold bg-white/[0.03] border border-white/[0.06] text-[#94a3b8]">
          첫 달 납입 <em className="not-italic font-extrabold text-[#34d399]">{formatKRW(firstPayment)}</em>
        </span>
        {firstPayment !== lastPayment && (
          <span className="inline-flex items-center gap-1.5 px-3.5 py-1.5 rounded-full text-xs font-semibold bg-white/[0.03] border border-white/[0.06] text-[#94a3b8]">
            마지막 달 <em className="not-italic font-extrabold text-[#60a5fa]">{formatKRW(lastPayment)}</em>
          </span>
        )}
        <span className="inline-flex items-center gap-1.5 px-3.5 py-1.5 rounded-full text-xs font-semibold bg-white/[0.03] border border-white/[0.06] text-[#94a3b8]">
          총 <em className="not-italic font-extrabold text-[#e2e8f0]">{plans.length}회</em> 상환
        </span>
      </div>

      <div className="bg-white/[0.02] border border-white/[0.06] rounded-[20px] overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full border-collapse text-[13px]">
            <thead>
              <tr className="bg-white/[0.03]">
                {["회차", "상환 원금", "이자", "월 납입금", ""].map((h, i) => (
                  <th
                    key={i}
                    className={[
                      "px-5 py-4 font-bold text-[11px] tracking-[0.08em] uppercase text-[#64748b] border-b border-white/[0.06]",
                      i === 0 ? "text-left" : "text-right",
                      i === 4 ? "w-[120px]" : "",
                    ].join(" ")}
                  >
                    {h}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {plans.map((p, idx) => {
                const pct = (totalPay(p) / maxPayment) * 100;
                return (
                  <tr
                    key={p.installmentNo}
                    className="border-b border-white/[0.03] transition-colors duration-150 hover:bg-white/[0.03]"
                    style={{
                      animation: `row-reveal 0.4s cubic-bezier(.22,1,.36,1) ${idx * 20}ms both`,
                    }}
                  >
                    <td className="px-5 py-3.5 text-[#475569] tabular-nums font-semibold">
                      {String(p.installmentNo).padStart(2, "0")}
                    </td>
                    <td className="px-5 py-3.5 text-right text-[#cbd5e1] font-medium tabular-nums whitespace-nowrap">
                      {formatKRW(p.principal)}
                    </td>
                    <td className="px-5 py-3.5 text-right text-[#fbbf24] font-medium tabular-nums opacity-80 whitespace-nowrap">
                      {formatKRW(p.interest)}
                    </td>
                    <td className="px-5 py-3.5 text-right text-[#34d399] font-bold tabular-nums whitespace-nowrap">
                      {formatKRW(totalPay(p))}
                    </td>
                    <td className="px-5 py-3.5 w-[120px]">
                      <div className="h-1 rounded bg-white/[0.04] overflow-hidden">
                        <div
                          className="h-full rounded bg-gradient-to-r from-[#34d399] to-[#60a5fa] transition-[width] duration-[600ms] ease-[cubic-bezier(.22,1,.36,1)]"
                          style={{ width: `${pct}%` }}
                        />
                      </div>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}