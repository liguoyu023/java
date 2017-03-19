package liguoyu.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * Created by liguoyu@58.com on 2016/7/11.
 */
public class SoftReferenceTest {
    public static void main(String[] args) {
        Object object = new Object();
        ReferenceQueue queue = new ReferenceQueue();
        SoftReference aSoftReference = new SoftReference(object,queue);

        SoftReference ref = null;
        while ((ref = (SoftReference) queue.poll()) != null) {
            // 清除ref
            ref = null;
        }

    }
}
