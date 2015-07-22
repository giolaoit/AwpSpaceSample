package net.awpspace.demoawpspace;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A tool for logging and customizing log tag.
 * 
 * @author Diep Hoang Savvycom JSC
 * 
 */
public class Logger {

	/**
	 * Global debug flag.
	 */
	public static final boolean DEBUG = true;

    public static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

	/**
	 * Prevents initialization.
	 */
	private Logger() {
		throw new AssertionError();
	}

	public static void d(Object caller, String message) {
		if (DEBUG) {
			Log.d(caller.getClass().getSimpleName(), getCallerName() + ": " + message);
		}
	}

	public static void e(Object caller, String message) {
		if (DEBUG) {
			Log.e(caller.getClass().getSimpleName(), getCallerName() + ": " + message);
		}
	}

	public static void i(Object caller, String message) {
		if (DEBUG) {
			Log.i(caller.getClass().getSimpleName(), getCallerName() + ": " + message);
		}
	}

	public static void v(Object caller, String message) {
		if (DEBUG) {
			Log.v(caller.getClass().getSimpleName(), getCallerName() + ": " + message);
		}
	}

	public static void w(Object caller, String message) {
		if (DEBUG) {
			Log.w(caller.getClass().getSimpleName(), getCallerName() + ": " + message);
		}
	}

    /**
     * Gets method name of current caller.
     *
     * @return
     */
    public static String getCallerName() {
        return getMethodName(1);
    }

    /**
     * Gets method name of the caller in depth of stack trace.
     *
     * @param depth
     * @return
     */
    public static String getMethodName(final int depth) {
        try {
            StackTraceElement element = new Throwable().getStackTrace()[depth + 2];
            return element.getMethodName();
        } catch (Exception e) {
            e(Logger.class, "Get fail: " + e.getMessage());
            return "";
        }
    }

    public static int getLineNumber(final int depth) {
        try {
            StackTraceElement element = new Throwable().getStackTrace()[depth + 2];
            return element.getLineNumber();
        } catch (Exception e) {
            e(Logger.class, "Get fail: " + e.getMessage());
            return 0;
        }
    }

    public static String getFileName(final int depth) {
        try {
            StackTraceElement element = new Throwable().getStackTrace()[depth + 2];
            return element.getFileName();
        } catch (Exception e) {
            e(Logger.class, "Get fail: " + e.getMessage());
            return "";
        }
    }

    public static void toFile(String message) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/HiSella");
        dir.mkdirs();
        File file = new File(dir, "debug.log");
        try {
            FileOutputStream f = new FileOutputStream(file, true);
            f.write((SDF.format(new Date()) + ": " + message + "\n").getBytes());
            f.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Fake object to force stack trace.
     */
    private static final Object fake = null;

    /**
     * Forces an exception to be thrown and get the stack trace.
     */
    public static void forceStackTrace() {
        try {
            fake.notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
