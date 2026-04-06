package com.loan.repayment.util;

import java.math.MathContext;
import java.math.RoundingMode;

public final class GlobalConstants {
    private GlobalConstants() {}

    public static final RoundingMode INTEREST_ROUNDING = RoundingMode.FLOOR;
    public static final RoundingMode PRINCIPAL_ROUNDING = RoundingMode.HALF_UP;

    public static final int MONTHS_IN_YEAR = 12;

    public static final int RATE_SCALE = 20;
    public static final MathContext PRECISION = new MathContext(20);

}
