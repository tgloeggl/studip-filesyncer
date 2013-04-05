/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filesyncer;

import java.util.HashMap;

/**
 *
 * @author Till Glöggler <tgloeggl@uos.de>
 */
public class SyncerPipeline {
    private SyncerConfig configFrom;
    private SyncerConfig configTo;
     
    public SyncerPipeline(SyncerConfig config_from, SyncerConfig config_to) {
        this.configFrom = config_from;
        this.configTo   = config_to;
    }

    public SyncerConfig getConfigFrom() {
        return configFrom;
    }

    public void setConfigFrom(SyncerConfig configFrom) {
        this.configFrom = configFrom;
    }

    public SyncerConfig getConfigTo() {
        return configTo;
    }

    public void setConfigTo(SyncerConfig configTo) {
        this.configTo = configTo;
    }
}