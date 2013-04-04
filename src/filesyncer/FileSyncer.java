/*
 * A FileSyncer for syncing one or more local folders to different remote locations
 * ( f.e. WebDAV, SSH, ... )
 */
package filesyncer;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class FileSyncer {

    static class ShowMessageListener implements ActionListener {

        TrayIcon trayIcon;
        String title;
        String message;
        TrayIcon.MessageType messageType;

        ShowMessageListener(TrayIcon trayIcon, String title, String message, TrayIcon.MessageType messageType) {
            this.trayIcon = trayIcon;
            this.title = title;
            this.message = message;
            this.messageType = messageType;
        }

        public void actionPerformed(ActionEvent e) {
            trayIcon.displayMessage(title, message, messageType);
        }
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runnable runner = new Runnable() {
            public void run() {
                if (SystemTray.isSupported()) {
                    final SystemTray tray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().getImage("reload.png");
                    PopupMenu popup = new PopupMenu();
                    
                    final TrayIcon trayIcon = new TrayIcon(image, "FileSyncer Version 0.0.1", popup);
                    trayIcon.setImageAutoSize(true);

                    // Einstellungen
                    MenuItem settings = new MenuItem("WebDAV syncen");
                    settings.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Starting syncer...");

                            String base_path = "url_to_webdav_server";
                            String dav_path   = "path_to_dav_resource";
                            
                            WebDAVSyncProvider webdav = new WebDAVSyncProvider(base_path, dav_path, "username", "password");
                            
                            /*
                            System.out.println("*** >>> RECEIVED FILES FROM SERVER ***");
                            for (SyncerFile f: webdav.getFiles()) {
                                System.out.println(f.getPath());
                            }
                            
                            System.out.println("*** <<< RECEIVED FILES FROM SERVER ***");
                            */
                            
                            
                            System.out.println("*** >>> SYNCING FILES ***");
                            Syncer syncer = new Syncer(new LocalSyncProvider("path_to_local_directory"), webdav);
                            syncer.sync();
                            
                            System.out.println("*** <<< SYNCING FILES ***");
                            
                        }
                    });
                    popup.add(settings);

                    
                    // add close menu-item
                    MenuItem item = new MenuItem("Close");
                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            tray.remove(trayIcon);
                        }
                    });
                    popup.add(item);
                    
                    // add tray icon
                    try {
                        tray.add(trayIcon);
                    } catch (AWTException e) {
                        System.err.println("Can't add to tray");
                    }
                } else {
                    System.err.println("Tray unavailable");
                }
            }
        };
        EventQueue.invokeLater(runner);
    }
}