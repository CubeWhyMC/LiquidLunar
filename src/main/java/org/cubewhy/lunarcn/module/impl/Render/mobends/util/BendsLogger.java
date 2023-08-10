package org.cubewhy.lunarcn.module.impl.Render.mobends.util;

public enum BendsLogger {
	INFO,DEBUG,ERROR;
	
	public static void log(String argText, BendsLogger argType){
    	if(argType != BendsLogger.DEBUG){
    		System.out.println("(MO'BENDS - " + argType.name() +" ) " + argText);
    	}
    }
}
