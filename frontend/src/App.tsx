import { useLoanResult } from "@/hooks";
import LoanForm from "@/components/LoanForm";
import RepaymentTable from "@/components/RepaymentTable";

export default function App() {
  const { plans, loading, error, submitted, currentType, submit, switchType, reset } =
    useLoanResult();

  return (
    <div className="relative min-h-screen overflow-hidden bg-[#0a0e17] font-sans text-white">
      <div
        className="fixed pointer-events-none rounded-full"
        style={{
          top: -200,
          right: -200,
          width: 600,
          height: 600,
          background:
            "radial-gradient(circle, rgba(52,211,153,0.06) 0%, transparent 70%)",
        }}
      />
      <div
        className="fixed pointer-events-none rounded-full"
        style={{
          bottom: -300,
          left: -200,
          width: 800,
          height: 800,
          background:
            "radial-gradient(circle, rgba(96,165,250,0.04) 0%, transparent 70%)",
        }}
      />

      <div className="relative z-[1] max-w-[640px] mx-auto px-6 pt-[60px] pb-20">
        <div className="text-center mb-12">
          <div className="inline-flex items-center gap-2.5 px-5 py-2.5 rounded-2xl bg-white/[0.03] border border-white/[0.06]">
            <span className="text-xl">🏦</span>
              <span style={{
                background: 'linear-gradient(to bottom right, #e2e8f0, #94a3b8)',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
                backgroundClip: 'text',
                fontSize: '0.875rem',
                fontWeight: 800,
                letterSpacing: '-0.02em',
              }}>
                LoanLab
              </span>
          </div>
        </div>

        {submitted ? (
          <RepaymentTable plans={plans} type={currentType} loading={loading} onSwitchType={switchType} onReset={reset} />
        ) : (
          <LoanForm onSubmit={submit} loading={loading} error={error} />
        )}
      </div>
    </div>
  );
}