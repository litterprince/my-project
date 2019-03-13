package com.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class RefTest {
    public static void main(String[] args){
        testStrong();

        testSoft();

        testWeak();

        testPhantom();
    }

    /**
     * GC不会回收
     */
    public static void testStrong(){
        Object obj = new Object();
        System.gc();
        System.out.println("strong after gc: "+obj.toString());
    }

    /**
     * GC在内存不足才会回收
     */
    public static void testSoft(){
        Object obj = new Object();
        SoftReference sf = new SoftReference<>(obj);
        obj = null;
        System.gc();
        System.out.println("soft after gc: "+sf.get());
    }

    /**
     * GC发现就回收
     */
    public static void testWeak(){
        Object obj = new Object();
        WeakReference sf = new WeakReference(obj);
        obj = null;
        System.out.println("weak before gc: "+sf.get());
        System.gc();
        System.out.println("weak after gc: "+sf.get());
    }

    /**
     * 有和没有一样，用于跟踪垃圾回收情况
     */
    public static void testPhantom(){
        Object obj = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference sf = new PhantomReference<>(obj,referenceQueue);
        obj = null;
        System.out.println("phantom before gc: "+sf.get());
        System.gc();
        System.out.println("phantom after gc: "+sf.get());
    }
}
