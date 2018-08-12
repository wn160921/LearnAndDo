package xin.wangning.domain;

public class TestResult {
    private String username;
    private String testNumber;
    private String resultJSON;
    //0未完成    1完成了
    private String finish;

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(String testNumber) {
        this.testNumber = testNumber;
    }

    public String getResultJSON() {
        return resultJSON;
    }

    public void setResultJSON(String resultJSON) {
        this.resultJSON = resultJSON;
    }
}
