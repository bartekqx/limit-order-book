package com.bartek.sulima.soft.infrastructure;

import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

@Component
public class KeysProvider {

    @Getter
    private final RSAPublicKey publicKey;

    public KeysProvider(ResourceUtil resourceUtil) throws Exception {
        publicKey = readPublicKey(resourceUtil.asString("classpath:keys/public-key.pem"));
    }

    private RSAPublicKey readPublicKey(String key) throws Exception {
        final String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        final byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }
}
