package net.opentsdb.horizon.secrets;

import java.util.Map;

public class UpsKeyReaderFactory implements KeyReaderFactory<UpsKeyReader> {
    @Override
    public UpsKeyReader createKeyReader(Map<String, Object> config) {
        return new UpsKeyReader();
    }
}
