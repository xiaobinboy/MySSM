package edu.gdpu.MySSM.test.javaseTest;

/**
 * @author mazebin
 * @date 2021年 02月27日 10:46:06
 */
public class ThreadTest {
    public static void main(String[] args) {
        TwoThread tt = new TwoThread();
        //tt.setName("A");
        /**
         * 上面一行代码的输出
         Count-Operate--begin
         Thread.currentThread().getName()= main
         this.getName()= Thread-0
         Count-Operate--end*/
        Thread t = new Thread(tt);//这里只是把tt这个对象交给t去调度，因此this获取的仍是tt这个对象，因此与currentThread有区别
        t.setName("A");
        t.start();
        /**
         * run--begin
         * Thread.currentThread().getName()=A
         * this.getName()= Thread-0
         * run--end
         */
        System.out.println("---------");
        System.out.println(t.getName());//输出A

        System.out.println("---------");
        t.run();/**
         run--begin
         Thread.currentThread().getName()=main
         this.getName()= Thread-0
         run--end
         */
        System.out.println("---------");
    }

}

class TwoThread extends Thread {
    public TwoThread() {
        System.out.println("Count-Operate--begin");
        System.out.println("Thread.currentThread().getName()= " + Thread.currentThread().getName());
        System.out.println("this.getName()= " + this.getName());
        System.out.println("Count-Operate--end");
    }

    @Override
    public void run() {
        System.out.println("run--begin");
        System.out.println("Thread.currentThread().getName()=" + Thread.currentThread().getName());
        System.out.println("this.getName()= " + this.getName());
        System.out.println("run--end");
    }
}
