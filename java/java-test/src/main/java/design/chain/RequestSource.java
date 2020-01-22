package design.chain;

import lombok.Data;

@Data
public class RequestSource {
    private Integer header;

    public RequestSource(Integer header) {
        this.header = header;
    }
}
