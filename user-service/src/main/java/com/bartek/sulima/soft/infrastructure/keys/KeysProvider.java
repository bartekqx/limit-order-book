package com.bartek.sulima.soft.infrastructure.keys;

import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

@Component
public class KeysProvider {

    @Getter
    private final RSAPrivateKey privateKey;

    public KeysProvider(ResourceUtil resourceUtil) throws Exception {
        privateKey = readPrivateKey(resourceUtil.asString("classpath:keys/private-key.pem"));
    }

    public RSAPrivateKey readPrivateKey(String key) throws Exception {

        final String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        final byte[] encoded = Base64.decodeBase64(privateKeyPEM);

        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);

        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}
