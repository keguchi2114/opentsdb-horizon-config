package net.opentsdb.horizon.secrets;

import java.util.Map;

public class EnvKeyReaderFactory implements KeyReaderFactory<EnvKeyReader> {
    @Override
    public EnvKeyReader createKeyReader(Map<String, Object> config) {
        return new EnvKeyReader();
    }
}
