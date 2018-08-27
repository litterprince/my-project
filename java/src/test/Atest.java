package test;

public class Atest {
    public static void main(String[] args) {
        System.out.println(ConfigNodeStatus.PUBLISH.getStatus());
        System.out.println(ConfigNodeStatus.WAIT.getStatus());
    }

    public enum ConfigNodeStatus {
        PUBLISH(1), WAIT(0);

        private int status;

        ConfigNodeStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }
}
