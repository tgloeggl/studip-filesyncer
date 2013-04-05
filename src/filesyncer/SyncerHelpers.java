/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import com.twmacinta.util.MD5;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Till Gl�ggler <tgloeggl@uos.de>
 */
public class SyncerHelpers {

    /**
     * Calculate and return an MD5 for the passed filename. Uses fast-md5 from
     * http://www.twmacinta.com/myjava/fast_md5.php
     *
     * @param String filename
     * @return String
     */
    public static String calculateMd5(File file) {

        // System.out.println("[INFO] Erstelle MD5-Hash f�r: " + file.getAbsolutePath());

        String hash = "";
        try {
             hash = MD5.asHex(MD5.getHash(file));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // System.out.println("[MD5] Berechneter MD5-Hash: " + hash);

        return hash;
    }
}
