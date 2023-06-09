package org.etl.core.core;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.etl.core.FileMonitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;

class FileMonitorApplicationTest {

    @Test
    void setUncaughtExceptionHandler() throws Exception {
        System.out.println(Thread.currentThread().getName());
        URL resource = FileMonitorApplicationTest.class.getClassLoader().getResource("testFolder");
        assert resource != null;
        File dir = new File(resource.getFile());
        FileMonitor fileMonitor = new FileMonitor(dir, pathname -> true,3000);

        fileMonitor.setFileAlterationListenerAdaptor(new FileAlterationListenerAdaptor(){
            @Override
            public void onFileCreate(File file) {
                System.out.println(Thread.currentThread().getName());

            }

            @Override
            public void onFileDelete(File file) {
                System.out.println("123");
            }

            @Override
            public void onDirectoryCreate(File directory) {
                System.out.println("123");
            }
        });
        fileMonitor.start();
        File test = new File(dir, "test.jar");
        boolean newFile = test.createNewFile();
        Assertions.assertTrue(newFile);
        test.deleteOnExit();
        Thread.sleep(10000);
    }

    @Test
    void start() {

    }
}