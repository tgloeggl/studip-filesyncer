/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.IOUtils;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class LocalSyncProvider implements SyncerResource {

    private String path;
    
    public LocalSyncProvider(String path) {
        this.path = path;
    }
    
    @Override
    public List<SyncerFile> getFiles() {
        return new ArrayList<SyncerFile>();
    }

    @Override
    public SyncerFile findByPath(String path) throws FileNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            return new LocalSyncFile(file);
        } 
        
        throw new FileNotFoundException("File does not exists: " + path);
    }

    @Override
    public void putFile(SyncerFile file) {
        // trim leading slash if present
        String remote_file_path = file.getPath();
        if (remote_file_path.startsWith("/")) {
            remote_file_path = remote_file_path.substring(1, remote_file_path.length());
        }
        
        System.out.println("Trying to create: " + this.path + remote_file_path);
        try {
            // create file and file-paths
            File local_file = new File(this.path + remote_file_path);
            local_file.getParentFile().mkdirs();
            // System.out.println("[PARENT] " + local_file.getParentFile().getAbsolutePath());
            local_file.createNewFile();
            
            // create stream and copy file-contents to file
            FileOutputStream out = new FileOutputStream(local_file);
            byte[] buffer = new byte[1024];
            System.out.println("[INFO] Output-stream created");
            
            InputStream is = file.getStream();
            
            System.out.println("[INFO] Input-stream created");
            int len;

            while ((len = is.read(buffer)) != -1) {
               out.write(buffer, 0, len);
            }
        } catch (IOException ex) {
            System.out.println("[ERROR]" + ex.getMessage());
            // Logger.getLogger(LocalSyncProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean canWrite() {
        return true;
    }
    
}
