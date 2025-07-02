import java.math.BigDecimal;
import java.math.BigInteger;

public class NumStats<T extends Number> {
    private long count = 0;
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = null;
    private BigDecimal max = null;

    public void add(T number) {
        BigDecimal v = (number instanceof BigInteger)
                ? new BigDecimal((BigInteger) number)
                : (number instanceof BigDecimal)
                ? (BigDecimal) number
                : new BigDecimal(number.toString());

        if (count == 0) {
            min = v;
            max = v;
        } else {
            if (v.compareTo(min) < 0) min = v;
            if (v.compareTo(max) > 0) max = v;
        }
        sum = sum.add(v);
        count++;
    }

    public long getCount() { return count; }
    public BigDecimal getSum()   { return sum; }
    public BigDecimal getMin()   { return min; }
    public BigDecimal getMax()   { return max; }

    public BigDecimal getAverage() {
        return count > 0
                ? sum.divide(BigDecimal.valueOf(count), BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;
    }
}
