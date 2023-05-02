package com.osariusz.paprysMapMod.client;

import com.osariusz.paprysMapMod.logicalMap.LogicalMap;

public class LogicalMapData {
    private static LogicalMap clientLogicalMap;

    public static void setLogicalMap(LogicalMap logicalMap){
        clientLogicalMap = logicalMap;
    }
    public static LogicalMap getLogicalMap(){
        return clientLogicalMap;
    }
}
