package net.opentsdb.horizon.secrets;

import java.nio.charset.StandardCharsets;

public class EnvKeyReader extends KeyReader {

    @Override
    protected byte[] readSecret(String key) throws KeyException {
        String value = System.getenv(key);
        if(value == null) {
            throw new KeyException(String.format("EnvKey %s is not found", key));
        }
        return value.getBytes(StandardCharsets.UTF_8);
    }
}
