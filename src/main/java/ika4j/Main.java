package ika4j;

public class Main {
    public static void main(String[] args) {
        Schedules schedules = new Schedules(args[0]);
        System.out.println(schedules.get現在のガチマッチ().getルール名());
        System.out.println(schedules.get次のガチマッチ().getルール名());
    }
}
