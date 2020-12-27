package org.chlorinepentoxide.quick;

import org.chlorinepentoxide.quick.parallelprocessing.ParallelProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;

public class StandardReader {

    private ParallelProcessor processor = new ParallelProcessor(Utility.getNumberOfCores());

    private List<File> files = new ArrayList<>();

    public StandardReader(String name) {
        files.add(new File(name));
    }

    public StandardReader(File file) {
        files.add(file);
    }

    public StandardReader(String[] filenames) {
        for(String filename:filenames) {
            files.add(new File(filename));
        }
    }

    public StandardReader(File[] files) {
        for(File file:files) {
            this.files.add(file);
        }
    }

    public Vector<String> firstVectorRead() throws IOException {
        Vector<String> vector = new Vector<>(1,1);
        BufferedReader reader = new BufferedReader(new FileReader(files.get(0)));
        while(true) {
            String st = reader.readLine();
            if(st == null) break;
            vector.addElement(st);
        }
        reader.close();
        return vector;
    }

    public String[] firstArrayRead() throws IOException {
        return Utility.toStringArray(firstVectorRead());
    }

    public String firstStringRead() throws IOException{
        return firstVectorRead().elementAt(0);
    }

    public Vector<Vector<String>> vectorReadAll() throws IOException, InterruptedException {
        Vector<Vector<String>> filesContent = new Vector<>();
        for(File fn:files) {
            Callable<Void> readFunction = () -> {
                Vector<String> output = new Vector<>(1, 1);
                BufferedReader reader = new BufferedReader(new FileReader(fn));
                while (true) {
                    String st = reader.readLine();
                    if (st == null) break;
                    output.addElement(st);
                }
                filesContent.addElement(output);
                reader.close();
                return null;
            };
            processor.newEvent(readFunction);
        }
        processor.executeAll();
        processor.flushStack();
        return filesContent;
    }

}
