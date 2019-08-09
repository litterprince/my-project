package algorithm.graph.domain.result;

import algorithm.graph.core.AbstractResult;

public class Result extends AbstractResult {
    public Result(){
        super(null);
    }

    public Result(String msg) {
        super(msg);
    }

    public void setMsg(String msg) {
        super.msg = msg;
    }

    @Override
    public String getMsg(){
        return (String) msg;
    }
}
