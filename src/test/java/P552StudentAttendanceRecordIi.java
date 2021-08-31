
//Java：学生出勤记录 II
public class P552StudentAttendanceRecordIi {
    public static void main(String[] args) {
//        Solution solution = new P552StudentAttendanceRecordIi().new Solution();
//        // TO TEST
//        for (int i = 0; i < 100; i++) {
//
//            int r1 = solution.checkRecord(i);
//            System.out.println("mine:" + r1);
//            int r2 = solution.checkRecord1(i);
//            System.out.println("right:" + r2);
//            assert r1 == r2;
//        }

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int checkRecord(int n) {
            return process("", 0, n);
        }

        private int process(String s, int index, int n) {
//            System.out.println(s);
            if (index >= n) {
                return checkRecord(s) ? 1 : 0;
            }
            return process(s + "P", index + 1, n) +
                    process(s + "L", index + 1, n) +
                    process(s + "A", index + 1, n);
        }

        private boolean checkRecord(String s) {
            int absentCount = 0;
            int lateCount = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == 'A') {
                    lateCount = 0;
                    absentCount++;
                    if (absentCount >= 2) {
                        return false;
                    }
                } else if (c == 'L') {
                    lateCount++;
                    if (lateCount >= 3) {
                        return false;
                    }
                } else {
                    lateCount = 0;
                }
            }
            return true;
        }

        public int checkRecord1(int n) {
            final int MOD = 1000000007;
            int[][][] dp = new int[n + 1][2][3]; // 长度，A 的数量，结尾连续 L 的数量
            dp[0][0][0] = 1;
            for (int i = 1; i <= n; i++) {
                // 以 P 结尾的数量
                for (int j = 0; j <= 1; j++) {
                    for (int k = 0; k <= 2; k++) {
                        dp[i][j][0] = (dp[i][j][0] + dp[i - 1][j][k]) % MOD;
                    }
                }
                // 以 A 结尾的数量
                for (int k = 0; k <= 2; k++) {
                    dp[i][1][0] = (dp[i][1][0] + dp[i - 1][0][k]) % MOD;
                }
                // 以 L 结尾的数量
                for (int j = 0; j <= 1; j++) {
                    for (int k = 1; k <= 2; k++) {
                        dp[i][j][k] = (dp[i][j][k] + dp[i - 1][j][k - 1]) % MOD;
                    }
                }
            }
            int sum = 0;
            for (int j = 0; j <= 1; j++) {
                for (int k = 0; k <= 2; k++) {
                    sum = (sum + dp[n][j][k]) % MOD;
                }
            }
            return sum;
        }

    }
//leetcode submit region end(Prohibit modification and deletion)

}