package com.tclj;
import tcl.lang.*;
public class TclRoutines {
    public void HelloWorldTest() {
        try {
            Interp interp;
            interp = new Interp();
            interp.eval("puts {Hello World}");
            interp.dispose();
        } catch (TclException e) {
            throw new RuntimeException("Tcl exception: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unknown exception: " + e.getMessage());
        }
    }

    public void CreateUserFilesystem(String userName) {
        try {
            Interp interp;
            interp = new Interp();
            interp.eval(
                    "set username " + userName + "; " +
                            "set timestamp " + java.time.LocalDateTime.now() + "; " +
                           "source createfs.tcl"
            );
            interp.dispose();
        } catch (TclException e) {
            throw new RuntimeException("Tcl exception: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unknown exception: " + e.getMessage());
        }
    }
}
