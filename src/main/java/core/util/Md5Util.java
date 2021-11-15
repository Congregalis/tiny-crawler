package core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    private static final Md5Util md5Util = new Md5Util();

    private Md5Util() {}

    public static Md5Util getInstance() {
        return md5Util;
    }

    public String md5(String data) {
        StringBuffer sb = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(data.getBytes(StandardCharsets.UTF_8));

            // 将字节数据转换为十六进制
            for (byte b : md5) {
                sb.append(Integer.toHexString(b & 0xff));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
