public class StringStats {
    private long count = 0;
    private int minLen = Integer.MAX_VALUE;
    private int maxLen = 0;

    public void add(String s) {
        int len = s.length();
        if (len < minLen) minLen = len;
        if (len > maxLen) maxLen = len;
        count++;
    }

    public long getCount() { return count; }
    public int getMinLen()  { return count > 0 ? minLen : 0; }
    public int getMaxLen()  { return count > 0 ? maxLen : 0; }
}
