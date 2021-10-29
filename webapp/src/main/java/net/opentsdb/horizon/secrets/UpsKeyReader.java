package net.opentsdb.horizon.secrets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.nio.charset.StandardCharsets;

public class UpsKeyReader extends KeyReader {

    final String SPLITTER = ":";
    final String VCAP_SERVICES = "VCAP_SERVICES";
    final String USER_PROVIDED = "user-provided";
    final String CREDENTIALS = "credentials";

    @Override
    protected byte[] readSecret(String key) throws KeyException {
        String[] values = key.split(SPLITTER);
        String upsName = values[0];
        String secretName = values[1];

        String secretValue = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(System.getenv(VCAP_SERVICES));
            JsonNode upsList = root.get(USER_PROVIDED);

            JsonNode targetUps = null;
            for(JsonNode ups : upsList) {
                if(ups.get("name").asText().equals(upsName)) {
                    targetUps = ups;
                    break;
                }
            }
            if(targetUps == null) {
                throw new KeyException(String.format("UPS(name=%s) is not found", upsName));
            }
            secretValue = targetUps.get(CREDENTIALS).get(secretName).asText();
        } catch (JsonProcessingException e) {
            throw new KeyException(String.format("json parse error"));
        } catch (NullPointerException e) {
            throw new KeyException(String.format("Secret(%s) is not found", secretName));
        }

        return secretValue.getBytes(StandardCharsets.UTF_8);
    }
}
