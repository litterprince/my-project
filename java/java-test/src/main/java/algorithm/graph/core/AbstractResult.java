package algorithm.graph.core;

import algorithm.graph.domain.IResult;

public class AbstractResult implements IResult {
    protected Object msg;

    protected AbstractResult(Object msg) {
        this.msg = msg;
    }

    @Override
    public Object getMsg() {
        return msg;
    }
}
