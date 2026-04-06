import type { CSSProperties } from "react";

type Variant = "neutral" | "amber" | "mint" | "blue";

interface SummaryCardProps {
  label: string;
  value: string;
  icon: string;
  variant?: Variant;
}

const palette: Record<
  Variant,
  { bg: string; border: string; text: string; accent: string; glow: string }
> = {
  neutral: {
    bg: "rgba(255,255,255,0.04)",
    border: "rgba(255,255,255,0.06)",
    text: "#e2e8f0",
    accent: "#94a3b8",
    glow: "0 20px 40px rgba(255,255,255,0.06)",
  },
  amber: {
    bg: "rgba(251,191,36,0.06)",
    border: "rgba(251,191,36,0.15)",
    text: "#fbbf24",
    accent: "#d97706",
    glow: "0 20px 40px rgba(251,191,36,0.15)",
  },
  mint: {
    bg: "rgba(52,211,153,0.06)",
    border: "rgba(52,211,153,0.15)",
    text: "#34d399",
    accent: "#059669",
    glow: "0 20px 40px rgba(52,211,153,0.15)",
  },
  blue: {
    bg: "rgba(96,165,250,0.06)",
    border: "rgba(96,165,250,0.15)",
    text: "#60a5fa",
    accent: "#3b82f6",
    glow: "0 20px 40px rgba(96,165,250,0.15)",
  },
};

export default function SummaryCard({
  label,
  value,
  icon,
  variant = "neutral",
}: SummaryCardProps) {
  const c = palette[variant];

  return (
    <div
      className="relative overflow-hidden rounded-[20px] p-[24px_20px] cursor-default transition-all duration-300 ease-[cubic-bezier(.22,1,.36,1)]"
      style={
        {
          background: c.bg,
          border: `1px solid ${c.border}`,
        } as CSSProperties
      }
      onMouseEnter={(e) => {
        e.currentTarget.style.transform = "translateY(-4px)";
        e.currentTarget.style.boxShadow = c.glow;
      }}
      onMouseLeave={(e) => {
        e.currentTarget.style.transform = "translateY(0)";
        e.currentTarget.style.boxShadow = "none";
      }}
    >
      <div
        className="text-[11px] font-bold tracking-[0.12em] uppercase mb-2"
        style={{ color: c.accent }}
      >
        {label}
      </div>
      <div
        className="text-[22px] font-extrabold tracking-[-0.02em]"
        style={{ color: c.text }}
      >
        {value}
      </div>
      <div className="absolute right-4 bottom-3 text-[32px] opacity-[0.08]">
        {icon}
      </div>
    </div>
  );
}