package model;

/**
 * @author Manuel Adams
 * @since 2019-12-25
 */
public class JsonMessage {

    public JsonMessageType type;
    public String value;
    public boolean hasError;

    public JsonMessage() {
    }

    public JsonMessage(JsonMessageType type, String value) {
        this.type = type;
        this.value = value;
        this.hasError = false;
    }

    public JsonMessage(JsonMessageType type, String value, boolean hasError) {
        this.type = type;
        this.value = value;
        this.hasError = hasError;
    }
}
