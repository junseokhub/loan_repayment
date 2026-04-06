import type { CalculateRequestDto, RepaymentType } from "@/types";
import { REPAYMENT_TYPES } from "@/constants";
import { useLoanForm } from "@/hooks";

interface LoanFormProps {
  onSubmit: (dto: CalculateRequestDto) => void;
  loading: boolean;
  error: string;
}

export default function LoanForm({ onSubmit, loading, error }: LoanFormProps) {
  const {
    principal, month, interestRate, type,
    isValid,
    setPrincipal, setMonth, setInterestRate, setType,
    buildDto,
  } = useLoanForm();

  const inputClass = [
    "w-full bg-white/[0.03] border border-white/[0.08] rounded-2xl",
    "py-4 pl-[18px] pr-12 text-[15px] font-medium text-[#f1f5f9]",
    "tabular-nums outline-none",
    "transition-all duration-300 ease-[cubic-bezier(.22,1,.36,1)]",
    "placeholder:text-slate-700",
    "focus:border-[rgba(52,211,153,0.4)] focus:bg-white/[0.05]",
    "focus:shadow-[0_0_0_4px_rgba(52,211,153,0.08)]",
  ].join(" ");

  return (
    <div style={{ animation: "fade-up 0.7s cubic-bezier(.22,1,.36,1) both" }}>
      <div className="text-center mb-12">
        <div className="inline-flex items-center gap-2 px-4 py-1.5 rounded-full bg-[rgba(52,211,153,0.08)] border border-[rgba(52,211,153,0.15)] mb-5">
          <span className="w-1.5 h-1.5 rounded-full bg-[#34d399]" />
          <span className="text-[11px] font-bold text-[#34d399] tracking-[0.1em] uppercase">
            Loan Calculator
          </span>
        </div>
        <h2 className="text-[40px] font-black text-[#f8fafc] tracking-[-0.04em] leading-[1.15]">
          나에게 맞는
          <br />
          <span style={{
          background: 'linear-gradient(to bottom right, #34d399, #60a5fa)',
          WebkitBackgroundClip: 'text',
          WebkitTextFillColor: 'transparent',
          backgroundClip: 'text',
        }}>
          상환 계획
        </span>
          을 세워보세요
        </h2>
        <p className="text-[#64748b] text-sm mt-3 font-normal">
          대출 정보를 입력하면 월별 상환 스케줄을 바로 확인할 수 있어요.
        </p>
      </div>

      <form onSubmit={(e) => { e.preventDefault(); if (isValid) onSubmit(buildDto()); }}>
        <div className="flex flex-col gap-6">
          <div>
            <label className="block text-xs font-bold text-[#94a3b8] mb-2 ml-1 tracking-[0.04em]">
              대출 원금
            </label>
            <div className="relative">
              <input
                className={inputClass}
                type="tel"
                placeholder="5,000,000"
                value={principal}
                onChange={(e) => setPrincipal(e.target.value)}
              />
              <span className="absolute right-[18px] top-1/2 -translate-y-1/2 text-[13px] font-semibold text-[#475569] pointer-events-none">
                원
              </span>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-3.5">
            <div>
              <label className="block text-xs font-bold text-[#94a3b8] mb-2 ml-1 tracking-[0.04em]">
                대출 기간
              </label>
              <div className="relative">
                <input
                  className={inputClass}
                  type="number"
                  placeholder="12"
                  value={month}
                  onChange={(e) => setMonth(e.target.value)}
                />
                <span className="absolute right-[18px] top-1/2 -translate-y-1/2 text-[13px] font-semibold text-[#475569] pointer-events-none">
                  개월
                </span>
              </div>
            </div>
            <div>
              <label className="block text-xs font-bold text-[#94a3b8] mb-2 ml-1 tracking-[0.04em]">
                연 이자율
              </label>
              <div className="relative">
                <input
                  className={inputClass}
                  type="number"
                  step="0.1"
                  placeholder="4.5"
                  value={interestRate}
                  onChange={(e) => setInterestRate(e.target.value)}
                />
                <span className="absolute right-[18px] top-1/2 -translate-y-1/2 text-[13px] font-semibold text-[#475569] pointer-events-none">
                  %
                </span>
              </div>
            </div>
          </div>

          <div>
            <label className="block text-xs font-bold text-[#94a3b8] mb-3 ml-1 tracking-[0.04em]">
              상환 방식
            </label>
            <div className="grid grid-cols-3 gap-2.5">
              {REPAYMENT_TYPES.map((rt) => {
                const active = type === rt.value;
                return (
                  <button
                    key={rt.value}
                    type="button"
                    onClick={() => setType(rt.value as RepaymentType)}
                    className={[
                      "relative text-left rounded-[18px] px-4 py-5 cursor-pointer",
                      "flex flex-col gap-1.5 border",
                      "transition-all duration-300 ease-[cubic-bezier(.22,1,.36,1)]",
                      active
                        ? "bg-[linear-gradient(135deg,rgba(52,211,153,0.12),rgba(96,165,250,0.08))] border-[rgba(52,211,153,0.35)]"
                        : "bg-white/[0.02] border-white/[0.06] hover:border-white/[0.15]",
                    ].join(" ")}
                  >
                    <span
                      className={[
                        "text-sm font-extrabold transition-colors duration-300",
                        active ? "text-[#f1f5f9]" : "text-[#64748b]",
                      ].join(" ")}
                    >
                      {rt.label}
                    </span>
                    <span
                      className={[
                        "text-[10px] font-medium",
                        active ? "text-[#94a3b8]" : "text-[#334155]",
                      ].join(" ")}
                    >
                      {rt.desc}
                    </span>
                    {active && (
                      <span className="absolute top-3 right-3 w-2 h-2 rounded-full bg-[#34d399] shadow-[0_0_12px_rgba(52,211,153,0.6)]" />
                    )}
                  </button>
                );
              })}
            </div>
          </div>
        </div>

        {error && (
          <div className="mt-5 px-[18px] py-3.5 rounded-[14px] bg-[rgba(239,68,68,0.08)] border border-[rgba(239,68,68,0.2)] text-[#f87171] text-[13px] font-medium">
            {error}
          </div>
        )}

        <button
          type="submit"
          disabled={loading || !isValid}
          className={[
            "w-full mt-8 py-[18px] rounded-[18px] border-none",
            "text-[15px] font-extrabold tracking-[-0.01em]",
            "transition-all duration-300 ease-[cubic-bezier(.22,1,.36,1)]",
            isValid && !loading
              ? "cursor-pointer bg-gradient-to-br from-[#34d399] to-[#2dd4bf] text-[#022c22] shadow-[0_8px_32px_rgba(52,211,153,0.25)] hover:-translate-y-0.5 hover:shadow-[0_12px_40px_rgba(52,211,153,0.35)] active:translate-y-0"
              : "cursor-not-allowed bg-white/[0.04] text-[#475569]",
          ].join(" ")}
        >
          {loading ? (
            <span className="inline-flex items-center gap-2">
              <span className="inline-block w-4 h-4 border-2 border-[rgba(0,0,0,0.2)] border-t-[#022c22] rounded-full animate-spin" />
              분석 중...
            </span>
          ) : (
            "상환 리포트 생성하기 →"
          )}
        </button>
      </form>
    </div>
  );
}