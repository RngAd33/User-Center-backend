package com.rngad33.usercenter.constant;

import java.io.File;

/**
 * AES常量接口
 */
public interface AESConstant {

    String ALGORITHM = "AES";

    String CONFUSION = "***********";

    String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    String KEY_FILE_PATH = System.getProperty("user.dir") + File.separator + "src/main/java/com/rngad33/usercenter/model/temp/aes_key.bin";

    String IV_FILE_PATH = System.getProperty("user.dir") + File.separator + "src/main/java/com/rngad33/usercenter/model/temp/aes_iv.bin";

}