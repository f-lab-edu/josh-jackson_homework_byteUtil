package kr.co.study;

import java.util.Arrays;

public final class Bytes {

    private Bytes() {}

    public static void main(String[] args) {

        // toLong
        byte[] input = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00};
        long result = Bytes.toLong(input);
        System.out.println(result); // 출력: 256

        // toShort
        byte[] input2 = new byte[]{0x01, 0x02};
        short result2 = Bytes.toShort(input2);
        System.out.println(result2);

        // fromLong
        long value = 123456789L;
        byte[] result3 = Bytes.fromLong(value);
        System.out.println(Arrays.toString(result3));
        // 출력 : [0, 0, 0, 0, 7, 91, -51, 21]

        byte[] result4 = Bytes.fromLong(value, 4);
        System.out.println(Arrays.toString(result4));
        // 출력: [0, 0, 0, 0]

        byte[] a = new byte[]{0x01, 0x02};
        byte[] b = new byte[]{0x01, 0x03};
        int cmp = Bytes.compare(a, b);
        System.out.println(cmp);

    }

    private static int compare(byte[] a, byte[] b) {

        checkArrayLengthEqual(a, b);
        for (int i = 0; i < a.length; i++) {
            int aValue = a[i] & 0xFF;
            int bValue = b[i] & 0xFF;
            if (aValue == bValue) {
                continue;
            }
            if (aValue < bValue) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

    private static void checkArrayLengthEqual(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("두 배열의 길이가 같아야 합니다.");
        }
    }

    private static byte[] fromLong(long value, int arrayLength) {
        checkArrayLength1to8(arrayLength);

        byte[] eightByteValueFromLong = fromLong(value);
        byte[] result = new byte[arrayLength];
        System.arraycopy(eightByteValueFromLong, 0, result, 0, arrayLength);

        return result;
    }

    private static void checkArrayLength1to8(int arrayLength) {
        if (arrayLength < 1 || arrayLength > 8) {
            throw new IllegalArgumentException("배열의 길이는 1 ~ 8 사이여야 합니다.");
        }
    }

    private static byte[] fromLong(long value) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (value & 0xFF);
            value >>= 8;
        }

        return result;
    }

    private static long toLong(byte[] bytes) {

        checkArrayLengthIs8(bytes);
        long result = 0;
        for (byte b : bytes) {
            result <<= 8;
            result |= (b & 0xFF);
        }
        return result;
    }

    private static short toShort(byte[] bytes) {
        checkArrayLengthIs2(bytes);
        int result = 0;
        for (byte b : bytes) {
            result <<= 8;
            result |= (b & 0xFF);
        }
        return (short) result;
    }

    private static void checkArrayLengthIs8(byte[] bytes) {
        checkNotNull(bytes);
        if (bytes.length != 8) {
            throw new IllegalArgumentException("배열의 크기가 8이어야 합니다.");
        }
    }

    private static void checkArrayLengthIs2(byte[] bytes) {
        checkNotNull(bytes);
        if (bytes.length != 2) {
            throw new IllegalArgumentException("배열의 크기가 2이어야 합니다.");
        }
    }

    private static void checkNotNull(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("배열을 아무 값도 참조하고 있지 않습니다.");
        }
    }
}
