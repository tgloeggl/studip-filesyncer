/*
 * A FileSyncer for syncing one or more local folders to different remote locations
 * ( f.e. WebDAV, SSH, ... )
 */
package filesyncer;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class FileSyncer {

    public FileSyncer() {
        File f = new File(Config.config_filename);
        if(f.exists()) {
            Config.loadConfig();
        } else {
            // Initial WebDAV configuration
            WebDAVSyncConfig webdav_config = new WebDAVSyncConfig();

            HashMap config = new HashMap<String, String>();
            config.put("base_path", "");
            config.put("dav_path", "");
            config.put("username", "");
            config.put("password", "");

            webdav_config.setConfig(config);

            // Initial Filesystem configuration
            LocalSyncConfig local_config = new LocalSyncConfig();

            HashMap config2 = new HashMap<String, String>();
            config2.put("local_path", "");

            local_config.setConfig(config2);

            SyncerPipeline pipeline = new SyncerPipeline(webdav_config, local_config);
            Config.addPipeline(pipeline);

            Config.storeConfig();
        }
    }

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
    
    private void run() {
        if (SystemTray.isSupported()) {
            final SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("reload.png");
            PopupMenu popup = new PopupMenu();

            final TrayIcon trayIcon = new TrayIcon(image, "FileSyncer Version 0.0.1", popup);
            trayIcon.setImageAutoSize(true);

            // Für jede Pipeline einen eigenen Eintrag
            
            ArrayList pipelines = Config.getPipelines();
            for (Object obj : pipelines) {
                final SyncerPipeline pl = (SyncerPipeline)obj;

                String from = pl.getConfigFrom().getClass().toString().split("\\.")[1].replaceAll("SyncConfig", "");
                String to   = pl.getConfigTo().getClass().toString().split("\\.")[1].replaceAll("SyncConfig", "");

                MenuItem entry = new MenuItem(from + " -> " + to);
                entry.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("[SYNC INITIATED] Starting sync, configuration details follow");
                        System.out.println(pl.getConfigFrom().getConfig());
                        System.out.println(pl.getConfigTo().getConfig());

                        System.out.println("*** >>> SYNCING FILES ***");
                        Syncer syncer = new Syncer(pl.getConfigFrom().getSyncProvider(), pl.getConfigTo().getSyncProvider());
                        syncer.sync();

                        System.out.println("*** <<< SYNCING FILES ***");
                    }
                });
                popup.add(entry);
            }


            // add close menu-item
            MenuItem config_item = new MenuItem("Konfiguration");
            config_item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // new MainFrame().setVisible(true);

                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new MainFrame().setVisible(true);
                        }
                    });
                }
            });
            popup.add(config_item);

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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final FileSyncer f = new FileSyncer();
        Runnable runner = new Runnable() {
            public void run() {
                f.run();
            }
        };
        EventQueue.invokeLater(runner);
    }
}