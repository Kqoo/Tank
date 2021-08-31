    //public class P443StringCompression {
    //    public static void main(String[] args) {
    //        Solution solution = new P443StringCompression().new Solution();
    //        // TO TEST
    //
    //        System.out.println(solution.compress(new char[]{'a','b','b','b','b','b','b','b','b','b','b','b','b'}));
    //        System.out.println(solution.compress2(new char[]{'a','b','b','b','b','b','b','b','b','b','b','b','b'}));
    //    }
    //
    //    //leetcode submit region begin(Prohibit modification and deletion)
    //    class Solution {
    //        public int compress(char[] chars) {
    //
    //        }
    //
    //        public int compress2(char[] chars) {
    //            int n = chars.length;
    //            int write = 0, left = 0;
    //            for (int read = 0; read < n; read++) {
    //                if (read == n - 1 || chars[read] != chars[read + 1]) {
    //                    chars[write++] = chars[read];
    //                    int num = read - left + 1;
    //                    if (num > 1) {
    //                        int anchor = write;
    //                        while (num > 0) {
    //                            chars[write++] = (char) (num % 10 + '0');
    //                            num /= 10;
    //                        }
    //                        reverse(chars, anchor, write - 1);
    //                    }
    //                    left = read + 1;
    //                }
    //            }
    //            System.out.println(chars);
    //            return write;
    //        }
    //
    //        public void reverse(char[] chars, int left, int right) {
    //            while (left < right) {
    //                char temp = chars[left];
    //                chars[left] = chars[right];
    //                chars[right] = temp;
    //                left++;
    //                right--;
    //            }
    //        }
    //
    //
    //    }
    //}